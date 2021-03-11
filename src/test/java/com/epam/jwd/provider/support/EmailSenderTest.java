package com.epam.jwd.provider.support;

import org.junit.Test;

import static org.junit.Assert.*;

public class EmailSenderTest {

    @Test
    public void sendMessage() {
        EmailSender.INSTANCE.sendMessage("Test properties");
    }
}