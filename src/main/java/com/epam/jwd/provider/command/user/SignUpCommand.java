package com.epam.jwd.provider.command.user;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.command.page.ShowUserSignUpPage;
import com.epam.jwd.provider.service.impl.RealUserService;

public enum SignUpCommand implements Command {
    INSTANCE;

    private final RealUserService userService;
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

    SignUpCommand() {
        this.userService = RealUserService.INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext request) {
        String login = String.valueOf(request.getParameter("userLogin"));
        final String password = String.valueOf(request.getParameter("userPassword"));
        final String repPassword = String.valueOf(request.getParameter("userRepPassword"));
        login = login.replace("\\s+", "");
        boolean signedUpSuccessfully = userService.signUp(login, password, repPassword);
        if (signedUpSuccessfully) {
            return LOGIN_PAGE_RESPONSE;
        } else {
            request.setAttribute("errorMessage", "Such login already exist or password incorrect");
            return ShowUserSignUpPage.INSTANCE.execute(request);
        }
    }
}
