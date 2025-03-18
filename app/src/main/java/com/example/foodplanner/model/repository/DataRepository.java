package com.example.foodplanner.model.repository;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;


import com.example.foodplanner.model.local.database.calendar.ScheduledMeal;
import com.example.foodplanner.model.local.database.favorites.DbMeal;
import com.example.foodplanner.model.local.database.LocalDataSource;
import com.example.foodplanner.model.remote.database.FirestoreDb;
import com.example.foodplanner.model.remote.server.categories.Categories;
import com.example.foodplanner.model.remote.server.countries.Countries;
import com.example.foodplanner.model.remote.server.ingredients.Ingredients;
import com.example.foodplanner.model.remote.server.meals.Meals;
import com.example.foodplanner.model.local.sharedpreferences.SharedPrefs;

import com.example.foodplanner.model.remote.server.network.RemoteDataSource;
import com.f2prateek.rx.preferences2.Preference;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class DataRepository {
    private RemoteDataSource remoteDataSource;
    private SharedPrefs sharedPrefs;
    private LocalDataSource localDataSource;
    private FirestoreDb firestoreDb;
    private static DataRepository dataRepository=null;
    //private static Context  context;


    private DataRepository(RemoteDataSource remoteDataSource,LocalDataSource localDataSource ,SharedPrefs sharedPrefs,FirestoreDb firestoreDb) {
        this.remoteDataSource = remoteDataSource;
        this.sharedPrefs=sharedPrefs;
        this.localDataSource=localDataSource;
        this.firestoreDb=firestoreDb;
    }
    public static DataRepository getInstance(RemoteDataSource remoteDataSource,LocalDataSource localDataSource ,SharedPrefs sharedPrefs,FirestoreDb firestoreDb)
    {
        if(dataRepository==null)
        {
            dataRepository=new DataRepository(remoteDataSource,localDataSource,sharedPrefs,firestoreDb);
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

    public Completable insertLocalMeal(DbMeal dbMeal)
    {


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

        return sharedPrefs.saveSharedPref(key,val);
    }
    public Preference<String>getLocalSharedPref(String key,String defaultValue)
    {

        return sharedPrefs.getSharedPref(key,defaultValue);

    }

    public Completable insertRemoteCalMeal(ScheduledMeal scheduledMeal) {
        return firestoreDb.savefirestoreCalMeal(scheduledMeal);
    }
    public Completable insertRemoteFavMeal(DbMeal dbMeal) {
        return firestoreDb.savefirestoreFavMeal(dbMeal);
    }
    public Observable<List<DbMeal>> getRemoteFavMeals(){

        return  firestoreDb.getFirestoreFavMeals();
    }
    public Observable<List<ScheduledMeal>> getRmoteCalMeals(){

        return firestoreDb.fetchScheduledMeals();

    }
    public Completable deleteRemoteCalMeal(String selectedDate,String mealId)
    {
        return firestoreDb.deleteScheduledMeal(selectedDate,mealId);
    }
    public Completable deleteRemoteFavMeal(String mealId){
        return firestoreDb.deleteFavoriteMeal(mealId);
    }


}
