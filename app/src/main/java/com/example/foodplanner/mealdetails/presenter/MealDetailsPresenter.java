package com.example.foodplanner.mealdetails.presenter;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.example.foodplanner.mealdetails.view.MealDetailsView;
import com.example.foodplanner.model.local.database.calendar.ScheduledMeal;
import com.example.foodplanner.model.local.database.favorites.DbMeal;
import com.example.foodplanner.model.remote.server.meals.Meal;
import com.example.foodplanner.model.repository.DataRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealDetailsPresenter {
    private MealDetailsView mealDetailsView;
    private DataRepository dataRepository;



    public MealDetailsPresenter(MealDetailsView mealDetailsView, DataRepository dataRepository) {
        this.mealDetailsView = mealDetailsView;
        this.dataRepository = dataRepository;
    }
    @SuppressLint("CheckResult")
    public void loadImageWithGlide(String imageUrl, Consumer<Bitmap> onSuccess, Consumer<Throwable> onError) {
        Log.d("GlideDebug", "Image URL: " + imageUrl);
        Single.<Bitmap>create(emitter -> {
                    try {
                        Bitmap bitmap = Glide.with(mealDetailsView.getContext())
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
                .subscribe(onSuccess,onError);
        //bitmap -> Log.i("TAG", "loadImageWithGlide: "), throwable -> Log.i("TAGError", throwable.getMessage()));
    }
    public void loadMainImage(String imageUrl) {
        loadImageWithGlide(imageUrl,
                bitmap -> mealDetailsView.setMainImage((Bitmap) bitmap),
                throwable -> mealDetailsView.makeToast(throwable.getMessage()));
    }
    @SuppressLint("CheckResult")
    public void saveFavMeal(Meal meal,Bitmap mealImage) {
        DbMeal dbMeal= new DbMeal(meal,mealImage);
        dataRepository.insertLocalMeal(dbMeal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ()-> mealDetailsView.makeToast("saved Successfully"),
                        throwable ->
                        {
                            Log.i("TAG", throwable.getMessage());
                            mealDetailsView.makeToast("Internal Problem");
                        });
        dataRepository.insertRemoteFavMeal(dbMeal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ()-> {
                            Log.i("TAG", "savedMealTo Firestore fav Succesfully");
                        },//mealDetailsView.makeToast("saved Successfully"),
                        throwable ->
                        {
                            throwable.getCause();
                            Log.i("TAG", throwable.getMessage());
                            //mealDetailsView.makeToast("Internal Problem");
                        });
    }
    @SuppressLint("CheckResult")
    public void saveCalMeal(Meal meal, String formattedDate, Bitmap mealImage) {
        ScheduledMeal scheduledMeal=new ScheduledMeal(formattedDate,new DbMeal(meal,mealImage));
        dataRepository.insertLocalCalMeal(scheduledMeal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ()-> mealDetailsView.makeToast("saved Successfully"),
                        throwable ->
                        {
                            Log.i("TAG", throwable.getMessage());
                            mealDetailsView.makeToast("Internal Problem");
                        });
        dataRepository.insertRemoteCalMeal(scheduledMeal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ()-> {
                            Log.i("TAG", "savedMealTo Firestore cal Succesfully");
                        },//mealDetailsView.makeToast("saved Successfully"),
                        throwable ->
                        {
                            throwable.getCause();
                            Log.i("TAG", throwable.getMessage());
                            //mealDetailsView.makeToast("Internal Problem");
                        });
    }
}
