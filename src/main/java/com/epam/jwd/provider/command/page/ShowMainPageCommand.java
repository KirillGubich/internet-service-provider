package com.epam.jwd.provider.command.page;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;

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

    @Override
    public ResponseContext execute(RequestContext request) {
        return MAIN_PAGE_RESPONSE;
    }

}
