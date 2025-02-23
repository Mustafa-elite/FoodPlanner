package com.example.foodplanner.model;

public class Ingredient {
    private String ingredient;
    private String measure;

    @Override
    public String toString() {
        return "Ingredient{" +
                "ingredient='" + ingredient + '\'' +
                ", measure='" + measure + '\'' +
                '}';
    }

    public Ingredient(String ingredient, String measure) {
        this.ingredient = ingredient;
        this.measure = measure;
    }

    public String getIngredient() { return ingredient; }
    public void setIngredient(String ingredient) { this.ingredient = ingredient; }

    public String getMeasure() { return measure; }
    public void setMeasure(String measure) { this.measure = measure; }
}
