package com.epam.jwd.provider.command.admin;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.model.dto.TariffDto;
import com.epam.jwd.provider.service.TariffService;
import com.epam.jwd.provider.service.impl.RealTariffService;

import java.math.BigDecimal;

/**
 * Command for creating and updating tariff information.
 */
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
    private static final String TARIFF_NAME_PARAMETER_NAME = "tariffName";
    private static final String DESCRIPTION_PARAMETER_NAME = "description";
    private static final String SPECIAL_OFFER_PARAMETER_NAME = "specialOffer";
    private static final String PRICE_PARAMETER_NAME = "price";
    private static final String DOWNLOAD_SPEED_PARAMETER_NAME = "downloadSpeed";
    private static final String UPLOAD_SPEED_PARAMETER_NAME = "uploadSpeed";
    private static final String ACTION_PARAMETER_NAME = "action";

    @Override
    public ResponseContext execute(RequestContext request) {
        String name = request.getParameter(TARIFF_NAME_PARAMETER_NAME);
        String description = request.getParameter(DESCRIPTION_PARAMETER_NAME);
        String specialOffer = request.getParameter(SPECIAL_OFFER_PARAMETER_NAME);
        BigDecimal price;
        double downloadSpeed;
        double uploadSpeed;
        try {
            price = new BigDecimal(request.getParameter(PRICE_PARAMETER_NAME));
            downloadSpeed = Double.parseDouble(request.getParameter(DOWNLOAD_SPEED_PARAMETER_NAME));
            uploadSpeed = Double.parseDouble(request.getParameter(UPLOAD_SPEED_PARAMETER_NAME));
        } catch (NumberFormatException | NullPointerException e) {
            return TARIFF_SETTINGS_PAGE_RESPONSE;
        }

        String action = request.getParameter(ACTION_PARAMETER_NAME);
        TariffDto tariff = getTariffDto(name, description, specialOffer, price, downloadSpeed, uploadSpeed);
        TariffService tariffService = RealTariffService.INSTANCE;
        if (CREATE_ACTION.equals(action)) {
            tariffService.create(tariff);
        } else if (UPDATE_ACTION.equals(action)) {
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
