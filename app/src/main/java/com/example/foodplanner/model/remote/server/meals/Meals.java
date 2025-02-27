package com.example.foodplanner.model.remote.server.meals;

import java.util.List;


public class Meals {
    private List<Meal> meals;

    @Override
    public String toString() {
        return "Meals{" +
                "meals=" + meals +
                '}';
    }

    public Meals(List<Meal> meals) {
        this.meals = meals;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }
}

