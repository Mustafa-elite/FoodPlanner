package com.example.foodplanner.home.view;

import android.widget.ImageView;

import com.example.foodplanner.model.Meal;

public interface HomeViewConnector {

    void getGlideImage(String strMealThumb, ImageView mealImage);

    void navigateToDetails(Meal meal);
}
