package com.example.foodplanner.calendar.view;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;
import com.example.foodplanner.favorite.view.FavoriteViewConnector;
import com.example.foodplanner.model.local.database.favorites.DbMeal;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

public class CalAdapter extends RecyclerView.Adapter<CalAdapter.ViewHolder> {

    Context context;
    CalendarViewConnector calendarViewConnector;

    public CalAdapter(Context context,CalendarViewConnector calendarViewConnector) {
        this.context = context;
        this.calendarViewConnector=calendarViewConnector;
    }
    List<DbMeal> dbMealList=new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.favorites_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DbMeal dbMeal=dbMealList.get(position);
        holder.favMealTitle.setText(dbMeal.getMealName());
        holder.favMealCategory.setText(dbMeal.getMealCategory());
        holder.favMealImage.setImageBitmap(dbMeal.getImage());
        holder.favDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendarViewConnector.deleteCalMeal(dbMeal);
            }
        });
        holder.fav_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarViewConnector.getMealAndAvigate(dbMeal);
            }
        });




    }

    @Override
    public int getItemCount() {
        return dbMealList.size();
    }

    public void updatemealsList(List<DbMeal> dbMealList) {
        this.dbMealList=dbMealList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView favMealImage;
        TextView favMealTitle,favMealCategory;
        Button favDeleteBtn;
        CardView fav_card_view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            favMealImage=itemView.findViewById(R.id.fav_meal_image);
            favMealTitle=itemView.findViewById(R.id.fav_meal_title);
            favMealCategory=itemView.findViewById(R.id.Fav_meal_category);
            favDeleteBtn=itemView.findViewById(R.id.delete_fav_Btn);
            fav_card_view=itemView.findViewById(R.id.fav_card_view);
        }
    }
}

