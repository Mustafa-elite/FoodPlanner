package com.example.foodplanner.model.remote.server.categories;


import com.google.gson.*;
        import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CategoriesDeserializer implements JsonDeserializer<Categories> {
    @Override
    public Categories deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray categoriesArray = json.getAsJsonObject().getAsJsonArray("categories");
        List<Category> categoryList = new ArrayList<>();

        for (JsonElement element : categoriesArray) {
            JsonObject categoryObject = element.getAsJsonObject();
            String name = categoryObject.get("strCategory").getAsString();
            String thumb = categoryObject.get("strCategoryThumb").getAsString();
            categoryList.add(new Category(name, thumb));
        }

        Categories categories = new Categories();
        categories.setCategories(categoryList);
        return categories;
    }
}
