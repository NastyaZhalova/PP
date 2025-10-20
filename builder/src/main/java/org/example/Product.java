package org.example;

import java.util.Locale;
import java.util.ResourceBundle;

public class Product {
    private String name;
    private double price;

    public static class Builder {
        private String name;
        private double price;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setPrice(double price) {
            this.price = price;
            return this;
        }

        public Product build() {
            return new Product(name, price);
        }
    }

    private Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public void display(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale,
                ResourceBundle.Control.getControl(ResourceBundle.Control.FORMAT_DEFAULT));

        System.out.println(bundle.getString("product.name") + ": " + name);
        System.out.println(bundle.getString("product.price") + ": " + price);
    }
}
