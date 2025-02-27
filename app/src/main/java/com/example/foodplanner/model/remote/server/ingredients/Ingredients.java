package com.example.foodplanner.model.remote.server.ingredients;

import java.util.List;

public class Ingredients {
    private List<Ingredient> ingredients;

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public String toString() {
        return "Ingredients{" +
                "ingredients=" + ingredients +
                '}';
    }

    public Ingredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
