package com.epam.jwd.provider.command.page;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.model.dto.SubscriptionDto;
import com.epam.jwd.provider.model.dto.TariffDto;
import com.epam.jwd.provider.service.SubscriptionService;
import com.epam.jwd.provider.service.impl.RealSubscriptionService;
import com.epam.jwd.provider.service.impl.RealTariffService;

import java.util.Collections;
import java.util.List;

public enum ShowUserProfilePage implements Command {
    INSTANCE;

    private static final ResponseContext PROFILE_PAGE_RESPONSE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/profile.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    private final SubscriptionService service = RealSubscriptionService.INSTANCE;
    private static final int RECORDS_PER_PAGE = 2;

    @Override
    public ResponseContext execute(RequestContext request) {
        Object accountId = request.getSessionAttribute("accountId");
        if (accountId == null) {
            return ShowUserLoginPage.INSTANCE.execute(request);
        }
        List<SubscriptionDto> userSubscriptions = service.findUserSubscriptions((Integer) accountId);
        List<TariffDto> specialOffers = RealTariffService.INSTANCE.findSpecialOffers();
        Collections.reverse(userSubscriptions);
        doPagination(request, userSubscriptions);
        request.setAttribute("specialOffers", specialOffers);
        return PROFILE_PAGE_RESPONSE;
    }

    private void doPagination(RequestContext request, List<SubscriptionDto> userSubscriptions) {
        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        int noOfRecords = userSubscriptions.size();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / RECORDS_PER_PAGE);
        int toIndex = page != noOfPages ? page * RECORDS_PER_PAGE : noOfRecords;
        List<SubscriptionDto> userSubscriptionsOnPage = userSubscriptions
                .subList((page - 1) * RECORDS_PER_PAGE, toIndex);
        request.setAttribute("userSubscriptions", userSubscriptionsOnPage);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
    }
}
