package com.cis2250.app;

import com.cis2250.network.JSONParser;
import com.cis2250.util.Utility;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URLEncoder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class MemberInformation extends ActionBarActivity {
     //sql url
    private static String sqlURL = Utility.urlString+"select_sql.php?sql=member&fields=member_id~user_type~last_name~first_name~password~email~phone_cell~phone_home~phone_work~address~status~membership_type~membership_date";

    //variables
    ArrayList<HashMap<String, String>> memberList;
    JSONArray members = null;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_OBJECT = "CisFitness";
    private static final String TAG_ID = "member_id";
    private static final String TAG_USERTYPE = "user_type";
    private static final String TAG_LASTNAME = "last_name";
    private static final String TAG_FIRSTNAME = "first_name";
    private static final String TAG_PASSWORD = "password";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_PHONECELL = "phone_cell";
    private static final String TAG_PHONEHOME = "phone_home";
    private static final String TAG_PHONEWORK = "phone_work";
    private static final String TAG_ADDRESS = "address";
    private static final String TAG_STATUS = "status";
    private static final String TAG_MEMBERSHIPTYPE = "membership_type";
    private static final String TAG_MEMBERSHIPDATE = "membership_date";
    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_information);
        // Hashmap for ListView
        memberList = new ArrayList<HashMap<String, String>>();
        loadMembers();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    public void loadMembers(){
        new Thread()
        {
            public void run() {
                // getting JSON string from URL
                JSONObject json;
                json = jParser.makeHttpRequest(sqlURL);

                // Log cat the JSON response
                Log.d("All Results: ", json.toString());

                try {
                    // Checking for SUCCESS TAG
                    int success = json.getInt(TAG_SUCCESS);

                    if (success == 1) {
                        members = json.getJSONArray(TAG_OBJECT);
                        for (int i = 0; i < members.length(); i++) {
                            JSONObject c = members.getJSONObject(i);

                            String sessionid = Utility.memberId;

                            // Storing each json item in variable
                            final String id = c.getString(TAG_ID);
                            if(sessionid.equals(id)){
                                //gets correct member info
                                final String userType = c.getString(TAG_USERTYPE);
                                final String lastName = c.getString(TAG_LASTNAME);
                                final String firstName = c.getString(TAG_FIRSTNAME);
                                final String password = c.getString(TAG_PASSWORD);
                                final String email = c.getString(TAG_EMAIL);
                                final String phoneCell = c.getString(TAG_PHONECELL);
                                final String phoneHome = c.getString(TAG_PHONEHOME);
                                final String phoneWork = c.getString(TAG_PHONEWORK);
                                final String address = c.getString(TAG_ADDRESS);
                                final String status = c.getString(TAG_STATUS);
                                final String membershipType = c.getString(TAG_MEMBERSHIPTYPE);
                                final String membershipDate = c.getString(TAG_MEMBERSHIPDATE);
                                //sets text boxes in thread with view access
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        EditText statusBox = (EditText) findViewById(R.id.Status);
                                        statusBox.setText(status);
                                        EditText idBox = (EditText) findViewById(R.id.MemberId);
                                        idBox.setText(id);
                                        EditText typeBox = (EditText) findViewById(R.id.MemberType);
                                        typeBox.setText(membershipType);
                                        EditText fNameBox = (EditText) findViewById(R.id.FName);
                                        fNameBox.setText(firstName);
                                        EditText lNameBox = (EditText) findViewById(R.id.LName);
                                        lNameBox.setText(lastName);
                                        EditText emailBox = (EditText) findViewById(R.id.Email);
                                        emailBox.setText(email);
                                        EditText phoneHomeBox = (EditText) findViewById(R.id.HomeNumber);
                                        phoneHomeBox.setText(phoneHome);
                                        EditText phoneCellBox = (EditText) findViewById(R.id.CellNumber);
                                        phoneCellBox.setText(phoneCell);
                                        EditText workCellBox = (EditText) findViewById(R.id.WorkPhone);
                                        workCellBox.setText(phoneWork);
                                        EditText addressBox = (EditText) findViewById(R.id.Address);
                                        addressBox.setText(address);
                                        EditText memberSinceBox = (EditText) findViewById(R.id.MemberSince);
                                        memberSinceBox.setText(membershipDate);
                                        EditText passwordBox = (EditText) findViewById(R.id.Password);
                                        passwordBox.setText(password);
                                    }
                                });

                            }
                        }
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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.member_information, menu);
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

    public void updateInfo(View view) {
        new Thread()
        {
            public void run() {
                String sqlUpdateURL = "";
                //edit boxs
                EditText lNameBox = (EditText) findViewById(R.id.LName);
                EditText fNameBox = (EditText) findViewById(R.id.FName);
                EditText passwordBox = (EditText) findViewById(R.id.Password);
                EditText rePasswordBox = (EditText) findViewById(R.id.rePassword);
                EditText emailBox = (EditText) findViewById(R.id.Email);
                EditText phoneCellBox = (EditText) findViewById(R.id.CellNumber);
                EditText phoneHomeBox = (EditText) findViewById(R.id.HomeNumber);
                EditText workCellBox = (EditText) findViewById(R.id.WorkPhone);
                EditText addressBox = (EditText) findViewById(R.id.Address);
                EditText idBox = (EditText) findViewById(R.id.MemberId);
                // declare and encode url to use
                try {
                sqlUpdateURL = Utility.urlString+"update_sql.php?sql=member&fields=last_name~first_name~password~email~phone_cell~phone_home~phone_work~address&"
                        +"parameters="+URLEncoder.encode(lNameBox.getText().toString(), "UTF-8")+"~"+URLEncoder.encode(fNameBox.getText().toString(), "UTF-8")+"~"+URLEncoder.encode(passwordBox.getText().toString(), "UTF-8")+"~"+URLEncoder.encode(emailBox.getText().toString(), "UTF-8")+"~"+URLEncoder.encode(phoneCellBox.getText().toString(), "UTF-8")+"~"+URLEncoder.encode(phoneHomeBox.getText().toString(), "UTF-8")+"~"+URLEncoder.encode(workCellBox.getText().toString(), "UTF-8")+"~"+URLEncoder.encode(addressBox.getText().toString(), "UTF-8")
                        +"&primarykey=member_id&primaryinfo="+URLEncoder.encode(idBox.getText().toString(), "UTF-8");

                }catch(UnsupportedEncodingException e){
                    e.printStackTrace();
                }
                //check to see if password and retype password fields match
                if (passwordBox.getText().toString().equals(rePasswordBox.getText().toString())){
                    JSONObject json;
                    json = jParser.makeHttpRequest(sqlUpdateURL);
                // Log cat the JSON response
                    Log.d("All Results: ", json.toString());
                    try {
                        // Checking for SUCCESS TAG
                        int success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            MemberInformation.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
                                }
                            });

                        }
                        else{
                            MemberInformation.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "failure", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }catch (JSONException e) {
                         e.printStackTrace();
                    }
                }else{
                    MemberInformation.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Password fields do not match", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

        }.start();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_member_information, container, false);
            return rootView;
        }
    }

}
