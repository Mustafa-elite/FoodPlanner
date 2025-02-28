package com.example.foodplanner.favorite.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.example.foodplanner.favorite.presenter.FavoritesPresenter;
import com.example.foodplanner.model.local.database.DbMeal;
import com.example.foodplanner.model.remote.server.network.RemoteDataSource;
import com.example.foodplanner.model.repository.DataRepository;

import java.util.List;


public class FavoritesFragment extends Fragment implements FavoriteView {
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
        defineViews(view);
        //buttonsHandle();
        favRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        favAdapter= new FavAdapter(getContext());

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
}