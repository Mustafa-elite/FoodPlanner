package com.example.foodplanner.splash.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;

import com.example.foodplanner.splash.model.OnBoardingPage;
import com.example.foodplanner.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import java.util.ArrayList;
import java.util.List;


public class OnboardFragment extends Fragment {
    ViewPager2 onboardPager;
    OnboardingAdapter adapter;


    public OnboardFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.onboard_fragment, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onboardPager=view.findViewById(R.id.onboardPager);
        SpringDotsIndicator dotsIndicator = view.findViewById(R.id.dots_indicator);


        List<OnBoardingPage>  pages=new ArrayList<>();
        pages.add(new OnBoardingPage(R.drawable.onboardimage1,getString(R.string.onBoard_page1_Title),getString(R.string.onBoard_page1_Description)));
        pages.add(new OnBoardingPage(R.drawable.onboardimage2,getString(R.string.onBoard_page2_Title),getString(R.string.onBoard_page2_description)));
        pages.add(new OnBoardingPage(R.drawable.onboardimage3,getString(R.string.onBoard_page3_Title),getString(R.string.onBoard_page3_description)));



        adapter = new OnboardingAdapter(pages,this);
        onboardPager.setAdapter(adapter);
        onboardPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if(position==pages.size()-1)
                {
                    Button splashSkipButton=getActivity().findViewById(R.id.splashSkipButton);
                    splashSkipButton.setText(R.string.start);
                }
            }
        });

        dotsIndicator.attachTo(onboardPager);


    }
}