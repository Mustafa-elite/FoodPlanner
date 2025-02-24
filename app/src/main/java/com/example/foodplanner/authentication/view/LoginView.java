package com.example.foodplanner.authentication.view;

public interface LoginView {
    void EmailFormatError();

    void emptyPasswordError();

    void onLogInFailure(String errMsg);

    void onLogInSuccessful();
}
