package com.example.foodplanner.search.view;

import android.widget.ImageView;

public interface SearchViewConnector {
    void getImage(String strCategoryThumb, ImageView searchItemImage);

    void getCategoryMeals(String strCategory);

    void getCountryMeals(String name);

    void getIngredientMeals(String ingredientName);
}
