package com.epam.jwd.provider.command.admin;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.model.dto.SubscriptionDto;
import com.epam.jwd.provider.model.dto.UserDto;
import com.epam.jwd.provider.model.entity.SubscriptionStatus;
import com.epam.jwd.provider.model.entity.UserRole;
import com.epam.jwd.provider.service.SubscriptionService;
import com.epam.jwd.provider.service.UserService;
import com.epam.jwd.provider.service.impl.RealSubscriptionService;
import com.epam.jwd.provider.service.impl.RealUserService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public enum ChangeSubscriptionStatusCommand implements Command {
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

    private static final ResponseContext ADMIN_PAGE_RESPONSE = new ResponseContext() {
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

    @Override
    public ResponseContext execute(RequestContext request) {
        Integer subscriptionId = Integer.valueOf(request.getParameter("subId"));
        Integer accountId = Integer.valueOf(request.getParameter("userId"));
        SubscriptionStatus status = SubscriptionStatus.of(request.getParameter("status"));
        UserRole userRole = (UserRole)request.getSessionAttribute("userRole");
        Optional<SubscriptionDto> subscription = fetchSubscriptionInfo(subscriptionId, accountId);
        if (subscription.isPresent()) {
            if (status.equals(SubscriptionStatus.CANCELED) || status.equals(SubscriptionStatus.DENIED)) {
                refundMoney(accountId, subscription.get().getPrice());
            }
            updateSubscriptionStatus(subscription.get(), status);
        }
        return UserRole.ADMIN.equals(userRole) ? ADMIN_PAGE_RESPONSE : USER_PAGE_RESPONSE;
    }

    private Optional<SubscriptionDto> fetchSubscriptionInfo(Integer subscriptionId, Integer accountId) {
        List<SubscriptionDto> userSubscriptions = subscriptionService.findUserSubscriptions(accountId);
        return userSubscriptions
                .stream()
                .filter(subscription -> subscription.getId().equals(subscriptionId))
                .findFirst();
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
