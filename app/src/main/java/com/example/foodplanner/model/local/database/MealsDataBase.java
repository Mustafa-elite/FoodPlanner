package com.example.foodplanner.model.local.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.foodplanner.model.local.database.calendar.ScheduledMeal;
import com.example.foodplanner.model.local.database.calendar.ScheduledMealDao;
import com.example.foodplanner.model.local.database.favorites.BitmapConverter;
import com.example.foodplanner.model.local.database.favorites.DbMeal;
import com.example.foodplanner.model.local.database.favorites.MealsDao;


@Database(entities = {DbMeal.class, ScheduledMeal.class}, version = 3,exportSchema = false)
@TypeConverters({BitmapConverter.class})
public abstract class MealsDataBase extends RoomDatabase{
    private static MealsDataBase mealsDataBase=null;
    public abstract MealsDao getMealsDao();
    public abstract ScheduledMealDao getScheduledMealDao();

    public static MealsDataBase getInstance(Context context){
        if(mealsDataBase==null)
        {
            mealsDataBase= Room
                    .databaseBuilder(context,MealsDataBase.class,"mealsDB")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return mealsDataBase;
    }
    public void clearDatabase() {
        new Thread(this::clearAllTables).start();
    }
}







