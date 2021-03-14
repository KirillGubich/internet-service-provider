package com.epam.jwd.provider.command.admin;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.command.page.ShowSubscriptionSettingsPage;
import com.epam.jwd.provider.model.dto.SubscriptionDto;
import com.epam.jwd.provider.model.entity.SubscriptionStatus;
import com.epam.jwd.provider.service.SubscriptionService;
import com.epam.jwd.provider.service.impl.RealSubscriptionService;

import java.util.List;
import java.util.stream.Collectors;

public enum ViewSubscriptionRequestsCommand implements Command {
    INSTANCE;

    @Override
    public ResponseContext execute(RequestContext request) {
        SubscriptionService service = RealSubscriptionService.INSTANCE;
        final List<SubscriptionDto> allSubscriptions = service.findAll();
        List<SubscriptionDto> requestedSubscriptions = allSubscriptions
                .stream()
                .filter(subscription -> SubscriptionStatus.REQUESTED.equals(subscription.getStatus()))
                .collect(Collectors.toList());
        if (requestedSubscriptions.isEmpty()) {
            request.setAttribute("infoMessage", "No requested subscriptions were found");
        } else {
            request.setAttribute("userSubscriptions", requestedSubscriptions);
        }
        return ShowSubscriptionSettingsPage.INSTANCE.execute(request);
    }
}
