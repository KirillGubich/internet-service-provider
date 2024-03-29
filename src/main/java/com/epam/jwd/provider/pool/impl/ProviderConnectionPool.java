package com.epam.jwd.provider.pool.impl;

import com.epam.jwd.provider.exception.PropertiesAbsenceException;
import com.epam.jwd.provider.exception.PropertyLoadingException;
import com.epam.jwd.provider.model.entity.ConnectionPoolProperties;
import com.epam.jwd.provider.exception.ConnectionTypeMismatchException;
import com.epam.jwd.provider.pool.ConnectionPool;
import com.epam.jwd.provider.util.PropertyReaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private ConnectionPoolProperties properties;

    private static final String URL_CONFIG = "?useJDBCCompliantTimezoneShift=true&serverTimezone=UTC";
    private static final Logger LOGGER = LoggerFactory.getLogger(ProviderConnectionPool.class);

    private ProviderConnectionPool() {
        freeConnections = new ArrayDeque<>();
        givenAwayConnections = new ArrayList<>();
    }

    public static ConnectionPool getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static final ConnectionPool instance = new ProviderConnectionPool();
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
    public void init() throws SQLException, PropertyLoadingException {
        properties = PropertyReaderUtil.retrieveProperties();
        registerDriver();
        createConnections(properties.getDefaultPoolSize());
    }

    @Override
    public void destroy() throws SQLException {
        for (ProxyConnection connection : freeConnections) {
            connection.realClose();
        }
        deregisterDrivers();
    }

    private boolean needsExpansion() {
        if (properties == null) {
            throw new PropertiesAbsenceException("Pool properties are not initialized");
        }
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
        LOGGER.info("Connections were successfully created. Quantity: " + connectionsToCreate);
    }

    private void registerDriver() throws SQLException {
        try {
            Class.forName(DRIVER_CLASS_NAME);
            DriverManager.registerDriver(DriverManager.getDriver(properties.getDatabaseUrl()));
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void deregisterDrivers() throws SQLException {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            DriverManager.deregisterDriver(drivers.nextElement());
        }
    }
}
