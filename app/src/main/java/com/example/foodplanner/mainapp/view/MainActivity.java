package com.example.foodplanner.mainapp.view;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.foodplanner.R;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController= ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment)).getNavController();
        NavigationUI.setupWithNavController(bottomNavigationView,navController);


        //MealsRemoteDataSource mealsClient= MealsRemoteDataSource.getInstance();

        //mealsClient.getRandomMealCall();


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



}