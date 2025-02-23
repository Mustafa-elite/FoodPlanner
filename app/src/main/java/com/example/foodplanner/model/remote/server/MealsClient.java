package com.example.foodplanner.model.remote.server;

import android.util.Log;

import com.example.foodplanner.model.Meals;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealsClient {
    private  String URL="https://www.themealdb.com/api/json/v1/1/";
    private MealsService service;
    private static MealsClient mealsClient=null;

    public MealsClient() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Meals.class, new MealsDeserializer())
                .create();
        Retrofit retrofit= new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(URL).build();
        service=retrofit.create(MealsService.class);
    }
    public static MealsClient getInstance()
    {
        if(mealsClient==null)
        {
            mealsClient=new MealsClient();
        }
        return  mealsClient;
    }
    public void getRandomMealCall(NetworkCallback networkCallback)
    {
        Call<Meals> call=service.getRandomMeal();
        call.enqueue(new Callback<Meals>() {
            @Override
            public void onResponse(Call<Meals> call, Response<Meals> response) {
                if(response.isSuccessful())
                {
                    networkCallback.onSuccessfulResult(response.body());
                }
                else {
                    networkCallback.onFailureResult(response.message());
                    Log.i("TAG", "onResponse: faaaaail");
                }
            }

            @Override
            public void onFailure(Call<Meals> call, Throwable t) {
                networkCallback.onFailureResult(t.getMessage());

            }
        });
    }
}



