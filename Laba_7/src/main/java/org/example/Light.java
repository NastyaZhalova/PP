package org.example;

public class Light extends Device {
    private int brightness = 50;

    public Light(String name) {
        super(name);
    }

    public void setBrightness(int value) {
        brightness = value;
        System.out.println(name + " яркость установлена на " + brightness + "%");
    }

    @Override
    public void respondToEvent(String event) {
        if (event.equals("Night")) {
            turnOn();
            setBrightness(70);
        }
    }
}
