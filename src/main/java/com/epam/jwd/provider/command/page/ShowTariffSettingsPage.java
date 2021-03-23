package com.epam.jwd.provider.command.page;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.model.dto.TariffDto;
import com.epam.jwd.provider.service.impl.RealTariffService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Shows tariffs settings page for administrator.
 */
public enum ShowTariffSettingsPage implements Command {
    INSTANCE;

    private static final ResponseContext TARIFF_SETTINGS_PAGE_RESPONSE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/tariffSettings.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    private static final String TARIFF_PARAMETER_NAME = "tariff";
    private static final String TARIFFS_ATTRIBUTE_NAME = "tariffs";

    @Override
    public ResponseContext execute(RequestContext request) {
        String tariffName = request.getParameter(TARIFF_PARAMETER_NAME);
        List<TariffDto> tariffs = RealTariffService.INSTANCE.findAll();
        if (tariffName != null) {
            Optional<TariffDto> tariffDto = RealTariffService.INSTANCE.findByName(tariffName);
            if (tariffDto.isPresent()) {
                tariffs = new ArrayList<>();
                tariffs.add(tariffDto.get());
            }
        }
        request.setAttribute(TARIFFS_ATTRIBUTE_NAME, tariffs);
        return TARIFF_SETTINGS_PAGE_RESPONSE;
    }
}
