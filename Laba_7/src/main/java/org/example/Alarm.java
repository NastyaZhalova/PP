package org.example;

public class Alarm extends Device {
    public Alarm(String name) {
        super(name);
    }

    @Override
    public void respondToEvent(String event) {
        if (event.equals("Intrusion")) {
            turnOn();
            System.out.println(name + " тревога! Сигнализация активирована.");
        }
    }
}
