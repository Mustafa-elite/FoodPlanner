package com.example.foodplanner.search.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;

import com.example.foodplanner.model.remote.server.categories.Category;

import java.util.List;
import java.util.stream.Collectors;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private List<Category> categoryList;
    SearchViewConnector searchViewConnector;
    public CategoriesAdapter(SearchViewConnector searchViewConnector, List<Category> categoryList) {
        this.searchViewConnector=searchViewConnector;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.search_item_name.setText(category.getStrCategory());
        searchViewConnector.getImage(category.getStrCategoryThumb(),holder.search_item_image);
        holder.search_linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchViewConnector.getCategoryMeals(category.getStrCategory());
            }
        });
    }


    @Override
    public int getItemCount() {
        return categoryList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView search_item_image;
        TextView search_item_name;
        LinearLayout search_linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            search_item_name=itemView.findViewById(R.id.search_item_name);
            search_item_image=itemView.findViewById(R.id.search_item_image);
            search_linearLayout=itemView.findViewById(R.id.search_linearLayout);
        }
    }
    public void updateCategoriesList(List<Category> categoryList)
    {
        this.categoryList=categoryList;
        notifyDataSetChanged();
    }
}


