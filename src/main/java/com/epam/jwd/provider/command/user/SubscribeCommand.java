package com.epam.jwd.provider.command.user;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.command.page.ShowSubscriptionPage;
import com.epam.jwd.provider.factory.impl.AddressDtoFactory;
import com.epam.jwd.provider.model.dto.AddressDto;
import com.epam.jwd.provider.model.dto.SubscriptionDto;
import com.epam.jwd.provider.model.dto.TariffDto;
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

/**
 * Submits user's request for a subscription.
 */
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
    private static final String TARIFF_PARAMETER_NAME = "tariff";
    private static final String VALIDITY_PARAMETER_NAME = "validity";
    private static final String CITY_PARAMETER_NAME = "city";
    private static final String ADDRESS_PARAMETER_NAME = "address";
    private static final String ERROR_MESSAGE_ATTRIBUTE_NAME = "errorMessage";
    private static final String ACCOUNT_ID_SESSION_ATTRIBUTE_NAME = "accountId";
    private static final String SPEED_TITLE = ". Speed: ";
    private static final String SPEED_UNIT = " MBit/s";

    @Override
    public ResponseContext execute(RequestContext request) {
        int validity;
        try {
            validity = Integer.parseInt(request.getParameter(VALIDITY_PARAMETER_NAME));
        } catch (NumberFormatException e) {
            return ShowSubscriptionPage.INSTANCE.execute(request);
        }

        String tariff = String.valueOf(request.getParameter(TARIFF_PARAMETER_NAME));
        Optional<TariffDto> tariffInfo = tariffService.findByName(tariff);
        if (!tariffInfo.isPresent()) {
            return setErrorMessage(request);
        }

        Optional<Integer> tariffId = tariffService.findTariffId(tariffInfo.get().getName());
        if (!tariffId.isPresent()) {
            return setErrorMessage(request);
        }

        Integer accountId = (Integer) request.getSessionAttribute(ACCOUNT_ID_SESSION_ATTRIBUTE_NAME);
        BigDecimal price = countPrice(validity, tariffInfo.get());
        boolean payedSuccessfully = payForSubscription(accountId, price);
        if (!payedSuccessfully) {
            return setErrorMessage(request);
        }

        String city = String.valueOf(request.getParameter(CITY_PARAMETER_NAME));
        String address = String.valueOf(request.getParameter(ADDRESS_PARAMETER_NAME));
        createSubscription(validity, city, address, tariffInfo.get(), tariffId.get(), price, accountId);
        return USER_PAGE_RESPONSE;
    }

    private ResponseContext setErrorMessage(RequestContext request) {
        request.setAttribute(ERROR_MESSAGE_ATTRIBUTE_NAME, Boolean.TRUE);
        return ShowSubscriptionPage.INSTANCE.execute(request);
    }

    private void createSubscription(int validity, String city, String address, TariffDto tariffInfo, Integer tariffId,
                                    BigDecimal price, Integer accountId) {
        AddressDto addressDto = AddressDtoFactory.INSTANCE.create(city, address);
        SubscriptionDto subscriptionDto = createDto(tariffInfo, accountId, tariffId, validity,
                price, addressDto);
        subscriptionService.create(subscriptionDto);
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
                .withTariffDescription(tariff.getDescription() + SPEED_TITLE + tariff.getDownloadSpeed()
                        + "/" + tariff.getUploadSpeed() + SPEED_UNIT)
                .withStatus(SubscriptionStatus.REQUESTED)
                .withAddress(addressDto)
                .build();
    }

    private boolean payForSubscription(Integer userId, BigDecimal cost) {
        if (userId == null || cost == null) {
            return false;
        }
        UserService userService = RealUserService.INSTANCE;
        cost = cost.negate();
        userService.addValueToBalance(userId, cost);
        return true;
    }
}
