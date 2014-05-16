package com.cis2250.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cis2250.announcements.AdminAnnouncements;
import com.cis2250.announcements.Announcements;
import com.cis2250.booking.AerobicActivity;
import com.cis2250.booking.AerobicActivity1;
import com.cis2250.business.CheckInCheckOut;
import com.cis2250.court.CourtBooking1Activity;
import com.cis2250.reports.ReportsMainActivity;
import com.cis2250.util.Utility;
import com.cis2250.weight_tracker.WeightTrackerActivity;

public class MainMenuActivity extends ActionBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // ReportsMain Activity Button
        Button reportsMain = (Button) findViewById(R.id.reports);
        reportsMain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(MainMenuActivity.this, ReportsMainActivity.class));
            }
        }
        );

        // CourtBookingActivity Button
        Button courtBookingButton = (Button) findViewById(R.id.buttonCourtBooking);
        courtBookingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(MainMenuActivity.this, CourtBooking1Activity.class));
            }
        }
        );

        // AnnouncementActivity Button
        Button announcementsButton = (Button) findViewById(R.id.buttonAnnouncements);
        announcementsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(MainMenuActivity.this, Announcements.class));
            }
        }
        );

        // AdminAnnouncementsActivity Button
        Button adminAnnouncementsButton = (Button) findViewById(R.id.buttonAdminAnnouncements);
        adminAnnouncementsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(MainMenuActivity.this, AdminAnnouncements.class));
            }
        }
        );

        // TrainerBooking Button
        Button trainingButton = (Button) findViewById(R.id.trainer_booking);
        trainingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(MainMenuActivity.this, TrainerBooking.class));
            }
        }
        );

        // AerobicActivity Button
        Button aerobicsButton = (Button) findViewById(R.id.aerobic_button);
        aerobicsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(MainMenuActivity.this, AerobicActivity1.class));
            }
        }
        );

        Button weightTrackingButton = (Button) findViewById(R.id.buttonWeightTracking);
        weightTrackingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(MainMenuActivity.this, WeightTrackerActivity.class));
            }
        }
        );

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    //Check in/Check out. Jake
    public void checkInCheckOut(View v) {
        //Put up the Yes/No message box asking whether the user is taking a towel
        if (Utility.checkedIn) { //USER ALREADY CHECKED IN
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder
                    .setTitle("CIS Fitness - Check Out")
                    .setMessage("Are you returning a towel?")
                    .setIcon(android.R.drawable.ic_menu_help)
                    .setNegativeButton("No", new DialogInterface.OnClickListener() { //User doesn't have a towel
                        public void onClick(DialogInterface dialog, int which) {
                            //No button clicked, check them in or out without a towel
                            CheckInCheckOut thisCheckInOut = new CheckInCheckOut();
                            thisCheckInOut.towel = 0;

                            Toast.makeText(getApplicationContext(), thisCheckInOut.checkInOut(),
                                    Toast.LENGTH_LONG).show();
                        }
                    })
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() { //User has a towel
                        public void onClick(DialogInterface dialog, int which) {
                            //Yes button clicked, check them in or out with a towel
                            CheckInCheckOut thisCheckInOut = new CheckInCheckOut();
                            thisCheckInOut.towel = 1;

                            Toast.makeText(getApplicationContext(), thisCheckInOut.checkInOut(),
                                    Toast.LENGTH_LONG).show();
                        }
                    })
                    .show();
        }
        else { //USER NOT CHECKED IN
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder
                    .setTitle("CIS Fitness - Check In")
                    .setMessage("Are you taking a towel?")
                    .setIcon(android.R.drawable.ic_menu_help)
                    .setNegativeButton("No", new DialogInterface.OnClickListener() { //User doesn't have a towel
                        public void onClick(DialogInterface dialog, int which) {
                            //No button clicked, check them in or out without a towel
                            CheckInCheckOut thisCheckInOut = new CheckInCheckOut();
                            thisCheckInOut.towel = 0;

                            Toast.makeText(getApplicationContext(), thisCheckInOut.checkInOut(),
                                    Toast.LENGTH_LONG).show();
                        }
                    })
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() { //User has a towel
                        public void onClick(DialogInterface dialog, int which) {
                            //Yes button clicked, check them in or out with a towel
                            CheckInCheckOut thisCheckInOut = new CheckInCheckOut();
                            thisCheckInOut.towel = 1;

                            Toast.makeText(getApplicationContext(), thisCheckInOut.checkInOut(),
                                    Toast.LENGTH_LONG).show();
                        }
                    })
                    .show();
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

    //redirects memberinformation *ADAM M*
    public void memberInformation(View view) {
        Intent intent = new Intent(this, MemberInformation.class);
        startActivity(intent);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }
    }

}
