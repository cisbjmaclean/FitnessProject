package com.cis2250.reports;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.cis2250.app.AboutActivity;
import com.cis2250.app.LoginActivity;
import com.cis2250.app.R;
import com.cis2250.app.SettingsActivity;
import com.cis2250.network.JSONParser;
import com.cis2250.util.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Mike Duffenais on 1/24/14.
 * Triggered from ReportsMainActivity to create reports.  uses switch case to determine which report to generate.
 */
public class CourtBookingReportActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // get passed values
        Bundle b = getIntent().getExtras();
        final int value = b.getInt("key");
        super.onCreate(savedInstanceState);
        // set content view
        setContentView(R.layout.activity_reports_court_booking);
        // set output location
        final TextView output = (TextView) findViewById(R.id.textViewReports);
        Thread t = new Thread() {
            public void run() {
                String memberID = Utility.memberId;
                //if (Utility.testing) memberID = "1";
                JSONParser jsonParser = new JSONParser();
                JSONObject report = new JSONObject();
                JSONObject users = null;
                System.out.println("this is value clicked" + value);
                switch (value) {
                    // case 1 runs check ins
                    case 1:
                        report = jsonParser.makeHttpRequest(Utility.urlString + "select_sql.php?sql=check_in&fields=membership_id~" +
                                "check_in_time~check_out_time~towel_taken~towel_return");
                        System.out.println("this is case 1");
                        break;
                    case 2:
                        // court 2 runs bookings
                        System.out.println("this is case 2");
                        report = jsonParser.makeHttpRequest(Utility.urlString + "select_sql.php?sql=court_bookings&fields=court_number" +
                                "~booking_date~start_time~member_id~member_id_opponent~notes~created_date");
                        users = jsonParser.makeHttpRequest(Utility.urlString + "select_sql.php?sql=member&fields=member_id~last_name~first_name");
                        break;
                    default:

                }

                String data = "";
                try {
                    //Get the instance of JSONArray that contains JSONObjects
                    JSONArray jsonArray = report.optJSONArray("CisFitness");
                    JSONArray jsonArrayusers = null;
                    JSONObject jsonObjectUsers = null;
                    // if users field is not null populate array
                    if (users != null) {
                        jsonArrayusers = users.optJSONArray("CisFitness");
                    }
                    //Iterate the jsonArray and print the info of JSONObjects
                    // for (int i = 0; i < jsonArray.length(); i++) {
                    //   JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        // second  switch case to cycle through data and populate output strings
                    switch (value) {
                        case 1:
                            data=null;
                            String checkInTime = null;
                            String checkOutTime = null;
                            String towelTaken = null;
                            String towelReturned = null;
                            // check for logged in members records.
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                if (memberID.equals(jsonObject.optString("membership_id"))) {
                                    checkInTime = jsonObject.optString("check_in_time");
                                    checkOutTime = jsonObject.optString("check_out_time");
                                    // set output strings if null or not = to 1
                                    if (checkOutTime == null || checkOutTime.equals("") || checkOutTime.equals("null")) {
                                        System.out.println("this is check out time" + checkOutTime);
                                        checkOutTime = "Not checked out";
                                    }
                                    towelTaken = jsonObject.optString("towel_taken");
                                    if (!towelTaken.equals("1")) {
                                        towelTaken = "No towel taken";
                                    } else {
                                        towelTaken = "Yes";
                                    }

                                    towelReturned = jsonObject.optString("towel_return");
                                    if (!towelReturned.equals("1")) {
                                        towelReturned = "Towel not returned";
                                    } else {
                                        towelReturned = "N/A";
                                    }
                                    // populate output string
                                    data += "Check in time:" + checkInTime + "\nCheck out time:  " + checkOutTime + " \nTowel taken: "
                                            + towelTaken + "\nTowel returned: " + towelReturned
                                            + "\n_______________________________________________\n";

                                }
                            }
                            break;
                        case 2:
                            data=null;
                            String booking_date = null;
                            String start_time = null;
                            String court_number = null;
                            String create_date = null;
                            String notes = null;
                            String opponent = null;
                            String member_id1 = null;
                            String member_id_opponent = null;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                member_id1 = jsonObject.optString("member_id");
                                member_id_opponent = jsonObject.optString("member_id_opponent");
                                if (report.length() == 0) {
                                    data = "No booked courts";
                                }
                                // check for logged in member info
                                if (memberID.equals(member_id1)) {
                                    System.out.println("in member match ");
                                    if (users != null) {
                                        for (int u = 0; u < jsonArrayusers.length(); u++) {
                                            jsonObjectUsers = jsonArrayusers.getJSONObject(u);
                                            // check for opponent
                                            if (member_id_opponent.equals(jsonObjectUsers.optString("member_id"))) {

                                                System.out.println("in opponent member match ");
                                                booking_date = jsonObject.optString("booking_date");
                                                start_time = jsonObject.optString("start_time");
                                                court_number = jsonObject.optString("court_number");
                                                create_date = jsonObject.optString("created_date");
                                                notes = jsonObject.optString("notes");
                                                opponent = jsonObjectUsers.optString("first_name") + " " + jsonObjectUsers.optString("last_name");
                                                // populate output string
                                                data += "Booking date: " + booking_date + "  \nCourt: " + court_number + " \nStart Time:" + start_time
                                                        + "\nOpponent: " + opponent + "\nNotes: " + notes + "\nDate Created: " + create_date
                                                        + "\n_______________________________________________\n";
                                            }
                                            if(member_id_opponent.equals("0")){

                                                System.out.println("in opponent member match ");
                                                booking_date = jsonObject.optString("booking_date");
                                                start_time = jsonObject.optString("start_time");
                                                court_number = jsonObject.optString("court_number");
                                                create_date = jsonObject.optString("created_date");
                                                notes = jsonObject.optString("notes");
                                                opponent = "No Opponent";
                                                // populate output string
                                                data += "Booking date: " + booking_date + "  \nCourt: " + court_number + " \nStart Time:" + start_time
                                                        + "\nOpponent: " + opponent + "\nNotes: " + notes + "\nDate Created: " + create_date
                                                        + "\n_______________________________________________\n";



                                            }

                                        }
                                    }
                                }
                                if(data==null){
                                    data = "No records to display";
                                }
                            }
                            break;
                        default:
                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                // set output string to textview and set scroll option
               System.out.println(data);
                System.out.println("this is logged in user " +memberID );
                output.setText(data);
                output.setMovementMethod(new ScrollingMovementMethod());

            }


        };

        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


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

