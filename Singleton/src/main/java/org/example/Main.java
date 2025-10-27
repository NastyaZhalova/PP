package org.example;

import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        Translator t1 = Translator.getInstance(new Locale("en"));
        Translator t2 = Translator.getInstance(new Locale("ru"));

        System.out.println(t1.translate("message.base")); // Hello
        System.out.println(t2.translate("message.base")); // Привет
    }
}
