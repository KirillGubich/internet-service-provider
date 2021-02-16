package com.epam.jwd.provider.command.user;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;

public enum SignUpCommand implements Command {
    INSTANCE;

    @Override
    public ResponseContext execute(RequestContext request) {
        return null;
    }
}
