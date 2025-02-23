package com.example.foodplanner.splash.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.foodplanner.R;


public class SplashFragment extends Fragment {


    public SplashFragment() {
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
        View view= inflater.inflate(R.layout.splash_fragment, container, false);


        new Handler().postDelayed(()->{

            OnboardFragment onboardFragment=new OnboardFragment();
            FragmentManager manger = getActivity().getSupportFragmentManager();
            FragmentTransaction trans = manger.beginTransaction();
            trans.replace(R.id.SplashContainer,onboardFragment);
            trans.commit();
            Button splashSkipButton=getActivity().findViewById(R.id.splashSkipButton);
            splashSkipButton.setVisibility(Button.VISIBLE);
        },4000);
        return view;
    }
}