package com.example.foodplanner.home.presenter;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.home.view.HomeView;
import com.example.foodplanner.model.remote.server.meals.Meal;
import com.example.foodplanner.model.repository.DataRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenter {
    private HomeView homeView;
    private DataRepository dataRepository;
    private Meal random_meal;



    public HomePresenter(HomeView homeView, DataRepository dataRepository) {
        this.homeView = homeView;
        this.dataRepository = dataRepository;
    }

    public Meal getViewdRandomMeal() {
        return random_meal;
    }
    @SuppressLint("CheckResult")
    public void getRandomMeal()
    {
        homeView.setRandomMealImage(BitmapFactory.decodeResource(homeView.getContext().getResources(), R.drawable.pot));
        dataRepository.getRemoteRandomMeal()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        item->
                        {
                            random_meal=item.getMeals().get(0);
                            String mealDescription=random_meal.getStrInstructions().length()>80? random_meal.getStrInstructions().substring(0,80)+"... Show More": random_meal.getStrInstructions();
                            homeView.setRandomMealTexts(random_meal.getStrMeal(),mealDescription);

                            loadImageWithGlide(random_meal.getStrMealThumb(),
                                    bitmap -> homeView.setRandomMealImage((Bitmap)bitmap),
                                    throwable -> homeView.makeToast("Image load failed "+ throwable.getMessage())
                                    );
                        },
                        throwable -> homeView.makeToast(throwable.getMessage())
                );
    }
    @SuppressLint("CheckResult")
    public void saveSharedPref(String key, String val)
    {
        dataRepository.saveLocalSharedPref(key,val)
                .asObservable()
                .subscribe(new io.reactivex.functions.Consumer<String>() {
                    @Override
                    public void accept(String username) {
                        homeView.makeToast(username +" saved succesfully");
                    }
                });
    }


    @SuppressLint("CheckResult")
    public void loadImageWithGlide(String imageUrl, Consumer<Bitmap> onSuccess, Consumer<Throwable> onError) {
        Log.d("GlideDebug", "Image URL: " + imageUrl);
        Single.<Bitmap>create(emitter -> {
                    try {
                        Bitmap bitmap = Glide.with(homeView.getContext())
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

    @SuppressLint("CheckResult")
    public void get10RandomMeals() {
        List<Meal> mealList=new ArrayList<>();
        for(int i=0;i<10;i++)
        {
            dataRepository.getRemoteRandomMeal()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            item-> mealList.add(item.getMeals().get(0)),
                            throwable -> homeView.makeToast(throwable.getMessage())
                    );
        }
        homeView.updateAdapterList(mealList);
    }
}
