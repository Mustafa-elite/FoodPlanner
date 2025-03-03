package com.example.foodplanner.authentication.view;

import android.content.Context;

public interface ThirdpartyAuthView {
    void onThirdPartySignUpSuccessful();

    void onThirdPartySignUpFailure(String errMsg);

    void showError(String s);

    Context getContextt();
}
