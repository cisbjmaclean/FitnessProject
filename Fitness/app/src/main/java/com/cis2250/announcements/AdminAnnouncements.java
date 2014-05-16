package com.cis2250.announcements;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.cis2250.app.R;
import com.cis2250.util.Utility;
import com.cis2250.network.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Dmitri on 26/01/14.
 */
public class AdminAnnouncements extends Activity implements View.OnClickListener {
    // Progress Dialog
    private ProgressDialog pDialog;
    PackageManager pm;
    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> announcementsList;

    // url to insert an announcement http://localhost/create_sql.php?sql=tableName&fields=field1~field2&parameters=param1~param2
    private static String post_announcement;
    private static String title_me;
    private static String announcement;
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_OBJECT = "CisFitness";
    private static final String TAG_EMAIL = "email";

    // create variables to hold teh objects on the form
    Button postButton = null;
    EditText textTitle = null;
    EditText textDescription = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_announcements);

        postButton = (Button) findViewById(R.id.buttonPost);
        textTitle = (EditText) findViewById(R.id.editTextTitle);
        textDescription = (EditText) findViewById(R.id.editTextDescription);

        postButton.setOnClickListener(this);
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

    @Override
    public void onClick(View view){
        post_announcement = Utility.urlString+"create_sql.php?sql=announcements&fields=announcement_title~announcement_description&parameters=";
        // Create a package manager (Twitter / Facebook)
        pm = getPackageManager();
        System.out.println("Button Pushed Is = " + view);
        // Store the temp strings
        String tempString1 = textTitle.getText().toString();
        String tempString2 = textDescription.getText().toString();
        // make strings for the twitter and facebook background thread
        title_me = tempString1;
        announcement = tempString2;
        // try catch for utf8 encoding of strings to be sent to the url
        try {
            tempString2 = URLEncoder.encode(tempString2, "UTF-8");
            tempString1 = URLEncoder.encode(tempString1, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // try catch for concat (was getting an incompatible type error)
        try{
            post_announcement +=  tempString1 + "~" + tempString2;
            // system outs are a programmers best friend for debugging
            System.out.println("The Url = ***" + post_announcement + "***");
        } catch (Exception e){
            System.out.println(e);
        }
        new Thread()
        {
            public void run() {
                // getting JSON string from URL

                JSONObject json;
                json = jParser.makeHttpRequest(post_announcement);

                // Log cat the JSON response
                Log.d("All Results: ", json.toString());

                try {
                    // Checking for SUCCESS TAG
                    int success = json.getInt(TAG_SUCCESS);

                    if (success == 1) {

                        // launch twitter and e-mail system
                        AdminAnnouncements.this.runOnUiThread(new Runnable() {

                            public void run() {
                                // sending the emails to all members of the gym to alert them of the announcement
                                try {
                                    new Thread()
                                    {
                                        public void run() {
                                            //Toast.makeText(AdminAnnouncements.this, "Your Message Is In The Database", Toast.LENGTH_LONG).show();
                                            // call to the json parser class to build a list of member e-mails
                                            JSONParser myParser = new JSONParser();
                                            JSONObject memberEmail;


                                            memberEmail = myParser.makeHttpRequest(Utility.urlString+"select_sql.php?sql=member&fields=email");

                                            try {
                                                JSONArray myArray = memberEmail.getJSONArray(TAG_OBJECT);
                                                // looping through ALL members e-mails
                                                for (int i = 0; i < myArray.length(); i++) {
                                                    JSONObject email = myArray.getJSONObject(i);
                                                    // Storing each json item in variable
                                                    final String[] tempEmail = new String[] {email.getString(TAG_EMAIL)};
                                                    // jordans intent needs to be run on the UI thread
                                                    AdminAnnouncements.this.runOnUiThread(new Runnable() {

                                                        public void run() {
                                                            // implement jordan's code here
                                                            // start the activity
                                                            startActivity(Intent.createChooser(Email.sendEmailFunctionAll(tempEmail, title_me, announcement), "Email"));

                                                        }
                                                    });
                                                }
                                                    //Toast.makeText(AdminAnnouncements.this, "Your Message Has Been E-mailed To All Members", Toast.LENGTH_LONG).show();
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }


                                        }
                                    }.start();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                // Twitter Facebook try catch just for twitter and face book side of the application
                                try {
                                    // start the Facebook activity, sending the message and Package Manager instance SocialPosting class
                                    startActivity(SocialPosting.facebook(title_me + " - " + announcement, pm));
                                    // start the Twitter activity, sending the message and Package Manager instance SocialPosting class
                                    startActivity(SocialPosting.twitter(title_me + " - " + announcement, pm));

                                    //Toast.makeText(AdminAnnouncements.this, "Your Message Has Sent To Twitter And Facebook", Toast.LENGTH_LONG).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    System.out.println("OMG IT WORKED WHAT A WAY TO DIE!");
                                }
                            }
                        });
                        System.out.println(TAG_SUCCESS);
                    } else {
                        System.out.println("YOU FAIL HORRIBLY DMITRI");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

}

