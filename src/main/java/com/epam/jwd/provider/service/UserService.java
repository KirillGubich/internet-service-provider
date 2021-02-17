package com.epam.jwd.provider.service;

import com.epam.jwd.provider.dao.impl.UserDao;
import com.epam.jwd.provider.domain.User;
import com.epam.jwd.provider.domain.UserDto;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class UserService implements CommonService<UserDto> {

    private static final String DUMMY_PASSWORD = "defaultPwd";
    private final UserDao userDao;

    public UserService() {
        this.userDao = new UserDao();
    }

    @Override
    public Optional<List<UserDto>> findAll() {
        return userDao.findAll()
                .map(
                        users -> users.stream()
                                .map(this::convertToDto)
                                .collect(toList())
                );
    }

    @Override
    public Optional<UserDto> save(UserDto dto) {
        return Optional.empty();
    }

    public Optional<UserDto> login(String name, String password) {
        final Optional<User> user = userDao.findByName(name);
        if (!user.isPresent()) {
            BCrypt.checkpw(password, DUMMY_PASSWORD); //to prevent timing attack
            return Optional.empty();
        }
        final String realPassword = user.get().getPassword();
        if (BCrypt.checkpw(password, realPassword)) {
            return user.map(this::convertToDto);
        } else {
           return Optional.empty();
        }
    }

    private UserDto convertToDto(User user) {
        return new UserDto(user.getLogin());
    }
}
