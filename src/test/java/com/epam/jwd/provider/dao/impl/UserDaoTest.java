package com.epam.jwd.provider.dao.impl;

import com.epam.jwd.provider.model.entity.User;
import com.epam.jwd.provider.pool.impl.ProviderConnectionPool;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDaoTest {

    @Test
    public void add() throws SQLException {
//        ProviderConnectionPool.getInstance().init();
//        User user = new User(1, "Vasya", "test");
//        UserDao userDao = UserDao.INSTANCE;
//        userDao.add(user);
//        ProviderConnectionPool.getInstance().destroyPool();
    }

//    @Test
//    public void save() throws SQLException {
//        ProviderConnectionPool.getInstance().init();
//        User user = new User(1, "", );
//        UserDao userDao = UserDao.INSTANCE;
//        userDao.add(user);
//        ProviderConnectionPool.getInstance().destroyPool();
//    }


    @Test
    public void readAll() throws SQLException {
        ProviderConnectionPool.getInstance().init();
        UserDao userDao = UserDao.INSTANCE;
        Optional<List<User>> users = userDao.readAll();
        users.ifPresent(userList -> userList.forEach(System.out::println));
        ProviderConnectionPool.getInstance().destroyPool();
    }

    @Test
    public void delete() throws SQLException {
        ProviderConnectionPool.getInstance().init();
        UserDao userDao = UserDao.INSTANCE;
        User user = User.builder().withId(18).build();
        userDao.delete(user);
        ProviderConnectionPool.getInstance().destroyPool();
    }
}