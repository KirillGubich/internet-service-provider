package com.epam.jwd.provider.command.admin;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.command.page.ShowUsersForAdminPage;
import com.epam.jwd.provider.model.dto.UserDto;
import com.epam.jwd.provider.service.UserService;
import com.epam.jwd.provider.service.impl.RealUserService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public enum ViewDebtorsCommand implements Command {
    INSTANCE;

    @Override
    public ResponseContext execute(RequestContext request) {
        UserService service = RealUserService.INSTANCE;
        List<UserDto> allUsers = service.findAll();
        List<UserDto> debtors = allUsers.stream()
                .filter(user -> user.getBalance() != null)
                .filter(user -> user.getBalance().compareTo(new BigDecimal("0")) < 0)
                .collect(Collectors.toList());
        if (debtors.isEmpty()) {
            request.setAttribute("infoMessage", "No debtors were found");
        } else {
            request.setAttribute("users", debtors);
        }
        return ShowUsersForAdminPage.INSTANCE.execute(request);
    }
}
