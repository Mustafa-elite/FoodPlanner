package com.example.foodplanner.search.view;

import android.content.Context;
import android.view.View;

import com.example.foodplanner.model.remote.server.categories.Category;
import com.example.foodplanner.model.remote.server.countries.Country;
import com.example.foodplanner.model.remote.server.ingredients.Ingredient;
import com.example.foodplanner.model.remote.server.meals.Meals;

import java.util.List;

public interface SearchViewInterface {
    Context getContext();

    void setCategoriesList(List<Category> categories);

    void setIngredientsList(List<Ingredient> ingredients);

    void setCountriesList(List<Country> countries);

    void navigateToSearchedMeals(Meals meals, String s);
}
