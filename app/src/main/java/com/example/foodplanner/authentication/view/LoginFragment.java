package com.example.foodplanner.authentication.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.example.foodplanner.authentication.presenter.LogInPresenter;
import com.example.foodplanner.authentication.presenter.ThirdPartyPresenter;
import com.example.foodplanner.model.repository.AuthRepository;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;


public class LoginFragment extends Fragment implements LoginView,ThirdpartyAuthView {

    EditText email_EditText,password_EditText;
    Button logIn_Btn,logIn_Skip_Btn, google_login_Btn,facebook_login_Btn1;
    LoginButton facebook_login_Btn;
    TextView signUp_Navigator_text;
    TextInputLayout passwordLayout ,emailTextLayeout;
    LogInPresenter loginUpPresenter;
    ThirdPartyPresenter thirdPartyPresenter;
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
        return inflater.inflate(R.layout.fragment_login, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BottomNavigationView bottomNav = getActivity().findViewById(R.id.bottomNavigationView);
        if (bottomNav != null) {
            bottomNav.setVisibility(View.GONE);
        }
        defineViews(view);
        buttonsHandle();
        loginUpPresenter=new LogInPresenter(this, AuthRepository.getInstance());
        thirdPartyPresenter=new ThirdPartyPresenter(this, AuthRepository.getInstance());

        //thirdPartyPresenter.facebookLogin(facebook_login_Btn);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
    }
    private void defineViews(View view) {
        email_EditText=view.findViewById(R.id.login_email_EditText);
        password_EditText=view.findViewById(R.id.login_password_EditText);
        logIn_Btn =view.findViewById(R.id.LogIn_Btn);
        logIn_Skip_Btn=view.findViewById(R.id.logIn_Skip_Btn);
        google_login_Btn=view.findViewById(R.id.google_login_Btn);
        facebook_login_Btn=(LoginButton) view.findViewById(R.id.facebook_login_Btn);
        signUp_Navigator_text=view.findViewById(R.id.signUp_Navigator_text);
        facebook_login_Btn1=view.findViewById(R.id.facebook_login_Btn1);
        passwordLayout = view.findViewById(R.id.login_passwordLayout);
        emailTextLayeout = view.findViewById(R.id.login_emailTextLayeout);
    }

    private void buttonsHandle() {
        logIn_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeErrorMsgs();
                loginUpPresenter.logIn(
                        email_EditText.getText().toString(),
                        password_EditText.getText().toString());

            }
        });
        logIn_Skip_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.popBackStack(R.id.homeFragment, true);
                navController.navigate(R.id.homeFragment);

            }
        });
        google_login_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignInClient.signOut().addOnCompleteListener(requireActivity(), task -> {
                    Intent signInIntent = googleSignInClient.getSignInIntent();
                    startActivityForResult(signInIntent, RC_SIGN_IN);
                });
            }
        });
        facebook_login_Btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //thirdPartyPresenter.facebookLogin(facebook_login_Btn);
                LoginManager.getInstance().logOut();
                AccessToken.setCurrentAccessToken(null);
                //facebook_login_Btn.performClick();
                thirdPartyPresenter.facebookLogin(facebook_login_Btn);
            }
        });
        signUp_Navigator_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.signUpFragment);

            }
        });
    }
    private void removeErrorMsgs()
    {
        emailTextLayeout.setError(null);
        passwordLayout.setError(null);
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
                if (account != null) {
                    thirdPartyPresenter.googleSignIn(account);
                }
            } catch (ApiException e) {
                toastMaker(getString(R.string.google_sign_in_failed));
            }
        }
        else {
            thirdPartyPresenter.getCallbackManager().onActivityResult(requestCode, resultCode, data);
        }
    }
    private void navigateToHome()
    {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.popBackStack(R.id.homeFragment, true);
        navController.navigate(R.id.homeFragment);
    }


    @Override
    public void onThirdPartySignUpSuccessful() {
        navigateToHome();
    }

    @Override
    public void onThirdPartySignUpFailure(String errMsg) {
        toastMaker(errMsg);
    }

    @Override
    public void showError(String s) {
        toastMaker(s);
    }

    @Override
    public void EmailFormatError() {
        emailTextLayeout.setError(getString(R.string.email_is_invalid));

    }

    @Override
    public void emptyPasswordError() {
        passwordLayout.setError(getString(R.string.password_cant_be_empty));
    }

    @Override
    public void onLogInFailure(String errMsg) {

        toastMaker(errMsg);
    }

    @Override
    public void onLogInSuccessful() {

        navigateToHome();
    }
}