package com.cis2250.booking;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.cis2250.network.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import com.cis2250.app.R;
import com.cis2250.util.Utility;

/**
 * Created by tmahar10730 on 2/5/14.
 */
public class AerobicActivity2 extends ListActivity {

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> theList;
    private static String url;

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_OBJECT = "CisFitness";

    JSONArray aerobics = null;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_aerobic2);

        url = Utility.urlString + "select_aerobics.php?fields=date~time~remaining_seats~category_id&thisTime="
                + AerobicActivity.staticTime + "&thisDate=" + AerobicActivity.staticDate;

        // Hashmap for ListView
        theList = new ArrayList<HashMap<String, String>>();

        new LoadAll().execute();

        // Get listview
        final ListView lv = getListView();
        lv.setClickable(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                Object o = lv.getItemAtPosition(position);

                //*******************************************************************************
                //Get the Hashmap back from the object. This was stored in there when the json
                //was brought back from the server.
                //*******************************************************************************
                HashMap<String, String> hashMapChosen = (HashMap) o;
                AerobicActivity.staticDate = hashMapChosen.get("date");
                AerobicActivity.staticTime = hashMapChosen.get("time");
                AerobicActivity.staticSeats = hashMapChosen.get("remaining_seats");
                AerobicActivity.staticCategory = hashMapChosen.get("category_id");

                startActivity(new Intent(AerobicActivity2.this, AerobicActivity3.class));
            }
        });
    }

    class LoadAll extends AsyncTask<String, String, String> {
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
                    // Getting Array
                    aerobics = json.getJSONArray(TAG_OBJECT);

                    // looping through All Bookings
                    for (int i = 0; i < aerobics.length(); i++) {
                        if (i>12) break;
                        JSONObject c = aerobics.getJSONObject(i);

                        // Storing each json item in variable
                        String date = c.getString("date");
                        String time = c.getString("time");
                        String seats = c.getString("remaining_seats");
                        String category = c.getString("category_id");
                        String prompt = category + " @ " + time + " -- Seats: " + seats;


                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each item to a HashMap key => value
                        map.put("prompt", prompt);
                        map.put("date", date);
                        map.put("time", time);
                        map.put("remaining_seats", seats);
                        map.put("category_id", category);

                        // adding HashMap to ArrayList
                        theList.add(map);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {

            // updating User Interface from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * putting the parsed JSON into a list view so that people can see it
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            AerobicActivity2.this, theList,
                            R.layout.list_item_aerobic_listing, new String[] {"prompt","date",
                            "time", "remaining_seats", "category_id"},
                            new int[] { R.id.aerobics });

                    // updating listview
                    setListAdapter(adapter);
                }
            });

        }

    }
}