package com.example.foodplanner.model.repository;

import android.content.Context;
import android.graphics.Bitmap;


import com.example.foodplanner.model.local.database.calendar.ScheduledMeal;
import com.example.foodplanner.model.local.database.favorites.DbMeal;
import com.example.foodplanner.model.local.database.LocalDataSource;
import com.example.foodplanner.model.remote.server.categories.Categories;
import com.example.foodplanner.model.remote.server.countries.Countries;
import com.example.foodplanner.model.remote.server.ingredients.Ingredients;
import com.example.foodplanner.model.remote.server.meals.Meal;
import com.example.foodplanner.model.remote.server.meals.Meals;
import com.example.foodplanner.model.local.sharedpreferences.SharedPrefs;

import com.example.foodplanner.model.remote.server.network.RemoteDataSource;
import com.f2prateek.rx.preferences2.Preference;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class DataRepository {
    private RemoteDataSource remoteDataSource;
    private SharedPrefs sharedPrefs;
    private LocalDataSource localDataSource;
    private static DataRepository dataRepository=null;
    private static Context  context;

    private DataRepository(RemoteDataSource remoteDataSource, Context _context) {
        this.remoteDataSource = remoteDataSource;
        sharedPrefs=new SharedPrefs(_context);
        context=_context;
        localDataSource=LocalDataSource.getInstance(_context);
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
    public Single<Meals> getRemoteMealById(String id) {
        return remoteDataSource.getMealByIdCall(id);
    }

    public Completable insertLocalMeal(Meal mealToSave, Bitmap mealImage)
    {
        DbMeal dbMeal= new DbMeal(mealToSave,mealImage);
        return localDataSource.insertFavMeal(dbMeal);
    }

    public Completable deleteLocalMeal(DbMeal dbMeal)
    {
        return localDataSource.deleteFavMeal(dbMeal);
    }
    public Flowable<List<DbMeal>> getLocalFavMeals()
    {
        return localDataSource.getStoredFavMeals();

    }

    public Flowable<List<ScheduledMeal>> getLocalCalMeals(String date)
    {
        return localDataSource.getCalendarMeals(date);
    }

    public Completable insertLocalCalMeal(ScheduledMeal scheduledMeal)
    {
        return localDataSource.insertCalendarMeal(scheduledMeal);
    }
    public Completable deleteLocalCalMeal(String selectedDate,String mealId)
    {
        return localDataSource.deleteCalendarMeal(selectedDate,mealId);
    }







    public Preference<String> saveLocalSharedPref(String key,String val)
    {
        sharedPrefs.setRxSharedPreferencesContext(context);

        return sharedPrefs.saveSharedPref(key,val);
    }
    public Preference<String>getLocalSharedPref(String key,String defaultValue)
    {
        sharedPrefs.setRxSharedPreferencesContext(context);
        return sharedPrefs.getSharedPref(key,defaultValue);

    }
}
