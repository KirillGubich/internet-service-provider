package com.epam.jwd.provider.pool.impl;

import com.epam.jwd.provider.domain.ConnectionPoolProperties;
import com.epam.jwd.provider.exception.ConnectionTypeMismatchException;
import com.epam.jwd.provider.pool.ConnectionPool;
import com.epam.jwd.provider.util.PropertyReaderUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProviderConnectionPool implements ConnectionPool {
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();
    private final Deque<ProxyConnection> freeConnections;
    private final List<ProxyConnection> givenAwayConnections;
    private ConnectionPoolProperties properties; //todo ? remake into constants

    private static final String URL_CONFIG = "?useJDBCCompliantTimezoneShift=true&serverTimezone=UTC";
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);

    private ProviderConnectionPool() {
        freeConnections = new ArrayDeque<>();
        givenAwayConnections = new ArrayList<>();
        init(); // todo remove
    }

    @Override
    public Connection takeConnection() throws InterruptedException, SQLException {
        lock.lock();
        if (needsExpansion()) {
            createConnections(properties.getExpansionStep());
        }
        try {
            while (freeConnections.isEmpty()) {
                notEmpty.await();
            }
            ProxyConnection connection = freeConnections.pollFirst();
            givenAwayConnections.add(connection);
            notFull.signal();
            return connection;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void returnConnection(Connection connection) throws ConnectionTypeMismatchException {
        lock.lock();
        if (connection.getClass() != ProxyConnection.class) {
            throw new ConnectionTypeMismatchException("Connection type mismatch");
        }
        try {
            freeConnections.addLast((ProxyConnection) connection);
            givenAwayConnections.remove(connection);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void init() {
        properties = PropertyReaderUtil.retrieveProperties();
        registerDriver();
        try {
            createConnections(properties.getDefaultPoolSize());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("Connection pool initialized successfully");
    }

    @Override
    public void destroyPool() {
        for (ProxyConnection connection : freeConnections) {
            try {
                connection.realClose();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
        }
        deregisterDrivers();
    }

    private boolean needsExpansion() {
        double totalConnections;
        totalConnections = givenAwayConnections.size() + freeConnections.size();
        boolean expansionPercentageReached = givenAwayConnections.size() / totalConnections * 100
                >= properties.getPercentageForExpansion();
        boolean isExpansionPossible = totalConnections + properties.getExpansionStep() <= properties.getMaxPoolSize();
        return expansionPercentageReached && isExpansionPossible;
    }

    private void createConnections(int connectionsToCreate) throws SQLException {
        for (int i = 0; i < connectionsToCreate; i++) {
            Connection realConnection = DriverManager.getConnection(properties.getDatabaseUrl() + URL_CONFIG,
                    properties.getDatabaseUser(), properties.getDatabasePassword());
            ProxyConnection proxyConnection = new ProxyConnection(realConnection);
            freeConnections.addLast(proxyConnection);
        }
    }

    private void registerDriver() {
        try {
            DriverManager.registerDriver(DriverManager.getDriver(properties.getDatabaseUrl()));
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void deregisterDrivers() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            try {
                DriverManager.deregisterDriver(drivers.nextElement());
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    public static ConnectionPool getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static final ConnectionPool instance = new ProviderConnectionPool();
    }
}
