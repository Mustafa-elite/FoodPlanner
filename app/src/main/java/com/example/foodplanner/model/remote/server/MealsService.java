package com.example.foodplanner.model.remote.server;

import com.example.foodplanner.model.Meals;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MealsService {

    @GET("random.php")
    Call<Meals> getRandomMeal();
}
