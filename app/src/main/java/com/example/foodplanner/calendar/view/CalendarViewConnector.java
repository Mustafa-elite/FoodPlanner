package com.example.foodplanner.calendar.view;

import com.example.foodplanner.model.local.database.favorites.DbMeal;

public interface CalendarViewConnector {
    void deleteCalMeal(DbMeal meal);

    void getMealAndAvigate(DbMeal dbMeal);
}
