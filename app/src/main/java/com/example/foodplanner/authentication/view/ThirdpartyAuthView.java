package com.example.foodplanner.authentication.view;

public interface ThirdpartyAuthView {
    void onThirdPartySignUpSuccessful();

    void onThirdPartySignUpFailure(String errMsg);

    void showError(String s);
}
