package com.epam.jwd.provider.command.user;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.command.page.ShowMainPage;

public enum SetLocalizationCommand implements Command {
    INSTANCE;

    private static final String DEFAULT_LOCALE = "en";

    @Override
    public ResponseContext execute(RequestContext request) {
        String locale = request.getParameter("locale");
        if (locale == null) {
            locale = DEFAULT_LOCALE;
        }
        request.setSessionAttribute("locale", locale);
        return ShowMainPage.INSTANCE.execute(request);
    }
}
