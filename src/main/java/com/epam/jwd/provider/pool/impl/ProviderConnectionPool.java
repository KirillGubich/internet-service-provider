package com.epam.jwd.provider.pool.impl;

import com.epam.jwd.provider.model.ConnectionPoolProperties;
import com.epam.jwd.provider.exception.ConnectionTypeMismatchException;
import com.epam.jwd.provider.pool.ConnectionPool;
import com.epam.jwd.provider.util.PropertyReaderUtil;

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

    private ProviderConnectionPool() {
        freeConnections = new ArrayDeque<>();
        givenAwayConnections = new ArrayList<>();
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
    public void init() throws SQLException {
        properties = PropertyReaderUtil.retrieveProperties();
        registerDriver();
        createConnections(properties.getDefaultPoolSize());
    }

    @Override
    public void destroyPool() throws SQLException {
        for (ProxyConnection connection : freeConnections) {
            connection.realClose();
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

    private void registerDriver() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            DriverManager.registerDriver(DriverManager.getDriver(properties.getDatabaseUrl()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); //todo log
        }
    }

    private void deregisterDrivers() throws SQLException {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            DriverManager.deregisterDriver(drivers.nextElement());
        }
    }

    public static ConnectionPool getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static final ConnectionPool instance = new ProviderConnectionPool();
    }
}
