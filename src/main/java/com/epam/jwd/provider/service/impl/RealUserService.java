package com.epam.jwd.provider.service.impl;

import com.epam.jwd.provider.dao.impl.UserDao;
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
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

public enum RealUserService implements UserService {
    INSTANCE;

    private static final String DUMMY_PASSWORD = "$2y$12$68aa7ZF41prLfrxYx9yCwunnY8OpWxP9uNdH2B1vaEO/EjAcCsqk.";
    private static final String LOGIN_REGEXP = "[a-zA-Z][\\w]{3,100}$";
    private static final String PASSWORD_REGEXP = "^(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$";
    private UserDao userDao = UserDao.getInstance();
    private static final Logger LOGGER = LoggerFactory.getLogger(RealUserService.class);

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void create(UserDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("UserDto parameter - null");
        }
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
        if (dto == null) {
            throw new IllegalArgumentException("UserDto parameter - null");
        }
        Optional<User> user = userDao.update(convertToUser(dto));
        return user.map(this::convertToDto);
    }

    @Override
    public void delete(UserDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("UserDto parameter - null");
        }
        userDao.delete(convertToUser(dto));
    }

    @Override
    public Optional<UserDto> login(String login, String password) {
        if (login == null || password == null) {
            throw new IllegalArgumentException("Login parameters - null");
        }
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
        if (login == null || password == null || passwordRepeat == null) {
            throw new IllegalArgumentException("Sign up parameters - null");
        }
        Optional<User> user = userDao.findUserByLogin(login);
        boolean passwordCorrect;
        boolean loginCorrect;
        loginCorrect = Pattern.matches(LOGIN_REGEXP, login);
        passwordCorrect = password.equals(passwordRepeat) && Pattern.matches(PASSWORD_REGEXP, password);
        if (!user.isPresent() && loginCorrect && passwordCorrect) {
            userDao.create(User.builder().withLogin(login).withPassword(hash(password)).build());
            return true;
        }
        return false;
    }

    @Override
    public Optional<UserDto> findById(Integer id) {
        if (id == null) {
            return Optional.empty();
        }
        Optional<User> user = userDao.findUserById(id);
        return user.map(this::convertToDto);
    }

    @Override
    public Optional<UserDto> findByLogin(String login) {
        if (login == null) {
            return Optional.empty();
        }
        Optional<User> user = userDao.findUserByLogin(login);
        return user.map(this::convertToDto);
    }

    @Override
    public boolean changePassword(Integer accountId, String oldPassword, String updPassword, String updPasswordRepeat) {
        if (accountId == null || oldPassword == null || updPassword == null || updPasswordRepeat == null) {
            throw new IllegalArgumentException("Change password parameters - null");
        }
        Optional<User> user = userDao.findUserById(accountId);
        if (!user.isPresent()) {
            LOGGER.error("An attempt was made to change an account password that does not exist");
            return false;
        }
        boolean passwordCorrect = checkPassword(oldPassword, updPassword, updPasswordRepeat, user.get());
        if (!passwordCorrect) {
            return false;
        }
        User updatedUser = updateUserPassword(accountId, updPassword, user.get());
        userDao.update(updatedUser);
        return true;
    }

    @Override
    public void addValueToBalance(Integer accountId, BigDecimal valueToAdd) {
        if (accountId == null || valueToAdd == null) {
            throw new IllegalArgumentException("Update balance parameters - null");
        }
        Optional<User> user = userDao.findUserById(accountId);
        if (user.isPresent()) {
            BigDecimal currentBalance = user.get().getBalance();
            BigDecimal updatedBalance = currentBalance.add(valueToAdd);
            updateBalance(user.get(), updatedBalance);
        } else {
            LOGGER.error("An attempt was made to top up an account balance that does not exist");
        }
    }

    private void updateBalance(User user, BigDecimal balance) {
        User updatedUser = User.builder()
                .withId(user.getId())
                .withLogin(user.getLogin())
                .withPassword(user.getPassword())
                .withStatus(user.getActive())
                .withBalance(balance)
                .build();
        userDao.update(updatedUser);
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
                && Pattern.matches(PASSWORD_REGEXP, updPassword);
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
