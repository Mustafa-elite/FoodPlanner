package com.example.foodplanner.model.local.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.example.foodplanner.model.local.database.calendar.ScheduledMeal;
import com.example.foodplanner.model.local.database.calendar.ScheduledMealDao;
import com.example.foodplanner.model.local.database.favorites.DbMeal;
import com.example.foodplanner.model.local.database.favorites.MealsDao;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class LocalDataSource {
    private final MealsDao mealsDao;
    private final ScheduledMealDao scheduledMealDao;
    private static LocalDataSource localDataSource=null;
    private Flowable<List<DbMeal>> meals;
    MealsDataBase mealsDataBase;

    private LocalDataSource(Context _context) {
        mealsDataBase = MealsDataBase.getInstance(_context);
        mealsDao= mealsDataBase.getMealsDao();
        scheduledMealDao=mealsDataBase.getScheduledMealDao();
        meals=mealsDao.getAllFavMeals();

    }

    public static LocalDataSource getInstance(Context context)
    {
        if(localDataSource ==null)
        {
            localDataSource =new LocalDataSource(context);

        }
        return localDataSource;
    }
    public Completable insertFavMeal(DbMeal dbMeal)
    {
        return mealsDao.insertMeal(dbMeal);
    }
    public Completable deleteFavMeal(DbMeal dbMeal)
    {
        return mealsDao.deleteMeal(dbMeal);
    }
    public Flowable<List<DbMeal>> getStoredFavMeals()
    {
        return meals;
    }

    public Flowable<List<ScheduledMeal>> getCalendarMeals(String date)
    {
        return scheduledMealDao.getMealsForDate(date);
    }

    public Completable insertCalendarMeal(ScheduledMeal scheduledMeal)
    {
        return scheduledMealDao.insertScheduledMeal(scheduledMeal);
    }
    public Completable deleteCalendarMeal(String selectedDate,String mealId)
    {
        return scheduledMealDao.deleteScheduledMeal(selectedDate,mealId);
    }
    public void clearAllData() {
        mealsDataBase.clearDatabase();
    }


}