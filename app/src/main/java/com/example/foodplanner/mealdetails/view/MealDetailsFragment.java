package com.example.foodplanner.mealdetails.view;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.example.foodplanner.mealdetails.presenter.MealDetailsPresenter;
import com.example.foodplanner.model.remote.server.meals.Meal;
import com.example.foodplanner.model.remote.server.network.RemoteDataSource;
import com.example.foodplanner.model.repository.DataRepository;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;


public class MealDetailsFragment extends Fragment implements MealDetailsView,DetailsViewConnector {

    TextView tv_meal_title,tv_category,tv_country,tv_instructions_details;
    ImageView btn_favorite,btn_calendar_add,iv_detail_Meal_image;
    RecyclerView recycler_ingredients;
    WebView webView;
    MealDetailsPresenter mealDetailsPresenter;
    Meal meal;
    Bitmap mealImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meal_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BottomNavigationView bottomNav = getActivity().findViewById(R.id.bottomNavigationView);
        if (bottomNav != null) {
            bottomNav.setVisibility(View.GONE);
        }
        defineViews(view);
        buttonsHandle();

        if (getArguments() != null) {
            meal = getArguments().getParcelable("random_meal");
            if (meal != null) {
                IngredientsAdapter ingredientsAdapter=new IngredientsAdapter(meal.getIngredients(),this);
                mealDetailsPresenter=new MealDetailsPresenter(this, DataRepository.getInstance(RemoteDataSource.getInstance(),getContext()));
                mealDetailsPresenter.loadMainImage(meal.getStrMealThumb());
                //mealDetailsPresenter.loadcountryImage(meal.getStrArea());
                LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recycler_ingredients.setLayoutManager(layoutManager);
                recycler_ingredients.setAdapter(ingredientsAdapter);
                setMealInfo();
                getYoutubeVideo();

            }
        }
    }

    private void buttonsHandle() {
        btn_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_favorite.setImageResource(R.drawable.red_colored_fav);
                mealDetailsPresenter.saveFavMeal(meal,mealImage);
            }
        });
    }

    private void getYoutubeVideo() {
        String videoId=Uri.parse(meal.getStrYoutube()).getQueryParameter("v");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setMediaPlaybackRequiresUserGesture(false);

        String videoUrl = "https://www.youtube.com/embed/" + videoId + "?autoplay=0&rel=0&modestbranding=1";

        String html = "<html><body style=\"margin:0;padding:0;\">" +
                "<iframe width=\"100%\" height=\"100%\" src=\"" + videoUrl +
                "\" frameborder=\"0\" allowfullscreen></iframe></body></html>";

        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        webView.loadData(html, "text/html", "utf-8");
    }

    private void setMealInfo() {
        tv_meal_title.setText(meal.getStrMeal());
        tv_category.setText(meal.getStrCategory());
        tv_country.setText(meal.getStrArea());
        tv_instructions_details.setText(meal.getStrInstructions());

    }

    private void defineViews(View view) {
        tv_meal_title=view.findViewById(R.id.tv_meal_title);
        tv_category=view.findViewById(R.id.tv_category);
        tv_country=view.findViewById(R.id.tv_country);
        tv_instructions_details=view.findViewById(R.id.tv_instructions_details);;
        btn_favorite=view.findViewById(R.id.btn_favorite);
        btn_calendar_add=view.findViewById(R.id.btn_calendar_add);
        iv_detail_Meal_image=view.findViewById(R.id.iv_detail_Meal_image);
        recycler_ingredients=view.findViewById(R.id.recycler_ingredients);;
        webView=view.findViewById(R.id.webView);
    }

    @Override
    public void setMainImage(Bitmap bitmap) {
        mealImage=bitmap;
        iv_detail_Meal_image.setImageBitmap(bitmap);
    }

    @Override
    public void makeToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void getIngredientImage(String imageUrl, ImageView ImageContainer) {
        mealDetailsPresenter.loadImageWithGlide(imageUrl,
                bitmap -> ImageContainer.setImageBitmap((Bitmap) bitmap),
                throwable -> makeToast(throwable.getMessage()));
    }
}