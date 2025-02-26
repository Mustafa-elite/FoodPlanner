package com.example.foodplanner.home.view;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.foodplanner.model.Meal;

import java.util.List;

public interface HomeView {

    void makeToast(String string);


    void setRandomMealTexts(String strMeal, String substring);

    Context getContext();

    void setRandomMealImage(Bitmap bitmap);

    void updateAdapterList(List<Meal> mealList);
}
