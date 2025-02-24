package com.example.foodplanner.home.presenter;

import android.annotation.SuppressLint;

import com.example.foodplanner.home.view.HomeView;
import com.example.foodplanner.model.repository.DataRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenter {
    private HomeView homeView;
    private DataRepository dataRepository;

    public HomePresenter(HomeView homeView, DataRepository dataRepository) {
        this.homeView = homeView;
        this.dataRepository = dataRepository;
    }
    @SuppressLint("CheckResult")
    public void getRandomMeal()
    {
        dataRepository.getRemoteRandomMeal()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        item->homeView.makeToast(item.getMeals().get(0).getStrMeal()),
                        throwable -> homeView.makeToast(throwable.getMessage())
                );
    }
}
