package com.epam.jwd.provider.service;

import com.epam.jwd.provider.model.dto.UserDto;

import java.util.Optional;

public interface UserService extends CommonService<UserDto> {

    Optional<UserDto> login(String login, String password);

    boolean signUp(String login, String password, String passwordRepeat);
}
