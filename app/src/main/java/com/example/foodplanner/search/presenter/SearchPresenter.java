package com.example.foodplanner.search.presenter;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.util.Log;


import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.Glide;
import com.example.foodplanner.mealdetails.view.MealDetailsView;
import com.example.foodplanner.model.remote.server.categories.Category;
import com.example.foodplanner.model.remote.server.countries.Country;
import com.example.foodplanner.model.remote.server.ingredients.Ingredient;
import com.example.foodplanner.model.repository.DataRepository;
import com.example.foodplanner.search.view.SearchViewInterface;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenter {
    private SearchViewInterface searchViewInterface;
    private DataRepository dataRepository;
    List<Category> fullCategoryList;
    List<Ingredient> fullIngredientList;
    List<Country> fullCountryList;
    public  static final int CATEGORYLIST=0;
    public  static final int INGREDIENTLIST=1;
    public  static final int COUNTRYLIST=2;


    public SearchPresenter(SearchViewInterface searchViewInterface, DataRepository dataRepository) {
        this.searchViewInterface = searchViewInterface;
        this.dataRepository = dataRepository;
    }

    @SuppressLint("CheckResult")
    public void loadImageWithGlide(String imageUrl, Consumer<Bitmap> onSuccess, Consumer<Throwable> onError) {
        Log.d("GlideDebug", "Image URL: " + imageUrl);
        Single.<Bitmap>create(emitter -> {
                    try {
                        Bitmap bitmap = Glide.with(searchViewInterface.getContext())
                                .asBitmap()
                                .load(imageUrl)
                                .submit()
                                .get();

                        emitter.onSuccess(bitmap);
                    } catch (Exception e) {
                        emitter.onError(e);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess, onError);
        //bitmap -> Log.i("TAG", "loadImageWithGlide: "), throwable -> Log.i("TAGError", throwable.getMessage()));
    }

    @SuppressLint("CheckResult")
    public void getCategories() {
        if(fullCategoryList==null)
        {
            dataRepository.getRemoteCategoriesList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            categories ->
                            {
                                fullCategoryList=categories.getCategories();
                                searchViewInterface.setCategoriesList(fullCategoryList);
                            },
                            throwable -> Log.i("TAG", throwable.getMessage()));

        }
        else {
            searchViewInterface.setCategoriesList(fullCategoryList);
        }

    }

    @SuppressLint("CheckResult")
    public void getIngredients() {
        if(fullIngredientList==null)
        {
            dataRepository.getRemoteIngredientsList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            ingredients ->
                            {
                                fullIngredientList=ingredients.getIngredients();
                                searchViewInterface.setIngredientsList(fullIngredientList);
                            },
                            throwable -> Log.i("TAG", throwable.getCause().toString()));
        }
        else {
            searchViewInterface.setIngredientsList(fullIngredientList);
        }

    }

    @SuppressLint("CheckResult")
    public void getCountries() {
        if(fullCountryList==null)
        {
            dataRepository.getRemoteCountriesList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            countries ->
                            {
                                fullCountryList=countries.getCountries();
                                searchViewInterface.setCountriesList(fullCountryList);
                            },
                            throwable -> Log.i("TAG", throwable.getMessage()));

        }
        else {
            searchViewInterface.setCountriesList(fullCountryList);
        }

    }

    public void filterList(String query, int FILTER) {
        switch (FILTER){
            case CATEGORYLIST:filterCategoryList(query);
            break;
            case COUNTRYLIST:filterCountryList(query);
            break;
            case INGREDIENTLIST:filterIngredientList(query);
            break;
        }

    }

    private void filterIngredientList(String query) {
        List<Ingredient> filteredList;
        filteredList=fullIngredientList
                .stream()
                .filter(
                        item->item
                                .getIngredientName()
                                .toLowerCase()
                                .startsWith(query))
                .collect(Collectors.toList());
        searchViewInterface.setIngredientsList(filteredList);

    }

    private void filterCountryList(String query) {
        List<Country> filteredList;
        filteredList=fullCountryList
                .stream()
                .filter(
                        item->item
                                .getName()
                                .toLowerCase()
                                .startsWith(query))
                .collect(Collectors.toList());
        searchViewInterface.setCountriesList(filteredList);

    }
    private void filterCategoryList(String query) {
        List<Category> filteredList;
        filteredList=fullCategoryList
                .stream()
                .filter(
                        item->item
                                .getStrCategory()
                                .toLowerCase()
                                .startsWith(query))
                .collect(Collectors.toList());
        searchViewInterface.setCategoriesList(filteredList);
    }
    @SuppressLint("CheckResult")
    public void goToCategoryMeals(String categoryName) {
        dataRepository.getRemoteMealsByCategory(categoryName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meals ->
                        {
                            searchViewInterface.navigateToSearchedMeals(meals,categoryName+ " Meals");
                            //Log.i("goToCategoryMeals", meals.toString());
                        },
                        throwable -> Log.i("TAG", throwable.getCause().toString()));
        ;
    }

    @SuppressLint("CheckResult")
    public void goToIngredientMeals(String ingredientName) {
        dataRepository.getRemoteMealsByIngredient(ingredientName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meals ->
                        {
                            searchViewInterface.navigateToSearchedMeals(meals,ingredientName+ " Meals");
                            //Log.i("goToIngredientMeals", meals.toString());
                        },
                        throwable -> Log.i("TAG", throwable.getCause().toString()));
        ;
    }

    @SuppressLint("CheckResult")
    public void goToCountryMeals(String countryName) {
        dataRepository.getRemoteMealsByCountry(countryName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meals ->
                        {
                            searchViewInterface.navigateToSearchedMeals(meals,countryName+ " Meals");
                            //Log.i("goToCountryMeals", meals.toString());
                        },
                        throwable -> Log.i("TAG", throwable.getCause().toString()));
        ;
    }
}
