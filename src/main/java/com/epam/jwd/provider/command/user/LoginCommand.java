package com.epam.jwd.provider.command.user;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.command.page.ShowUserLoginPage;
import com.epam.jwd.provider.model.dto.UserDto;
import com.epam.jwd.provider.model.entity.UserRole;
import com.epam.jwd.provider.service.UserService;
import com.epam.jwd.provider.service.impl.RealUserService;

import java.util.Optional;

/**
 * Command for entering into the personal account.
 * Checks the username and password and, upon successful authorization,
 * redirects to the appropriate page, depending on the user's role.
 */
public enum LoginCommand implements Command {
    INSTANCE;

    private final UserService userService;
    private static final ResponseContext ADMIN_PAGE_RESPONSE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/controller?command=show_admin_page";
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };
    private static final ResponseContext USER_PAGE_RESPONSE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/controller?command=show_profile";
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };

    private static final String USER_LOGIN_PARAMETER_NAME = "userLogin";
    private static final String USER_PASSWORD_PARAMETER_NAME = "userPassword";
    private static final String USER_ROLE_SESSION_ATTRIBUTE_NAME = "userRole";
    private static final String USER_LOGIN_SESSION_ATTRIBUTE_NAME = "userLogin";
    private static final String ACCOUNT_ID_SESSION_ATTRIBUTE_NAME = "accountId";
    private static final String ERROR_MESSAGE_ATTRIBUTE_NAME = "errorMessage";

    LoginCommand() {
        userService = RealUserService.INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext request) {
        String login = String.valueOf(request.getParameter(USER_LOGIN_PARAMETER_NAME));
        final String password = String.valueOf(request.getParameter(USER_PASSWORD_PARAMETER_NAME));
        login = login.replace("\\s+", "");
        final Optional<UserDto> user = userService.login(login, password);
        ResponseContext result;
        if (user.isPresent()) {
            request.setSessionAttribute(USER_ROLE_SESSION_ATTRIBUTE_NAME, user.get().getRole());
            request.setSessionAttribute(USER_LOGIN_SESSION_ATTRIBUTE_NAME, login);
            request.setSessionAttribute(ACCOUNT_ID_SESSION_ATTRIBUTE_NAME, user.get().getId());
            if (user.get().getRole().equals(UserRole.ADMIN)) {
                result = ADMIN_PAGE_RESPONSE;
            } else {
                result = USER_PAGE_RESPONSE;
            }
        } else {
            result = ShowUserLoginPage.INSTANCE.execute(request);
            request.setAttribute(ERROR_MESSAGE_ATTRIBUTE_NAME, Boolean.TRUE);
        }
        return result;
    }
}
