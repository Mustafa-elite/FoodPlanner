package com.example.foodplanner.model.remote.server.countries;

import java.util.List;

public class Countries {
    private List<Country> countries;

    public List<Country> getCountries() {
        return countries;
    }

    @Override
    public String toString() {
        return "Countries{" +
                "countries=" + countries +
                '}';
    }

    public Countries(List<Country> countries) {
        this.countries = countries;
    }
}
