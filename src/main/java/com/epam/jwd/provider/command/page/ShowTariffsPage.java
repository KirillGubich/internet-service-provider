package com.epam.jwd.provider.command.page;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.model.dto.TariffDto;
import com.epam.jwd.provider.service.impl.RealTariffService;

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

    private static final int RECORDS_PER_PAGE = 4;

    @Override
    public ResponseContext execute(RequestContext request) {
        List<TariffDto> tariffs = RealTariffService.INSTANCE.findAll();
        List<TariffDto> specialOffers = RealTariffService.INSTANCE.findSpecialOffers();
        doPagination(request, tariffs);
        request.setAttribute("specialOffers", specialOffers);
        return TARIFFS_PAGE_RESPONSE;
    }

    private void doPagination(RequestContext request, List<TariffDto> tariffs) {
        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        int noOfRecords = tariffs.size();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / RECORDS_PER_PAGE);
        int toIndex = page != noOfPages ? page * RECORDS_PER_PAGE : noOfRecords;
        List<TariffDto> tariffsOnPage = tariffs.subList((page - 1) * RECORDS_PER_PAGE, toIndex);
        request.setAttribute("tariffs", tariffsOnPage);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
    }
}
