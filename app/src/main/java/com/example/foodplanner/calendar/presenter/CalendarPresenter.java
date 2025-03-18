package com.example.foodplanner.calendar.presenter;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.foodplanner.calendar.model.CalendarHelper;
import com.example.foodplanner.calendar.view.CalendarView;
import com.example.foodplanner.model.local.database.calendar.ScheduledMeal;
import com.example.foodplanner.model.local.database.favorites.DbMeal;
import com.example.foodplanner.model.repository.DataRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CalendarPresenter {

    private CalendarView calendarView;
    private DataRepository dataRepository;
    List<DbMeal> dbMealList=new ArrayList<>();

    public CalendarPresenter(CalendarView calendarView, DataRepository dataRepository) {
        this.calendarView = calendarView;
        this.dataRepository = dataRepository;
    }

    @SuppressLint("CheckResult")
    public void getDateMeal(String selectedDate) {
        Log.i("TAG", "getDateMeal: "+selectedDate);
        dataRepository.getLocalCalMeals(selectedDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        scheduledMealList->
                        {
                            dbMealList.clear();
                            for (ScheduledMeal scheduledMeal : scheduledMealList) {
                                dbMealList.add(scheduledMeal.getMeal());
                            };
                            calendarView.updateAdapterList(dbMealList);
                        },
                        throwable -> calendarView.makeToast(throwable.getMessage()));;
    }

    @SuppressLint("CheckResult")
    public void removeCalMeal(DbMeal dbMeal, String selectedDate) {
        dataRepository.deleteLocalCalMeal(selectedDate,dbMeal.getMealId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ()-> calendarView.makeToast("Deleted Successfully"),
                        throwable ->
                        {
                            //Log.i("TAG", throwable.getMessage());
                            calendarView.makeToast("Internal Problem");
                        });
        dataRepository.deleteRemoteCalMeal(selectedDate,dbMeal.getMealId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ()-> Log.i("TAG", "removeCalMeal: deleted cal "),
                        throwable ->
                        {
                            //Log.i("TAG", throwable.getMessage());
                            calendarView.makeToast("Internal Problem deleting remote cal meal");
                        });
        CalendarHelper.removeMealFromCalendar(calendarView.getContext(), new ScheduledMeal(selectedDate,dbMeal));
    }

    @SuppressLint("CheckResult")
    public void findMealById(String mealId) {
        dataRepository.getRemoteMealById(mealId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meals -> calendarView.navigateToDetails(meals.getMeals().get(0)),
                        throwable ->
                        {
                            //Log.i("TAG", throwable.getMessage());
                            calendarView.makeToast("Internal Problem");
                        });
    }
}
