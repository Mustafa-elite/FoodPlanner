package com.example.foodplanner.authentication.presenter;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;

import com.example.foodplanner.authentication.model.AuthUser;
import com.example.foodplanner.authentication.view.SignUpView;
import com.example.foodplanner.model.remote.authentication.AuthCallback;
import com.example.foodplanner.model.repository.Repository;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.login.LoginResult;
import com.google.firebase.firestore.auth.User;

import org.json.JSONException;

public class SignUpPresenter implements AuthCallback {
    SignUpView view;
    Repository repository;

    public SignUpPresenter(SignUpView view,Repository repository) {
        this.view = view;
        this.repository=repository;
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

    public void registerUser(LoginResult loginResult) {

        AccessToken accessToken = loginResult.getAccessToken();
        fetchUserData(accessToken);
    }
    private void fetchUserData(AccessToken accessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                (object, response) -> {
                    try {
                        //Log.i("TAG", "Full JSON Response: " + object.toString());
                        String name = object.getString("name");
                        String email = object.has("email")? object.getString("email"):"No Email Provided";
                        String profilePicUrl = object.getJSONObject("picture")
                                .getJSONObject("data")
                                .getString("url");
                        AuthUser user=AuthUser.getInstance(name,email,null);
                        user.setAutherized(true);
                        Log.i("TAG", "User Name: " + name);
                        Log.i("TAG", "User Email: " + email);
                        Log.i("TAG", "Profile Pic URL: " + profilePicUrl);

                        view.navigateToNextFragment();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,picture.width(200).height(200)");
        request.setParameters(parameters);
        request.executeAsync();
    }
}
