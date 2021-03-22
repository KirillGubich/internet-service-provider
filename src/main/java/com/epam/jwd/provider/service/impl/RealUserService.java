package com.epam.jwd.provider.service.impl;

import com.epam.jwd.provider.dao.impl.UserDao;
import com.epam.jwd.provider.exception.AccountAbsenceException;
import com.epam.jwd.provider.model.dto.UserDto;
import com.epam.jwd.provider.model.entity.User;
import com.epam.jwd.provider.service.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public enum RealUserService implements UserService {
    INSTANCE;

    private static final String DUMMY_PASSWORD = "$2y$12$68aa7ZF41prLfrxYx9yCwunnY8OpWxP9uNdH2B1vaEO/EjAcCsqk.";
    private static final int PASSWORD_MINIMAL_LENGTH = 8;
    private UserDao userDao = UserDao.getInstance();
    private static final Logger LOGGER = LoggerFactory.getLogger(RealUserService.class);

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

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
                BCrypt.checkpw(password, DUMMY_PASSWORD);
            } catch (IllegalArgumentException e) {
                LOGGER.error(e.getMessage());
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
        boolean passwordCorrect;
        passwordCorrect = password.equals(passwordRepeat) && password.length() >= PASSWORD_MINIMAL_LENGTH;
        if (!user.isPresent() && passwordCorrect) {
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
    public Optional<UserDto> findByLogin(String login) {
        Optional<User> user = userDao.findUserByLogin(login);
        return user.map(this::convertToDto);
    }

    @Override
    public void updateBalance(Integer accountId, BigDecimal balance) {
        Optional<User> user = userDao.findUserById(accountId);
        if (!user.isPresent()) {
            LOGGER.error("An attempt was made to top up an account balance that does not exist");
            throw new AccountAbsenceException("Account with such id doesn't exist");
        }
        User updatedUser = User.builder()
                .withId(accountId)
                .withLogin(user.get().getLogin())
                .withPassword(user.get().getPassword())
                .withStatus(user.get().getActive())
                .withBalance(balance)
                .build();
        userDao.update(updatedUser);
    }

    @Override
    public boolean changePassword(Integer accountId, String oldPassword, String updPassword, String updPasswordRepeat) {
        if (accountId == null || oldPassword == null || updPassword == null || updPasswordRepeat == null) {
            return false;
        }
        Optional<User> user = userDao.findUserById(accountId);
        if (!user.isPresent()) {
            LOGGER.error("An attempt was made to change an account password that does not exist");
            throw new AccountAbsenceException("Account with such id doesn't exist");
        }
        boolean passwordCorrect = checkPassword(oldPassword, updPassword, updPasswordRepeat, user.get());
        if (!passwordCorrect) {
            return false;
        }
        User updatedUser = updateUserPassword(accountId, updPassword, user.get());
        userDao.update(updatedUser);
        return true;
    }

    private User updateUserPassword(Integer accountId, String updPassword, User user) {
        return User.builder()
                .withId(accountId)
                .withLogin(user.getLogin())
                .withPassword(hash(updPassword))
                .withBalance(user.getBalance())
                .withStatus(user.getActive())
                .build();
    }

    private boolean checkPassword(String oldPassword, String updPassword, String updPasswordRepeat, User user) {
        return BCrypt.checkpw(oldPassword, user.getPassword())
                && updPassword.equals(updPasswordRepeat)
                && updPassword.length() >= PASSWORD_MINIMAL_LENGTH;
    }

    private UserDto convertToDto(User user) {
        return UserDto.builder()
                .withId(user.getId())
                .withLogin(user.getLogin())
                .withPassword(user.getPassword())
                .withRole(user.getRole())
                .withBalance(user.getBalance())
                .withActive(user.getActive())
                .build();
    }

    private User convertToUser(UserDto dto) {
        return User.builder()
                .withId(dto.getId())
                .withLogin(dto.getLogin())
                .withPassword(dto.getPassword())
                .withRole(dto.getRole())
                .withBalance(dto.getBalance())
                .withStatus(dto.getActive())
                .build();
    }

    private String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
