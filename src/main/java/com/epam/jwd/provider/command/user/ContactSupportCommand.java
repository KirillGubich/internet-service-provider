package com.epam.jwd.provider.command.user;

import com.epam.jwd.provider.command.Command;
import com.epam.jwd.provider.command.RequestContext;
import com.epam.jwd.provider.command.ResponseContext;
import com.epam.jwd.provider.support.EmailSender;

/**
 * Contact with support command. Sends users message to support email.
 */
public enum ContactSupportCommand implements Command {
    INSTANCE;

    private static final ResponseContext SETTINGS_PAGE_RESPONSE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/controller?command=show_settings_page";
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };
    private static final String NAME_PARAMETER_NAME = "name";
    private static final String EMAIL_PARAMETER_NAME = "email";
    private static final String SUBJECT_PARAMETER_NAME = "subject";
    private static final String MESSAGE_PARAMETER_NAME = "message";

    @Override
    public ResponseContext execute(RequestContext request) {
        String name = request.getParameter(NAME_PARAMETER_NAME);
        String email = request.getParameter(EMAIL_PARAMETER_NAME);
        String subject = request.getParameter(SUBJECT_PARAMETER_NAME);
        String message = request.getParameter(MESSAGE_PARAMETER_NAME);
        String messageForSupport =
                "Name: " + name + "\n" +
                "Email: " + email + "\n" +
                "Subject: " + subject + "\n\n" +
                 message;
        EmailSender.INSTANCE.sendMessage(messageForSupport);
        return SETTINGS_PAGE_RESPONSE;
    }
}
