package com.epam.jwd.provider.command.admin;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.model.dto.UserDto;
import com.epam.jwd.provider.service.UserService;
import com.epam.jwd.provider.service.impl.RealUserService;

import java.util.Optional;

/**
 * Deletes user account.
 */
public enum DeleteAccountCommand implements Command {
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
    private static final String USER_ID_PARAMETER_NAME = "userId";

    @Override
    public ResponseContext execute(RequestContext request) {
        int userId;
        try {
            userId = Integer.parseInt(request.getParameter(USER_ID_PARAMETER_NAME));
        } catch (NumberFormatException e) {
            return USERS_FOR_ADMIN_PAGE_RESPONSE;
        }
        UserService service = RealUserService.INSTANCE;
        Optional<UserDto> user = service.findById(userId);
        user.ifPresent(service::delete);
        return USERS_FOR_ADMIN_PAGE_RESPONSE;
    }
}
