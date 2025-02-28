package com.example.foodplanner.model.remote.server.meals;

import java.util.List;


import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public class Meals implements Parcelable {
    private List<Meal> meals;

    public Meals(List<Meal> meals) {
        this.meals = meals;
    }

    protected Meals(Parcel in) {
        meals = in.createTypedArrayList(Meal.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(meals);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Meals> CREATOR = new Creator<Meals>() {
        @Override
        public Meals createFromParcel(Parcel in) {
            return new Meals(in);
        }

        @Override
        public Meals[] newArray(int size) {
            return new Meals[size];
        }
    };

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    @Override
    public String toString() {
        return "Meals{" +
                "meals=" + meals +
                '}';
    }
}


