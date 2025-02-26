package com.example.foodplanner.mealdetails.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;

public interface MealDetailsView {
    
    Context getContext();

    void setMainImage(Bitmap bitmap);

    void makeToast(String message);
}
