package com.example.foodplanner.model.remote.server;

import com.example.foodplanner.model.Meals;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;

public interface MealsService {

    @GET("random.php")
    Single<Meals> getRandomMeal();
}
