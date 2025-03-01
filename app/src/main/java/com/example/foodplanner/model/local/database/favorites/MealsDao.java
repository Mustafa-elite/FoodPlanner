package com.example.foodplanner.model.local.database.favorites;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;


@Dao
public interface MealsDao {

    @Query("SELECT * FROM meals_table")
    Flowable<List<DbMeal>> getAllFavMeals();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMeal(DbMeal dbMeal);

    @Delete
    Completable deleteMeal(DbMeal dbMeal);
}


