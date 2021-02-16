package com.epam.jwd.provider.command.page;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.domain.UserDto;
import com.epam.jwd.provider.service.UserService;

import java.util.Collections;
import java.util.List;

public enum ShowMainPageCommand implements Command {
    INSTANCE;

    private static final ResponseContext MAIN_PAGE_RESPONSE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/main.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    private final UserService userService = new UserService();

    @Override
    public ResponseContext execute(RequestContext request) {
        final List<UserDto> users = userService.findAll().orElse(Collections.emptyList());
        request.setAttribute("users", users);
        request.setAttribute("test", "MY TEXT");
        return MAIN_PAGE_RESPONSE;
    }

}
