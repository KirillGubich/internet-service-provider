package com.epam.jwd.provider.command.admin;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.command.page.ShowUsersForAdminPage;
import com.epam.jwd.provider.model.dto.UserDto;
import com.epam.jwd.provider.service.UserService;
import com.epam.jwd.provider.service.impl.RealUserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public enum FindUserCommand implements Command {
    INSTANCE;

    @Override
    public ResponseContext execute(RequestContext request) {
        String login = request.getParameter("login");
        UserService userService = RealUserService.INSTANCE;
        Optional<UserDto> user = userService.findByLogin(login);
        List<UserDto> users = new ArrayList<>();
        if (user.isPresent()) {
            users.add(user.get());
            request.setAttribute("users", users);
        } else {
            request.setAttribute("infoMessage", "User with the given login was not found");
        }
        return ShowUsersForAdminPage.INSTANCE.execute(request);
    }
}
