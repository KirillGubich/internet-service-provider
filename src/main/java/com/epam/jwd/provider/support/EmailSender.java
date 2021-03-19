package com.epam.jwd.provider.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

public enum EmailSender {
    INSTANCE;

    private static final String MAIL_PROPERTIES_FILE_NAME = "mail.properties";
    private static final String MESSAGE_SUBJECT = "Support request";
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSender.class);

    public void sendMessage(String messageToSend) {
        final Properties properties = new Properties();
        try {
            properties.load(EmailSender.class.getClassLoader().getResourceAsStream(MAIL_PROPERTIES_FILE_NAME));
            final String supportEmail = properties.getProperty("mail.smtps.user");
            final String supportPassword = properties.getProperty("mail.smtps.password");
            Session mailSession = Session.getDefaultInstance(properties);
            MimeMessage message = createMimeMessage(messageToSend, supportEmail, mailSession);
            Transport transport = mailSession.getTransport();
            transport.connect(supportEmail, supportPassword);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (IOException | MessagingException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private MimeMessage createMimeMessage(String messageToSend, String supportEmail, Session mailSession)
            throws MessagingException {
        MimeMessage message = new MimeMessage(mailSession);
        message.setFrom(new InternetAddress(supportEmail));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(supportEmail));
        message.setSubject(MESSAGE_SUBJECT);
        message.setText(messageToSend);
        return message;
    }
}
