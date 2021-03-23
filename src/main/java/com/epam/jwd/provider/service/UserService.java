package com.epam.jwd.provider.service;

import com.epam.jwd.provider.model.dto.UserDto;
import com.epam.jwd.provider.model.entity.User;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Expands the basic capabilities of the {@link CommonService} for interacting with {@link User}
 *
 * @author Kirill Gubich
 */
public interface UserService extends CommonService<UserDto> {

    /**
     * Login to the system.
     * Checks the existence of an account, the correctness of the password.
     *
     * @param login    account login
     * @param password account password
     * @return user if it exist, else - empty optional
     */
    Optional<UserDto> login(String login, String password);

    /**
     * Creates new user account
     *
     * @param login          account login
     * @param password       account password
     * @param passwordRepeat account password repeat
     * @return true if registration was successful, else - false
     */
    boolean signUp(String login, String password, String passwordRepeat);

    /**
     * Finds user by id
     *
     * @param id user id
     * @return user if exist, else - empty optional
     */
    Optional<UserDto> findById(Integer id);

    /**
     * Finds user by login
     *
     * @param login user login
     * @return user if exist, else - empty optional
     */
    Optional<UserDto> findByLogin(String login);

    /**
     * Updates user balance
     *
     * @param accountId account id
     * @param balance   new balance value
     */
    void updateBalance(Integer accountId, BigDecimal balance);

    /**
     * Changes user password
     *
     * @param accountId         account id
     * @param oldPassword       current password
     * @param updPassword       updated password
     * @param updPasswordRepeat updated password repeat
     * @return true if password changed successfully, else - false
     */
    boolean changePassword(Integer accountId, String oldPassword, String updPassword, String updPasswordRepeat);
}
