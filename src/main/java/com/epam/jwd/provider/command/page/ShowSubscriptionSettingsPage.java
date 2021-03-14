package com.epam.jwd.provider.command.page;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;

public enum ShowSubscriptionSettingsPage implements Command {
    INSTANCE;

    private static final ResponseContext USERS_SUBSCRIPTION_SETTINGS_PAGE_RESPONSE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/subscriptionSettings.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    @Override
    public ResponseContext execute(RequestContext request) {
        return USERS_SUBSCRIPTION_SETTINGS_PAGE_RESPONSE;
    }
}
