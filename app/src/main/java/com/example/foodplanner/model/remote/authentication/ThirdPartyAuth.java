package com.example.foodplanner.model.remote.authentication;

import android.os.Bundle;
import android.util.Log;

import com.example.foodplanner.authentication.model.AuthUser;
import com.facebook.GraphRequest;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class ThirdPartyAuth {

    private FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    private static ThirdPartyAuth thirdPartyAuth=null;

    private ThirdPartyAuth() {
        this.firebaseAuth = FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
    }
    public static ThirdPartyAuth getInstance()
    {
        if(thirdPartyAuth==null)
        {
            thirdPartyAuth=new ThirdPartyAuth();
        }
        return thirdPartyAuth;
    }

    public void handleFacebookSignIn(LoginResult loginResult, AuthCallback callback) {
        AuthCredential credential = FacebookAuthProvider.getCredential(loginResult.getAccessToken().getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        Log.i("TAG", user.getEmail() );
                        Log.i("TAG", user.getDisplayName());
                        String name = user.getEmail();
                        String email = user.getDisplayName();

                        DocumentReference userRef = firestore.collection("users").document(user.getUid());

                        Map<String, Object> userData = new HashMap<>();
                        userData.put("name", name);
                        userData.put("email", email);
                        userRef.set(userData)
                                .addOnSuccessListener(aVoid ->
                                {
                                    AuthUser authUser=AuthUser.getInstance(name,email,null);
                                    authUser.setAutherized(true);
                                    callback.onSuccess();
                                })
                                .addOnFailureListener(e -> callback.onFailure("Failed to store user data in Firestore."));
                    } else {
                        callback.onFailure(task.getException().getMessage());
                    }
                });
    }

    public void handleGoogleSignIn(GoogleSignInAccount account, AuthCallback callback) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            String userId = user.getUid();
                            String name = user.getDisplayName();
                            String email = user.getEmail();


                            Map<String, Object> userData = new HashMap<>();
                            userData.put("name", name);
                            userData.put("email", email);

                            firestore.collection("users")
                                    .document(userId)
                                    .set(userData)
                                    .addOnSuccessListener(aVoid ->
                                    {
                                        AuthUser authUser=AuthUser.getInstance(name,email,null);
                                        authUser.setAutherized(true);
                                        callback.onSuccess();
                                    })
                                    .addOnFailureListener(e -> callback.onFailure("Failed to save user in Firestore."));
                        } else {
                            callback.onFailure("User data is null.");
                        }
                    } else {
                        callback.onFailure(task.getException().getMessage());
                    }
                });
    }


}
