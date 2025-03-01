package com.example.foodplanner.searchedmeals.view;

import android.app.Activity;
import android.content.Context;

import com.example.foodplanner.model.remote.server.meals.Meal;

import java.util.List;

public interface SearchedView {
    Context getContext();

    void navigateToMealDetails(Meal meal);

    void makeToast(String message);

    void updateAdapterList(List<Meal> filteredList);
}
