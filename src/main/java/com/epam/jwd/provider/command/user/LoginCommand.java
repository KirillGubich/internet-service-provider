package com.epam.jwd.provider.command.user;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.model.dto.UserDto;
import com.epam.jwd.provider.model.entity.UserRole;
import com.epam.jwd.provider.service.UserService;
import com.epam.jwd.provider.service.impl.RealUserService;

import java.util.Optional;

public enum LoginCommand implements Command {
    INSTANCE;

    private final UserService userService;
    private static final ResponseContext ADMIN_PAGE_RESPONSE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/controller?command=show_profile"; //todo admin page
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
    private static final ResponseContext LOGIN_PAGE_RESPONSE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/controller?command=show_user_login_page";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    LoginCommand() {
        userService = RealUserService.INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext request) {
        final String login = String.valueOf(request.getParameter("userLogin"));
        final String password = String.valueOf(request.getParameter("userPassword"));
        final Optional<UserDto> user = userService.login(login, password);
        ResponseContext result;
        if (user.isPresent()) {
            request.setSessionAttribute("userRole", user.get().getRole());
            request.setSessionAttribute("userLogin", login); //todo session params?
            if (user.get().getRole().equals(UserRole.ADMIN)) {
                result = ADMIN_PAGE_RESPONSE;
            } else {
                result = USER_PAGE_RESPONSE;
            }
        } else {
            result = LOGIN_PAGE_RESPONSE;
        }
        return result;
    }
}
