package org.example;

import java.util.Locale;
import java.util.ResourceBundle;

public class Translator {
    private static Translator instance;
    private Locale locale;
    private ResourceBundle bundle;

    private Translator(Locale locale) {
        this.locale = locale;
        this.bundle = ResourceBundle.getBundle("messages", locale);
    }

    public static Translator getInstance(Locale locale) {
        if (instance == null || !instance.locale.equals(locale)) {
            instance = new Translator(locale);
        }
        return instance;
    }

    public String translate(String key) {
        return bundle.getString(key);
    }
}
