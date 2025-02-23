package com.example.foodplanner.model.remote.authentication;

public interface AuthCallback {
    void onFailure(String s);

    void onSuccess();
}
