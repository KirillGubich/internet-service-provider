package com.epam.jwd.provider.dao.impl;

import com.epam.jwd.provider.dao.CommonDao;
import com.epam.jwd.provider.model.entity.Tariff;
import com.epam.jwd.provider.pool.ConnectionPool;
import com.epam.jwd.provider.pool.impl.ProviderConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public enum TariffDao implements CommonDao<Tariff> {
    INSTANCE;

    private static final String FIND_ALL_TARIFFS_SQL = "SELECT id, name, description, special_offer, " +
            "price, download_speed, upload_speed FROM tariffs";
    private static final String FIND_TARIFF_BY_NAME_SQL = "SELECT id, name, description, special_offer, " +
            "price, download_speed, upload_speed FROM tariffs WHERE name=?";
    private static final String CREATE_TARIFF_SQL = "INSERT INTO tariffs (name, description, special_offer, " +
            "price, download_speed, upload_speed) values (?,?,?,?,?,?)";
    private static final String DELETE_TARIFF_BY_ID_SQL = "DELETE FROM tariffs WHERE id=?";
    private static final String UPDATE_TARIFF_SQL = "UPDATE tariffs SET description=?, special_offer=?, " +
            "price=?, download_speed=?, upload_speed=? WHERE id=?";
    private static final ConnectionPool connectionPool = ProviderConnectionPool.getInstance();

    @Override
    public Optional<List<Tariff>> readAll() {
        try (final Connection conn = connectionPool.takeConnection();
             final PreparedStatement statement = conn.prepareStatement(FIND_ALL_TARIFFS_SQL)) {
            ResultSet resultSet = statement.executeQuery();
            List<Tariff> tariffs = new ArrayList<>();
            while (resultSet.next()) {
                Optional<Tariff> tariff = extractTariff(resultSet);
                tariff.ifPresent(tariffs::add);
            }
            return Optional.of(tariffs);
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();//todo log
            return Optional.empty();
        }
    }

    @Override
    public Optional<Tariff> read(Tariff entity) {
        try (final Connection conn = connectionPool.takeConnection();
             final PreparedStatement statement = conn.prepareStatement(FIND_TARIFF_BY_NAME_SQL)) {
            statement.setString(1, entity.getName());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractTariff(resultSet);
            }
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();//todo log
        }
        return Optional.empty();
    }

    @Override
    public Optional<Tariff> update(Tariff entity) {
        Optional<Tariff> tariff = read(Tariff.builder().withName(entity.getName()).build());
        try (final Connection conn = connectionPool.takeConnection();
             final PreparedStatement statement = conn.prepareStatement(UPDATE_TARIFF_SQL)) {
            statement.setString(1, entity.getDescription());
            statement.setBoolean(2, entity.getSpecialOffer());
            statement.setBigDecimal(3, entity.getCostPerDay());
            statement.setDouble(4, entity.getDownloadSpeed());
            statement.setDouble(5, entity.getUploadSpeed());
            statement.setInt(6, entity.getId());
            statement.executeUpdate();
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();//todo log
        }
        return tariff;
    }

    @Override
    public void delete(Tariff entity) {
        try (final Connection conn = connectionPool.takeConnection();
             final PreparedStatement statement = conn.prepareStatement(DELETE_TARIFF_BY_ID_SQL)) {
            statement.setInt(1, entity.getId());
            statement.executeUpdate();
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();//todo log
        }
    }

    @Override
    public void create(Tariff entity) {
        try (final Connection conn = connectionPool.takeConnection();
             final PreparedStatement statement = conn.prepareStatement(CREATE_TARIFF_SQL)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getDescription());
            statement.setBoolean(3, entity.getSpecialOffer());
            statement.setBigDecimal(4, entity.getCostPerDay());
            statement.setDouble(5, entity.getDownloadSpeed());
            statement.setDouble(6, entity.getUploadSpeed());
            statement.executeUpdate();
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();//todo log
        }
    }

    private Optional<Tariff> extractTariff(ResultSet resultSet) throws SQLException {
        return Optional.of(Tariff.builder()
        .withId(resultSet.getInt("id"))
        .withName(resultSet.getString("name"))
        .withDescription(resultSet.getString("description"))
        .withSpecialOffer(resultSet.getBoolean("special_offer"))
        .withCostPerDay(resultSet.getBigDecimal("price"))
        .withDownloadSpeed(resultSet.getDouble("download_speed"))
        .withUploadSpeed(resultSet.getDouble("upload_speed"))
        .build());
    }
}