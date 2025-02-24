package com.example.foodplanner.model.remote.server;

import com.example.foodplanner.model.Meals;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealsRemoteDataSource {
    private  String URL="https://www.themealdb.com/api/json/v1/1/";
    private MealsService service;
    private static MealsRemoteDataSource mealsRemoteDataSource =null;

    private MealsRemoteDataSource() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Meals.class, new MealsDeserializer())
                .create();
        Retrofit retrofit= new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .baseUrl(URL).build();
        service=retrofit.create(MealsService.class);
    }
    public static MealsRemoteDataSource getInstance()
    {
        if(mealsRemoteDataSource ==null)
        {
            mealsRemoteDataSource =new MealsRemoteDataSource();
        }
        return mealsRemoteDataSource;
    }
    public Single<Meals> getRandomMealCall()
    {
        return service.getRandomMeal();
        /*call.enqueue(new Callback<Meals>() {
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
        });*/
    }
}



