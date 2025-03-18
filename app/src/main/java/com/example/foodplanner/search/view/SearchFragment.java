package com.example.foodplanner.search.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.example.foodplanner.model.local.database.LocalDataSource;
import com.example.foodplanner.model.local.sharedpreferences.SharedPrefs;
import com.example.foodplanner.model.remote.database.FirestoreDb;
import com.example.foodplanner.model.remote.server.categories.Category;
import com.example.foodplanner.model.remote.server.countries.Country;
import com.example.foodplanner.model.remote.server.ingredients.Ingredient;
import com.example.foodplanner.model.remote.server.meals.Meal;
import com.example.foodplanner.model.remote.server.meals.Meals;
import com.example.foodplanner.model.remote.server.network.RemoteDataSource;
import com.example.foodplanner.model.repository.DataRepository;
import com.example.foodplanner.search.presenter.SearchPresenter;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class SearchFragment extends Fragment implements SearchViewConnector, SearchViewInterface {


    RecyclerView search_recyclerView;
    ChipGroup chipGroup;
    Chip chipCategory, chipIngredient, chipCountry;
    TextInputLayout search_bar;
    EditText search_edit_text;
    SearchPresenter searchPresenter;
    CategoriesAdapter categoriesAdapter;
    IngredientAdapter ingredientsAdapter;
    CountriesAdapter countriesAdapter ;
    Observable<String> searchObservable;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        defineViews(view);
        //buttonsHandle();

        searchPresenter=new SearchPresenter(this,
                DataRepository.getInstance(
                        RemoteDataSource.getInstance(),
                        LocalDataSource.getInstance(getContext()),
                        new SharedPrefs(getContext()),
                        new FirestoreDb()));

        search_recyclerView.setHasFixedSize(true);
        categoriesAdapter = new CategoriesAdapter(this, new ArrayList<>());
        ingredientsAdapter = new IngredientAdapter(this,new ArrayList<>());
        countriesAdapter = new CountriesAdapter(this,new ArrayList<>());

        search_recyclerView.setAdapter(categoriesAdapter);
        chipGroupSet();
        searchHandle();
    }


    @SuppressLint("CheckResult")
    private void searchHandle() {

        searchObservable = Observable.create(emitter -> {
            search_edit_text.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    emitter.onNext(s.toString());

                }
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void afterTextChanged(Editable s) {}
            });
        });
        searchObservable.debounce(400, TimeUnit.MILLISECONDS)
        .distinctUntilChanged()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                query -> handleSearchQuery(query),
                throwable -> makeToast("Error searching")
        );
    }




    private void handleSearchQuery(String query) {
        Log.d("SearchDebug", query);
        int checkedId=chipGroup.getCheckedChipId();
        if (checkedId == R.id.chipCategory) {
            searchPresenter.filterList(query,SearchPresenter.CATEGORYLIST);
        } else if (checkedId == R.id.chipIngredient) {
            searchPresenter.filterList(query,SearchPresenter.INGREDIENTLIST);

        } else if (checkedId == R.id.chipCountry) {
            searchPresenter.filterList(query,SearchPresenter.COUNTRYLIST);
        }
        else {
            categoriesAdapter.updateCategoriesList(new ArrayList<>());
            search_recyclerView.setAdapter(categoriesAdapter);
            search_bar.setHint("Search");
        }
    }

    private void chipGroupSet() {
        chipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.chipCategory) {
                searchPresenter.getCategories();
                search_bar.setHint("Search By Category");

            } else if (checkedId == R.id.chipIngredient) {
                searchPresenter.getIngredients();
                search_bar.setHint("Search By Ingredients");
            } else if (checkedId == R.id.chipCountry) {
                searchPresenter.getCountries();
                search_bar.setHint("Search By Country");
            }
            else {
                categoriesAdapter.updateCategoriesList(new ArrayList<>());
                search_recyclerView.setAdapter(categoriesAdapter);
                search_bar.setHint("Search");
            }
        });
    }

    private void defineViews(View view) {
        search_recyclerView = view.findViewById(R.id.search_recyclerView);
        chipGroup = view.findViewById(R.id.chipGroup);
        chipCategory = view.findViewById(R.id.chipCategory);
        chipIngredient = view.findViewById(R.id.chipIngredient);
        chipCountry = view.findViewById(R.id.chipCountry);
        search_bar = view.findViewById(R.id.search_bar);
        search_edit_text=view.findViewById(R.id.search_edit_text);
    }

    @Override
    public void getImage(String imageUrl, ImageView ImageContainer) {
        searchPresenter.loadImageWithGlide(imageUrl,
                bitmap -> ImageContainer.setImageBitmap((Bitmap) bitmap),
                throwable -> makeToast(throwable.getMessage()));
    }

    @Override
    public void getCategoryMeals(String categoryName) {
        searchPresenter.goToCategoryMeals(categoryName);
    }

    @Override
    public void getCountryMeals(String countryName) {
        if(countryName.equals("Your Country")){
            if(isLocationPermitted()){
                searchPresenter.getLocationMeals();
            }
            else {
                requestUserPermission();
            }
        }
        else{
            searchPresenter.goToCountryMeals(countryName);
        }

    }

    private void requestUserPermission() {
        Log.i("TAG", "requestUserPermission: ");
        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                LOCATION_PERMISSION_REQUEST_CODE);
    }

    @Override
    public void getIngredientMeals(String ingredientName) {
        searchPresenter.goToIngredientMeals(ingredientName);
    }

    //@Override
    public void makeToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setCategoriesList(List<Category> categories) {
        search_recyclerView.setAdapter(categoriesAdapter);
        categoriesAdapter.updateCategoriesList(categories);
        search_recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
    }

    @Override
    public void setIngredientsList(List<Ingredient> ingredients) {
        search_recyclerView.setAdapter(ingredientsAdapter);
        ingredientsAdapter.updateIngredientList(ingredients);
        search_recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
    }

    @Override
    public void setCountriesList(List<Country> countries) {
        search_recyclerView.setAdapter(countriesAdapter);
        countriesAdapter.updateCountriesList(countries);
        search_recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
    }

    @Override
    public void navigateToSearchedMeals(Meals meals, String title) {
        navigateToMeals(meals,title);
    }

    private void navigateToMeals(Meals meals,String title) {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        Bundle bundle= new Bundle();
        bundle.putParcelable("searched_meals",meals);
        bundle.putString("title",title);
        navController.navigate(R.id.searchedMealsFragment2,bundle);
    }

    private boolean isLocationPermitted() {
        return ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i("TAG", "onRequestPermissionsResult: 11");
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i("TAG", "onRequestPermissionsResult: ");
                searchPresenter.getLocationMeals();
            } else {

                Toast.makeText(getContext(), "Location Permission is required to know your country", Toast.LENGTH_SHORT).show();
            }
        }
    }

}