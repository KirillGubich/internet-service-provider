package com.epam.jwd.provider.command.user;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.model.dto.UserDto;
import com.epam.jwd.provider.service.impl.RealUserService;

import java.util.Optional;

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
    private static final ResponseContext SIGN_UP_PAGE_RESPONSE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/controller?command=show_user_sign_up_page";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    SignUpCommand() {
        this.userService = RealUserService.INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext request) {
        final String login = String.valueOf(request.getParameter("userLogin"));
        final String password = String.valueOf(request.getParameter("userPassword"));
        final String repPassword = String.valueOf(request.getParameter("userRepPassword"));
        Optional<UserDto> user = userService.find(UserDto.builder().withLogin(login).build());
        if (!user.isPresent() && password.equals(repPassword)) {
            userService.create(UserDto.builder().withLogin(login).withPassword(password).build());
            return LOGIN_PAGE_RESPONSE;
        } else {
            request.setAttribute("errorMessage", "invalid credentials");
            return SIGN_UP_PAGE_RESPONSE;
        }
    }
}
