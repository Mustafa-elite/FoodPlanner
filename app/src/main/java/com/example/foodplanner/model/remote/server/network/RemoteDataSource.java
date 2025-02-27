package com.example.foodplanner.model.remote.server.network;

import android.util.Log;

import com.example.foodplanner.model.remote.server.categories.Categories;
import com.example.foodplanner.model.remote.server.categories.CategoriesDeserializer;

import com.example.foodplanner.model.remote.server.countries.Countries;
import com.example.foodplanner.model.remote.server.countries.CountriesDeserializer;
import com.example.foodplanner.model.remote.server.countries.Country;
import com.example.foodplanner.model.remote.server.ingredients.Ingredient;
import com.example.foodplanner.model.remote.server.ingredients.Ingredients;
import com.example.foodplanner.model.remote.server.ingredients.IngredientsDeserializer;
import com.example.foodplanner.model.remote.server.meals.Meals;
import com.example.foodplanner.model.remote.server.meals.MealsDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteDataSource {
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static RemoteDataSource instance = null;
    private ApiService apiService;

    private RemoteDataSource() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Meals.class, new MealsDeserializer())
                .registerTypeAdapter(Categories.class, new CategoriesDeserializer())
                .registerTypeAdapter(Countries.class, new CountriesDeserializer())
                .registerTypeAdapter(Ingredients.class, new IngredientsDeserializer())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public static RemoteDataSource getInstance() {
        if (instance == null) {
            instance = new RemoteDataSource();
        }
        return instance;
    }

    public Single<Meals> getRandomMealCall() {
        return apiService.getRandomMeal();
    }

    public Single<Categories> getCategoriesCall() {
        return apiService.getCategories();
    }

    public Single<Ingredients> getIngredientsCall() {
        return apiService.getIngredients();
    }

    public Single<Countries> getCountriesCall() {
        return apiService.getCountries();
    }
    public Single<Meals> getMealsByCategoryCall(String category) {
        return apiService.getMealsByCategory(category);
    }
    public Single<Meals> getMealsByIngredientCall(String ingredient) {
        return apiService.getMealsByIngredient(ingredient);
    }
    public Single<Meals> getMealsByCountryCall(String country) {
        return apiService.getMealsByCountry(country);
    }



}
