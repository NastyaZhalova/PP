package org.example;

import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        Message msg = new ExclamationDecorator(new SimpleMessage());

        System.out.println(msg.getText(new Locale("en"))); // Hello!
        System.out.println(msg.getText(new Locale("ru"))); // Привет!
    }
}
