package com.epam.jwd.provider.command.user;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.model.dto.UserDto;
import com.epam.jwd.provider.model.entity.UserRole;
import com.epam.jwd.provider.service.UserService;

import java.util.Optional;

public enum SignUpCommand implements Command {
    INSTANCE;

    private final UserService userService;

    SignUpCommand() {
        this.userService = UserService.INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext request) {
        final String login = String.valueOf(request.getParameter("userLogin"));
        final String password = String.valueOf(request.getParameter("userPassword"));
        final String repPassword = String.valueOf(request.getParameter("userRepPassword"));
        Optional<UserDto> user = userService.findByName(login);
        if (!user.isPresent() && password.equals(repPassword)) {
            userService.add(UserDto.builder().withLogin(login).withPassword(password).build()); //todo factory and all params
            request.setSessionAttribute("userName", login);
            request.setSessionAttribute("userRole", UserRole.USER); //todo User rank
            return new ResponseContext() {
                @Override
                public String getPage() {
                    return "/controller?command=show_profile";
                }

                @Override
                public boolean isRedirect() {
                    return true;
                }
            };
        } else {
            return new ResponseContext() {
                @Override
                public String getPage() {
                    return "/controller?command=show_user_sign_up_page";
                }

                @Override
                public boolean isRedirect() {
                    return true;
                }
            };
        }

    }
}
