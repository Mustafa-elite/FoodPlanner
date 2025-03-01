package com.example.foodplanner.searchedmeals.view;

import android.widget.ImageView;

import com.example.foodplanner.model.remote.server.meals.Meal;

public interface SearchedViewConnector {
    void getGlideImage(String strMealThumb, ImageView mealImage);

    void navigateToDetails(Meal meal);
}
