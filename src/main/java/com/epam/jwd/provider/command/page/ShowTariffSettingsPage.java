package com.epam.jwd.provider.command.page;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.model.dto.TariffDto;
import com.epam.jwd.provider.service.impl.RealTariffService;

import java.util.List;

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

    @Override
    public ResponseContext execute(RequestContext request) {
        List<TariffDto> tariffs = RealTariffService.INSTANCE.findAll();
        request.setAttribute("tariffs", tariffs);
        return TARIFF_SETTINGS_PAGE_RESPONSE;
    }
}
