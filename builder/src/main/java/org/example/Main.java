package org.example;

import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        Product product = new Product.Builder()
                .setName("Laptop")
                .setPrice(999.99)
                .build();

        product.display(new Locale("en")); // English
        product.display(new Locale("ru")); // Russian
    }
}
