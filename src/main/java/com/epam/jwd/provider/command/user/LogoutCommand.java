package com.epam.jwd.provider.command.user;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.model.entity.UserRole;

public enum LogoutCommand implements Command {
    INSTANCE;

    private static final ResponseContext LOGOUT_RESPONSE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/controller";
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };
    private static final String USER_ROLE_SESSION_ATTRIBUTE_NAME = "userRole";
    private static final String ACCOUNT_ID_SESSION_ATTRIBUTE_NAME = "accountId";
    private static final String USER_LOGIN_SESSION_ATTRIBUTE_NAME = "userLogin";

    @Override
    public ResponseContext execute(RequestContext request) {
        request.setSessionAttribute(USER_ROLE_SESSION_ATTRIBUTE_NAME, UserRole.GUEST);
        request.setSessionAttribute(USER_LOGIN_SESSION_ATTRIBUTE_NAME, null);
        request.setSessionAttribute(ACCOUNT_ID_SESSION_ATTRIBUTE_NAME, null);
        request.invalidateSession();
        return LOGOUT_RESPONSE;
    }
}
