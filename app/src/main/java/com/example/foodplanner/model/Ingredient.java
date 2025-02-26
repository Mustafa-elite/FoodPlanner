package com.example.foodplanner.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {
    private String ingredient;
    private String measure;

    @Override
    public String toString() {
        return "Ingredient{" +
                "ingredient='" + ingredient + '\'' +
                ", measure='" + measure + '\'' +
                '}';
    }

    public Ingredient(String ingredient, String measure) {
        this.ingredient = ingredient;
        this.measure = measure;
    }

    public String getIngredientName() { return ingredient; }
    public void setIngredient(String ingredient) { this.ingredient = ingredient; }

    public String getMeasure() { return measure; }
    public void setMeasure(String measure) { this.measure = measure; }
    protected Ingredient(Parcel in) {
        ingredient = in.readString();
        measure = in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ingredient);
        dest.writeString(measure);
    }
}
