package com.epam.jwd.provider.dao.impl;

import com.epam.jwd.provider.dao.CommonDao;
import com.epam.jwd.provider.exception.AddressIdException;
import com.epam.jwd.provider.factory.impl.AddressFactory;
import com.epam.jwd.provider.model.entity.Address;
import com.epam.jwd.provider.pool.ConnectionPool;
import com.epam.jwd.provider.pool.impl.ProviderConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public enum AddressDao implements CommonDao<Address> {
    INSTANCE;

    private static final String FIND_ALL_ADDRESSES_SQL = "SELECT address_id, city, address FROM addresses " +
            "INNER JOIN cities on addresses.city_id = cities.city_id";
    private static final String FIND_ADDRESS_BY_ID_SQL = "SELECT address_id, city, address FROM addresses " +
            "INNER JOIN cities on addresses.city_id = cities.city_id WHERE address_id=?";
    private static final String DELETE_ADDRESS_SQL = "DELETE FROM addresses WHERE address_id=?";
    private static final String CREATE_ADDRESS_SQL = "INSERT INTO addresses (address, city_id) VALUES (?,?)";
    private static final String UPDATE_ADDRESS_SQL = "UPDATE addresses SET address=?, city_id=? WHERE address_id=?";
    private static final String FIND_CITY_ID_SQL = "SELECT city_id FROM cities WHERE city=?";
    private static final String CREATE_CITY_SQL = "INSERT INTO cities (city) VALUES (?)";
    private static final ConnectionPool connectionPool = ProviderConnectionPool.getInstance();
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressDao.class);

    @Override
    public void create(Address entity) {
        try (final Connection conn = connectionPool.takeConnection();
             final PreparedStatement statement = conn.prepareStatement(CREATE_ADDRESS_SQL)) {
            int cityId = findCityId(entity.getCity());
            statement.setString(1, entity.getAddress());
            statement.setInt(2, cityId);
            statement.executeUpdate();
        } catch (InterruptedException | SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public Optional<Address> findById(Address entity) {
        try (final Connection conn = connectionPool.takeConnection();
             final PreparedStatement statement = conn.prepareStatement(FIND_ADDRESS_BY_ID_SQL)) {
            statement.setInt(1, entity.getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractAddress(resultSet);
            }
        } catch (InterruptedException | SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<Address>> readAll() {
        try (final Connection conn = connectionPool.takeConnection();
             final PreparedStatement statement = conn.prepareStatement(FIND_ALL_ADDRESSES_SQL)) {
            ResultSet resultSet = statement.executeQuery();
            List<Address> addresses = new ArrayList<>();
            while (resultSet.next()) {
                Optional<Address> address = extractAddress(resultSet);
                address.ifPresent(addresses::add);
            }
            return Optional.of(addresses);
        } catch (InterruptedException | SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Address> update(Address entity) throws AddressIdException {
        Optional<Address> address = findById(entity);
        try (final Connection conn = connectionPool.takeConnection();
             final PreparedStatement statement = conn.prepareStatement(UPDATE_ADDRESS_SQL)) {
            Integer id = entity.getId();
            if (id == null) {
                throw new AddressIdException("Address id is NULL");
            }
            int cityId = findCityId(entity.getCity());
            statement.setString(1, entity.getAddress());
            statement.setInt(2, cityId);
            statement.setInt(3, id);
            statement.executeUpdate();
        } catch (InterruptedException | SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return address;
    }

    @Override
    public void delete(Address entity) {
        try (final Connection conn = connectionPool.takeConnection();
             final PreparedStatement statement = conn.prepareStatement(DELETE_ADDRESS_SQL)) {
            Integer id = entity.getId();
            if (id == null) {
                id = 0;
            }
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (InterruptedException | SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private int findCityId(String cityName) {
        Optional<Integer> cityId = fetchCityIdFromDataBase(cityName);
        if (!cityId.isPresent()) {
            createCity(cityName);
            cityId = fetchCityIdFromDataBase(cityName);
        }
        return cityId.get();
    }

    private Optional<Integer> fetchCityIdFromDataBase(String cityName) {
        try (final Connection conn = connectionPool.takeConnection();
             final PreparedStatement statement = conn.prepareStatement(FIND_CITY_ID_SQL)) {
            statement.setString(1, cityName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(resultSet.getInt("city_id"));
            }
        } catch (InterruptedException | SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return Optional.empty();
    }

    private void createCity(String cityName) {
        try (final Connection conn = connectionPool.takeConnection();
             final PreparedStatement statement = conn.prepareStatement(CREATE_CITY_SQL)) {
            statement.setString(1, cityName);
            statement.executeUpdate();
        } catch (InterruptedException | SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private Optional<Address> extractAddress(ResultSet resultSet) throws SQLException {
        return Optional.of(AddressFactory.INSTANCE.create(
                resultSet.getInt("address_id"),
                resultSet.getString("city"),
                resultSet.getString("address")));
    }
}
