package org.example;

public class Hotel {
    private String city;
    private String name;
    private int stars;

    public Hotel(String city, String name, int stars) {
        this.city = city;
        this.name = name;
        this.stars = stars;
    }

    public String getCity() { return city; }
    public String getName() { return name; }
    public int getStars(){return stars;}

    @Override
    public String toString() {
        return name + " (" + stars + " stars)";
    }
}
