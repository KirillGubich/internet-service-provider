package com.epam.jwd.provider.service;

import com.epam.jwd.provider.dao.impl.UserDao;
import com.epam.jwd.provider.model.entity.User;
import com.epam.jwd.provider.model.dto.UserDto;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public enum UserService implements CommonService<UserDto> {
    INSTANCE;

    private static final String DUMMY_PASSWORD = "$2y$12$68aa7ZF41prLfrxYx9yCwunnY8OpWxP9uNdH2B1vaEO/EjAcCsqk.";
    private final UserDao userDao;

    UserService() {
        this.userDao = UserDao.INSTANCE;
    }

    @Override
    public void add(UserDto dto) {
        userDao.add(convertToUser(dto));
    }

    @Override
    public Optional<List<UserDto>> findAll() {
        return userDao.findAll()
                .map(users -> users.stream()
                        .map(this::convertToDto)
                        .collect(toList())
                );
    }

    @Override
    public Optional<UserDto> save(UserDto dto) {
        Optional<User> user = userDao.save(convertToUser(dto));
        return user.map(this::convertToDto);
    }

    public Optional<UserDto> login(String name, String password) {
        final Optional<User> user = userDao.findByLogin(name);
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

    public Optional<UserDto> findByName(String name) {
        Optional<User> user = userDao.findByLogin(name);
        return user.map(this::convertToDto);
    }

    public void changePassword(String password) {
        //todo через UserDao.save
    }

    private UserDto convertToDto(User user) {
        return UserDto.builder().withLogin(user.getLogin()).withRole(user.getRole()).build(); //todo factory
    }

    private User convertToUser(UserDto user) {
        return User.builder().withLogin(user.getLogin()).withPassword(user.getPassword()).build(); //todo factory
    }

    private String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
