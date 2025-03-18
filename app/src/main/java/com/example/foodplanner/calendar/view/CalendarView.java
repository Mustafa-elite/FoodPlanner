package com.example.foodplanner.calendar.view;

import android.content.Context;

import com.example.foodplanner.model.local.database.favorites.DbMeal;
import com.example.foodplanner.model.remote.server.meals.Meal;

import java.util.List;

public interface CalendarView {
    void updateAdapterList(List<DbMeal> dbMealList);

    void makeToast(String message);

    void navigateToDetails(Meal meal);

    Context getContext();
}
