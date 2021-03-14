package com.epam.jwd.provider.command.admin;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.model.dto.TariffDto;
import com.epam.jwd.provider.service.TariffService;
import com.epam.jwd.provider.service.impl.RealTariffService;

import java.math.BigDecimal;

public enum TariffServiceCommand implements Command {
    INSTANCE;

    private static final ResponseContext TARIFF_SETTINGS_PAGE_RESPONSE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/controller?command=show_tariff_settings_page";
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };
    private static final String CREATE_ACTION = "create";
    private static final String UPDATE_ACTION = "update";
    private static final String TARIFF_IS_SPECIAL_OFFER = "yes";

    @Override
    public ResponseContext execute(RequestContext request) {
        String name = request.getParameter("tariffName");
        String description = request.getParameter("description");
        String specialOffer = request.getParameter("specialOffer");
        BigDecimal price = new BigDecimal(request.getParameter("price"));
        Double downloadSpeed = Double.valueOf(request.getParameter("downloadSpeed"));
        Double uploadSpeed = Double.valueOf(request.getParameter("uploadSpeed"));
        String action = request.getParameter("action");
        TariffDto tariff = getTariffDto(name, description, specialOffer, price, downloadSpeed, uploadSpeed);
        TariffService tariffService = RealTariffService.INSTANCE;
        if (action.equals(CREATE_ACTION)) {
            tariffService.create(tariff);
        } else if (action.equals(UPDATE_ACTION)) {
            tariffService.save(tariff);
        }
        return TARIFF_SETTINGS_PAGE_RESPONSE;
    }

    private TariffDto getTariffDto(String name, String description, String specialOffer, BigDecimal price,
                                   Double downloadSpeed, Double uploadSpeed) {
        TariffDto tariff;
        if (TARIFF_IS_SPECIAL_OFFER.equals(specialOffer)) {
            tariff = createTariff(name, description, true, price, downloadSpeed, uploadSpeed);
        } else {
            tariff = createTariff(name, description, false, price, downloadSpeed, uploadSpeed);
        }
        return tariff;
    }

    private TariffDto createTariff(String name, String description, boolean specialOffer, BigDecimal price,
                                   Double downloadSpeed, Double uploadSpeed) {
        return TariffDto.builder()
                .withName(name)
                .withDescription(description)
                .withCostPerDay(price)
                .withSpecialOffer(specialOffer)
                .withDownloadSpeed(downloadSpeed)
                .withUploadSpeed(uploadSpeed)
                .build();
    }
}
