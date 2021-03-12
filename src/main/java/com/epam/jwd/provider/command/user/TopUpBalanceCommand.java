package com.epam.jwd.provider.command.user;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.command.page.ShowUserLoginPage;
import com.epam.jwd.provider.model.dto.UserDto;
import com.epam.jwd.provider.service.UserService;
import com.epam.jwd.provider.service.impl.RealUserService;

import java.math.BigDecimal;
import java.util.Optional;

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

    @Override
    public ResponseContext execute(RequestContext request) {
        BigDecimal topUpValue = new BigDecimal(request.getParameter("topUpValue"));
        Integer accountId = (Integer) request.getSessionAttribute("accountId");
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
        userService.updateBalance(accountId, updatedBalance);
        return SETTINGS_PAGE_RESPONSE_REDIRECT;
    }
}
