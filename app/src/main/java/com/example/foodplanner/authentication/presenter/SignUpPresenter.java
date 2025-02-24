package com.example.foodplanner.authentication.presenter;

import android.util.Patterns;

import com.example.foodplanner.authentication.model.AuthUser;
import com.example.foodplanner.authentication.view.SignUpView;
import com.example.foodplanner.model.remote.authentication.AuthCallback;
import com.example.foodplanner.model.repository.AuthRepository;

public class SignUpPresenter implements AuthCallback {
    SignUpView view;
    AuthRepository repository;

    public SignUpPresenter(SignUpView view, AuthRepository authRepository) {
        this.view = view;
        this.repository= authRepository;
    }



    @Override
    public void onFailure(String errMsg) {
        view.onSignUpFailure(errMsg);
        AuthUser.deleteUser();

    }

    @Override
    public void onSuccess() {
        view.onSignUpSuccessful();
        AuthUser.getInstance().setAutherized(true);

    }


    public void signUp(String name, String email, String password, String confirmPassword) {
        boolean isDataValid=validateData(name,email,password,confirmPassword);
        if(isDataValid)
        {
            repository.Signup(AuthUser.getInstance(name,email,password),this);
        }
    }
    private boolean validateData(String name, String email, String password, String confirmPassword)
    {
        boolean validData=true;
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            view.EmailFormatError();
            validData=false;
        }
        if(password.length()<6)
        {
            view.shortPasswordError();
            validData=false;
        }
        if(!password.equals(confirmPassword))
        {
            view.passwordConfirmError();
            validData=false;
        }
        return validData;
    }



}
