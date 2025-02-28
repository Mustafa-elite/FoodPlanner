package com.example.foodplanner.model.remote.server.network;

import com.example.foodplanner.model.remote.server.categories.Categories;
import com.example.foodplanner.model.remote.server.countries.Countries;
import com.example.foodplanner.model.remote.server.countries.Country;
import com.example.foodplanner.model.remote.server.ingredients.Ingredient;
import com.example.foodplanner.model.remote.server.ingredients.Ingredients;
import com.example.foodplanner.model.remote.server.meals.Meals;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("random.php")
    Single<Meals> getRandomMeal();

    @GET("categories.php")
    Single<Categories> getCategories();




    @GET("list.php?a=list")
    Single<Countries> getCountries();

    @GET("list.php?i=list")
    Single<Ingredients> getIngredients();

    @GET("filter.php")
    Single<Meals> getMealsByCategory(@Query("c") String category);
    @GET("filter.php")
    Single<Meals> getMealsByIngredient(@Query("i") String ingredient);

    @GET("filter.php")
    Single<Meals> getMealsByCountry(@Query("a") String country);

    @GET("lookup.php")
    Single<Meals> getMealById(@Query("i") String id);
}
