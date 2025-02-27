package com.example.foodplanner.model.remote.server.countries;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CountriesDeserializer implements JsonDeserializer<Countries> {
    @Override
    public Countries deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<Country> countries = new ArrayList<>();
        JsonArray mealsArray = json.getAsJsonObject().getAsJsonArray("meals");

        for (JsonElement element : mealsArray) {
            JsonObject mealObject = element.getAsJsonObject();
            String countryName = mealObject.get("strArea").getAsString();
            countries.add(new Country(countryName));
        }
        return new Countries(countries);
    }
}