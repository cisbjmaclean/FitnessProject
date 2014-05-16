package com.cis2250.business;

import android.util.Log;

import com.cis2250.util.Utility;
import com.cis2250.network.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by joconnor29647 on 1/24/14.
 */
public class CheckInCheckOut {
    public String idToCheckOut = "";
    //fields to enter
    private String memberId = Utility.memberId;
    private String currentTimeString = "";
    public int towel;
    //JSONParser
    private JSONParser jParser = new JSONParser();
    //URLs *CHANGE IP TO SERVER LOCATION*
    private String insertURL = Utility.urlString+"create_sql.php?sql=check_in&fields=membership_id~check_in_time~towel_taken&parameters=";
    private String updateURL = Utility.urlString+"update_sql.php?sql=check_in&fields=check_out_time~towel_return&parameters=";
    // JSON Node names
    private final String TAG_SUCCESS = "success";


    //check user in, if they are already checked in then check them out.
    public String checkInOut() {
        if (!Utility.checkedIn) {
            //check user in
            checkIn();
            //if they were checked in then display appropriate message
            if (Utility.checkedIn) {
                return "You have been checked in successfully.";
            } else {
                return "There was an error checking you in. Please try again.";
            }
        } else {
            //check user out
            checkOut();
            //if they were checked out then display appropriate message
            if (!Utility.checkedIn) {
                return "You have been checked out successfully.";
            } else {
                return "There was an error checking you out. Please try again.";
            }
        }
    }

    //******************************************************************
    //Check in user using their member_id, a timestamp, and towel amount
    //******************************************************************
    public void checkIn() {
        //get current Timestamp
        Timestamp currentTime = new Timestamp(new Date().getTime());
        //utf-8 encode string value
        try {
            currentTimeString = URLEncoder.encode(String.valueOf(currentTime), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //complete insertURL by appending the memberID, timestamp, and number of towels taken
        try {
            insertURL += memberId + "~" + currentTimeString + "~" + towel;
        } catch (Exception e) {
            System.out.println(e);
        }

        //run thread that inserts values into DB
        Thread t = new Thread() {
            public void run() {
                //getting JSON object from URL
                JSONObject json = jParser.makeHttpRequest(insertURL);
                //Log cat the JSON response
                Log.d("All Results: ", json.toString());

                try {
                    // Checking for SUCCESS TAG
                    int success = json.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        //success inserting into DB
                        Utility.checkedIn = true;
                    } else {
                        System.out.println("Error connecting to DB");
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
    }
    //End of function checkIn


    //*********************************************************
    //Use check_in_id from getLastCheckInId() to check user out
    //*********************************************************
    public void checkOut() {
        //get check_in_id for last time user has checked in without checking out
        final ExecutorService service = Executors.newFixedThreadPool(1);
        try {
            idToCheckOut = service.submit(new getLastCheckInId()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //get current Timestamp
        Timestamp currentTime = new Timestamp(new Date().getTime());
        try {
            currentTimeString = URLEncoder.encode(String.valueOf(currentTime), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //put values in URL
        try {
            updateURL += currentTimeString + "~" + towel + "&primarykey=check_in_id&primaryinfo=" + idToCheckOut;
        } catch (Exception e) {
            System.out.println(e);
        }

        //run thread that inserts values into DB
        Thread t = new Thread() {
            public void run() {
                //getting JSON object from URL
                JSONObject json = jParser.makeHttpRequest(updateURL);
                //Log cat the JSON response
                Log.d("All Results: ", json.toString());

                try {
                    // Checking for SUCCESS TAG
                    int success = json.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        //success updating DB
                        Utility.checkedIn = false;
                    } else {
                        System.out.println("Error connecting to DB");
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
    }
    //End of function checkOut

    //********************************************************************
    //Gather check_in_id of last time user checked in without checking out
    //********************************************************************
    class getLastCheckInId implements Callable<String> {
        private JSONArray jsonArrayCheckInsOuts = null;
        private String selectURL = Utility.urlString+"select_sql.php?sql=check_in&fields=check_in_id~membership_id~check_in_time~check_out_time~towel_taken~towel_return";
        private final String TAG_OBJECT = "CisFitness";

        public String call() {
            String checkOutId = "";
            //getting JSON object from URL
            JSONObject json = jParser.makeHttpRequest(selectURL);
            //Log cat the JSON response
            Log.d("All Results: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    //getting results
                    jsonArrayCheckInsOuts = json.getJSONArray(TAG_OBJECT);

                    // looping through results
                    for (int i = 0; i < jsonArrayCheckInsOuts.length(); i++) {
                        JSONObject c = jsonArrayCheckInsOuts.getJSONObject(i);

                        if (c.getString("membership_id").equals(memberId) && c.getString("check_out_time").equals("null")) {
                            //find the last time they have checked in without checking out
                            checkOutId = c.getString("check_in_id");
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return checkOutId;
        }
    }
    //End of class getLastCheckInId

    public boolean isCheckedIn() {
        //get check_in_id for last time user has checked in without checking out
        final ExecutorService service = Executors.newFixedThreadPool(1);
        try {
            idToCheckOut = service.submit(new getLastCheckInId()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if(!idToCheckOut.isEmpty()) {
            return true;
        }
        else {
            return false;
        }

    }
}
