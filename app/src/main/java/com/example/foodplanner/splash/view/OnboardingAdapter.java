package com.example.foodplanner.splash.view;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.splash.model.OnBoardingPage;
import com.example.foodplanner.R;

import java.util.List;

public class OnboardingAdapter extends RecyclerView.Adapter<OnboardingAdapter.ViewHolder> {

    List<OnBoardingPage> pages;
    Fragment fragment;

    public OnboardingAdapter(List<OnBoardingPage> pages) {
        this.pages = pages;
    }

    public OnboardingAdapter(List<OnBoardingPage> pages, OnboardFragment onboardFragment) {
        this.pages = pages;
        fragment=onboardFragment;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.onboarding_page,parent,false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OnBoardingPage page=pages.get(position);
        holder.imageView.setImageResource(page.getImageResId());

        BitmapDrawable drawable = (BitmapDrawable) holder.imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        Palette.from(bitmap).generate(palette -> {
            if (palette != null) {
                int dominantColor = palette.getDominantColor(Color.WHITE);
                holder.itemView.setBackgroundColor(dominantColor);
            }
        });


        holder.Title.setText(page.getTitle());
        holder.Description.setText(page.getDescription());

    }


    @Override
    public int getItemCount() {
        return pages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView Title,Description;
        public ViewHolder(View view) {
            super(view);
            imageView=view.findViewById(R.id.onboarding_image);
            Title=view.findViewById(R.id.onboarding_title);
            Description=view.findViewById(R.id.onboarding_description);
        }
    }
}
