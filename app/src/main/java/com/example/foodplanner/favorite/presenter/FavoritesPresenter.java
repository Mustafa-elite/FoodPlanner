package com.example.foodplanner.favorite.presenter;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.foodplanner.favorite.view.FavoriteView;
import com.example.foodplanner.model.local.database.favorites.DbMeal;
import com.example.foodplanner.model.repository.DataRepository;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavoritesPresenter {

    private FavoriteView favoriteView;
    private DataRepository dataRepository;
    List<DbMeal> dbMealList;

    public FavoritesPresenter(FavoriteView favoriteView, DataRepository dataRepository) {
        this.favoriteView = favoriteView;
        this.dataRepository = dataRepository;
        //dataRepository.getLocalFavMeals();
    }


    @SuppressLint("CheckResult")
    public void getFavMeals() {
        dataRepository.getLocalFavMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        item->
                        {
                            dbMealList=item;
                            favoriteView.updateAdapterList(dbMealList);
                        },
                        throwable -> favoriteView.makeToast(throwable.getMessage()));

    }

    @SuppressLint("CheckResult")
    public void removeFavMeal(DbMeal dbMeal) {
        dataRepository.deleteLocalMeal(dbMeal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ()-> favoriteView.makeToast("Deleted Successfully"),
                        throwable ->
                        {
                            //Log.i("TAG", throwable.getMessage());
                            favoriteView.makeToast("Internal Problem");
                        });
        dataRepository.deleteRemoteFavMeal(dbMeal.getMealId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ()-> Log.i("TAG", "removeFavMeal: remote"),
                        throwable ->
                        {
                            //Log.i("TAG", throwable.getMessage());
                            favoriteView.makeToast("Internal Problem in remote delete");
                        });;
    }

    @SuppressLint("CheckResult")
    public void findMealById(String mealId) {
        dataRepository.getRemoteMealById(mealId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meals -> favoriteView.navigateToDetails(meals.getMeals().get(0)),
                        throwable ->
                        {
                            //Log.i("TAG", throwable.getMessage());
                            favoriteView.makeToast("Internal Problem");
                        });;
    }
}
