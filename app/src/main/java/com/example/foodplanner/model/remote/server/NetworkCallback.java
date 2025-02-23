package com.example.foodplanner.model.remote.server;

import com.example.foodplanner.model.Meals;

public interface NetworkCallback {
    void onSuccessfulResult(Meals body);

    void onFailureResult(String message);
}
