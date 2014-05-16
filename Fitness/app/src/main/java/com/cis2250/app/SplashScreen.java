package com.cis2250.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import java.lang.Override;
import java.lang.Runnable;

public class SplashScreen extends Activity {

    // Set timer for splash screen
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Delay when the following code runs
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                // Set screen to go to once timer is up
                Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(i);
                finish();
            }
            //Set time for the delayed action
        }, SPLASH_TIME_OUT);
    }

}