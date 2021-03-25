package com.epam.jwd.provider.command.user;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.command.page.ShowUserLoginPage;
import com.epam.jwd.provider.exception.AccountAbsenceException;
import com.epam.jwd.provider.model.dto.UserDto;
import com.epam.jwd.provider.service.UserService;
import com.epam.jwd.provider.service.impl.RealUserService;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * User balance replenishment command.
 */
public enum TopUpBalanceCommand implements Command {
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
    private static final String TOP_UP_VALUE_PARAMETER_NAME = "topUpValue";
    private static final String ACCOUNT_ID_SESSION_ATTRIBUTE_NAME = "accountId";

    @Override
    public ResponseContext execute(RequestContext request) {
        BigDecimal topUpValue;
        try {
            topUpValue = new BigDecimal(request.getParameter(TOP_UP_VALUE_PARAMETER_NAME));
        } catch (NullPointerException e) {
            return SETTINGS_PAGE_RESPONSE_REDIRECT;
        }
        Integer accountId = (Integer) request.getSessionAttribute(ACCOUNT_ID_SESSION_ATTRIBUTE_NAME);
        if (accountId == null) {
            return ShowUserLoginPage.INSTANCE.execute(request);
        }
        UserService userService = RealUserService.INSTANCE;
        Optional<UserDto> user = userService.findById(accountId);
        if (!user.isPresent()) {
            return ShowUserLoginPage.INSTANCE.execute(request);
        }
        BigDecimal currentBalance = user.get().getBalance();
        BigDecimal updatedBalance = currentBalance.add(topUpValue);
        try {
            userService.updateBalance(accountId, updatedBalance);
        } catch (AccountAbsenceException e) {
            return ShowUserLoginPage.INSTANCE.execute(request);
        }
        return SETTINGS_PAGE_RESPONSE_REDIRECT;
    }
}
