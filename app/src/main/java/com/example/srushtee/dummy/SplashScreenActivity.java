package com.example.srushtee.dummy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreenActivity extends AppCompatActivity {
    private PreferenceManager preferenceManager;
    private static int SPLASH_TIME_OUT=7000;
    private SharedPreferences pref;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        final Boolean isFirstRun=getSharedPreferences("PREFERENCE",MODE_PRIVATE).getBoolean("isFirstrun",true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isFirstRun)
                {
                    getSharedPreferences("PREFERENCE",MODE_PRIVATE).edit().putBoolean("isFirstrun",false).apply();
                    Intent main = new Intent(SplashScreenActivity.this, SliderActivity.class);
                    SplashScreenActivity.this.startActivity(main);
                    SplashScreenActivity.this.finish();
                }
                else {
                    Intent main = new Intent(SplashScreenActivity.this, ProfileActivity.class);
                    SplashScreenActivity.this.startActivity(main);
                    SplashScreenActivity.this.finish();

                }
            }
        }, 2500);
    }


}
