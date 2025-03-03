package com.example.foodplanner.profile.view;

import android.annotation.SuppressLint;
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
import android.widget.TextView;

import com.example.foodplanner.R;
import com.example.foodplanner.model.local.database.LocalDataSource;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ProfileFragment extends Fragment {

   Button login_Btn;
   TextView profile_name,favorite_meals,calendar_meals;
    FirebaseUser user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        defineViews(view);
        buttonsHandle();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null)
        {
            String welcomeMessage=getString(R.string.hello_chef)+" "+ user.getDisplayName();
            profile_name.setText(welcomeMessage);
            login_Btn.setText(R.string.log_out);
        }
        else {
            profile_name.setText(getString(R.string.hello_chef));
            login_Btn.setText(R.string.Signin);
        }




    }

    private void buttonsHandle() {
        login_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user=FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null)
                {
                    FirebaseAuth.getInstance().signOut();
                    LocalDataSource.getInstance(getContext()).clearAllData();
                }
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.loginFragment);

            }
        });
        favorite_meals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToFavorites();
            }
        });
        calendar_meals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToCalendar();
            }
        });
    }

    private void navigateToCalendar() {
        BottomNavigationView bottomNav = getActivity().findViewById(R.id.bottomNavigationView);
        bottomNav.setSelectedItemId(R.id.calendarFragment);
    }
    private void navigateToFavorites() {
        BottomNavigationView bottomNav = getActivity().findViewById(R.id.bottomNavigationView);
        bottomNav.setSelectedItemId(R.id.favoritesFragment);
    }

    private void defineViews(View view) {
        profile_name=view.findViewById(R.id.profile_name);
        favorite_meals=view.findViewById(R.id. favorite_meals);
        calendar_meals=view.findViewById(R.id.calendar_meals);
        login_Btn =view.findViewById(R.id.login_Btn);
    }
}