package com.epam.jwd.provider.command.admin;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.model.dto.TariffDto;
import com.epam.jwd.provider.service.TariffService;
import com.epam.jwd.provider.service.impl.RealTariffService;

public enum DeleteTariffCommand implements Command {
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

    private static final String TARIFF_NAME_PARAMETER_NAME = "tariffName";

    @Override
    public ResponseContext execute(RequestContext request) {
        String tariffName = request.getParameter(TARIFF_NAME_PARAMETER_NAME);
        TariffDto tariffDto = TariffDto.builder().withName(tariffName).build();
        TariffService tariffService = RealTariffService.INSTANCE;
        tariffService.delete(tariffDto);
        return TARIFF_SETTINGS_PAGE_RESPONSE;
    }
}
