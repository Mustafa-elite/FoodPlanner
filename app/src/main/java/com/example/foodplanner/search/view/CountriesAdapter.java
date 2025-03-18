package com.example.foodplanner.search.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;
import com.example.foodplanner.model.remote.server.countries.Country;
import com.example.foodplanner.search.model.CountryCodeConverter;

import java.util.List;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.ViewHolder> {

    private List<Country> countryList;
    private Context context;
    SearchViewConnector searchViewConnector;

    private final String baseURL="https://www.themealdb.com/images/icons/flags/big/64/";
    public CountriesAdapter(SearchViewConnector searchViewConnector, List<Country> countryList) {
        this.searchViewConnector=searchViewConnector;
        this.countryList = countryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Country country = countryList.get(position);
        holder.search_item_name.setText(country.getName());
        String CountryCode= CountryCodeConverter.getCountryCode(country.getName());
        if(country.getName().equals("Your Country"))
        {
            holder.search_item_image.setImageResource(R.drawable.location);
        }
        else {
            searchViewConnector.getImage(baseURL+CountryCode+".png", holder.search_item_image);
        }

        holder.search_linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchViewConnector.getCountryMeals(country.getName());

            }
        });
    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }

    public void updateCountriesList(List<Country> countries) {
        countryList=countries;
        notifyDataSetChanged();
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
}
