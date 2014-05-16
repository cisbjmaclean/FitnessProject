package com.cis2250.court;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TimePicker;

import com.cis2250.app.AboutActivity;
import com.cis2250.app.LoginActivity;
import com.cis2250.app.R;
import com.cis2250.app.SettingsActivity;
import com.cis2250.util.Utility;
import com.cis2250.network.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


/**
 * Created by bjmaclean on 1/20/14.S
 */
public class CourtBooking1Activity extends ListActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> theList;

    // url to get all announcements list
    private static String url;

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_OBJECT = "CisFitness";


    private  TimePicker timePicker;
    private DatePicker datePicker;

    // announcements JSONArray
    JSONArray announcements = null;

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

        Date myDate = new Date();
        String today = new SimpleDateFormat("yyyy-MM-dd").format(myDate);

        theList = new ArrayList<HashMap<String, String>>();

        url = Utility.urlString+"select_sql_court_member.php?member_id="+Utility.memberId+"&bookingdate="+today;
        System.out.println("bjm url to get court for member:"+url);

        new LoadAll().execute();

        setContentView(R.layout.activity_court_booking_1);
        Button viewAvailabilityButton = (Button) findViewById(R.id.buttonViewAvailability);

        datePicker = (DatePicker) findViewById(R.id.datePicker);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
        viewAvailabilityButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String datePicked = "" + datePicker.getYear() +"-"+ String.format("%02d", datePicker.getMonth()+1)+"-"+ String.format("%02d", datePicker.getDayOfMonth());
                System.out.println("datePicker.ymd.." + datePicked);
                CourtBooking.bookingDateStatic = datePicked;

                int hour = timePicker.getCurrentHour();
                System.out.println("timePicker hour..." + hour);
                if (hour > 0) hour--;
                String timePicked = String.format("%02d", hour)+":"+String.format("%02d", timePicker.getCurrentMinute());
                CourtBooking.startTimeRequestedStatic = timePicked;
                System.out.println("timePicker..." + timePicked);
                startActivity(new Intent(CourtBooking1Activity.this, CourtBooking2Activity.class));
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
            case R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;
        }

        return true;
    }

    class LoadAll extends AsyncTask<String, String, String> {


        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CourtBooking1Activity.this);
            pDialog.setMessage("Loading court bookings. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All announcements from url
         * */
        protected String doInBackground(String... args) {

            // getting JSON string from URL
            JSONObject json;
            json = jParser.makeHttpRequest(url);

            // Log cat the JSON response
            Log.d("All Results: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {

                    announcements = json.getJSONArray(TAG_OBJECT);

                    // looping through All Bookings
                    for (int i = 0; i < announcements.length(); i++) {
                       // if (i>12) break;
                        JSONObject c = announcements.getJSONObject(i);
                        // CourtBooking.setCourtBookingJSON(c);

                        // Storing each json item in variable
                        String courtName = c.getString("court_name");
                        String bookingDate = c.getString("booking_date");
                        String startTime = c.getString("start_time");
                        String detailsToShow = courtName + " on " + bookingDate + " at " +startTime;
                        // for testing purposes
                        //System.out.println("Title = " + title);
                        //System.out.println("Description = " + description);

                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each item to a HashMap key => value
                        map.put("details", detailsToShow);

                        // adding HashMap to ArrayList
                        theList.add(map);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing the background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all announcements
            pDialog.dismiss();
            // updating User Interface from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * putting the parsed JSON into a list view so that people can see it
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            CourtBooking1Activity.this, theList,
                            R.layout.list_item_court_members, new String[] {"details"},
                            new int[] { R.id.courtsForMember });
                    // updating listview
                    setListAdapter(adapter);
                }
            });

        }

    }


}

