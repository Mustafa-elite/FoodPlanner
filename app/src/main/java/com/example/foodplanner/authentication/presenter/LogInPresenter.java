package com.example.foodplanner.authentication.presenter;

import android.util.Patterns;

import com.example.foodplanner.authentication.view.LoginView;
import com.example.foodplanner.model.remote.authentication.AuthCallback;
import com.example.foodplanner.model.repository.AuthRepository;

public class LogInPresenter implements AuthCallback {

    LoginView view;
    AuthRepository repository;

    public LogInPresenter(LoginView view, AuthRepository authRepository){
        this.view = view;
        this.repository= authRepository;
    }

    public void logIn(String email, String password) {
        boolean isValid=true;
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            view.EmailFormatError();
            isValid=false;
        }
        if(password.isEmpty())
        {
            view.emptyPasswordError();
            isValid=false;
        }
        if(isValid)
        {
            repository.logIn(email,password,this);
        }

    }

    @Override
    public void onFailure(String errMsg) {
        view.onLogInFailure(errMsg);
        //AuthUser.deleteUser();

    }

    @Override
    public void onSuccess() {
        view.onLogInSuccessful();

    }
}
