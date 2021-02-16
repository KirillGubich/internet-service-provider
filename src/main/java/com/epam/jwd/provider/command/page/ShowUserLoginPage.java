package com.epam.jwd.provider.command.page;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;

public enum ShowUserLoginPage implements Command {
    INSTANCE;

    private static final ResponseContext LOGIN_PAGE_RESPONSE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/login.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    @Override
    public ResponseContext execute(RequestContext request) {
        return LOGIN_PAGE_RESPONSE;
    }
}
