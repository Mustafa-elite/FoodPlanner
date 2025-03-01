package com.example.foodplanner.favorite.view;

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
import android.widget.Toast;

import com.example.foodplanner.mainapp.NetworkUtil;
import com.example.foodplanner.mainapp.view.ConfirmationDialogFragment;
import com.example.foodplanner.R;
import com.example.foodplanner.favorite.presenter.FavoritesPresenter;
import com.example.foodplanner.model.local.database.favorites.DbMeal;
import com.example.foodplanner.model.remote.server.meals.Meal;
import com.example.foodplanner.model.remote.server.network.RemoteDataSource;
import com.example.foodplanner.model.repository.DataRepository;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;


public class FavoritesFragment extends Fragment implements FavoriteView,FavoriteViewConnector {
    RecyclerView favRecyclerView;
    FavoritesPresenter favoritesPresenter;
    FavAdapter favAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BottomNavigationView bottomNav = getActivity().findViewById(R.id.bottomNavigationView);
        if (bottomNav != null) {
            bottomNav.setVisibility(View.VISIBLE);
        }
        defineViews(view);
        //buttonsHandle();
        favRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        favAdapter= new FavAdapter(getContext(),this);

        favoritesPresenter =new FavoritesPresenter(this, DataRepository.getInstance(RemoteDataSource.getInstance(),getContext()));


        favRecyclerView.setAdapter(favAdapter);
        favoritesPresenter.getFavMeals();
    }

    private void defineViews(View view) {
        favRecyclerView =view.findViewById(R.id.fav_Meals_recycler_view);
    }

    @Override
    public void updateAdapterList(List<DbMeal> dbMealList) {
        favAdapter.updatemealsList(dbMealList);
    }
    @Override
    public void makeToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();

    }

    @Override
    public void navigateToDetails(Meal meal) {
        navigateToMealDetails(meal);
    }


    @Override
    public void deleteFavMeal(DbMeal dbMeal) {
        ConfirmationDialogFragment dialog = ConfirmationDialogFragment.newInstance(
                getString(R.string.do_you_really_want_to_delete_the_meal_from_the_favorite),"Delete",
                () -> {favoritesPresenter.removeFavMeal(dbMeal);}
        );
        dialog.show(getActivity().getSupportFragmentManager(), "ConfirmationDialog");


    }

    @Override
    public void getMealAndAvigate(DbMeal dbMeal) {
        if(NetworkUtil.isConnected(getContext()))
        {
            favoritesPresenter.findMealById(dbMeal.getMealId());
        }
        else {
            makeToast("No Internet");
        }

    }
    private void navigateToMealDetails(Meal meal) {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        Bundle bundle= new Bundle();
        bundle.putParcelable("random_meal",meal);
        navController.navigate(R.id.mealDetailsFragment,bundle);
    }
}