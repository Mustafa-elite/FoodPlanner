package com.example.foodplanner.searchedmeals.presenter;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.example.foodplanner.model.remote.server.categories.Category;
import com.example.foodplanner.model.remote.server.meals.Meal;
import com.example.foodplanner.model.repository.DataRepository;
import com.example.foodplanner.searchedmeals.view.SearchedView;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchedMealsPresenter {
    private SearchedView searchedView;
    private DataRepository dataRepository;

    List<Meal> mealList;



    public SearchedMealsPresenter(SearchedView searchedView, DataRepository dataRepository) {
        this.searchedView = searchedView;
        this.dataRepository = dataRepository;
    }

    @SuppressLint("CheckResult")
    public void loadImageWithGlide(String imageUrl, Consumer<Bitmap> onSuccess, Consumer<Throwable> onError) {
        Log.d("GlideDebug", "Image URL: " + imageUrl);
        Single.<Bitmap>create(emitter -> {
                    try {
                        Bitmap bitmap = Glide.with(searchedView.getContext())
                                .asBitmap()
                                .load(imageUrl)
                                .submit()
                                .get();
                        emitter.onSuccess(bitmap);
                    } catch (Exception e) {
                        emitter.onError(e);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess, onError);
        //bitmap -> Log.i("TAG", "loadImageWithGlide: "), throwable -> Log.i("TAGError", throwable.getMessage()));
    }

    @SuppressLint("CheckResult")
    public void getMeal(String idMeal) {
        dataRepository.getRemoteMealById(idMeal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meals ->searchedView.navigateToMealDetails(meals.getMeals().get(0)),
                        throwable -> searchedView.makeToast(throwable.getMessage())
                );
    }

    public void filterList(String query) {
        Log.i("TAG", query);
        List<Meal> filteredList;
        filteredList=mealList
                .stream()
                .filter(
                        item->item.getStrMeal()
                                .toLowerCase()
                                .startsWith(query))
                .collect(Collectors.toList());
        searchedView.updateAdapterList(filteredList);
    }

    public void setMealList(List<Meal> mealList) {
        this.mealList =mealList;
    }
}
