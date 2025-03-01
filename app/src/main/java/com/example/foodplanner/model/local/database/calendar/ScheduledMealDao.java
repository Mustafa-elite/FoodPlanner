package com.example.foodplanner.model.local.database.calendar;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface ScheduledMealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertScheduledMeal(ScheduledMeal scheduledMeal);

    @Query("SELECT * FROM scheduled_meals WHERE date = :date")
    Flowable<List<ScheduledMeal>> getMealsForDate(String date);

    @Query("DELETE FROM scheduled_meals WHERE date = :date AND mealId = :mealId")
    Completable deleteScheduledMeal(String date, String mealId);
}
