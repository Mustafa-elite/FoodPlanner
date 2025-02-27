package com.example.foodplanner.model.remote.server.countries;
public class Country {
    private String name;

    public Country(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    @Override
    public String toString() {
        return "Country{" +
                "name='" + name + '\'' +
                '}';
    }
}
