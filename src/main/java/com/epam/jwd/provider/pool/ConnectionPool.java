package com.epam.jwd.provider.pool;

import com.epam.jwd.provider.exception.ConnectionTypeMismatchException;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionPool {

    Connection takeConnection() throws InterruptedException, SQLException;

    void returnConnection(Connection connection) throws ConnectionTypeMismatchException;

    void init();

    void destroyPool();

}
