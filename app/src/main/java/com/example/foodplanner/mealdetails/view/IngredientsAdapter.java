package com.example.foodplanner.mealdetails.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;

import com.example.foodplanner.model.Ingredient;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder>{
    private List<Ingredient> ingredients;
    private DetailsViewConnector detailsViewConnector;
    private final String baseImageURL="https://www.themealdb.com/images/ingredients/";
    public IngredientsAdapter(List<Ingredient> ingredients, DetailsViewConnector detailsViewConnector) {
        this.ingredients = ingredients;
        this.detailsViewConnector=detailsViewConnector;
    }

    @NonNull
    @Override
    public IngredientsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.ingredient_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String ingredientName=ingredients.get(position).getIngredientName();
        holder.tv_ingredient_name.setText(ingredientName);
        holder.tv_ingredient_measure.setText(ingredients.get(position).getMeasure());
        detailsViewConnector.getIngredientImage(baseImageURL+ingredientName+"-small.png",holder.iv_ingredient_image);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_ingredient_image;
        TextView tv_ingredient_name,tv_ingredient_measure;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_ingredient_image = itemView.findViewById(R.id.iv_ingredient_image);
            tv_ingredient_name = itemView.findViewById(R.id.tv_ingredient_name);
            tv_ingredient_measure = itemView.findViewById(R.id.tv_ingredient_measure);
        }
    }
}
