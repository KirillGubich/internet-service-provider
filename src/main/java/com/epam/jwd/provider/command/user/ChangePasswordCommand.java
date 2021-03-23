package com.epam.jwd.provider.command.user;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.command.page.ShowSettingsPage;
import com.epam.jwd.provider.command.page.ShowUserLoginPage;
import com.epam.jwd.provider.exception.AccountAbsenceException;
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
    private static final String PASSWORD_PARAMETER_NAME = "password";
    private static final String NEW_PASSWORD_PARAMETER_NAME = "newPassword";
    private static final String NEW_PASSWORD_REPEAT_PARAMETER_NAME = "repNewPassword";
    private static final String ACCOUNT_ID_SESSION_ATTRIBUTE_NAME = "accountId";
    private static final String ERROR_ATTRIBUTE_NAME = "error";

    @Override
    public ResponseContext execute(RequestContext request) {
        String password = request.getParameter(PASSWORD_PARAMETER_NAME);
        String newPassword = request.getParameter(NEW_PASSWORD_PARAMETER_NAME);
        String repNewPassword = request.getParameter(NEW_PASSWORD_REPEAT_PARAMETER_NAME);
        Integer accountId = (Integer) request.getSessionAttribute(ACCOUNT_ID_SESSION_ATTRIBUTE_NAME);
        UserService userService = RealUserService.INSTANCE;
        boolean passwordSuccessfulUpdated;
        try {
            passwordSuccessfulUpdated = userService.changePassword(accountId, password,
                    newPassword, repNewPassword);
        } catch (AccountAbsenceException e) {
            return ShowUserLoginPage.INSTANCE.execute(request);
        }
        if (passwordSuccessfulUpdated) {
            return SETTINGS_PAGE_RESPONSE_REDIRECT;
        } else {
            request.setAttribute(ERROR_ATTRIBUTE_NAME, Boolean.TRUE);
            return ShowSettingsPage.INSTANCE.execute(request);
        }
    }
}
