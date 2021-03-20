package com.epam.jwd.provider.command.page;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.model.dto.UserDto;
import com.epam.jwd.provider.service.UserService;
import com.epam.jwd.provider.service.impl.RealUserService;

import java.util.List;

public enum ShowUsersForAdminPage implements Command {
    INSTANCE;

    private static final int RECORDS_PER_PAGE = 4;

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

    @Override
    public ResponseContext execute(RequestContext request) {
        UserService service = RealUserService.INSTANCE;
        List<UserDto> allUsers = service.findAll();
        if (!allUsers.isEmpty()) {
            doPagination(request, allUsers);
        }
        return USERS_FOR_ADMIN_PAGE_RESPONSE;
    }

    private void doPagination(RequestContext request, List<UserDto> users) {
        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        int noOfRecords = users.size();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / RECORDS_PER_PAGE);
        int toIndex = page != noOfPages ? page * RECORDS_PER_PAGE : noOfRecords;
        List<UserDto> usersOnPage = users
                .subList((page - 1) * RECORDS_PER_PAGE, toIndex);
        request.setAttribute("users", usersOnPage);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
    }
}
