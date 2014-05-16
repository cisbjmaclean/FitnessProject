package com.cis2250.booking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import com.cis2250.app.R;

public class AerobicActivity1 extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aerobic1);

        final Button myButton = (Button) findViewById(R.id.showButton);
        final DatePicker myDate = (DatePicker) findViewById(R.id.datePicker);

        myButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String thisDate = myDate.getYear() + "-" + (myDate.getMonth()+1) + "-" + myDate.getDayOfMonth();
                AerobicActivity.staticDate = thisDate;
                startActivity(new Intent(AerobicActivity1.this, AerobicActivity2.class));
            }
        }
        );
    }

// @Override
// public boolean onCreateOptionsMenu(Menu menu) {
//
// // Inflate the menu; this adds items to the action bar if it is present.
// getMenuInflater().inflate(R.menu.main, menu);
// return true;
// }
//
// @Override
// public boolean onOptionsItemSelected(MenuItem item) {
// // Handle action bar item clicks here. The action bar will
// // automatically handle clicks on the Home/Up button, so long
// // as you specify a parent activity in AndroidManifest.xml.
// int id = item.getItemId();
// /*if (id == R.id.action_settings) {
// return true;
// }
// return super.onOptionsItemSelected(item);*/
//
//// Intent intent;
//// switch (id) {
//// case R.id.action_settings:
//// intent = new Intent(this, SettingsActivity.class);
//// startActivity(intent);
//// break;
//// case R.id.action_logout:
//// intent = new Intent(this, LoginActivity.class);
//// startActivity(intent);
//// finish();
//// break;
//// case R.id.action_about:
//// intent = new Intent(this, AboutActivity.class);
//// startActivity(intent);
//// break;
//// case R.id.home:
//// NavUtils.navigateUpFromSameTask(this);
//// break;
//// }
//
// return true;
// }
}