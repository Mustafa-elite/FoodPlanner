package com.example.foodplanner.home.view;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.example.foodplanner.home.presenter.HomePresenter;
import com.example.foodplanner.model.remote.server.meals.Meal;
import com.example.foodplanner.model.remote.server.network.RemoteDataSource;
import com.example.foodplanner.model.repository.DataRepository;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements HomeView , HomeViewConnector {

    HomePresenter homePresenter;

    TextView random_meal_title,random_meal_description;

    ImageView random_meal_image;
    RecyclerView recyclerView;
    RandomMealsAdapter randomMealsAdapter;


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
        randomMealsAdapter= new RandomMealsAdapter(new ArrayList<>(),this);
        defineViews(view);
        buttonsHandle();
        homePresenter = new HomePresenter(this, DataRepository.getInstance(RemoteDataSource.getInstance(),getContext()));
        homePresenter.getRandomMeal();
        homePresenter.get10RandomMeals();



        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(randomMealsAdapter);
        //homePresenter.saveSharedPref("username","Ahmed");



    }

    private void buttonsHandle() {
        random_meal_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToMealDetails(homePresenter.getViewdRandomMeal());

            }
        });
    }

    private void navigateToMealDetails(Meal meal) {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        Bundle bundle= new Bundle();
        bundle.putParcelable("random_meal",meal);
        navController.navigate(R.id.mealDetailsFragment,bundle);
    }

    private void defineViews(View view) {
        random_meal_title= view.findViewById(R.id.random_meal_title);
        random_meal_description=view.findViewById(R.id.random_meal_description);
        random_meal_image=view.findViewById(R.id.random_meal_image);
        recyclerView = view.findViewById(R.id.random_meals_recycler_view);
    }


    @Override
    public void makeToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();

    }

    @Override
    public void setRandomMealTexts(String mealName, String mealDescription) {
        random_meal_title.setText(mealName);
        random_meal_description.setText(mealDescription);
    }

    @Override
    public void setRandomMealImage(Bitmap bitmap) {
        random_meal_image.setImageBitmap(bitmap);
    }

    @Override
    public void updateAdapterList(List<Meal> mealList) {
        randomMealsAdapter.updateList(mealList);
    }

    @Override
    public void getGlideImage(String imageUrl, ImageView ImageContainer) {
        homePresenter.loadImageWithGlide(imageUrl,
                bitmap -> ImageContainer.setImageBitmap((Bitmap) bitmap),
                throwable -> makeToast(throwable.getMessage()));

    }

    @Override
    public void navigateToDetails(Meal meal) {
        navigateToMealDetails(meal);
    }
}