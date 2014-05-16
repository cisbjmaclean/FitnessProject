package com.cis2250.court;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.cis2250.app.R;
import com.cis2250.util.Utility;
import com.cis2250.network.JSONParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jeff White on 2/12/14.
 */
public class CourtBooking2bActivity extends ListActivity {

    ArrayList<HashMap<String, String>> theList;
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_OBJECT = "CisFitness";

    // announcements JSONArray
    JSONArray announcements = null;

    // Progress Dialog
    private ProgressDialog pDialog;
    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    String[] member_id;
    String[] wholename;

    JSONParser jp = new JSONParser();
    JSONObject jo;
    String url = Utility.urlString + "select_sql.php?sql=member&fields=member_id~last_name~first_name";

    @Override
    protected void onCreate(Bundle savedInstanceState){

        Thread t = new Thread(){
          public void run(){
              jo = jp.makeHttpRequest(url);
              try {
                  System.out.println(jo.getInt("success"));
                  int success = jo.getInt("success");
                  if (success == 1) {
                      JSONArray ja = jo.getJSONArray("CisFitness");
                      member_id = new String[ja.length()];
                      wholename = new String[ja.length()];
                      for (int i=0; i < ja.length(); i++) {
                          JSONObject jo2 = ja.getJSONObject(i);
                          member_id[i] = jo2.getString("member_id");
                          wholename[i] = jo2.getString("first_name") + " " + jo2.getString("last_name");

                      }
                  }
              } catch (JSONException e) {
                  e.printStackTrace();
              }

          }
        };

        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opponentchooser);

//        Spinner opponents = (Spinner)findViewById(R.id.spinneropponent);
//        opponents.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, wholename));


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
                CourtBooking.opponentIdStatic = hashMapChosen.get("member_id");
                CourtBooking.opponentName = hashMapChosen.get("full_name");

//                System.out.println("BJM courtNumber="+courtNumber);
                //startActivity(new Intent(CourtBooking2Activity.this, CourtBooking3Activity.class));
                startActivity(new Intent(CourtBooking2bActivity.this, CourtBooking3Activity.class));
            }
        });






//        String opponent = member_id[opponents.getSelectedItemPosition()];
//        CourtBooking.opponentIdStatic = opponent;
//        startActivity(new Intent(CourtBooking2bActivity.this, CourtBooking3Activity.class));
    }

    class LoadAll extends AsyncTask<String, String, String> {


        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CourtBooking2bActivity.this);
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
            json = jParser.makeHttpRequest(url);

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
                        String memberId = c.getString("member_id");
                        String firstName = c.getString("first_name");
                        String lastName = c.getString("last_name");
                        String fullName = firstName+ " " + lastName;
                        // for testing purposes
                        //System.out.println("Title = " + title);
                        //System.out.println("Description = " + description);

                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each item to a HashMap key => value
                        map.put("full_name", fullName);
                        map.put("first_name", firstName);
                        map.put("last_name", lastName);
                        map.put("member_id", memberId);

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
                            CourtBooking2bActivity.this, theList,
                            R.layout.list_item_all_members, new String[] {"full_name","first_name",
                            "last_name", "member_id"},
                            new int[] { R.id.allMembers });
                    // updating listview
                    setListAdapter(adapter);
                }
            });

        }

    }



}
