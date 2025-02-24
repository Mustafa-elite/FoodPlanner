package com.example.foodplanner.model.repository;

import com.example.foodplanner.model.Meals;
import com.example.foodplanner.model.remote.server.MealsRemoteDataSource;

import io.reactivex.rxjava3.core.Single;

public class DataRepository {
    private MealsRemoteDataSource mealsRemoteDataSource;
    private static DataRepository dataRepository=null;

    private DataRepository(MealsRemoteDataSource mealsRemoteDataSource) {
        this.mealsRemoteDataSource = mealsRemoteDataSource;
    }
    public static DataRepository getInstance(MealsRemoteDataSource mealsRemoteDataSource)
    {
        if(dataRepository==null)
        {
            dataRepository=new DataRepository(mealsRemoteDataSource);
        }
        return dataRepository;
    }
    public Single<Meals> getRemoteRandomMeal()
    {
        return mealsRemoteDataSource.getRandomMealCall();
    }
}
