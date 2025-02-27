package com.example.foodplanner.model.remote.server.meals;

import android.util.Log;

import com.example.foodplanner.model.remote.server.ingredients.Ingredient;
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

            String idMeal = getSafeString(mealObject, "idMeal");
            String strMeal = getSafeString(mealObject, "strMeal");
            String strDrinkAlternate = getSafeString(mealObject, "strDrinkAlternate");
            String strCategory = getSafeString(mealObject, "strCategory");
            String strArea = getSafeString(mealObject, "strArea");
            String strInstructions = getSafeString(mealObject, "strInstructions");
            String strMealThumb = getSafeString(mealObject, "strMealThumb");
            String strTags = getSafeString(mealObject, "strTags");
            String strYoutube = getSafeString(mealObject, "strYoutube");
            String strSource = getSafeString(mealObject, "strSource");
            String strImageSource = getSafeString(mealObject, "strImageSource");
            String strCreativeCommonsConfirmed = getSafeString(mealObject, "strCreativeCommonsConfirmed");
            String dateModified = getSafeString(mealObject, "dateModified");

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
    private String getSafeString(JsonObject jsonObject, String key) {
        return jsonObject.has(key) && !jsonObject.get(key).isJsonNull()
                ? jsonObject.get(key).getAsString()
                : null;
    }

}

