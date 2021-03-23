package com.epam.jwd.provider.command.admin;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.model.dto.SubscriptionDto;
import com.epam.jwd.provider.model.entity.SubscriptionStatus;
import com.epam.jwd.provider.service.SubscriptionService;
import com.epam.jwd.provider.service.impl.RealSubscriptionService;

import java.util.List;
import java.util.stream.Collectors;

public enum ViewSubscriptionRequestsCommand implements Command {
    INSTANCE;

    private static final ResponseContext USERS_SUBSCRIPTION_SETTINGS_PAGE_RESPONSE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/subscriptionSettings.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    private static final int RECORDS_PER_PAGE = 2;
    private static final String PAGE_PARAMETER_NAME = "page";
    private static final String USER_SUBSCRIPTIONS_ATTRIBUTE_NAME = "userSubscriptions";
    private static final String PAGES_COUNT_ATTRIBUTE_NAME = "noOfPages";
    private static final String CURRENT_PAGE_ATTRIBUTE_NAME = "currentPage";
    private static final String COMMAND_ATTRIBUTE_NAME = "command";
    private static final String PAGE_COMMAND = "view_subscription_requests";

    @Override
    public ResponseContext execute(RequestContext request) {
        SubscriptionService service = RealSubscriptionService.INSTANCE;
        final List<SubscriptionDto> allSubscriptions = service.findAll();
        List<SubscriptionDto> requestedSubscriptions = allSubscriptions
                .stream()
                .filter(subscription -> SubscriptionStatus.REQUESTED.equals(subscription.getStatus()))
                .collect(Collectors.toList());
        if (!requestedSubscriptions.isEmpty()) {
            doPagination(request, requestedSubscriptions);
        }
        return USERS_SUBSCRIPTION_SETTINGS_PAGE_RESPONSE;
    }

    private void doPagination(RequestContext request, List<SubscriptionDto> subscriptions) {
        int page = 1;
        if (request.getParameter(PAGE_PARAMETER_NAME) != null) {
            page = Integer.parseInt(request.getParameter(PAGE_PARAMETER_NAME));
        }
        int noOfRecords = subscriptions.size();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / RECORDS_PER_PAGE);
        int toIndex = page != noOfPages ? page * RECORDS_PER_PAGE : noOfRecords;
        List<SubscriptionDto> userSubscriptionsOnPage = subscriptions
                .subList((page - 1) * RECORDS_PER_PAGE, toIndex);
        request.setAttribute(USER_SUBSCRIPTIONS_ATTRIBUTE_NAME, userSubscriptionsOnPage);
        request.setAttribute(PAGES_COUNT_ATTRIBUTE_NAME, noOfPages);
        request.setAttribute(CURRENT_PAGE_ATTRIBUTE_NAME, page);
        request.setAttribute(COMMAND_ATTRIBUTE_NAME, PAGE_COMMAND);
    }
}
