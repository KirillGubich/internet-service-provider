package com.epam.jwd.provider.command.page;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.model.dto.TariffDto;
import com.epam.jwd.provider.service.impl.RealTariffService;

import java.util.List;

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

    @Override
    public ResponseContext execute(RequestContext request) {
        List<TariffDto> tariffs = RealTariffService.INSTANCE.findAll();
        List<TariffDto> specialOffers = RealTariffService.INSTANCE.findSpecialOffers();
        request.setAttribute("tariffs", tariffs);
        request.setAttribute("specialOffers", specialOffers);
        return SUBSCRIPTION_PAGE_RESPONSE;
    }
}
