package com.cis2250.reports;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.cis2250.app.AboutActivity;
import com.cis2250.app.LoginActivity;
import com.cis2250.app.R;
import com.cis2250.app.SettingsActivity;


/**
 * Created by Mike Duffenais on 1/24/14.
 * Triggered from main menu. - allows user to choose which report to run and passes over key to run switch case
 */
public class ReportsMainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.reports_main);

        Button checkIns = (Button) findViewById(R.id.checkIns);
        checkIns.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ReportsMainActivity.this, CourtBookingReportActivity.class);
                Bundle b = new Bundle();
                b.putInt("key", 1); //passed key for switch case
                intent.putExtras(b); //Put item to your next Intent
                startActivity(intent);
            }
        }
        );
        Button bookings = (Button) findViewById(R.id.bookings);
        bookings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ReportsMainActivity.this, CourtBookingReportActivity.class);
                Bundle b = new Bundle();
                b.putInt("key", 2); //passed key for switch case
                intent.putExtras(b); //Put item to your next Intent
                startActivity(intent);
            }
        }
        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        /*if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);*/

        Intent intent;
        switch (id) {
            case R.id.action_settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.action_logout:
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.action_about:
                intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
        }

        return true;
    }

}

