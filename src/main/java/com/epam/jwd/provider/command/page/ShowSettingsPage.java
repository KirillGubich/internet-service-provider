package com.epam.jwd.provider.command.page;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.model.dto.UserDto;
import com.epam.jwd.provider.service.UserService;
import com.epam.jwd.provider.service.impl.RealUserService;

import java.util.Optional;

public enum ShowSettingsPage implements Command {
    INSTANCE;

    private static final ResponseContext SETTINGS_PAGE_RESPONSE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/settings.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    private final UserService userService = RealUserService.INSTANCE;
    private static final String USER_ROLE_SESSION_ATTRIBUTE_NAME = "accountId";
    private static final String USER_INFO_ATTRIBUTE_NAME = "userInfo";
    private static final String STATUS_ATTRIBUTE_NAME = "status";
    private static final String ACTIVE_STATUS = "Active";
    private static final String BLOCKED_STATUS = "Blocked";

    @Override
    public ResponseContext execute(RequestContext request) {
        Object accountId = request.getSessionAttribute(USER_ROLE_SESSION_ATTRIBUTE_NAME);
        if (accountId == null) {
            return ShowUserLoginPage.INSTANCE.execute(request);
        }
        Optional<UserDto> user = userService.findById((Integer) accountId);
        if (!user.isPresent()) {
            return ShowUserLoginPage.INSTANCE.execute(request);
        }
        request.setAttribute(USER_INFO_ATTRIBUTE_NAME, user.get());
        if (user.get().getActive()) {
            request.setAttribute(STATUS_ATTRIBUTE_NAME, ACTIVE_STATUS);
        } else {
            request.setAttribute(STATUS_ATTRIBUTE_NAME, BLOCKED_STATUS);
        }
        return SETTINGS_PAGE_RESPONSE;
    }
}
