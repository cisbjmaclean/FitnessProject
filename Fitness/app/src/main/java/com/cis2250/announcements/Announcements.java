package com.cis2250.announcements;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.cis2250.app.R;
import com.cis2250.util.Utility;
import com.cis2250.network.JSONParser;

public class Announcements extends ListActivity  {

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> announcementsList;

    // url to get all announcements list
    private static String url_all_announcements = Utility.urlString+"select_sql.php?sql=announcements%20ORDER%20BY%20announcement_id%20DESC%20LIMIT%2016&fields=announcement_id~announcement_title~announcement_description";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_OBJECT = "CisFitness";
    private static final String TAG_DESCRIPTION = "announcement_description";
    private static final String TAG_TITLE = "announcement_title";


    // announcements JSONArray
    JSONArray announcements = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements);

        // Hashmap for ListView
        announcementsList = new ArrayList<HashMap<String, String>>();

        // Loading announcements in Background Thread
        new LoadAllAnnouncements().execute();

        // Get listview
        ListView lv = getListView();
    }

    class LoadAllAnnouncements extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Announcements.this);
            pDialog.setMessage("Loading Announcements. Please wait...");
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
            json = jParser.makeHttpRequest(url_all_announcements);

            // Log cat the JSON response
            Log.d("All Results: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // Announcements found
                    // Getting Array of Announcements
                    announcements = json.getJSONArray(TAG_OBJECT);

                    // looping through All Announcements
                    for (int i = 0; i < announcements.length(); i++) {
                        JSONObject c = announcements.getJSONObject(i);

                        // Storing each json item in variable
                        String title = c.getString(TAG_TITLE);
                        String description = c.getString(TAG_DESCRIPTION);

                        // for testing purposes
                        //System.out.println("Title = " + title);
                        //System.out.println("Description = " + description);

                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each item to a HashMap key => value
                        map.put(TAG_TITLE, title);
                        map.put(TAG_DESCRIPTION, description);

                        // adding HashMap to ArrayList
                        announcementsList.add(map);
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
                            Announcements.this, announcementsList,
                            R.layout.list_item, new String[] {TAG_TITLE,
                            TAG_DESCRIPTION},
                            new int[] { R.id.title, R.id.description });
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
