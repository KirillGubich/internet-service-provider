package com.epam.jwd.provider.command.admin;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.model.dto.UserDto;
import com.epam.jwd.provider.service.UserService;
import com.epam.jwd.provider.service.impl.RealUserService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Displays all users with a negative balance.
 */
public enum ViewDebtorsCommand implements Command {
    INSTANCE;

    private static final ResponseContext USERS_FOR_ADMIN_PAGE_RESPONSE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/usersForAdmin.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    private static final int RECORDS_PER_PAGE = 4;
    private static final String PAGE_PARAMETER_NAME = "page";
    private static final String USERS_ATTRIBUTE_NAME = "users";
    private static final String PAGES_COUNT_ATTRIBUTE_NAME = "noOfPages";
    private static final String CURRENT_PAGE_ATTRIBUTE_NAME = "currentPage";
    private static final String COMMAND_ATTRIBUTE_NAME = "command";
    private static final String PAGE_COMMAND = "view_debtors";

    @Override
    public ResponseContext execute(RequestContext request) {
        UserService service = RealUserService.INSTANCE;
        List<UserDto> allUsers = service.findAll();
        List<UserDto> debtors = allUsers.stream()
                .filter(user -> user.getBalance() != null)
                .filter(user -> user.getBalance().compareTo(new BigDecimal("0")) < 0)
                .collect(Collectors.toList());
        if (!debtors.isEmpty()) {
            doPagination(request, debtors);
        }
        return USERS_FOR_ADMIN_PAGE_RESPONSE;
    }

    private void doPagination(RequestContext request, List<UserDto> users) {
        int page = 1;
        if (request.getParameter(PAGE_PARAMETER_NAME) != null) {
            page = Integer.parseInt(request.getParameter(PAGE_PARAMETER_NAME));
        }
        int noOfRecords = users.size();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / RECORDS_PER_PAGE);
        int toIndex = page != noOfPages ? page * RECORDS_PER_PAGE : noOfRecords;
        List<UserDto> usersOnPage = users
                .subList((page - 1) * RECORDS_PER_PAGE, toIndex);
        request.setAttribute(USERS_ATTRIBUTE_NAME, usersOnPage);
        request.setAttribute(PAGES_COUNT_ATTRIBUTE_NAME, noOfPages);
        request.setAttribute(CURRENT_PAGE_ATTRIBUTE_NAME, page);
        request.setAttribute(COMMAND_ATTRIBUTE_NAME, PAGE_COMMAND);
    }
}
