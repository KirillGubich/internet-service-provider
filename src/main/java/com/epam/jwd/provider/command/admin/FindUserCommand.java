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

    private static final ResponseContext USERS_FOR_ADMIN_PAGE_RESPONSE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/usersForAdmin.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    @Override
    public ResponseContext execute(RequestContext request) {
        int id;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            request.setAttribute("infoMessage", "Incorrect user id");
            return USERS_FOR_ADMIN_PAGE_RESPONSE;
        }
        UserService userService = RealUserService.INSTANCE;
        Optional<UserDto> user = userService.findById(id);
        List<UserDto> users = new ArrayList<>();
        if (user.isPresent()) {
            users.add(user.get());
            request.setAttribute("users", users);
        } else {
            request.setAttribute("infoMessage", "User with the given id was not found");
        }
        return USERS_FOR_ADMIN_PAGE_RESPONSE;
    }
}
