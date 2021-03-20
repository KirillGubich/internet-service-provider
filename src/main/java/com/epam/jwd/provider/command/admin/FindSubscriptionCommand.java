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

    @Override
    public ResponseContext execute(RequestContext request) {
        int id;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            return USERS_SUBSCRIPTION_SETTINGS_PAGE_RESPONSE;
        }
        SubscriptionService service = RealSubscriptionService.INSTANCE;
        Optional<SubscriptionDto> subscriptionDto = service.findById(id);
        List<SubscriptionDto> subscriptions = new ArrayList<>();
        if (subscriptionDto.isPresent()) {
            subscriptions.add(subscriptionDto.get());
            request.setAttribute("userSubscriptions", subscriptions);
        }
        return USERS_SUBSCRIPTION_SETTINGS_PAGE_RESPONSE;
    }
}
