package com.example.foodplanner.model.repository;

import android.content.Context;


import com.example.foodplanner.model.remote.server.categories.Categories;
import com.example.foodplanner.model.remote.server.countries.Countries;
import com.example.foodplanner.model.remote.server.countries.Country;
import com.example.foodplanner.model.remote.server.ingredients.Ingredient;
import com.example.foodplanner.model.remote.server.ingredients.Ingredients;
import com.example.foodplanner.model.remote.server.meals.Meals;
import com.example.foodplanner.model.local.sharedpreferences.SharedPrefs;

import com.example.foodplanner.model.remote.server.network.RemoteDataSource;
import com.f2prateek.rx.preferences2.Preference;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class DataRepository {
    private RemoteDataSource remoteDataSource;
    private SharedPrefs sharedPrefs;
    private static DataRepository dataRepository=null;
    private static Context  context;

    private DataRepository(RemoteDataSource remoteDataSource, Context _context) {
        this.remoteDataSource = remoteDataSource;
        sharedPrefs=new SharedPrefs(_context);
        context=_context;
    }
    public static DataRepository getInstance(RemoteDataSource remoteDataSource, Context _context)
    {
        if(dataRepository==null)
        {
            dataRepository=new DataRepository(remoteDataSource,_context);
        }
        else {
            context=_context;

        }
        return dataRepository;
    }

    public Single<Meals> getRemoteRandomMeal()
    {

        return remoteDataSource.getRandomMealCall();

    }
    public Single<Categories> getRemoteCategoriesList()
    {

        return remoteDataSource.getCategoriesCall();

    }
    public Single<Ingredients> getRemoteIngredientsList()
    {

        return remoteDataSource.getIngredientsCall();

    }
    public Single<Countries> getRemoteCountriesList()
    {

        return remoteDataSource.getCountriesCall();

    }
    public Single<Meals> getRemoteMealsByCategory(String category) {
        return remoteDataSource.getMealsByCategoryCall(category);
    }
    public Single<Meals> getRemoteMealsByIngredient(String ingredient) {
        return remoteDataSource.getMealsByIngredientCall(ingredient);
    }
    public Single<Meals> getRemoteMealsByCountry(String country) {
        return remoteDataSource.getMealsByCountryCall(country);
    }



    public Preference<String> saveLocalSharedPref(String key,String val)
    {
        sharedPrefs.setRxSharedPreferencesContext(context);
        return sharedPrefs.saveSharedPref(key,val);
    }
}
