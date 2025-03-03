package com.example.foodplanner.mainapp.view;

import android.os.Bundle;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.foodplanner.R;

import com.example.foodplanner.mainapp.NetworkUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController= ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment)).getNavController();
        NavigationUI.setupWithNavController(bottomNavigationView,navController);

        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
            navController.navigate(R.id.homeFragment);
        }
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int destinationId = item.getItemId();

            if (destinationId==R.id.searchFragment&& !NetworkUtil.isConnected(this)) {
                Toast.makeText(this, "No internet connection!", Toast.LENGTH_SHORT).show();
                return false;
            }
            if((destinationId==R.id.favoritesFragment ||destinationId==R.id.calendarFragment)&&
            FirebaseAuth.getInstance().getCurrentUser()==null)
            {
                openSignInDialog();
                return false;

            }
            navController.navigate(destinationId);
            return true;
        });


    }

    private void openSignInDialog() {
        ConfirmationDialogFragment dialog = ConfirmationDialogFragment.newInstance(
                "You have to be Signed in to view your favourites and calendar ","Sign In",
                () -> {
                    NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
                    navController.navigate(R.id.loginFragment);
                }
        );
        dialog.show(getSupportFragmentManager(), "ConfirmationDialog");
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