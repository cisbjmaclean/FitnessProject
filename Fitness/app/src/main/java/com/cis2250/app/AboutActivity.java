package com.cis2250.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Joel on 16/01/14.
 */
public class AboutActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Button backButton = (Button)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                // Return user to the Main Menu
                Intent intent = new Intent(AboutActivity.this, MainMenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
