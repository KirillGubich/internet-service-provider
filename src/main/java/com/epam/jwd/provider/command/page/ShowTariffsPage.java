package com.epam.jwd.provider.command.page;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.model.dto.TariffDto;
import com.epam.jwd.provider.service.impl.RealTariffService;

import java.util.List;

/**
 * Shows page with information about tariffs and special offers.
 */
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

    private static final int RECORDS_PER_PAGE = 4;
    private static final String SPECIAL_OFFERS_ATTRIBUTE_NAME = "specialOffers";
    private static final String PAGE_PARAMETER_NAME = "page";
    private static final String TARIFFS_PAGE_ATTRIBUTE_NAME = "tariffs";
    private static final String PAGES_COUNT_ATTRIBUTE_NAME = "noOfPages";
    private static final String CURRENT_PAGE_ATTRIBUTE_NAME = "currentPage";

    @Override
    public ResponseContext execute(RequestContext request) {
        List<TariffDto> tariffs = RealTariffService.INSTANCE.findAll();
        List<TariffDto> specialOffers = RealTariffService.INSTANCE.findSpecialOffers();
        if (!tariffs.isEmpty()) {
            doPagination(request, tariffs);
        }
        request.setAttribute(SPECIAL_OFFERS_ATTRIBUTE_NAME, specialOffers);
        return TARIFFS_PAGE_RESPONSE;
    }

    private void doPagination(RequestContext request, List<TariffDto> tariffs) {
        int page = 1;
        if (request.getParameter(PAGE_PARAMETER_NAME) != null) {
            page = Integer.parseInt(request.getParameter(PAGE_PARAMETER_NAME));
        }
        int noOfRecords = tariffs.size();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / RECORDS_PER_PAGE);
        int toIndex = page != noOfPages ? page * RECORDS_PER_PAGE : noOfRecords;
        List<TariffDto> tariffsOnPage = tariffs.subList((page - 1) * RECORDS_PER_PAGE, toIndex);
        request.setAttribute(TARIFFS_PAGE_ATTRIBUTE_NAME, tariffsOnPage);
        request.setAttribute(PAGES_COUNT_ATTRIBUTE_NAME, noOfPages);
        request.setAttribute(CURRENT_PAGE_ATTRIBUTE_NAME, page);
    }
}
