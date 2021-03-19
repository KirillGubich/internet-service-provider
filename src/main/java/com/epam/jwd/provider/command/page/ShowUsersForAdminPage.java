package com.epam.jwd.provider.command.page;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.model.dto.UserDto;
import com.epam.jwd.provider.service.UserService;
import com.epam.jwd.provider.service.impl.RealUserService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public enum ShowUsersForAdminPage implements Command {
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
        UserService service = RealUserService.INSTANCE;
        List<UserDto> allUsers = service.findAll();
        if (allUsers.isEmpty()) {
            request.setAttribute("infoMessage", "No users were found");
        } else {
            request.setAttribute("users", allUsers);
        }
        return USERS_FOR_ADMIN_PAGE_RESPONSE;
    }
}
