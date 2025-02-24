package com.example.foodplanner.home.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.example.foodplanner.home.presenter.HomePresenter;
import com.example.foodplanner.model.remote.server.MealsRemoteDataSource;
import com.example.foodplanner.model.repository.DataRepository;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class HomeFragment extends Fragment implements HomeView {

    HomePresenter homePresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BottomNavigationView bottomNav = getActivity().findViewById(R.id.bottomNavigationView);
        if (bottomNav != null) {
            bottomNav.setVisibility(View.VISIBLE);
        }
        homePresenter=new HomePresenter(this, DataRepository.getInstance(MealsRemoteDataSource.getInstance()));
        homePresenter.getRandomMeal();


    }

    @Override
    public void makeToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();

    }
}