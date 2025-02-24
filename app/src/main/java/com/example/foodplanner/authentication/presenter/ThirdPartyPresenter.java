package com.example.foodplanner.authentication.presenter;

import androidx.fragment.app.Fragment;

import com.example.foodplanner.authentication.view.ThirdpartyAuthView;
import com.example.foodplanner.model.remote.authentication.AuthCallback;
import com.example.foodplanner.model.repository.AuthRepository;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.Arrays;

public class ThirdPartyPresenter {
    private ThirdpartyAuthView view;
    private AuthRepository repository;
    private CallbackManager callbackManager;
    public ThirdPartyPresenter(ThirdpartyAuthView view, AuthRepository repository) {
        this.view = view;
        this.repository = repository;

    }
    public void facebookLogin(LoginButton facebookButton) {
        callbackManager = CallbackManager.Factory.create();
//        facebookButton.setReadPermissions(Arrays.asList("public_profile", "email"));
//        facebookButton.setFragment((Fragment) view);
//        facebookButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
        LoginManager.getInstance().logInWithReadPermissions((Fragment) view, Arrays.asList("public_profile", "email"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                repository.facebookSignIn(loginResult, new AuthCallback() {
                    @Override
                    public void onSuccess() {
                        view.onThirdPartySignUpSuccessful();
                    }
                    @Override
                    public void onFailure(String errMsg) {
                        view.onThirdPartySignUpFailure(errMsg);
                    }
                });
            }

            @Override
            public void onCancel() {
                view.showError("Facebook Sign-in Canceled");
            }

            @Override
            public void onError(FacebookException error) {
                view.showError("Facebook Sign-in Failed: " + error.getMessage());
            }
        });
    }

    public void googleSignIn(GoogleSignInAccount account) {
        repository.googleSignIn(account, new AuthCallback() {
            @Override
            public void onSuccess() {
                view.onThirdPartySignUpSuccessful();
            }

            @Override
            public void onFailure(String errMsg) {
                view.onThirdPartySignUpFailure(errMsg);
            }
        });
    }


    public CallbackManager getCallbackManager() {
        return callbackManager;
    }
}
