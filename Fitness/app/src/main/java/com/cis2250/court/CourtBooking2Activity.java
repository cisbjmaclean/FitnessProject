package com.cis2250.court;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.cis2250.app.AboutActivity;
import com.cis2250.app.LoginActivity;
import com.cis2250.app.R;
import com.cis2250.app.SettingsActivity;
import com.cis2250.util.Utility;
import com.cis2250.network.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by bjmaclean on 1/20/14.S
 */
public class CourtBooking2Activity extends ListActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> theList;

    // url to get all announcements list
    private static String url;
    private static String url_all_courts;

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_OBJECT = "CisFitness";



    // announcements JSONArray
    JSONArray announcements = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_court_booking_2);

        url = Utility.urlString+"select_sql_court.php?starttime="+CourtBooking.startTimeRequestedStatic+"&bookingdate="+CourtBooking.bookingDateStatic;
        url_all_courts = url;

        // Hashmap for ListView
        theList = new ArrayList<HashMap<String, String>>();
        System.out.println("url="+url);
        // Loading announcements in Background Thread
        new LoadAll().execute();

        // Get listview
        final ListView lv = getListView();
        lv.setClickable(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                Object o = lv.getItemAtPosition(position);
//                Toast.makeText(getApplicationContext(),
//                        o.toString(), Toast.LENGTH_SHORT).show();

                //*******************************************************************************
                //Get the Hashmap back from the object.  This was stored in there when the json
                //was brought back from the server.
                //*******************************************************************************
                HashMap<String, String> hashMapChosen = (HashMap) o;
                CourtBooking.courtNumberStatic = hashMapChosen.get("court_number");
                CourtBooking.courtNameStatic = hashMapChosen.get("court_name");
                CourtBooking.startTimeStatic = hashMapChosen.get("start_time");

//                System.out.println("BJM courtNumber="+courtNumber);
                CourtBooking.bookDetails = o.toString();
                //startActivity(new Intent(CourtBooking2Activity.this, CourtBooking3Activity.class));
                startActivity(new Intent(CourtBooking2Activity.this, CourtBooking2bActivity.class));
            }
        });
    }
    class LoadAll extends AsyncTask<String, String, String> {


        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CourtBooking2Activity.this);
            pDialog.setMessage("Loading available courts. Please wait...");
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
            json = jParser.makeHttpRequest(url_all_courts);

            // Log cat the JSON response
            Log.d("All Results: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // court found
                    // Getting Array of courts
                    announcements = json.getJSONArray(TAG_OBJECT);

                    // looping through All Bookings
                    for (int i = 0; i < announcements.length(); i++) {
                        if (i>12) break;
                        JSONObject c = announcements.getJSONObject(i);
                       // CourtBooking.setCourtBookingJSON(c);

                        // Storing each json item in variable
                        String startTime = c.getString("start_time");
                        String courtName = c.getString("court_name");
                        String courtNumber = c.getString("court_number");
                        String prompt = courtName + " " + startTime;
                        // for testing purposes
                        //System.out.println("Title = " + title);
                        //System.out.println("Description = " + description);

                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each item to a HashMap key => value
                        map.put("prompt", prompt);
                        map.put("start_time", startTime);
                        map.put("court_name", courtName);
                        map.put("court_number", courtNumber);

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
                            CourtBooking2Activity.this, theList,
                            R.layout.list_item_court_listing, new String[] {"prompt","start_time",
                            "court_name", "court_number"},
                            new int[] { R.id.courts });
                    // updating listview
                    setListAdapter(adapter);
                }
            });

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
        switch(id) {
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

