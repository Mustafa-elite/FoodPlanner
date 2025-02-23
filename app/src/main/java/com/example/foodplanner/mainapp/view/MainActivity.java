package com.example.foodplanner.mainapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.foodplanner.R;
import com.example.foodplanner.authentication.view.SignUpFragment;
import com.example.foodplanner.model.Meals;
import com.example.foodplanner.model.remote.server.MealsClient;
import com.example.foodplanner.model.remote.server.NetworkCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements NetworkCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController= ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment)).getNavController();
        NavigationUI.setupWithNavController(bottomNavigationView,navController);


        MealsClient mealsClient= MealsClient.getInstance();
        mealsClient.getRandomMealCall(this);


    }

    @Override
    public void onBackPressed() {
        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment instanceof NavHostFragment) {
            NavController navController = ((NavHostFragment) navHostFragment).getNavController();
            int currentFragmentId = navController.getCurrentDestination().getId();

            if (currentFragmentId == R.id.signUpFragment || currentFragmentId == R.id.loginFragment) {
                navController.navigate(R.id.homeFragment);
                return;
            }

            if (currentFragmentId == R.id.homeFragment) {
                moveTaskToBack(true);
                return;
            }

            if (currentFragmentId == R.id.searchFragment ||
                    currentFragmentId == R.id.favoritesFragment ||
                    currentFragmentId == R.id.calendarFragment ||
                    currentFragmentId == R.id.profileFragment) {
                navController.navigate(R.id.homeFragment);
                return;
            }
        }

        super.onBackPressed();
    }


    @Override
    public void onSuccessfulResult(Meals body) {
        Log.i("TAG", body.toString());
    }

    @Override
    public void onFailureResult(String message) {

    }

}