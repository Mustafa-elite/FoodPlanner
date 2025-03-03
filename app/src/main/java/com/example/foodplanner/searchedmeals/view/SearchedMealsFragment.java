package com.example.foodplanner.searchedmeals.view;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.example.foodplanner.model.local.database.LocalDataSource;
import com.example.foodplanner.model.local.sharedpreferences.SharedPrefs;
import com.example.foodplanner.model.remote.database.FirestoreDb;
import com.example.foodplanner.model.remote.server.meals.Meal;
import com.example.foodplanner.model.remote.server.meals.Meals;
import com.example.foodplanner.model.remote.server.network.RemoteDataSource;
import com.example.foodplanner.model.repository.DataRepository;
import com.example.foodplanner.searchedmeals.presenter.SearchedMealsPresenter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class SearchedMealsFragment extends Fragment implements SearchedViewConnector,SearchedView{

    TextView searched_Meals_Title;

    TextInputLayout meals_search_bar;
    TextInputEditText meals_search_edit_text;
    RecyclerView searched_Meals_recycler_view;
    Meals meals;
    SearchedMealsPresenter searchedMealsPresenter;
    SearchedMealsAdapter searchedMealsAdapter;
    Observable<String> searchObservable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_searched_meals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        defineViews(view);
        if (getArguments() != null) {
            searched_Meals_Title.setText(getArguments().getString("title"));
            meals=getArguments().getParcelable("searched_meals");

        }
        searchedMealsPresenter=new SearchedMealsPresenter(this,
                DataRepository.getInstance(
                        RemoteDataSource.getInstance(),
                        LocalDataSource.getInstance(getContext()),
                        new SharedPrefs(getContext()),
                        new FirestoreDb()));
        searchedMealsAdapter=new SearchedMealsAdapter(meals.getMeals(),this);
        searchedMealsPresenter.setMealList(meals.getMeals());
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        searched_Meals_recycler_view.setLayoutManager(layoutManager);
        searched_Meals_recycler_view.setAdapter(searchedMealsAdapter);
        searchHandle();
    }

    private void defineViews(View view) {
        searched_Meals_Title=view.findViewById(R.id.searched_Meals_Title);
        meals_search_bar=view.findViewById(R.id.meals_search_bar);
        meals_search_edit_text=view.findViewById(R.id.meals_search_edit_text);
        searched_Meals_recycler_view=view.findViewById(R.id.searched_Meals_recycler_view);
    }

    @Override
    public void getGlideImage(String imageUrl, ImageView ImageContainer) {
            searchedMealsPresenter.loadImageWithGlide(imageUrl,
                    bitmap -> ImageContainer.setImageBitmap((Bitmap) bitmap),
                    throwable -> makeToast(throwable.getMessage()));


    }
    @Override
    public void navigateToDetails(Meal meal) {
        searchedMealsPresenter.getMeal(meal.getIdMeal());


    }




    @Override
    public void navigateToMealDetails(Meal meal) {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        Bundle bundle= new Bundle();
        bundle.putParcelable("random_meal",meal);
        navController.navigate(R.id.mealDetailsFragment,bundle);
    }

    @Override
    public void makeToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();

    }

    @Override
    public void updateAdapterList(List<Meal> filteredList) {
        searchedMealsAdapter.updateList(filteredList);
    }

    @SuppressLint("CheckResult")
    private void searchHandle() {

        searchObservable = Observable.create(emitter -> {
            meals_search_edit_text.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Log.i("TAG", s.toString());
                    emitter.onNext(s.toString());

                }
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void afterTextChanged(Editable s) {}
            });
        });
        searchObservable
                .debounce(400, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        query -> searchedMealsPresenter.filterList(query),
                        throwable -> makeToast("Error searching")
                );
    }
}