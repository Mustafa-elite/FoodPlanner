package com.example.foodplanner.model.repository;

import com.example.foodplanner.authentication.model.AuthUser;
import com.example.foodplanner.model.remote.authentication.AuthCallback;
import com.example.foodplanner.model.remote.authentication.FireBaseAuthentication;

public class Repository {
    private FireBaseAuthentication fireBaseAuthentication;

    private static Repository repository=null;
    private Repository() {
        this.fireBaseAuthentication = FireBaseAuthentication.getInstance();
    }
    public static Repository getInstance()
    {
        if(repository==null)
        {
            repository=new Repository();
        }
        return repository;

    }

    public void Signup(AuthUser authUser, AuthCallback authCallback)
    {
        fireBaseAuthentication.signUpUser(authUser,authCallback);
    }

}
