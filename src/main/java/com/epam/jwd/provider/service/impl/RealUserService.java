package com.epam.jwd.provider.service.impl;

import com.epam.jwd.provider.dao.impl.UserDao;
import com.epam.jwd.provider.model.dto.UserDto;
import com.epam.jwd.provider.model.entity.User;
import com.epam.jwd.provider.service.UserService;
import org.mindrot.jbcrypt.BCrypt;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public enum RealUserService implements UserService {
    INSTANCE;

    private static final String DUMMY_PASSWORD = "$2y$12$68aa7ZF41prLfrxYx9yCwunnY8OpWxP9uNdH2B1vaEO/EjAcCsqk.";
    private static final int PASSWORD_MINIMAL_LENGTH = 8;
    private final UserDao userDao = UserDao.INSTANCE;

    @Override
    public void create(UserDto dto) {
        userDao.create(convertToUser(dto));
    }

    @Override
    public List<UserDto> findAll() {
        Optional<List<User>> users = userDao.readAll();
        return users.map(userList -> userList.stream()
                .map(this::convertToDto)
                .collect(toList()))
                .orElseGet(ArrayList::new);
    }

    @Override
    public Optional<UserDto> save(UserDto dto) {
        Optional<User> user = userDao.update(convertToUser(dto));
        return user.map(this::convertToDto);
    }

    @Override
    public void delete(UserDto dto) {
        userDao.delete(convertToUser(dto));
    }

    @Override
    public Optional<UserDto> login(String login, String password) {
        final Optional<User> user = userDao.findUserByLogin(login);
        if (!user.isPresent()) {
            try {
                BCrypt.checkpw(password, DUMMY_PASSWORD); //todo to prevent timing attack
            } catch (IllegalArgumentException e) {
                // todo fix
            }
            return Optional.empty();
        }
        final String realPassword = user.get().getPassword();
        if (BCrypt.checkpw(password, realPassword)) {
            return user.map(this::convertToDto);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean signUp(String login, String password, String passwordRepeat) {
        Optional<User> user = userDao.findUserByLogin(login);
        boolean isPasswordCorrect;
        isPasswordCorrect = password.equals(passwordRepeat) && password.length() > PASSWORD_MINIMAL_LENGTH;
        if (!user.isPresent() && isPasswordCorrect) {
            userDao.create(User.builder().withLogin(login).withPassword(hash(password)).build());
            return true;
        }
        return false;
    }

    @Override
    public Optional<UserDto> findById(Integer id) {
        Optional<User> user = userDao.findUserById(id);
        return user.map(this::convertToDto);
    }

    @Override
    public void updateBalance(Integer accountId, BigDecimal balance) {
        userDao.updateUserBalance(accountId, balance);
    }


    public void changePassword(String password) {
        //todo через UserDao.save и добавить в интерфейс
    }

    private UserDto convertToDto(User user) {
        return UserDto.builder()
                .withId(user.getId())
                .withLogin(user.getLogin())
                .withRole(user.getRole())
                .withBalance(user.getBalance())
                .withActive(user.getActive())
                .build();
    }

    private User convertToUser(UserDto dto) {
        return User.builder()
                .withId(dto.getId())
                .withLogin(dto.getLogin())
                .withPassword(hash(dto.getPassword()))
                .withRole(dto.getRole())
                .withBalance(dto.getBalance())
                .withStatus(dto.getActive())
                .build();
    }

    private String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
