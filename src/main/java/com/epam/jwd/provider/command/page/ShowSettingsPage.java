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

    @Override
    public ResponseContext execute(RequestContext request) {
        Object accountId = request.getSessionAttribute("accountId");
        if (accountId == null) {
            return ShowUserLoginPage.INSTANCE.execute(request);
        }
        Optional<UserDto> user = userService.findById((Integer) accountId);
        if (!user.isPresent()) {
            return ShowUserLoginPage.INSTANCE.execute(request);
        }
        request.setAttribute("userInfo", user.get());
        if (user.get().getActive()) {
            request.setAttribute("status", "Active");
        } else {
            request.setAttribute("status", "Blocked");        }
        return SETTINGS_PAGE_RESPONSE;
    }
}
