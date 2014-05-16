package com.cis2250.court;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cis2250.CalendarUtils.CalendarActivity;
import com.cis2250.app.AboutActivity;
import com.cis2250.app.LoginActivity;
import com.cis2250.app.R;
import com.cis2250.app.SettingsActivity;
import com.cis2250.network.JSONParser;
import com.cis2250.util.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by bjmaclean on 1/20/14.S
 */
public class CourtBooking3Activity extends Activity {

    private static final String TAG_SUCCESS = "success";
    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();
    private String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

//        Thread t = new Thread() {
//            public void run() {
//                System.out.println("starting to run json stuff");
//                JSONParser jsonParser = new JSONParser();
//                jsonParser.makeHttpRequest(Utility.urlString+"select_sql.php?sql=member&fields=member_id~user_type~last_name~first_name");
//                System.out.println("Finished calling json");
//            }
//        };
//        t.start();
//        try {
//            t.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        //get the button


        setContentView(R.layout.activity_court_booking_3);
        TextView bookDetailsText = (TextView) findViewById(R.id.BookingDetails);
        bookDetailsText.setText(CourtBooking.getBookingConfirmationString());
        Button confirmButton = (Button) findViewById(R.id.buttonConfirm);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                //Based on their confirmation will want to insert a row into the court booking table.


                Date myDate = new Date();
                String today = new SimpleDateFormat("yyyy-MM-dd").format(myDate);

                try {
                    url = Utility.urlString + "create_sql.php?sql=court_bookings&fields=court_number~booking_date~start_time~member_id~member_id_opponent~notes~created_date&parameters=";
                    url += CourtBooking.courtNumberStatic + "~"
                            + URLEncoder.encode(CourtBooking.bookingDateStatic, "UTF-8") + "~"
                            + URLEncoder.encode(CourtBooking.startTimeStatic, "UTF-8") + "~"
                            + Utility.memberId + "~"
                            + CourtBooking.opponentIdStatic + "~"
                            + URLEncoder.encode("Booked on Mobile", "UTF-8") + "~"
                            + URLEncoder.encode(today, "UTF-8");




                    //Calling Ryans code to add the entry to the calendar.



                    System.out.println("bjm url for insert court booking="+url);
                } catch (Exception e) {
                    System.out.println("Error encoding insert for court booking");
                }




                new Thread() {
                    public void run() {
                        // getting JSON string from URL
                        JSONObject json;
                        json = jParser.makeHttpRequest(url);

                        // Log cat the JSON response
                        Log.d("All Results: ", json.toString());

                        try {
                            // Checking for SUCCESS TAG
                            int success = json.getInt(TAG_SUCCESS);

                            if (success == 1) {
                                // do nothing
                                System.out.println(TAG_SUCCESS);
                            } else {
                                System.out.println("YOU FAIL HORRIBLY DMITRI");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();















                startActivity(new Intent(CourtBooking3Activity.this, CalendarActivity.class));
                //startActivity(new Intent(CourtBooking3Activity.this, CourtBooking1Activity.class));


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

