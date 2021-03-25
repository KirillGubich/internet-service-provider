package com.epam.jwd.provider.command.admin;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.model.dto.UserDto;
import com.epam.jwd.provider.service.UserService;
import com.epam.jwd.provider.service.impl.RealUserService;

import java.util.Optional;

/**
 * Changes user's status. Upon completion, returns the administrator to user settings page.
 */
public enum ChangeUserStatusCommand implements Command {
    INSTANCE;

    private static final ResponseContext USERS_FOR_ADMIN_PAGE_RESPONSE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/controller?command=show_users_for_admin_page";
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };

    private static final String ID_PARAMETER_NAME = "id";
    private static final String ACTIVE_PARAMETER_NAME = "active";
    private static final String UNBLOCK_PARAMETER = "unblock";

    @Override
    public ResponseContext execute(RequestContext request) {
        int id;
        try {
            id = Integer.parseInt(request.getParameter(ID_PARAMETER_NAME));
        } catch (NumberFormatException e) {
            return USERS_FOR_ADMIN_PAGE_RESPONSE;
        }
        String active = request.getParameter(ACTIVE_PARAMETER_NAME);
        UserService userService = RealUserService.INSTANCE;
        Optional<UserDto> user = userService.findById(id);
        if (user.isPresent()) {
            UserDto userDto;
            if (active.equals(UNBLOCK_PARAMETER)) {
                userDto = updateUser(user.get(), true);
            } else {
                userDto = updateUser(user.get(), false);
            }
            userService.save(userDto);
        }
        return USERS_FOR_ADMIN_PAGE_RESPONSE;
    }

    private UserDto updateUser(UserDto userDto, Boolean status) {
        return UserDto.builder()
                .withId(userDto.getId())
                .withLogin(userDto.getLogin())
                .withPassword(userDto.getPassword())
                .withRole(userDto.getRole())
                .withBalance(userDto.getBalance())
                .withActive(status)
                .build();
    }
}
