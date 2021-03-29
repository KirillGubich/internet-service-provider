package com.epam.jwd.provider.command.admin;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.model.dto.SubscriptionDto;
import com.epam.jwd.provider.service.SubscriptionService;
import com.epam.jwd.provider.service.impl.RealSubscriptionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Searches for subscriptions by a given ID or user ID.
 * The search result is displayed on the page or a corresponding no result message.
 */
public enum FindSubscriptionCommand implements Command {
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

    private static final String ID_PARAMETER_NAME = "id";
    private static final String USER_ID_PARAMETER_NAME = "userId";
    private static final String USER_SUBSCRIPTIONS_ATTRIBUTE_NAME = "userSubscriptions";
    private final SubscriptionService service = RealSubscriptionService.INSTANCE;

    @Override
    public ResponseContext execute(RequestContext request) {
        String idParameter = request.getParameter(ID_PARAMETER_NAME);
        List<SubscriptionDto> subscriptions = new ArrayList<>();
        Optional<SubscriptionDto> subscription = findSubscriptionByIdParameter(idParameter);
        if (!subscription.isPresent()) {
            String userIdParameter = request.getParameter(USER_ID_PARAMETER_NAME);
            List<SubscriptionDto> userSubscriptions = findSubscriptionByUserIdParameter(userIdParameter);
            subscriptions.addAll(userSubscriptions);
        } else {
            subscriptions.add(subscription.get());
        }
        request.setAttribute(USER_SUBSCRIPTIONS_ATTRIBUTE_NAME, subscriptions);
        return USERS_SUBSCRIPTION_SETTINGS_PAGE_RESPONSE;
    }

    private Optional<SubscriptionDto> findSubscriptionByIdParameter(String idParameter) {
        int id;
        try {
            id = Integer.parseInt(idParameter);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
        return service.findById(id);
    }

    private List<SubscriptionDto> findSubscriptionByUserIdParameter(String userIdParameter) {
        int id;
        try {
            id = Integer.parseInt(userIdParameter);
        } catch (NumberFormatException e) {
            return new ArrayList<>();
        }
        return service.findUserSubscriptions(id);
    }
}
