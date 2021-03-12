package com.epam.jwd.provider.command.user;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.command.page.ShowSettingsPage;
import com.epam.jwd.provider.service.UserService;
import com.epam.jwd.provider.service.impl.RealUserService;

public enum ChangePasswordCommand implements Command {
    INSTANCE;

    private static final ResponseContext SETTINGS_PAGE_RESPONSE_REDIRECT = new ResponseContext() {
        @Override
        public String getPage() {
            return "/controller?command=show_settings_page";
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };

    @Override
    public ResponseContext execute(RequestContext request) {
        String password = request.getParameter("password");
        String newPassword = request.getParameter("newPassword");
        String repNewPassword = request.getParameter("repNewPassword");
        Integer accountId = (Integer) request.getSessionAttribute("accountId");
        UserService userService = RealUserService.INSTANCE;
        boolean passwordSuccessfulUpdated = userService.changePassword(accountId, password, newPassword, repNewPassword);
        if (passwordSuccessfulUpdated) {
            return SETTINGS_PAGE_RESPONSE_REDIRECT;
        } else {
            request.setAttribute("errorMessage", "An incorrect current password was entered");
            return ShowSettingsPage.INSTANCE.execute(request);
        }
    }
}
