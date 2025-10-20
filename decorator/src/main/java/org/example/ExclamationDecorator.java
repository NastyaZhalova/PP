package org.example;

import java.util.Locale;

public class ExclamationDecorator implements Message {
    private Message message;

    public ExclamationDecorator(Message message) {
        this.message = message;
    }

    public String getText(Locale locale) {
        return message.getText(locale) + "!";
    }
}
