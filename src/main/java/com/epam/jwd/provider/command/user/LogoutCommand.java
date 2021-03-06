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

    @Override
    public ResponseContext execute(RequestContext request) {
        request.setSessionAttribute("userRole", UserRole.GUEST);
        request.invalidateSession();
        return LOGOUT_RESPONSE;
    }
}
