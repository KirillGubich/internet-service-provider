package com.epam.jwd.provider.command.user;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.model.dto.SubscriptionDto;
import com.epam.jwd.provider.model.dto.UserDto;
import com.epam.jwd.provider.model.entity.SubscriptionStatus;
import com.epam.jwd.provider.service.SubscriptionService;
import com.epam.jwd.provider.service.UserService;
import com.epam.jwd.provider.service.impl.RealSubscriptionService;
import com.epam.jwd.provider.service.impl.RealUserService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Command to cancel a subscription request issued by the user.
 */
public enum CancelSubscriptionCommand implements Command {
    INSTANCE;

    private static final ResponseContext USER_PAGE_RESPONSE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/controller?command=show_profile";
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };

    private final SubscriptionService subscriptionService = RealSubscriptionService.INSTANCE;
    private static final String SUBSCRIPTION_ID_PARAMETER_NAME = "subId";
    private static final String USER_ID_PARAMETER_NAME = "userId";

    @Override
    public ResponseContext execute(RequestContext request) {
        Integer subscriptionId = Integer.valueOf(request.getParameter(SUBSCRIPTION_ID_PARAMETER_NAME));
        Integer accountId = Integer.valueOf(request.getParameter(USER_ID_PARAMETER_NAME));
        List<SubscriptionDto> userSubscriptions = subscriptionService.findUserSubscriptions(accountId);
        Optional<SubscriptionDto> subscriptionDto = userSubscriptions
                .stream()
                .filter(subscription -> subscription.getId().equals(subscriptionId))
                .findFirst();
        if (subscriptionDto.isPresent()) {
            refundMoney(accountId, subscriptionDto.get().getPrice());
            cancelSubscription(subscriptionDto.get());
        }
        return USER_PAGE_RESPONSE;
    }

    private void refundMoney(Integer accountId, BigDecimal valueToRefund) {
        UserService userService = RealUserService.INSTANCE;
        Optional<UserDto> user = userService.findById(accountId);
        if (user.isPresent()) {
            BigDecimal currentBalance = user.get().getBalance();
            BigDecimal updatedBalance = currentBalance.add(valueToRefund);
            userService.updateBalance(accountId, updatedBalance);
        }
    }

    private void cancelSubscription(SubscriptionDto subscription) {
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
                .withStatus(SubscriptionStatus.CANCELED)
                .build();
        subscriptionService.save(canceledSubscription);
    }
}
