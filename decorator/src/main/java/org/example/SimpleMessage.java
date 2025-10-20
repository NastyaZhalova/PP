package org.example;

import org.example.Message;

import java.util.ResourceBundle;
import java.util.Locale;

public class SimpleMessage implements Message {
    public String getText(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
        return bundle.getString("message.base");
    }
}
