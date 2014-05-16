package com.cis2250.booking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.cis2250.app.R;
import com.cis2250.network.*;
import com.cis2250.util.Utility;

/**
 * Created by tmahar10730 on 2/5/14.
 */

public class AerobicActivity3 extends Activity{

    private static String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        url = Utility.urlString + "select_aerobics_trainers.php?fields=trainerEmail&thisTrainer="
                + AerobicActivity.staticTrainer;

        setContentView(R.layout.activity_aerobic3);
        TextView bookDetailsText = (TextView) findViewById(R.id.BookingDetails);
        bookDetailsText.setText(AerobicActivity.getBookingConfirmationString());

        Button confirmButton = (Button) findViewById(R.id.buttonConfirm);
        Button homeButton = (Button) findViewById(R.id.buttonHome);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String[] myTest = new String[] {AerobicActivity.staticTrainer};
                startActivity(Intent.createChooser(Email.sendEmailFunctionAll(myTest, "Lesson Booked", "A new lesson has been booked \n"
                        +AerobicActivity.staticCategory+" on "+AerobicActivity.staticDate+" at "
                        +AerobicActivity.staticTime), "Email"));



            }

        }


        );

        homeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(AerobicActivity3.this, AerobicActivity1.class));
            }

        }
        );
    }

// @Override
// public boolean onCreateOptionsMenu(Menu menu) {
//
// // Inflate the menu; this adds items to the action bar if it is present.
// //getMenuInflater().inflate(R.menu.main_menu, menu);
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
// Intent intent;
// switch (id) {
// case R.id.action_settings:
// intent = new Intent(this, SettingsActivity.class);
// startActivity(intent);
// break;
// case R.id.action_logout:
// intent = new Intent(this, LoginActivity.class);
// startActivity(intent);
// finish();
// break;
// case R.id.action_about:
// intent = new Intent(this, AboutActivity.class);
// startActivity(intent);
// break;
// }
//
// return true;
// }
//
}