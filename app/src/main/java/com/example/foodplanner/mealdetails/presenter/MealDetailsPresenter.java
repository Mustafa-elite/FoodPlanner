package com.example.foodplanner.mealdetails.presenter;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.example.foodplanner.home.view.HomeView;
import com.example.foodplanner.mealdetails.view.MealDetailsView;
import com.example.foodplanner.model.Meal;
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
}
