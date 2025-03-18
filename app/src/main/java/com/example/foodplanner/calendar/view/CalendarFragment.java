package com.example.foodplanner.calendar.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.foodplanner.mainapp.NetworkUtil;
import com.example.foodplanner.mainapp.view.ConfirmationDialogFragment;
import com.example.foodplanner.R;
import com.example.foodplanner.calendar.presenter.CalendarPresenter;
import com.example.foodplanner.model.local.database.LocalDataSource;
import com.example.foodplanner.model.local.database.favorites.DbMeal;
import com.example.foodplanner.model.local.sharedpreferences.SharedPrefs;
import com.example.foodplanner.model.remote.database.FirestoreDb;
import com.example.foodplanner.model.remote.server.meals.Meal;
import com.example.foodplanner.model.remote.server.network.RemoteDataSource;
import com.example.foodplanner.model.repository.DataRepository;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;


public class CalendarFragment extends Fragment implements com.example.foodplanner.calendar.view.CalendarView,CalendarViewConnector {

    RecyclerView CalRecyclerView;
    RecyclerView calRecyclerView;
    CalendarPresenter calendarPresenter;
    CalAdapter calAdapter;
    CalendarView calendarView;
    String selectedDate;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BottomNavigationView bottomNav = getActivity().findViewById(R.id.bottomNavigationView);
        if (bottomNav != null) {
            bottomNav.setVisibility(View.VISIBLE);
        }
        defineViews(view);
        buttonsHandle();
        calRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        calAdapter= new CalAdapter(getContext(),this);

        calendarPresenter =new CalendarPresenter(this,
                DataRepository.getInstance(
                        RemoteDataSource.getInstance(),
                        LocalDataSource.getInstance(getContext()),
                        new SharedPrefs(getContext()),
                        new FirestoreDb()));


        calRecyclerView.setAdapter(calAdapter);





    }

    private void buttonsHandle() {
        calendarView.setOnDateChangeListener((cView, year, month, dayOfMonth) -> {
            selectedDate = dayOfMonth + "-" + (month + 1) + "-" + year;
            //Toast.makeText(getContext(), "Selected Date: " + selectedDate, Toast.LENGTH_SHORT).show();
            calendarPresenter.getDateMeal(selectedDate);
        });
    }

    private void defineViews(View view) {
        calendarView = view.findViewById(R.id.calendarView);
        calRecyclerView=view.findViewById(R.id.cal_recycler_view);

    }

    @Override
    public void deleteCalMeal(DbMeal dbMeal) {
        ConfirmationDialogFragment dialog = ConfirmationDialogFragment.newInstance(
                "Do you Really want To delete the meal From the Calendar","Delete",
                () -> {
                    calendarPresenter.removeCalMeal(dbMeal,selectedDate);
                }
        );
        dialog.show(getActivity().getSupportFragmentManager(), "ConfirmationDialog");

    }

    @Override
    public void getMealAndAvigate(DbMeal dbMeal) {
        if(NetworkUtil.isConnected(getContext()))
        {
            calendarPresenter.findMealById(dbMeal.getMealId());
        }
        else {
            makeToast("No Internet");
        }
    }

    @Override
    public void updateAdapterList(List<DbMeal> dbMealList) {
        calAdapter.updatemealsList(dbMealList);
    }
    @Override
    public void makeToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();

    }

    @Override
    public void navigateToDetails(Meal meal) {
        navigateToMealDetails(meal);
    }
    private void navigateToMealDetails(Meal meal) {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        Bundle bundle= new Bundle();
        bundle.putParcelable("random_meal",meal);
        navController.navigate(R.id.mealDetailsFragment,bundle);
    }
}