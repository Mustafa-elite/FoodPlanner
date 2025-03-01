package com.example.foodplanner.favorite.view;

import com.example.foodplanner.model.local.database.favorites.DbMeal;

public interface FavoriteViewConnector {
    void deleteFavMeal(DbMeal dbMeal);

    void getMealAndAvigate(DbMeal dbMeal);
}
