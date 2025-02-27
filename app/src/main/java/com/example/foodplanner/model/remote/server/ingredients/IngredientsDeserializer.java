package com.example.foodplanner.model.remote.server.ingredients;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class IngredientsDeserializer implements JsonDeserializer<Ingredients> {

    @Override
    public Ingredients deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<Ingredient> ingredientsList = new ArrayList<>();

        JsonObject jsonObject = json.getAsJsonObject();

        if (jsonObject.has("meals")) {
            for (JsonElement element : jsonObject.getAsJsonArray("meals")) {
                JsonObject ingredientObj = element.getAsJsonObject();
                String ingredientName = ingredientObj.get("strIngredient").getAsString();
                ingredientsList.add(new Ingredient(ingredientName, ""));
            }
        }
        return new Ingredients(ingredientsList) ;
    }
}
