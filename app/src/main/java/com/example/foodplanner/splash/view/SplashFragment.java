package com.example.foodplanner.splash.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.foodplanner.R;
import com.example.foodplanner.mainapp.view.MainActivity;
import com.example.foodplanner.model.local.database.LocalDataSource;
import com.example.foodplanner.model.local.sharedpreferences.SharedPrefs;
import com.example.foodplanner.model.remote.database.FirestoreDb;
import com.example.foodplanner.model.remote.server.network.RemoteDataSource;
import com.example.foodplanner.model.repository.DataRepository;


public class SplashFragment extends Fragment {


    DataRepository dataRepository;
    public SplashFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("CheckResult")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.splash_fragment, container, false);

        dataRepository=DataRepository.getInstance(
                RemoteDataSource.getInstance(),
                LocalDataSource.getInstance(getContext()),
                new SharedPrefs(getContext()),
                new FirestoreDb());



        new Handler().postDelayed(()->{
            String firstTimeFlag=dataRepository.getLocalSharedPref("firstTime","true").get();
            if(firstTimeFlag.equals("true"))
            {
                dataRepository.saveLocalSharedPref("firstTime","false")
                        .asObservable()
                        .subscribe(new io.reactivex.functions.Consumer<String>() {
                            @Override
                            public void accept(String username) {
                                Log.i("TAG", "savedSharePRef");
                            }
                        });
                OnboardFragment onboardFragment=new OnboardFragment();
                FragmentManager manger = getActivity().getSupportFragmentManager();
                FragmentTransaction trans = manger.beginTransaction();
                trans.replace(R.id.SplashContainer,onboardFragment);
                trans.commit();
                Button splashSkipButton=getActivity().findViewById(R.id.splashSkipButton);
                splashSkipButton.setVisibility(Button.VISIBLE);
            }
            else {

                Intent startIntent= new Intent(getContext(), MainActivity.class);
                startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(startIntent);
            }
        },4000);
        return view;
    }
}