package com.example.foodplanner.home.view;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;
import com.example.foodplanner.model.remote.server.meals.Meal;

import java.util.List;

public class RandomMealsAdapter extends RecyclerView.Adapter<RandomMealsAdapter.ViewHolder>{

    private List<Meal> mealList;

    HomeViewConnector homeViewConnector;

    public RandomMealsAdapter(List<Meal> mealList, HomeViewConnector homeViewConnector) {
        //this.context = context;
        this.mealList = mealList;
        this.homeViewConnector = homeViewConnector;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.item_meal_card,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meal meal=mealList.get(position);
        holder.mealTitle.setText(meal.getStrMeal());
        homeViewConnector.getGlideImage(meal.getStrMealThumb(),holder.mealImage);
        holder.mealImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeViewConnector.navigateToDetails(meal);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

    public void updateList(List<Meal> mealList) {
        this.mealList=mealList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mealImage;
        TextView mealTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImage = itemView.findViewById(R.id.row_meal_image);
            mealTitle = itemView.findViewById(R.id.row_meal_title);

        }
    }
}