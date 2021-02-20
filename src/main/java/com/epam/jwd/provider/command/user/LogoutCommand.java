package com.epam.jwd.provider.command.user;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.command.page.ShowMainPageCommand;
import com.epam.jwd.provider.model.UserRole;

public enum LogoutCommand implements Command {
    INSTANCE;

    @Override
    public ResponseContext execute(RequestContext request) {
        request.setSessionAttribute("userRole", UserRole.GUEST);
        request.invalidateSession();
        return ShowMainPageCommand.INSTANCE.execute(request);
    }
}
