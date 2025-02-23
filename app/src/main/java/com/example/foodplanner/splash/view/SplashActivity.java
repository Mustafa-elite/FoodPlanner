package com.example.foodplanner.splash.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.foodplanner.mainapp.view.MainActivity;
import com.example.foodplanner.R;

public class SplashActivity extends AppCompatActivity {

    Button splashSkipButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SplashFragment splashFragment = new SplashFragment();
        FragmentManager manger = getSupportFragmentManager();
        FragmentTransaction trans = manger.beginTransaction();
        trans.add(R.id.SplashContainer, splashFragment, "SplashFrag");
        trans.commit();
        splashSkipButton =findViewById(R.id.splashSkipButton);
        splashSkipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent= new Intent(SplashActivity.this, MainActivity.class);
                startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(startIntent);
            }
        });


    }
}