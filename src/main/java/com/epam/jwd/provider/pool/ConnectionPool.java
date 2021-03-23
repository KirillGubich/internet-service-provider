package com.epam.jwd.provider.pool;

import com.epam.jwd.provider.exception.ConnectionTypeMismatchException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * With a large number of clients working with the application, to its base
 * a large number of requests are being processed. Establishing a connection
 * with a database is an expensive operation in terms of the required resources.
 * An effective way to solve this problem is to organize a pool
 * used connections that are not physically closed, but stored
 * queued and re-provided for other requests.
 * Life cycle of connection pool begins with init() and ends with destroy() methods.
 *
 * @author Kirill Gubich
 */
public interface ConnectionPool {

    /**
     * Takes connection from the pool. Monitors the availability of free connections.
     * Expands the number of connections as needed.
     *
     * @return database connection
     * @throws InterruptedException if the current thread is interrupted
     * @throws SQLException         if could not establish a connection to the database
     */
    Connection takeConnection() throws InterruptedException, SQLException;

    /**
     * Returns connection to the pool
     *
     * @param connection returnable connection
     * @throws ConnectionTypeMismatchException if connection has unknown type
     */
    void returnConnection(Connection connection) throws ConnectionTypeMismatchException;

    /**
     * Initializes the pool: registers the driver
     * and creates an initial number of connections.
     *
     * @throws SQLException if could not register driver or create connection
     */
    void init() throws SQLException;

    /**
     * Destroys the pool: deregisters the driver
     * and close connections.
     *
     * @throws SQLException if could not deregister driver or close connection
     */
    void destroy() throws SQLException;

}
