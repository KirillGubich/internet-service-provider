package com.epam.jwd.provider.service.impl;

import com.epam.jwd.provider.dao.impl.UserDao;
import com.epam.jwd.provider.exception.AccountAbsenceException;
import com.epam.jwd.provider.model.dto.UserDto;
import com.epam.jwd.provider.model.entity.User;
import com.epam.jwd.provider.model.entity.UserRole;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Null;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RealUserServiceTest {

    private static RealUserService service;
    private static UserDao userDao;

    @BeforeClass
    public static void beforeClass() throws Exception {
        service = RealUserService.INSTANCE;
        userDao = Mockito.mock(UserDao.class);
    }

    @Test
    public void findAll_receiveAllUsers_whenExist() {
        List<User> users = createUserList();
        Mockito.when(userDao.readAll()).thenReturn(Optional.of(users));
        service.setUserDao(userDao);
        List<UserDto> expected = new ArrayList<>();
        for (User user : users) {
            expected.add(convertToDto(user));
        }
        List<UserDto> actual = service.findAll();
        assertEquals(expected, actual);
    }

    @Test
    public void save_getOldValue_whenExist() {
        User user = createUser();
        UserDto userDto = createUserDto();
        Mockito.when(userDao.update(user)).thenReturn(Optional.of(user));
        service.setUserDao(userDao);
        Optional<UserDto> actual = service.save(userDto);
        Optional<UserDto> expected = Optional.of(convertToDto(user));
        assertEquals(expected, actual);
    }

    @Test
    public void save_getOptionalEmpty_whenNotExist() {
        User user = createUser();
        UserDto userDto = createUserDto();
        Mockito.when(userDao.update(user)).thenReturn(Optional.empty());
        service.setUserDao(userDao);
        Optional<UserDto> actual = service.save(userDto);
        Optional<UserDto> expected = Optional.empty();
        assertEquals(expected, actual);
    }

    @Test
    public void login_getUserDto_whenPasswordCorrect() {
        String password = "SecretPassword";
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = User.builder()
                .withLogin("Name")
                .withPassword(hashedPassword)
                .build();
        Mockito.when(userDao.findUserByLogin(user.getLogin())).thenReturn(Optional.of(user));
        service.setUserDao(userDao);
        Optional<UserDto> actual = service.login(user.getLogin(), password);
        Optional<UserDto> expected = Optional.of(convertToDto(user));
        assertEquals(expected, actual);
    }

    @Test
    public void login_getOptionalEmpty_whenPasswordIncorrect() {
        String password = "SecretPassword";
        String incorrectPassword = "IncorrectPassword";
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = User.builder()
                .withLogin("Name")
                .withPassword(hashedPassword)
                .build();
        Mockito.when(userDao.findUserByLogin(user.getLogin())).thenReturn(Optional.of(user));
        service.setUserDao(userDao);
        Optional<UserDto> actual = service.login(user.getLogin(), incorrectPassword);
        Optional<UserDto> expected = Optional.empty();
        assertEquals(expected, actual);
    }

    @Test
    public void login_getOptionalEmpty_whenAccountNotExist() {
        String login = "Name";
        String password = "SecretPassword";
        Mockito.when(userDao.findUserByLogin(login)).thenReturn(Optional.empty());
        service.setUserDao(userDao);
        Optional<UserDto> actual = service.login(login, password);
        Optional<UserDto> expected = Optional.empty();
        assertEquals(expected, actual);
    }

    @Test
    public void signUp_receiveFalse_whenLoginAlreadyExist() {
        String login = "Name";
        String password = "CorrectPassword1";
        User user = User.builder()
                .withLogin(login)
                .build();
        Mockito.when(userDao.findUserByLogin(login)).thenReturn(Optional.of(user));
        service.setUserDao(userDao);
        boolean actual = service.signUp(login, password, password);
        assertFalse(actual);
    }

    @Test
    public void signUp_receiveFalse_whenPasswordToShort() {
        String login = "Name";
        String password = "toShort";
        Mockito.when(userDao.findUserByLogin(login)).thenReturn(Optional.empty());
        service.setUserDao(userDao);
        boolean actual = service.signUp(login, password, password);
        assertFalse(actual);
    }

    @Test
    public void signUp_receiveFalse_whenPasswordsNotMatches() {
        String login = "Name";
        String password = "CorrectPassword1";
        Mockito.when(userDao.findUserByLogin(login)).thenReturn(Optional.empty());
        service.setUserDao(userDao);
        boolean actual = service.signUp(login, password, password + "anything");
        assertFalse(actual);
    }

    @Test
    public void signUp_receiveTrue_whenEverythingCorrect() {
        String login = "Name";
        String password = "CorrectPassword1";
        Mockito.when(userDao.findUserByLogin(login)).thenReturn(Optional.empty());
        service.setUserDao(userDao);
        boolean actual = service.signUp(login, password, password);
        assertTrue(actual);
    }

    @Test
    public void findById_receiveUserDto_whenExist() {
        User user = createUser();
        Mockito.when(userDao.findUserById(user.getId())).thenReturn(Optional.of(user));
        service.setUserDao(userDao);
        Optional<UserDto> actual = service.findById(user.getId());
        Optional<UserDto> expected = Optional.of(convertToDto(user));
        assertEquals(expected, actual);
    }

    @Test
    public void findByLogin_receiveUserDto_whenExist() {
        User user = createUser();
        Mockito.when(userDao.findUserByLogin(user.getLogin())).thenReturn(Optional.of(user));
        service.setUserDao(userDao);
        Optional<UserDto> actual = service.findByLogin(user.getLogin());
        Optional<UserDto> expected = Optional.of(convertToDto(user));
        assertEquals(expected, actual);
    }

    @Test(expected = AccountAbsenceException.class)
    public void updateBalance_catchException_whenAccountNotExist() {
        Integer accountId = 1;
        Mockito.when(userDao.findUserById(accountId)).thenReturn(Optional.empty());
        service.setUserDao(userDao);
        service.updateBalance(accountId, new BigDecimal("0"));
    }

    @Test(expected = AccountAbsenceException.class)
    public void changePassword_receiveException_WhenAccountNotExist() {
        Integer accountId = 1;
        Mockito.when(userDao.findUserById(accountId)).thenReturn(Optional.empty());
        service.setUserDao(userDao);
        service.changePassword(accountId, "", "", "");
    }

    @Test
    public void changePassword_receiveFalse_WhenPasswordIncorrect() {
        Integer accountId = 1;
        String password = "SecretPassword";
        String newPassword = "NewPassword";
        String incorrectPassword = "IncorrectPassword";
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = User.builder()
                .withLogin("Name")
                .withPassword(hashedPassword)
                .build();
        Mockito.when(userDao.findUserById(accountId)).thenReturn(Optional.of(user));
        service.setUserDao(userDao);
        boolean actual = service.changePassword(accountId, incorrectPassword, newPassword, newPassword);
        assertFalse(actual);
    }

    @Test
    public void changePassword_receiveFalse_WhenNewPasswordToShort() {
        Integer accountId = 1;
        String password = "SecretPassword";
        String newPassword = "NewPassword";
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = User.builder()
                .withLogin("Name")
                .withPassword(hashedPassword)
                .build();
        Mockito.when(userDao.findUserById(accountId)).thenReturn(Optional.of(user));
        service.setUserDao(userDao);
        boolean actual = service.changePassword(accountId, password, newPassword, newPassword);
        assertTrue(actual);
    }

    @Test
    public void changePassword_receiveTrue_WhenEverythingCorrect() {
        Integer accountId = 1;
        String password = "SecretPassword";
        String newPassword = "toShort";
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = User.builder()
                .withLogin("Name")
                .withPassword(hashedPassword)
                .build();
        Mockito.when(userDao.findUserById(accountId)).thenReturn(Optional.of(user));
        service.setUserDao(userDao);
        boolean actual = service.changePassword(accountId, password, newPassword, newPassword);
        assertFalse(actual);
    }

    private User createUser() {
        return User.builder()
                .withId(1)
                .withLogin("defaultLogin")
                .withPassword("defaultPassword")
                .withRole(UserRole.USER)
                .withBalance(new BigDecimal("0"))
                .withStatus(true)
                .build();
    }

    private UserDto createUserDto() {
        return UserDto.builder()
                .withId(1)
                .withLogin("defaultLogin")
                .withPassword("defaultPassword")
                .withRole(UserRole.USER)
                .withBalance(new BigDecimal("0"))
                .withActive(true)
                .build();
    }

    private List<User> createUserList() {
        List<User> userList = new ArrayList<>();
        User user = User.builder()
                .withId(1)
                .withLogin("defaultLogin")
                .withPassword("defaultPassword")
                .withRole(UserRole.USER)
                .withBalance(new BigDecimal("0"))
                .withStatus(true)
                .build();
        User secondUser = User.builder()
                .withId(2)
                .withLogin("secondDefaultLogin")
                .withPassword("secondDefaultPassword")
                .withRole(UserRole.USER)
                .withBalance(new BigDecimal("10"))
                .withStatus(false)
                .build();
        userList.add(user);
        userList.add(secondUser);
        return userList;
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
}