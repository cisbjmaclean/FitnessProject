package com.cis2250.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import com.cis2250.network.JSONParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.cis2250.util.Utility;

/**
 * Created by Joel on 16/01/14.
 */
public class LoginActivity extends Activity {

    JSONObject json = null;
    String url = null;
    JSONParser parser = new JSONParser();

    // catchable error
    Boolean hasError = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                // get user input
                EditText userText = (EditText) findViewById(R.id.usernameText);
                EditText passText = (EditText) findViewById(R.id.passwordText);

                // convert user input to String
                String user = userText.getText().toString();
                String pass = passText.getText().toString();

                // encode variables
                try {
                    user = URLEncoder.encode(user, "UTF-8");
                    pass = URLEncoder.encode(pass, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                // URL to execute PHP
                url = Utility.urlString + "login_sql.php?sql=member&fields=member_id~user_type&userColumn=member_id&userID=" + user + "&passwordColumn=password&userPassword=" + pass;
                // url = "http://10.0.172.124//login_sql.php?sql=member&fields=member_id~user_type&userColumn=member_id&userID=" + user + "&passwordColumn=password&userPassword=" + pass;

                // create a new thread
                Thread t = new Thread() {
                    public void run() {
                        json = parser.makeHttpRequest(url);

                        try {
                            int success = json.getInt("success");

                            // if user found in database
                            if (success == 1) {
                                JSONArray userInfo = json.getJSONArray("CisFitness");

                                JSONObject userDetails = userInfo.getJSONObject(0);

                                Utility.memberId = userDetails.getString("member_id");

                                if (userDetails.getString("user_type").equals("1")) {
                                    // User is a member. Go to member menu
                                    Utility.isAdmin = false;
                                } else if(userDetails.getString("user_type").equals("2")) {
                                    // User is an admin. Go to admin menu
                                    Utility.isAdmin = true;
                                }

                                // launch the MainMenuActivity
                                Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // if error occurred
                                hasError = true;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                // start the thread and later join it to the main thread
                try {
                    t.start();
                    t.join();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                // toast a message based if the was an error with log in credentials
                if (hasError) {
                    Toast.makeText(LoginActivity.this, "Login Failed.", Toast.LENGTH_LONG).show();
                    hasError = false;
                } else {
                    if (Utility.isAdmin) {
                        Toast.makeText(LoginActivity.this, "Logged in as Admin.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Login Success.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
