package com.epam.jwd.provider.command;

import com.epam.jwd.provider.command.page.ShowMainPageCommand;
import com.epam.jwd.provider.command.page.ShowUserLoginPage;
import com.epam.jwd.provider.command.page.ShowUserProfilePage;
import com.epam.jwd.provider.command.user.LoginCommand;
import com.epam.jwd.provider.command.user.LogoutCommand;
import com.epam.jwd.provider.command.user.SignUpCommand;

public enum CommandManager {
    LOGIN(LoginCommand.INSTANCE),
    LOGOUT(LogoutCommand.INSTANCE),
    SIGN_UP(SignUpCommand.INSTANCE),
    SHOW_PROFILE(ShowUserProfilePage.INSTANCE),
    SHOW_USER_LOGIN_PAGE(ShowUserLoginPage.INSTANCE),
    DEFAULT(ShowMainPageCommand.INSTANCE);

    private final Command command;

    CommandManager(Command command) {
        this.command = command;
    }

    static Command of(String name) {
        for (CommandManager action : values()) {
            if (action.name().equalsIgnoreCase(name)) {
                return action.command;
            }
        }
        return DEFAULT.command;
    }
}
