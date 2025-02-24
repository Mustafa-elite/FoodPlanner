package com.example.foodplanner.model.remote.authentication;

import com.example.foodplanner.authentication.model.AuthUser;
import com.example.foodplanner.model.remote.authentication.AuthCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FireBaseAuthentication {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private static FireBaseAuthentication fireBaseAuthentication=null;

    private FireBaseAuthentication() {
        mAuth = FirebaseAuth.getInstance();
        db= FirebaseFirestore.getInstance();
    }
    public static FireBaseAuthentication getInstance()
    {
        if(fireBaseAuthentication==null)
        {
            fireBaseAuthentication=new FireBaseAuthentication();
        }
        return fireBaseAuthentication;
    }

    public void signUpUser(AuthUser authUser, AuthCallback callback) {
        mAuth.createUserWithEmailAndPassword(authUser.getEmail(), authUser.getPassword())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            saveUserToFirestore(user.getUid(),authUser.getName(), authUser.getEmail(), callback);
                        } else {
                            callback.onFailure("User creation successful, but user object is null.");
                        }
                    } else {
                        callback.onFailure(task.getException().getMessage());
                    }
                });
    }

    public void signInUser(String email, String password, AuthCallback callback) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        AuthUser authUser=AuthUser.getInstance(user.getDisplayName(),user.getEmail(),null);
                        authUser.setAutherized(true);
                        callback.onSuccess();
                    } else {
                        callback.onFailure(task.getException().getMessage());
                    }
                });
    }

    private void saveUserToFirestore(String userId, String name, String email, AuthCallback callback) {
        Map<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("email", email);

        db.collection("users").document(userId)
                .set(user)
                .addOnSuccessListener(aVoid -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onFailure("Error saving user data: " + e.getMessage()));
    }

}
