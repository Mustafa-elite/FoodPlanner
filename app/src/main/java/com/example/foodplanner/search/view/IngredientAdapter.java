package com.example.foodplanner.search.view;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;
import com.example.foodplanner.model.remote.server.categories.Category;
import com.example.foodplanner.model.remote.server.ingredients.Ingredient;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {

    private List<Ingredient> ingredientList;
    SearchViewConnector searchViewConnector;
    private final String baseURL="https://www.themealdb.com/images/ingredients/";

    public IngredientAdapter(SearchViewConnector searchViewConnector,List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
        this.searchViewConnector=searchViewConnector;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingredient ingredient = ingredientList.get(position);
        holder.search_item_name.setText(ingredient.getIngredientName());
        String ingredientUrl=ingredient.getIngredientName().replace(" ","_")+"-Small.png";
        searchViewConnector.getImage(baseURL+ingredientUrl, holder.search_item_image);
        Log.i("TAG", baseURL+ingredient.getIngredientName());
        holder.search_linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchViewConnector.getIngredientMeals(ingredient.getIngredientName());
            }
        });
    }


    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView search_item_image;
        TextView search_item_name;

        LinearLayout search_linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            search_item_name = itemView.findViewById(R.id.search_item_name);
            search_item_image = itemView.findViewById(R.id.search_item_image);
            search_linearLayout=itemView.findViewById(R.id.search_linearLayout);
        }
    }
    public void updateIngredientList(List<Ingredient> ingredientList)
    {
        this.ingredientList=ingredientList;
        notifyDataSetChanged();
    }
}
