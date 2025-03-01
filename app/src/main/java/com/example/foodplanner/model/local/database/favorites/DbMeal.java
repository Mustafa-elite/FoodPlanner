package com.example.foodplanner.model.local.database.favorites;


import android.graphics.Bitmap;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.foodplanner.model.remote.server.meals.Meal;



@Entity(tableName = "meals_table")
public class DbMeal {
    @PrimaryKey
    @NonNull
    private String mealId;
    private String mealName;

    @Override
    public String toString() {
        return "DbMeal{" +
                "mealId='" + mealId + '\'' +
                ", mealName='" + mealName + '\'' +
                ", mealCategory='" + mealCategory + '\'' +
                ", mealThumb='" + mealThumb + '\'' +
                '}';
    }

    private String mealCategory;
    private String mealThumb;
    @TypeConverters(BitmapConverter.class)
    private Bitmap image;

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public DbMeal(Meal meal, Bitmap image) {
        this.mealId = meal.getIdMeal();
        this.mealName = meal.getStrMeal();
        this.mealCategory = meal.getStrCategory();
        this.mealThumb = meal.getStrMealThumb();
        this.image = image;
    }

    @NonNull
    public String getMealId() {
        return mealId;
    }

    @NonNull
    public void setMealId(String mealId) {
        this.mealId = mealId;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getMealCategory() {
        return mealCategory;
    }

    public void setMealCategory(String mealCategory) {
        this.mealCategory = mealCategory;
    }

    public String getMealThumb() {
        return mealThumb;
    }

    public void setMealThumb(String mealThumb) {
        this.mealThumb = mealThumb;
    }

    public DbMeal(Meal meal) {
        this.mealId = meal.getIdMeal();
        this.mealName = meal.getStrMeal();
        this.mealCategory = meal.getStrCategory();
        this.mealThumb = meal.getStrMealThumb();
    }

    public DbMeal() {
    }
}
