package com.example.foodplanner.mealdetails.view;

import android.graphics.Bitmap;
import android.net.Uri;
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
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.example.foodplanner.mainapp.view.ConfirmationDialogFragment;
import com.example.foodplanner.mealdetails.presenter.MealDetailsPresenter;
import com.example.foodplanner.model.local.database.LocalDataSource;
import com.example.foodplanner.model.local.sharedpreferences.SharedPrefs;
import com.example.foodplanner.model.remote.database.FirestoreDb;
import com.example.foodplanner.model.remote.server.meals.Meal;
import com.example.foodplanner.model.remote.server.network.RemoteDataSource;
import com.example.foodplanner.model.repository.DataRepository;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.firebase.auth.FirebaseAuth;

import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


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
                mealDetailsPresenter=new MealDetailsPresenter(this,
                        DataRepository.getInstance(
                                RemoteDataSource.getInstance(),
                                LocalDataSource.getInstance(getContext()),
                                new SharedPrefs(getContext()),
                                new FirestoreDb()));
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
                favBtnHandle();
            }
        });
        btn_calendar_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calBtnHandle();

            }
        });
    }

    private void calBtnHandle() {
        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
            openDatePicker();
        }
        else {
            ConfirmationDialogFragment dialog = ConfirmationDialogFragment.newInstance(
                    getString(R.string.you_have_to_be_signed_in_to_add_meals_to_calendar),"Sign In",
                    () -> {
                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                        navController.navigate(R.id.loginFragment);
                    }
            );
            dialog.show(getActivity().getSupportFragmentManager(), "ConfirmationDialog");
        }
    }

    private void favBtnHandle() {
        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
            btn_favorite.setImageResource(R.drawable.red_colored_fav);
            mealDetailsPresenter.saveFavMeal(meal,mealImage);
        }
        else {
            ConfirmationDialogFragment dialog = ConfirmationDialogFragment.newInstance(
                    "You have to be Signed in to add meals to favorite","Sign In",
                    () -> {
                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                        navController.navigate(R.id.loginFragment);
                    }
            );
            dialog.show(getActivity().getSupportFragmentManager(), "ConfirmationDialog");
        }
    }

    private void openDatePicker() {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Meal Date ")
                .setTheme(R.style.CustomDatePicker)
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();

        datePicker.show(getParentFragmentManager(), "DATE_PICKER");

        datePicker.addOnPositiveButtonClickListener(selection -> {
            String formattedDate = new SimpleDateFormat("d-M-y", Locale.getDefault()).format(new Date(selection));
            //makeToast(formattedDate);
            mealDetailsPresenter.saveCalMeal(meal,formattedDate,mealImage);
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