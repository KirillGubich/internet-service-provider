package com.epam.jwd.provider.command.admin;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.model.dto.SubscriptionDto;
import com.epam.jwd.provider.model.entity.SubscriptionStatus;
import com.epam.jwd.provider.service.SubscriptionService;
import com.epam.jwd.provider.service.UserService;
import com.epam.jwd.provider.service.impl.RealSubscriptionService;
import com.epam.jwd.provider.service.impl.RealUserService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Changes subscription status. Returns money to the user's account if necessary.
 * Upon completion, it redirects administrator to subscription settings page.
 */
public enum ChangeSubscriptionStatusCommand implements Command {
    INSTANCE;

    private static final ResponseContext SUBSCRIPTION_SETTINGS_PAGE_RESPONSE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/controller?command=show_subscription_settings_page";
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };

    private final SubscriptionService subscriptionService = RealSubscriptionService.INSTANCE;
    private final UserService userService = RealUserService.INSTANCE;
    private static final String SUBSCRIPTION_ID_PARAMETER_NAME = "subId";
    private static final String USER_ID_PARAMETER_NAME = "userId";
    private static final String STATUS_PARAMETER_NAME = "status";

    @Override
    public ResponseContext execute(RequestContext request) {
        int subscriptionId;
        Integer accountId;
        try {
            subscriptionId = Integer.parseInt(request.getParameter(SUBSCRIPTION_ID_PARAMETER_NAME));
            accountId = Integer.valueOf(request.getParameter(USER_ID_PARAMETER_NAME));
        } catch (NumberFormatException e) {
            return SUBSCRIPTION_SETTINGS_PAGE_RESPONSE;
        }
        SubscriptionStatus status = SubscriptionStatus.of(request.getParameter(STATUS_PARAMETER_NAME));
        Optional<SubscriptionDto> subscription = fetchSubscriptionInfo(subscriptionId, accountId);
        if (subscription.isPresent()) {

            if (SubscriptionStatus.CANCELED.equals(status) || SubscriptionStatus.DENIED.equals(status)) {
                userService.addValueToBalance(accountId, subscription.get().getPrice());
            }
            updateSubscriptionStatus(subscription.get(), status);
        }
        return SUBSCRIPTION_SETTINGS_PAGE_RESPONSE;
    }

    private Optional<SubscriptionDto> fetchSubscriptionInfo(Integer subscriptionId, Integer accountId) {
        List<SubscriptionDto> userSubscriptions = subscriptionService.findUserSubscriptions(accountId);
        return userSubscriptions
                .stream()
                .filter(Objects::nonNull)
                .filter(subscription -> subscription.getId().equals(subscriptionId))
                .findFirst();

    }

    private void updateSubscriptionStatus(SubscriptionDto subscription, SubscriptionStatus status) {
        SubscriptionDto canceledSubscription = SubscriptionDto.builder()
                .withId(subscription.getId())
                .withUserId(subscription.getUserId())
                .withTariffId(subscription.getTariffId())
                .withStartDate(subscription.getStartDate())
                .withEndDate(subscription.getEndDate())
                .withTariffName(subscription.getTariffName())
                .withTariffDescription(subscription.getTariffDescription())
                .withPrice(subscription.getPrice())
                .withAddress(subscription.getAddress())
                .withStatus(status)
                .build();
        subscriptionService.save(canceledSubscription);
    }
}
