package com.epam.jwd.provider.dao.impl;

import com.epam.jwd.provider.dao.CommonDao;
import com.epam.jwd.provider.model.entity.User;
import com.epam.jwd.provider.model.entity.UserRole;
import com.epam.jwd.provider.pool.ConnectionPool;
import com.epam.jwd.provider.pool.impl.ProviderConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public enum UserDao implements CommonDao<User> {
    INSTANCE;

    private static final String FIND_ALL_USERS_SQL = "SELECT id, login FROM user_list"; //todo upd
    private static final String FIND_USER_BY_NAME_SQL = "SELECT id, login, password FROM user_list WHERE login=";
    private static final String INSERT_USER_SQL = "INSERT INTO user_list (login, password) values (?,?)"; //todo add params
    private static final String UPDATE_USER_SQL = "UPDATE user_list SET id=?, login=?, password=? where login=?";
    private static final String GET_USER_ROLE_SQL = "SELECT role_name FROM user_list INNER JOIN user_role " +
            "ON user_list.role_id=user_role.id WHERE login=";
    private static final ConnectionPool connectionPool = ProviderConnectionPool.getInstance();

    @Override
    public Optional<List<User>> findAll() {
        try (final Connection conn = connectionPool.takeConnection();
             final Statement statement = conn.createStatement();
             final ResultSet resultSet = statement.executeQuery(FIND_ALL_USERS_SQL)) {
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(User.builder().withId(resultSet.getInt("id"))
                        .withLogin(resultSet.getString("login")).build()); //todo add all fields
            }
            return Optional.of(users);
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();//todo log
            return Optional.empty();
        }
    }

    @Override
    public void add(User entity) {
        try (final Connection conn = connectionPool.takeConnection();
             final PreparedStatement statement = conn.prepareStatement(INSERT_USER_SQL)) {
            statement.setString(1, entity.getLogin());
            statement.setString(2, entity.getPassword());
            statement.executeUpdate();
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();//todo log
        }
    }

    @Override
    public Optional<User> save(User entity) {
        Optional<User> oldUser = Optional.empty();
        try (final Connection conn = connectionPool.takeConnection();
             final PreparedStatement statement = conn.prepareStatement(UPDATE_USER_SQL)) {
            oldUser = findByLogin(entity.getLogin());
            if (!oldUser.isPresent()) {
                return Optional.empty();
            }
            statement.setInt(1, oldUser.get().getId());
            statement.setString(2, entity.getLogin());
            statement.setString(3, entity.getPassword());
            statement.setString(4, "'" + entity.getLogin() + "'");
            statement.executeUpdate();
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();//todo log
        }
        return oldUser;
    }

    public Optional<User> findByLogin(String login) {
        try (final Connection conn = connectionPool.takeConnection();
             final Statement statement = conn.createStatement();
             final ResultSet resultSet = statement.executeQuery(FIND_USER_BY_NAME_SQL + "'" + login + "'")) {
            Optional<User> user = Optional.empty();
            if (resultSet.next()) {
                UserRole role = UserRole.GUEST;
                Optional<UserRole> userRole = extractUserRole(login);
                if (userRole.isPresent()) {
                    role = userRole.get();
                }
                user = Optional.of(User.builder()
                        .withId(resultSet.getInt("id"))
                        .withLogin(resultSet.getString("login"))
                        .withPassword(resultSet.getString("password"))
                        .withRole(role)
                        .build());
            }
            return user;
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();//todo log
            return Optional.empty();
        }
    }

    private Optional<UserRole> extractUserRole(String login) {
        try (final Connection conn = connectionPool.takeConnection();
             final Statement statement = conn.createStatement();
             final ResultSet resultSet = statement.executeQuery(GET_USER_ROLE_SQL + "'" + login + "'")) {
            Optional<UserRole> userRole = Optional.empty();
            if (resultSet.next()) {
                UserRole role = UserRole.of(resultSet.getString("role_name"));
                userRole = Optional.of(role);
            }
            return userRole;
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();//todo log
            return Optional.empty();
        }
    }
}
