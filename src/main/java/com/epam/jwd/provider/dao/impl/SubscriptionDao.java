package com.epam.jwd.provider.dao.impl;

import com.epam.jwd.provider.dao.CommonDao;
import com.epam.jwd.provider.model.entity.Subscription;
import com.epam.jwd.provider.model.entity.SubscriptionStatus;
import com.epam.jwd.provider.pool.ConnectionPool;
import com.epam.jwd.provider.pool.impl.ProviderConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public enum SubscriptionDao implements CommonDao<Subscription> {
    INSTANCE;

    private static final String FIND_ALL_SUBSCRIPTIONS_SQL = "SELECT id, user_id, tariff_id, start_date, end_date, " +
            "cost, tariff_name, tariff_description, address_id, status FROM subscriptions INNER JOIN " +
            "subscription_status ON subscriptions.status_id=subscription_status.status_id";
    private static final String CREATE_SUBSCRIPTION_SQL = "INSERT INTO subscriptions (user_id, tariff_id, " +
            "start_date, end_date, cost, tariff_name, tariff_description, address_id, status_id) " +
            "VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String DELETE_SUBSCRIPTION_SQL = "DELETE FROM subscriptions WHERE id=?";
    private static final String UPDATE_SUBSCRIPTION_SQL = "UPDATE subscriptions SET user_id=?, tariff_id=?, " +
            "start_date=?, end_date=?, cost=?, tariff_name=?, tariff_description=?, address_id=?, status_id=? " +
            "WHERE id=?";
    private static final String FIND_SUBSCRIPTIONS_BY_USER_ID_SQL = "SELECT id, user_id, tariff_id, start_date, " +
            "end_date, cost, tariff_name, tariff_description, address_id, status FROM subscriptions INNER JOIN " +
            "subscription_status ON subscriptions.status_id=subscription_status.status_id WHERE user_id=?";
    private static final String FIND_SUBSCRIPTION_BY_ID_SQL = "SELECT id, user_id, tariff_id, start_date, end_date, " +
            "cost, tariff_name, tariff_description, address_id, status FROM subscriptions INNER JOIN " +
            "subscription_status ON subscriptions.status_id=subscription_status.status_id WHERE id=?";
    private static final ConnectionPool connectionPool = ProviderConnectionPool.getInstance();
    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionDao.class);

    @Override
    public void create(Subscription entity) {
        try (final Connection conn = connectionPool.takeConnection();
             final PreparedStatement statement = conn.prepareStatement(CREATE_SUBSCRIPTION_SQL)) {
            fillStatement(entity, statement);
            statement.executeUpdate();
        } catch (InterruptedException | SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public Optional<List<Subscription>> readAll() {
        try (final Connection conn = connectionPool.takeConnection();
             final PreparedStatement statement = conn.prepareStatement(FIND_ALL_SUBSCRIPTIONS_SQL)) {
            return fetchSubscriptionsList(statement);
        } catch (InterruptedException | SQLException e) {
            LOGGER.error(e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<Subscription> findById(Subscription entity) {
        try (final Connection conn = connectionPool.takeConnection();
             final PreparedStatement statement = conn.prepareStatement(FIND_SUBSCRIPTION_BY_ID_SQL)) {
            statement.setInt(1, entity.getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
               return extractSubscription(resultSet);
            }
        } catch (InterruptedException | SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return Optional.empty();
    }

    public Optional<List<Subscription>> findUserSubscriptions(Integer userId) {
        try (final Connection conn = connectionPool.takeConnection();
             final PreparedStatement statement = conn.prepareStatement(FIND_SUBSCRIPTIONS_BY_USER_ID_SQL)) {
            statement.setInt(1, userId);
            return fetchSubscriptionsList(statement);
        } catch (InterruptedException | SQLException e) {
            LOGGER.error(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Subscription> update(Subscription entity) {
        Optional<Subscription> subscription = findById(entity);
        try (final Connection conn = connectionPool.takeConnection();
             final PreparedStatement statement = conn.prepareStatement(UPDATE_SUBSCRIPTION_SQL)) {
            fillStatement(entity, statement);
            statement.setInt(10, entity.getId());
            statement.executeUpdate();
        } catch (InterruptedException | SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return subscription;
    }

    private void fillStatement(Subscription entity, PreparedStatement statement) throws SQLException {
        statement.setInt(1, entity.getUserId());
        statement.setInt(2, entity.getTariffId());
        statement.setDate(3, java.sql.Date.valueOf(entity.getStartDate()));
        statement.setDate(4, java.sql.Date.valueOf(entity.getEndDate()));
        statement.setBigDecimal(5, entity.getPrice());
        statement.setString(6, entity.getTariffName());
        statement.setString(7, entity.getTariffDescription());
        statement.setInt(8, entity.getAddressId());
        statement.setInt(9, entity.getStatus().getId());
    }

    @Override
    public void delete(Subscription entity) {
        try (final Connection conn = connectionPool.takeConnection();
             final PreparedStatement statement = conn.prepareStatement(DELETE_SUBSCRIPTION_SQL)) {
            statement.setInt(1, entity.getId());
            statement.executeUpdate();
        } catch (InterruptedException | SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private Optional<List<Subscription>> fetchSubscriptionsList(PreparedStatement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery();
        List<Subscription> subscriptions = new ArrayList<>();
        while (resultSet.next()) {
            Optional<Subscription> subscription = extractSubscription(resultSet);
            subscription.ifPresent(subscriptions::add);
        }
        return Optional.of(subscriptions);
    }

    private Optional<Subscription> extractSubscription(ResultSet resultSet) throws SQLException {
        return Optional.of(Subscription.builder()
                .withId(resultSet.getInt("id"))
                .withUserId(resultSet.getInt("user_id"))
                .withTariffId(resultSet.getInt("tariff_id"))
                .withStartDate(asLocalDate(resultSet.getDate("start_date")))
                .withEndDate(asLocalDate(resultSet.getDate("end_date")))
                .withPrice(resultSet.getBigDecimal("cost"))
                .withTariffName(resultSet.getString("tariff_name"))
                .withTariffDescription(resultSet.getString("tariff_description"))
                .withAddressId(resultSet.getInt("address_id"))
                .withStatus(SubscriptionStatus.of(resultSet.getString("status")))
                .build());
    }

    private LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
