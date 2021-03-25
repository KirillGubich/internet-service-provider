package com.epam.jwd.provider.command.admin;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.model.dto.SubscriptionDto;
import com.epam.jwd.provider.service.SubscriptionService;
import com.epam.jwd.provider.service.impl.RealSubscriptionService;

import java.util.Optional;

/**
 * Deletes subscription.
 */
public enum DeleteSubscriptionCommand implements Command {
    INSTANCE;

    private static final ResponseContext SUBSCRIPTION_SETTINGS_PAGE_RESPONSE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/controller?command=show_subscription_settings_page";
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };
    private static final String SUBSCRIPTION_ID_PARAMETER_NAME = "subId";

    @Override
    public ResponseContext execute(RequestContext request) {
        int subId;
        try {
            subId = Integer.parseInt(request.getParameter(SUBSCRIPTION_ID_PARAMETER_NAME));
        } catch (NumberFormatException e) {
            return SUBSCRIPTION_SETTINGS_PAGE_RESPONSE;
        }
        SubscriptionService service = RealSubscriptionService.INSTANCE;
        Optional<SubscriptionDto> subscription = service.findById(subId);
        subscription.ifPresent(service::delete);
        return SUBSCRIPTION_SETTINGS_PAGE_RESPONSE;
    }
}
