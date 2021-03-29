package com.epam.jwd.provider.command.user;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.command.page.ShowUserSignUpPage;
import com.epam.jwd.provider.service.UserService;
import com.epam.jwd.provider.service.impl.RealUserService;

/**
 * User sign up command.
 */
public enum SignUpCommand implements Command {
    INSTANCE;

    private static final ResponseContext LOGIN_PAGE_RESPONSE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/controller?command=show_user_login_page";
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };

    private static final String USER_LOGIN_PARAMETER_NAME = "userLogin";
    private static final String USER_PASSWORD_PARAMETER_NAME = "userPassword";
    private static final String USER_PASSWORD_REPEAT_PARAMETER_NAME = "userRepPassword";
    private static final String ERROR_MESSAGE_ATTRIBUTE_NAME = "errorMessage";
    private final UserService userService = RealUserService.INSTANCE;

    @Override
    public ResponseContext execute(RequestContext request) {
        String login = String.valueOf(request.getParameter(USER_LOGIN_PARAMETER_NAME));
        final String password = String.valueOf(request.getParameter(USER_PASSWORD_PARAMETER_NAME));
        final String repPassword = String.valueOf(request.getParameter(USER_PASSWORD_REPEAT_PARAMETER_NAME));
        login = login.replace("\\s+", "");
        boolean signedUpSuccessfully = userService.signUp(login, password, repPassword);
        if (signedUpSuccessfully) {
            return LOGIN_PAGE_RESPONSE;
        } else {
            request.setAttribute(ERROR_MESSAGE_ATTRIBUTE_NAME, Boolean.TRUE);
            return ShowUserSignUpPage.INSTANCE.execute(request);
        }
    }
}
