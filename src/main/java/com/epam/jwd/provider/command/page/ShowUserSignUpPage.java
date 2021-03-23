package com.epam.jwd.provider.command.page;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;

/**
 * Shows user sign up page.
 */
public enum ShowUserSignUpPage implements Command {
    INSTANCE;

    private static final ResponseContext REGISTER_PAGE_RESPONSE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/signup.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    @Override
    public ResponseContext execute(RequestContext request) {
        return REGISTER_PAGE_RESPONSE;
    }
}
