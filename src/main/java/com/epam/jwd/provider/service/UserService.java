package com.epam.jwd.provider.service;

import com.epam.jwd.provider.model.dto.UserDto;

import java.math.BigDecimal;
import java.util.Optional;

public interface UserService extends CommonService<UserDto> {

    Optional<UserDto> login(String login, String password);

    boolean signUp(String login, String password, String passwordRepeat);

    Optional<UserDto> findById(Integer id);

    void updateBalance(Integer accountId, BigDecimal balance);
}
