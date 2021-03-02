package com.epam.jwd.provider.service.impl;

import com.epam.jwd.provider.dao.impl.UserDao;
import com.epam.jwd.provider.model.entity.User;
import com.epam.jwd.provider.model.dto.UserDto;
import com.epam.jwd.provider.service.UserService;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public enum RealUserService implements UserService {
    INSTANCE;

    private static final String DUMMY_PASSWORD = "$2y$12$68aa7ZF41prLfrxYx9yCwunnY8OpWxP9uNdH2B1vaEO/EjAcCsqk.";
    private final UserDao userDao;

    RealUserService() {
        this.userDao = UserDao.INSTANCE;
    }

    @Override
    public void create(UserDto dto) {
        userDao.create(convertToUser(dto));
    }

    @Override
    public Optional<List<UserDto>> findAll() {
        return userDao.readAll()
                .map(users -> users.stream()
                        .map(this::convertToDto)
                        .collect(toList())
                );
    }

    @Override
    public Optional<UserDto> save(UserDto dto) {
        Optional<User> user = userDao.update(convertToUser(dto));
        return user.map(this::convertToDto);
    }

    @Override
    public void delete(UserDto dto) {

    }

    @Override
    public Optional<UserDto> login(String login, String password) {
        final Optional<User> user = userDao.read(User.builder().withLogin(login).build());
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
    public Optional<UserDto> find(UserDto dto) {
        Optional<User> user = userDao.read(convertToUser(dto));
        return user.map(this::convertToDto);
    }

    public void changePassword(String password) {
        //todo через UserDao.save
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

    private User convertToUser(UserDto user) {
        return User.builder().withLogin(user.getLogin()).withPassword(hash(user.getPassword())).build();
    }

    private String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
