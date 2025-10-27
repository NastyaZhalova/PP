package org.example;

public class Thermostat extends Device {
    private int temperature = 22;

    public Thermostat(String name) {
        super(name);
    }

    public void setTemperature(int temp) {
        temperature = temp;
        System.out.println(name + " температура установлена на " + temperature + "°C");
    }

    @Override
    public void respondToEvent(String event) {
        if (event.equals("Cold")) {
            turnOn();
            setTemperature(24);
        }
    }
}
