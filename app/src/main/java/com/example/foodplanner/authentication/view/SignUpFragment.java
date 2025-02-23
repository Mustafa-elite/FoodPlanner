package com.example.foodplanner.authentication.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.example.foodplanner.authentication.presenter.SignUpPresenter;
import com.example.foodplanner.model.repository.Repository;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONException;

import java.util.Arrays;


public class SignUpFragment extends Fragment implements SignUpView{

    EditText name_EditText,email_EditText,password_EditText,confirm_Password_EditText;
    Button signUp_Btn,signUp_Skip_Btn,google_Signup_Btn,facebook_Signup_Btn1;
    LoginButton facebook_Signup_Btn;
    TextView signUp_Navigator_text;
    TextInputLayout passwordLayout ,confirmPasswordLayout,emailTextLayeout,nameTextLayeout;
    private SignUpPresenter signUpPresenter;
    CallbackManager callbackManager;
    private GoogleSignInClient googleSignInClient;
    private static final int RC_SIGN_IN = 100;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        signUpPresenter=new SignUpPresenter(this, Repository.getInstance());
        defineViews(view);
        buttonsHandle();
        BottomNavigationView bottomNav = getActivity().findViewById(R.id.bottomNavigationView);
        if (bottomNav != null) {
            bottomNav.setVisibility(View.GONE);
        }


        callbackManager = CallbackManager.Factory.create();
        facebook_Signup_Btn.setReadPermissions(Arrays.asList("public_profile", "email"));
        facebook_Signup_Btn.setFragment(this);
        facebook_Signup_Btn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.i("TAG", "onSuccess: ttt");
                signUpPresenter.registerUser(loginResult);
            }

            @Override
            public void onCancel() {
                // App code
                Log.i("TAG", "onCancel: ");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.i("TAG", "onError: ");
                // App code
            }
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)) // Replace with your client ID
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        BottomNavigationView bottomNav = getActivity().findViewById(R.id.bottomNavigationView);
        if (bottomNav != null) {
            bottomNav.setVisibility(View.VISIBLE);
        }
    }

    private void buttonsHandle() {
        signUp_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeErrorMsgs();
                signUpPresenter.signUp(
                        name_EditText.getText().toString(),
                        email_EditText.getText().toString(),
                        password_EditText.getText().toString(),
                        confirm_Password_EditText.getText().toString());

            }
        });
        signUp_Skip_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.popBackStack(R.id.homeFragment, true);
                navController.navigate(R.id.homeFragment);

            }
        });
        google_Signup_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignInClient.signOut().addOnCompleteListener(requireActivity(), task -> {
                    Intent signInIntent = googleSignInClient.getSignInIntent();
                    startActivityForResult(signInIntent, RC_SIGN_IN);
                });


            }
        });
        facebook_Signup_Btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                AccessToken.setCurrentAccessToken(null);
                facebook_Signup_Btn.performClick();
            }
        });
        signUp_Navigator_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.loginFragment);

            }
        });
    }

    private void defineViews(View view) {
        name_EditText=view.findViewById(R.id.Name_EditText);
        email_EditText=view.findViewById(R.id.email_EditText);
        password_EditText=view.findViewById(R.id.password_EditText);
        confirm_Password_EditText=view.findViewById(R.id.confirm_Password_EditText);
        signUp_Btn=view.findViewById(R.id.signUp_Btn);
        signUp_Skip_Btn=view.findViewById(R.id.signUp_Skip_Btn);
        google_Signup_Btn=view.findViewById(R.id.google_Signup_Btn);
        facebook_Signup_Btn=(LoginButton) view.findViewById(R.id.facebook_Signup_Btn);
        signUp_Navigator_text=view.findViewById(R.id.signUp_Navigator_text);
        facebook_Signup_Btn1=view.findViewById(R.id.facebook_Signup_Btn1);
        passwordLayout = view.findViewById(R.id.password_EditText_Layout);
        confirmPasswordLayout=view.findViewById(R.id.confirm_Password_EditText_Layout);
        emailTextLayeout = view.findViewById(R.id.email_EditText_Layout);
        nameTextLayeout = view.findViewById(R.id.Name_EditText_Layout);



    }

    @Override
    public void onSignUpFailure(String errMsg) {
        toastMaker(errMsg);
    }

    @Override
    public void onSignUpSuccessful() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.homeFragment);

//        else {
//            Log.i("TAG", "onSignUpSuccessful: test");
//        }
    }

    @Override
    public void EmailFormatError() {
        emailTextLayeout.setError(getString(R.string.email_is_invalid));
    }

    @Override
    public void shortPasswordError() {
        passwordLayout.setError(getString(R.string.password_is_too_short_6));

    }

    @Override
    public void passwordConfirmError() {
        confirmPasswordLayout.setError(getString(R.string.passwords_must_match));
    }

    @Override
    public void navigateToNextFragment() {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.popBackStack(R.id.homeFragment, true);
        navController.navigate(R.id.homeFragment);
    }

    public void toastMaker(String s)
    {
        Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.e("GoogleSignIn", "Google sign in failed", e);
                Toast.makeText(getActivity(), "Google Sign-In Failed!", Toast.LENGTH_SHORT).show();
            }
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        Log.i("TAGGOOGLE", user.getDisplayName());
                        Log.i("TAGGOOGLE", user.getEmail());
                        //navigateToHome();
                    } else {
                        Log.i("TAGGOOGLE", "failed to google auth");

                    }
                });
    }

    private void removeErrorMsgs()
    {
        emailTextLayeout.setError(null);
        passwordLayout.setError(null);
        confirmPasswordLayout.setError(null);
    }
}