package com.example.foodplanner.model.repository;

import android.content.Context;

import com.example.foodplanner.model.Meals;
import com.example.foodplanner.model.local.sharedpreferences.SharedPrefs;
import com.example.foodplanner.model.remote.server.MealsRemoteDataSource;
import com.f2prateek.rx.preferences2.Preference;

import io.reactivex.rxjava3.core.Single;

public class DataRepository {
    private MealsRemoteDataSource mealsRemoteDataSource;
    private SharedPrefs sharedPrefs;
    private static DataRepository dataRepository=null;
    private static Context  context;

    private DataRepository(MealsRemoteDataSource mealsRemoteDataSource, Context _context) {
        this.mealsRemoteDataSource = mealsRemoteDataSource;
        sharedPrefs=new SharedPrefs(_context);
        context=_context;
    }
    public static DataRepository getInstance(MealsRemoteDataSource mealsRemoteDataSource, Context _context)
    {
        if(dataRepository==null)
        {
            dataRepository=new DataRepository(mealsRemoteDataSource,_context);
        }
        else {
            context=_context;

        }
        return dataRepository;
    }

    public Single<Meals> getRemoteRandomMeal()
    {

        return mealsRemoteDataSource.getRandomMealCall();

    }
    public Preference<String> saveLocalSharedPref(String key,String val)
    {
        sharedPrefs.setRxSharedPreferencesContext(context);
        return sharedPrefs.saveSharedPref(key,val);
    }
}
