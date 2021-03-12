package com.epam.jwd.provider.command.user;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.command.page.ShowSubscriptionPage;
import com.epam.jwd.provider.factory.impl.AddressDtoFactory;
import com.epam.jwd.provider.model.dto.AddressDto;
import com.epam.jwd.provider.model.dto.SubscriptionDto;
import com.epam.jwd.provider.model.dto.TariffDto;
import com.epam.jwd.provider.model.dto.UserDto;
import com.epam.jwd.provider.model.entity.SubscriptionStatus;
import com.epam.jwd.provider.service.SubscriptionService;
import com.epam.jwd.provider.service.TariffService;
import com.epam.jwd.provider.service.UserService;
import com.epam.jwd.provider.service.impl.RealSubscriptionService;
import com.epam.jwd.provider.service.impl.RealTariffService;
import com.epam.jwd.provider.service.impl.RealUserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public enum SubscribeCommand implements Command {
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
    private final TariffService tariffService = RealTariffService.INSTANCE;

    @Override
    public ResponseContext execute(RequestContext request) {
        String tariff = String.valueOf(request.getParameter("tariff"));
        int validity = Integer.parseInt(request.getParameter("validity"));
        String city = String.valueOf(request.getParameter("city"));
        String address = String.valueOf(request.getParameter("address"));

        Optional<TariffDto> tariffInfo = tariffService.findByName(tariff);
        if (!tariffInfo.isPresent()) {
            request.setAttribute("errorMessage", "Selected tariff is not available.");
            return ShowSubscriptionPage.INSTANCE.execute(request);
        }

        Integer accountId = (Integer) request.getSessionAttribute("accountId");
        Optional<Integer> tariffId = tariffService.findTariffId(tariffInfo.get().getName());
        if (!tariffId.isPresent()) {
            request.setAttribute("errorMessage", "Selected tariff is not available.");
            return ShowSubscriptionPage.INSTANCE.execute(request);
        }

        AddressDto addressDto = AddressDtoFactory.INSTANCE.create(city, address);
        BigDecimal price = countPrice(validity, tariffInfo.get());
        if (!payForSubscription(accountId, price)) {
            request.setAttribute("errorMessage", "Account is blocked. Contact support.");
            return ShowSubscriptionPage.INSTANCE.execute(request);
        }

        SubscriptionDto subscriptionDto = createDto(tariffInfo.get(), accountId, tariffId.get(), validity,
                price, addressDto);
        subscriptionService.create(subscriptionDto);

        return USER_PAGE_RESPONSE;
    }


    private BigDecimal countPrice(int validity, TariffDto tariffInfo) {
        BigDecimal costPerDay = tariffInfo.getCostPerDay();
        return costPerDay.multiply(new BigDecimal(validity));
    }

    private SubscriptionDto createDto(TariffDto tariff, Integer userId, Integer tariffId, Integer validity,
                                      BigDecimal price, AddressDto addressDto) {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(validity);
        return SubscriptionDto.builder()
                .withUserId(userId)
                .withTariffId(tariffId)
                .withPrice(price)
                .withStartDate(startDate)
                .withEndDate(endDate)
                .withTariffName(tariff.getName())
                .withTariffDescription(tariff.getDescription() + ". Speed: " + tariff.getDownloadSpeed()
                        + "/" + tariff.getUploadSpeed() + " MBit/s")
                .withStatus(SubscriptionStatus.REQUESTED)
                .withAddress(addressDto)
                .build();
    }

    private boolean payForSubscription(Integer userId, BigDecimal cost) {
        if (userId == null || cost == null) {
            return false;
        }
        UserService userService = RealUserService.INSTANCE;
        Optional<UserDto> user = userService.findById(userId);
        if (!user.isPresent() || !user.get().getActive()) {
            return false;
        }
        BigDecimal currentBalance = user.get().getBalance();
        BigDecimal updatedBalance = currentBalance.subtract(cost);
        userService.updateBalance(userId, updatedBalance);
        return true;
    }
}
