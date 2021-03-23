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

/**
 * Displays user profile with all his subscriptions and special offers.
 */
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
    private static final String ACCOUNT_ID_SESSION_ATTRIBUTE_NAME = "accountId";
    private static final String SPECIAL_OFFERS_ATTRIBUTE_NAME = "specialOffers";
    private static final String PAGE_PARAMETER_NAME = "page";
    private static final String USER_SUBSCRIPTIONS_ATTRIBUTE_NAME = "userSubscriptions";
    private static final String PAGES_COUNT_ATTRIBUTE_NAME = "noOfPages";
    private static final String CURRENT_PAGE_ATTRIBUTE_NAME = "currentPage";

    @Override
    public ResponseContext execute(RequestContext request) {
        Object accountId = request.getSessionAttribute(ACCOUNT_ID_SESSION_ATTRIBUTE_NAME);
        if (accountId == null) {
            return ShowUserLoginPage.INSTANCE.execute(request);
        }
        List<TariffDto> specialOffers = RealTariffService.INSTANCE.findSpecialOffers();
        List<SubscriptionDto> userSubscriptions = service.findUserSubscriptions((Integer) accountId);
        if (!userSubscriptions.isEmpty()) {
            Collections.reverse(userSubscriptions);
            doPagination(request, userSubscriptions);
        }
        request.setAttribute(SPECIAL_OFFERS_ATTRIBUTE_NAME, specialOffers);
        return PROFILE_PAGE_RESPONSE;
    }

    private void doPagination(RequestContext request, List<SubscriptionDto> userSubscriptions) {
        int page = 1;
        if (request.getParameter(PAGE_PARAMETER_NAME) != null) {
            page = Integer.parseInt(request.getParameter(PAGE_PARAMETER_NAME));
        }
        int noOfRecords = userSubscriptions.size();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / RECORDS_PER_PAGE);
        int toIndex = page != noOfPages ? page * RECORDS_PER_PAGE : noOfRecords;
        List<SubscriptionDto> userSubscriptionsOnPage = userSubscriptions
                .subList((page - 1) * RECORDS_PER_PAGE, toIndex);
        request.setAttribute(USER_SUBSCRIPTIONS_ATTRIBUTE_NAME, userSubscriptionsOnPage);
        request.setAttribute(PAGES_COUNT_ATTRIBUTE_NAME, noOfPages);
        request.setAttribute(CURRENT_PAGE_ATTRIBUTE_NAME, page);
    }
}
