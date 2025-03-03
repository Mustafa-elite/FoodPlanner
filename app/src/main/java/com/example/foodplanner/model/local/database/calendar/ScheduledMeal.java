package com.example.foodplanner.model.local.database.calendar;


import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.foodplanner.model.local.database.favorites.DbMeal;

@Entity(tableName = "scheduled_meals", primaryKeys = {"date", "mealId"})
public class ScheduledMeal {
    @NonNull
    private String date;

    @Embedded
    @NonNull
    private DbMeal meal;

    public ScheduledMeal(@NonNull String date, @NonNull DbMeal meal) {
        this.date = date;
        this.meal = meal;
    }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public DbMeal getMeal() { return meal; }
    public void setMeal(DbMeal meal) { this.meal = meal; }


}
