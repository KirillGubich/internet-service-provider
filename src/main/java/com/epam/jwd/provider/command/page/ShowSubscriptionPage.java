package com.epam.jwd.provider.command.page;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.model.dto.TariffDto;
import com.epam.jwd.provider.service.TariffService;
import com.epam.jwd.provider.service.impl.RealTariffService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public enum ShowSubscriptionPage implements Command {
    INSTANCE;

    private static final ResponseContext SUBSCRIPTION_PAGE_RESPONSE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/subscription.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    private static final TariffService tariffService = RealTariffService.INSTANCE;
    private static final String TARIFF_PARAMETER_NAME = "tariff";
    private static final String TARIFFS_ATTRIBUTE_NAME = "tariffs";
    private static final String SPECIAL_OFFERS_ATTRIBUTE_NAME = "specialOffers";

    @Override
    public ResponseContext execute(RequestContext request) {
        String tariffName = request.getParameter(TARIFF_PARAMETER_NAME);
        List<TariffDto> tariffs = tariffService.findAll();
        if (tariffName != null) {
            Optional<TariffDto> tariffDto = tariffService.findByName(tariffName);
            if (tariffDto.isPresent()) {
                tariffs = new ArrayList<>();
                tariffs.add(tariffDto.get());
            }
        }
        List<TariffDto> specialOffers = tariffService.findSpecialOffers();
        request.setAttribute(TARIFFS_ATTRIBUTE_NAME, tariffs);
        request.setAttribute(SPECIAL_OFFERS_ATTRIBUTE_NAME, specialOffers);
        return SUBSCRIPTION_PAGE_RESPONSE;
    }
}
