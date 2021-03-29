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
    private static final String LOGIN_PARAMETER_NAME = "login";
    private static final String USERS_ATTRIBUTE_NAME = "users";
    private final UserService userService = RealUserService.INSTANCE;

    @Override
    public ResponseContext execute(RequestContext request) {
        String idParameter = request.getParameter(ID_PARAMETER_NAME);
        UserService userService = RealUserService.INSTANCE;
        List<UserDto> users = new ArrayList<>();
        Optional<UserDto> user = findUserByIdParameter(idParameter);
        if (!user.isPresent()) {
            String login = request.getParameter(LOGIN_PARAMETER_NAME);
            user = userService.findByLogin(login);
        }
        if (user.isPresent()) {
            users.add(user.get());
            request.setAttribute(USERS_ATTRIBUTE_NAME, users);
        }
        return USERS_FOR_ADMIN_PAGE_RESPONSE;
    }

    private Optional<UserDto> findUserByIdParameter(String idParameter) {
        int id;
        try {
            id = Integer.parseInt(idParameter);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
        return userService.findById(id);
    }
}
