package com.epam.jwd.provider.command.page;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.model.dto.SubscriptionDto;
import com.epam.jwd.provider.model.dto.TariffDto;
import com.epam.jwd.provider.service.SubscriptionService;
import com.epam.jwd.provider.service.impl.RealSubscriptionService;
import com.epam.jwd.provider.service.impl.RealTariffService;

import java.util.List;

public enum ShowUserProfilePage implements Command {
    INSTANCE;

    private static final ResponseContext PROFILE_PAGE_RESPONSE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/profile.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    private static final ResponseContext LOGIN_PAGE_RESPONSE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/controller?command=show_user_login_page";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    private final SubscriptionService service = RealSubscriptionService.INSTANCE;

    @Override
    public ResponseContext execute(RequestContext request) {
        Object accountId = request.getSessionAttribute("accountId");
        if (accountId == null) {
            return LOGIN_PAGE_RESPONSE;
        }
        List<SubscriptionDto> userSubscriptions = service.findUserSubscriptions((Integer) accountId);
        List<TariffDto> specialOffers = RealTariffService.INSTANCE.findSpecialOffers();
        request.setAttribute("userSubscriptions", userSubscriptions);
        request.setAttribute("specialOffers", specialOffers);
        return PROFILE_PAGE_RESPONSE;
    }
}
