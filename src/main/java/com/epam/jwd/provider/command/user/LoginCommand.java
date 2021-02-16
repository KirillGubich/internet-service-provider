package com.epam.jwd.provider.command.user;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.domain.UserDto;
import com.epam.jwd.provider.service.UserService;

import java.util.Optional;

public enum LoginCommand implements Command {
    INSTANCE;

    private final UserService userService;

    LoginCommand() {
        userService = new UserService();
    }

    @Override
    public ResponseContext execute(RequestContext request) {
        final String name = String.valueOf(request.getAttribute("userName"));
        final String password = String.valueOf(request.getAttribute("userPassword"));
        final Optional<UserDto> user = userService.login(name, password);
        ResponseContext result;
        if (user.isPresent()) {
            request.setSessionAttribute("userName", name);
            result = new ResponseContext() {
                @Override
                public String getPage() {
                    return "/controller";
                }

                @Override
                public boolean isRedirect() {
                    return true;
                }
            }; //TODO: rewrite with redirect
        } else {
            request.setAttribute("errorMessage", "invalid credentials");
            result = new ResponseContext() {
                @Override
                public String getPage() {
                    return "/controller";
                }
                @Override
                public boolean isRedirect() {
                    return true;
                }
            }; //TODO: redirect
        }
        return result;
    }
}
