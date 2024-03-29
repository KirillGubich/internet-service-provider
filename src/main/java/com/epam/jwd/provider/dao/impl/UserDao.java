package com.epam.jwd.provider.dao.impl;

import com.epam.jwd.provider.dao.CommonDao;
import com.epam.jwd.provider.exception.PropertiesAbsenceException;
import com.epam.jwd.provider.model.entity.User;
import com.epam.jwd.provider.model.entity.UserRole;
import com.epam.jwd.provider.pool.ConnectionPool;
import com.epam.jwd.provider.pool.impl.ProviderConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao implements CommonDao<User> {

    private static final String FIND_ACCOUNT_BY_LOGIN_SQL = "SELECT id, login, password, balance, active FROM accounts " +
            "LEFT JOIN users ON accounts.id=users.account_id where login=?";
    private static final String FIND_ACCOUNT_BY_ID_SQL = "SELECT id, login, password, balance, active FROM accounts " +
            "LEFT JOIN users ON accounts.id=users.account_id where id=?";
    private static final String CREATE_ACCOUNT_SQL = "INSERT INTO accounts (login, password) VALUES (?,?)";
    private static final String CREATE_USER_SQL = "INSERT INTO users (account_id, balance, active) VALUES (?,?,?)";
    private static final String FIND_ALL_ACCOUNTS_SQL = "SELECT id, login, password, balance, active FROM accounts " +
            "LEFT JOIN users ON accounts.id=users.account_id";
    private static final String FIND_ACCOUNT_ID_SQL = "SELECT id FROM accounts WHERE login=?";
    private static final String DELETE_USER_BY_ID_SQL = "DELETE FROM users WHERE account_id=?";
    private static final String DELETE_ACCOUNT_BY_ID_SQL = "DELETE FROM accounts WHERE id=?";
    private static final String UPDATE_ACCOUNT_INFO_SQL = "UPDATE accounts SET login=?, password=? WHERE id=?";
    private static final String UPDATE_USER_INFO_SQL = "UPDATE users SET balance=?, active=? WHERE account_id=?";
    private static final ConnectionPool connectionPool = ProviderConnectionPool.getInstance();
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDao.class);

    private UserDao() {
    }

    private static class SingletonHolder {
        private static final UserDao instance = new UserDao();
    }

    public static UserDao getInstance() {
        return SingletonHolder.instance;
    }

    @Override
    public Optional<List<User>> readAll() {
        try (final Connection conn = connectionPool.takeConnection();
             final PreparedStatement statement = conn.prepareStatement(FIND_ALL_ACCOUNTS_SQL)) {
            ResultSet resultSet = statement.executeQuery();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                Optional<User> user = resultSet.getBigDecimal("balance") != null ?
                        extractUser(resultSet, UserRole.USER) : extractUser(resultSet, UserRole.ADMIN);
                user.ifPresent(users::add);
            }
            return Optional.of(users);
        } catch (InterruptedException | SQLException | PropertiesAbsenceException e) {
            LOGGER.error(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public void create(User entity) {
        try {
            createAccount(entity.getLogin(), entity.getPassword());
            Integer accountId = findAccountIdByLogin(entity.getLogin());
            createUser(accountId);
        } catch (SQLException | InterruptedException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public Optional<User> findUserByLogin(String login) {
        try (final Connection conn = connectionPool.takeConnection();
             final PreparedStatement statement = conn.prepareStatement(FIND_ACCOUNT_BY_LOGIN_SQL)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBigDecimal("balance") != null ?
                        extractUser(resultSet, UserRole.USER) : extractUser(resultSet, UserRole.ADMIN);
            }
        } catch (InterruptedException | SQLException | PropertiesAbsenceException e) {
            LOGGER.error(e.getMessage());
        }
        return Optional.empty();
    }

    public Optional<User> findUserById(Integer id) {
        try (final Connection conn = connectionPool.takeConnection();
             final PreparedStatement statement = conn.prepareStatement(FIND_ACCOUNT_BY_ID_SQL)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBigDecimal("balance") != null ?
                        extractUser(resultSet, UserRole.USER) : extractUser(resultSet, UserRole.ADMIN);
            }
        } catch (InterruptedException | SQLException | PropertiesAbsenceException e) {
            LOGGER.error(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> update(User entity) {
        Optional<User> user = findUserByLogin(entity.getLogin());
        try (final Connection conn = connectionPool.takeConnection();
             final PreparedStatement accountStatement = conn.prepareStatement(UPDATE_ACCOUNT_INFO_SQL);
             final PreparedStatement userStatement = conn.prepareStatement(UPDATE_USER_INFO_SQL)) {
            accountStatement.setString(1, entity.getLogin());
            accountStatement.setString(2, entity.getPassword());
            accountStatement.setInt(3, entity.getId());
            userStatement.setBigDecimal(1, entity.getBalance());
            userStatement.setBoolean(2, entity.getActive());
            userStatement.setInt(3, entity.getId());
            accountStatement.executeUpdate();
            userStatement.executeUpdate();
        } catch (InterruptedException | SQLException | PropertiesAbsenceException e) {
            LOGGER.error(e.getMessage());
        }
        return user;
    }

    @Override
    public void delete(User entity) {
        try (final Connection conn = connectionPool.takeConnection();
             final PreparedStatement accountStatement = conn.prepareStatement(DELETE_ACCOUNT_BY_ID_SQL);
             final PreparedStatement userStatement = conn.prepareStatement(DELETE_USER_BY_ID_SQL)) {
            accountStatement.setInt(1, entity.getId());
            userStatement.setInt(1, entity.getId());
            accountStatement.executeUpdate();
            userStatement.executeUpdate();
        } catch (InterruptedException | SQLException | PropertiesAbsenceException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private Optional<User> extractUser(ResultSet resultSet, UserRole role) throws SQLException {
        return Optional.of(User.builder()
                .withId(resultSet.getInt("id"))
                .withLogin(resultSet.getString("login"))
                .withPassword(resultSet.getString("password"))
                .withRole(role)
                .withBalance(resultSet.getBigDecimal("balance"))
                .withStatus(resultSet.getBoolean("active"))
                .build());
    }

    private Integer findAccountIdByLogin(String login) throws SQLException, InterruptedException {
        final Connection conn = connectionPool.takeConnection();
        final PreparedStatement statement = conn.prepareStatement(FIND_ACCOUNT_ID_SQL);
        statement.setString(1, login);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return resultSet.getInt("id");
    }

    private void createAccount(String login, String password) throws SQLException, InterruptedException {
        final Connection conn = connectionPool.takeConnection();
        final PreparedStatement accountStatement = conn.prepareStatement(CREATE_ACCOUNT_SQL);
        accountStatement.setString(1, login);
        accountStatement.setString(2, password);
        accountStatement.executeUpdate();

    }

    private void createUser(Integer accountId) throws SQLException, InterruptedException {
        final Connection conn = connectionPool.takeConnection();
        final PreparedStatement userStatement = conn.prepareStatement(CREATE_USER_SQL);
        userStatement.setInt(1, accountId);
        userStatement.setBigDecimal(2, new BigDecimal("0"));
        userStatement.setBoolean(3, true);
        userStatement.executeUpdate();
    }
}
