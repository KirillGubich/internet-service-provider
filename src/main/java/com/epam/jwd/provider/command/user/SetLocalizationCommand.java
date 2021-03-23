package com.epam.jwd.provider.command.user;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.command.page.ShowMainPage;

/**
 * Localization setting command.
 */
public enum SetLocalizationCommand implements Command {
    INSTANCE;

    private static final String DEFAULT_LOCALE = "en";
    private static final String LOCALE_PARAMETER_NAME = "locale";
    private static final String LOCALE_SESSION_ATTRIBUTE_NAME = "locale";

    @Override
    public ResponseContext execute(RequestContext request) {
        String locale = request.getParameter(LOCALE_PARAMETER_NAME);
        if (locale == null) {
            locale = DEFAULT_LOCALE;
        }
        request.setSessionAttribute(LOCALE_SESSION_ATTRIBUTE_NAME, locale);
        return ShowMainPage.INSTANCE.execute(request);
    }
}
