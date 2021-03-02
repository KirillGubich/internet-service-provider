package com.epam.jwd.provider.command.page;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.factory.impl.TariffDtoFactory;
import com.epam.jwd.provider.model.dto.TariffDto;
import com.epam.jwd.provider.model.entity.Tariff;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public enum ShowTariffsPage implements Command {
    INSTANCE;

    private static final ResponseContext TARIFFS_PAGE_RESPONSE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/tariffs.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    @Override
    public ResponseContext execute(RequestContext request) {
        List<TariffDto> tariffs = new ArrayList<>();
        tariffs.add(TariffDtoFactory.INSTANCE.create("Super", new BigDecimal("1.5"), 50.0, 25.0)); //todo from db
        tariffs.add(TariffDtoFactory.INSTANCE.create("Max", new BigDecimal("1.3"), 570.0, 125.0));
        tariffs.add(TariffDtoFactory.INSTANCE.create("ProMAX", new BigDecimal("1.8"), 150.0, 225.0));
        request.setAttribute("tariffs", tariffs);
        return TARIFFS_PAGE_RESPONSE;
    }
}
