package com.example.foodplanner.model.repository;

import com.example.foodplanner.authentication.model.AuthUser;
import com.example.foodplanner.model.remote.authentication.AuthCallback;
import com.example.foodplanner.model.remote.authentication.FireBaseAuthentication;
import com.example.foodplanner.model.remote.authentication.ThirdPartyAuth;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class AuthRepository {
    private FireBaseAuthentication fireBaseAuthentication;
    private ThirdPartyAuth thirdPartyAuth;

    private static AuthRepository repository=null;
    private AuthRepository() {
        this.fireBaseAuthentication = FireBaseAuthentication.getInstance();
        this.thirdPartyAuth=ThirdPartyAuth.getInstance();

    }
    public static AuthRepository getInstance()
    {
        if(repository==null)
        {
            repository=new AuthRepository();
        }
        return repository;

    }

    public void Signup(AuthUser authUser, AuthCallback authCallback)
    {
        fireBaseAuthentication.signUpUser(authUser,authCallback);
    }

    public void logIn(String email,String password, AuthCallback authCallback)
    {
        fireBaseAuthentication.signInUser(email,password,authCallback);
    }
    public void facebookSignIn(LoginResult loginResult, AuthCallback callback)
    {
        thirdPartyAuth.handleFacebookSignIn(loginResult,callback);

    }

    public void googleSignIn(GoogleSignInAccount account, AuthCallback callback)
    {
        thirdPartyAuth.handleGoogleSignIn(account,callback);

    }




}
