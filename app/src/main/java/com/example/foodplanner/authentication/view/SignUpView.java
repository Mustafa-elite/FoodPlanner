package com.example.foodplanner.authentication.view;

public interface SignUpView {
    void onSignUpFailure(String s);

    void onSignUpSuccessful();

    void EmailFormatError();

    void shortPasswordError();

    void passwordConfirmError();

    void navigateToNextFragment();
}
