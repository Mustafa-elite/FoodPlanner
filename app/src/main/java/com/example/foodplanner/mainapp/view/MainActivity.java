package com.example.foodplanner.mainapp.view;

import android.os.Bundle;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.foodplanner.R;

import com.example.foodplanner.home.presenter.HomePresenter;
import com.example.foodplanner.mainapp.NetworkUtil;
import com.example.foodplanner.mainapp.presenter.MainAppPresenter;
import com.example.foodplanner.mainapp.presenter.ScheduledReminderWorker;
import com.example.foodplanner.model.local.database.LocalDataSource;
import com.example.foodplanner.model.local.sharedpreferences.SharedPrefs;
import com.example.foodplanner.model.remote.database.FirestoreDb;
import com.example.foodplanner.model.remote.server.meals.Meal;
import com.example.foodplanner.model.remote.server.network.RemoteDataSource;
import com.example.foodplanner.model.repository.DataRepository;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements MainActivityView{

    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        navController= ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment)).getNavController();
        NavigationUI.setupWithNavController(bottomNavigationView,navController);

        MainAppPresenter mainAppPresenter= new MainAppPresenter(
                        DataRepository.getInstance(
                                RemoteDataSource.getInstance(),
                                LocalDataSource.getInstance(this),
                                new SharedPrefs(this),
                                new FirestoreDb()),this);
        if(getIntent().hasExtra("scheduledNotification")){
            String mealId=getIntent().getStringExtra("scheduledNotification");
            mainAppPresenter.getMealAndNavigate(mealId);
        }
        else{
            WorkManager.getInstance(this).enqueue(
            new OneTimeWorkRequest.Builder(ScheduledReminderWorker.class).build());
        }
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

        ScheduledMealTask();


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
    public void ScheduledMealTask(){
        PeriodicWorkRequest periodicWorkRequest=new PeriodicWorkRequest.Builder(ScheduledReminderWorker.class
                ,1, TimeUnit.DAYS,
                15,TimeUnit.MINUTES)
                .build();
        WorkManager.getInstance(this).enqueueUniquePeriodicWork("MealReminder", ExistingPeriodicWorkPolicy.UPDATE,periodicWorkRequest);
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
    public void navigateToDetails(Meal meal) {
        Bundle bundle= new Bundle();
        bundle.putParcelable("random_meal",meal);
        navController.navigate(R.id.mealDetailsFragment,bundle);
    }
}