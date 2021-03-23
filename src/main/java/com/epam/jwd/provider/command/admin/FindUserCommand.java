package com.epam.jwd.provider.command.admin;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.model.dto.UserDto;
import com.epam.jwd.provider.service.UserService;
import com.epam.jwd.provider.service.impl.RealUserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Searches for a user by a given ID.
 *  The search result is displayed on the page or a corresponding no result message.
 */
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

    private static final String ID_PARAMETER_NAME = "id";
    private static final String USERS_ATTRIBUTE_NAME = "users";

    @Override
    public ResponseContext execute(RequestContext request) {
        int id;
        try {
            id = Integer.parseInt(request.getParameter(ID_PARAMETER_NAME));
        } catch (NumberFormatException e) {
            return USERS_FOR_ADMIN_PAGE_RESPONSE;
        }
        UserService userService = RealUserService.INSTANCE;
        Optional<UserDto> user = userService.findById(id);
        List<UserDto> users = new ArrayList<>();
        if (user.isPresent()) {
            users.add(user.get());
            request.setAttribute(USERS_ATTRIBUTE_NAME, users);
        }
        return USERS_FOR_ADMIN_PAGE_RESPONSE;
    }
}
