package org.example;

public abstract class Device implements EventResponsive {
    protected String name;
    protected boolean isOn;

    public Device(String name) {
        this.name = name;
        this.isOn = false;
    }

    public void turnOn() {
        isOn = true;
        System.out.println(name + " включено.");
    }

    public void turnOff() {
        isOn = false;
        System.out.println(name + " выключено.");
    }

    public void status() {
        System.out.println(name + " статус: " + (isOn ? "включено" : "выключено"));
    }
}
