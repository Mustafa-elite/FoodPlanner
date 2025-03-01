package com.example.foodplanner.favorite.view;

import com.example.foodplanner.model.local.database.favorites.DbMeal;
import com.example.foodplanner.model.remote.server.meals.Meal;

import java.util.List;

public interface FavoriteView {
    void updateAdapterList(List<DbMeal> dbMealList);

    void makeToast(String message);

    void navigateToDetails(Meal meal);
}
