package com.epam.jwd.provider.dao.impl;

import com.epam.jwd.provider.dao.CommonDao;
import com.epam.jwd.provider.domain.User;
import com.epam.jwd.provider.pool.ConnectionPool;
import com.epam.jwd.provider.pool.impl.ProviderConnectionPool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao implements CommonDao<User> {

    private static final String FIND_ALL_USERS_SQL = "SELECT id, login FROM user_list";
    private static final String FIND_USER_BY_NAME_SQL = "SELECT id, login, password FROM user_list WHERE login =";
    private static final ConnectionPool connectionPool = ProviderConnectionPool.getInstance();

    @Override
    public Optional<List<User>> findAll() {
        try (final Connection conn = connectionPool.takeConnection();
             final Statement statement = conn.createStatement();
             final ResultSet resultSet = statement.executeQuery(FIND_ALL_USERS_SQL)) {
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(new User(resultSet.getInt("id"), resultSet.getString("login"), null));
            }
            return Optional.of(users);
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> save(User entity) {
        return Optional.empty();
    }

    public Optional<User> findByName(String name) {
        try (final Connection conn = connectionPool.takeConnection();
             final Statement statement = conn.createStatement();
             final ResultSet resultSet = statement.executeQuery(FIND_USER_BY_NAME_SQL + "'" + name + "'")) {
            Optional<User> user = Optional.empty();
            if (resultSet.next()) {
                user = Optional.of(new User(resultSet.getInt("id"),
                        resultSet.getString("login"), resultSet.getString("password")));
            }
            return user;
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
