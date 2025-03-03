package com.example.foodplanner.home.view;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import com.example.foodplanner.mainapp.NetworkUtil;
import com.example.foodplanner.model.local.database.LocalDataSource;
import com.example.foodplanner.model.local.sharedpreferences.SharedPrefs;
import com.example.foodplanner.model.remote.database.FirestoreDb;
import com.example.foodplanner.model.remote.server.meals.Meal;
import com.example.foodplanner.model.remote.server.network.RemoteDataSource;
import com.example.foodplanner.model.repository.DataRepository;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements HomeView , HomeViewConnector {

    HomePresenter homePresenter;

    TextView random_meal_title,random_meal_description,recyclerviewText;

    ImageView random_meal_image, noInternetImageView;
    RecyclerView recyclerView;
    RandomMealsAdapter randomMealsAdapter;
    ConstraintLayout header_constraintLayout;
    CardView cardview;



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
        defineViews(view);
        buttonsHandle();


        noInternetImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData(view);
            }
        });
        loadData(view);





    }

    private void loadData(View view) {
        if (NetworkUtil.isConnected(requireContext())) {
            noInternetImageView.setVisibility(View.GONE);
            header_constraintLayout.setVisibility(View.VISIBLE);
            cardview.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);

            recyclerviewText.setVisibility(View.VISIBLE);
            randomMealsAdapter= new RandomMealsAdapter(new ArrayList<>(),this);

            homePresenter = new HomePresenter(this,
                    DataRepository.getInstance(
                            RemoteDataSource.getInstance(),
                            LocalDataSource.getInstance(getContext()),
                            new SharedPrefs(getContext()),
                            new FirestoreDb()));
            homePresenter.getRandomMeal();
            homePresenter.get10RandomMeals();

            LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setAdapter(randomMealsAdapter);
            //homePresenter.saveSharedPref("username","Ahmed");
        }
        else {
            noInternetImageView.setVisibility(View.VISIBLE);
            header_constraintLayout.setVisibility(View.GONE);
            cardview.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            recyclerviewText.setVisibility(View.GONE);


        }
    }

    private void buttonsHandle() {
        random_meal_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NetworkUtil.isConnected(getContext()))
                {
                    navigateToMealDetails(homePresenter.getViewdRandomMeal());
                }
                else {
                    makeToast("No Internet");
                }
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
        noInternetImageView=view.findViewById(R.id.noInternetImageView);
        header_constraintLayout=view.findViewById(R.id.header_constraintLayout);
        cardview=view.findViewById(R.id.cardview);
        recyclerviewText=view.findViewById(R.id.recyclerviewText);
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

        if(NetworkUtil.isConnected(getContext()))
        {
            navigateToMealDetails(meal);
        }
        else {
            makeToast("No Internet");
        }

    }
}