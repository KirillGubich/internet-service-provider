package com.epam.jwd.provider.command.user;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.model.dto.UserDto;
import com.epam.jwd.provider.service.UserService;

import java.util.Optional;

public enum LoginCommand implements Command {
    INSTANCE;

    private final UserService userService;

    LoginCommand() {
        userService = UserService.INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext request) {
        final String login = String.valueOf(request.getParameter("userLogin"));
        final String password = String.valueOf(request.getParameter("userPassword"));
        final Optional<UserDto> user = userService.login(login, password);
        ResponseContext result;
        if (user.isPresent()) {
            request.setSessionAttribute("userRole", user.get().getRole()); //todo user Rank
            request.setSessionAttribute("userLogin", login);
            result = new ResponseContext() {
                @Override
                public String getPage() {
                    return "/controller?command=show_profile";
                }

                @Override
                public boolean isRedirect() {
                    return true;
                }
            }; //TODO: rewrite with redirect
        } else {
            request.setAttribute("errorMessage", "invalid credentials"); //todo error message log in
            result = new ResponseContext() {
                @Override
                public String getPage() {
                    return "/controller?command=show_user_login_page";
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
