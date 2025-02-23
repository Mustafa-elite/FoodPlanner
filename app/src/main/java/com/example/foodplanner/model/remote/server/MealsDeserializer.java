package com.example.foodplanner.model.remote.server;

import com.example.foodplanner.model.Ingredient;
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.Meals;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import com.google.gson.*;

import java.util.ArrayList;
import java.util.List;

public class MealsDeserializer implements JsonDeserializer<Meals> {
    @Override
    public Meals deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonArray mealsArray = jsonObject.getAsJsonArray("meals");

        List<Meal> mealsList = new ArrayList<>();

        for (JsonElement mealElement : mealsArray) {
            JsonObject mealObject = mealElement.getAsJsonObject();

            String idMeal = mealObject.get("idMeal").getAsString();
            String strMeal = mealObject.get("strMeal").getAsString();
            String strDrinkAlternate = mealObject.get("strDrinkAlternate").isJsonNull() ? null : mealObject.get("strDrinkAlternate").getAsString();
            String strCategory = mealObject.get("strCategory").getAsString();
            String strArea = mealObject.get("strArea").getAsString();
            String strInstructions = mealObject.get("strInstructions").getAsString();
            String strMealThumb = mealObject.get("strMealThumb").getAsString();
            String strTags = mealObject.get("strTags").isJsonNull() ? null : mealObject.get("strTags").getAsString();
            String strYoutube = mealObject.get("strYoutube").isJsonNull() ? null : mealObject.get("strYoutube").getAsString();
            String strSource = mealObject.get("strSource").isJsonNull() ? null : mealObject.get("strSource").getAsString();
            String strImageSource = mealObject.get("strImageSource").isJsonNull() ? null : mealObject.get("strImageSource").getAsString();
            String strCreativeCommonsConfirmed = mealObject.get("strCreativeCommonsConfirmed").isJsonNull() ? null : mealObject.get("strCreativeCommonsConfirmed").getAsString();
            String dateModified = mealObject.get("dateModified").isJsonNull() ? null : mealObject.get("dateModified").getAsString();

            // Extracting Ingredients dynamically
            List<Ingredient> ingredientList = new ArrayList<>();
            for (int i = 1; i <= 20; i++) {
                String ingredientKey = "strIngredient" + i;
                String measureKey = "strMeasure" + i;

                if (mealObject.has(ingredientKey) && mealObject.has(measureKey)) {
                    String ingredient = mealObject.get(ingredientKey).isJsonNull() ? "" : mealObject.get(ingredientKey).getAsString().trim();
                    String measure = mealObject.get(measureKey).isJsonNull() ? "" : mealObject.get(measureKey).getAsString().trim();

                    if (!ingredient.isEmpty()) {
                        ingredientList.add(new Ingredient(ingredient, measure));
                    }
                }
            }

            Meal meal = new Meal(idMeal, strMeal, strDrinkAlternate, strCategory, strArea, strInstructions, strMealThumb,
                    strTags, strYoutube, ingredientList,
                    strSource, strImageSource, strCreativeCommonsConfirmed, dateModified);

            mealsList.add(meal);
        }
        return new Meals(mealsList);
    }
}

