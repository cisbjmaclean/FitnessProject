package com.cis2250.CalendarUtils;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import com.cis2250.app.R;
import com.cis2250.court.CourtBooking1Activity;
import com.cis2250.court.CourtBooking3Activity;

import java.util.Calendar;

public class CalendarActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
//            getFragmentManager().beginTransaction()
//                    .add(R.id.container, new PlaceholderFragment())
//                    .commit();
            //Not sure where to implement this code
            AccountManager aM = AccountManager.get(this);
            Intent intentCalendar;
            Uri uriCalendar = null;
            String description = "This is a test!";//this will be a description passed from the courtBookings
            String title = "Test!";//This will be a title (court # will do)
            intentCalendar = new Intent(Intent.ACTION_VIEW,uriCalendar);
            int mYear = 0;
            int mMonth = 0;
            int mDay = 0;
            int mTime = 0;
            //Use the native calendar app to view the date
            // startActivity(intentCalendar);
            try {
                CalendarUtils myCal = new CalendarUtils();
                this.getContentResolver().insert(CalendarContract.Events.CONTENT_URI, myCal.addEvent(aM,mYear,mMonth,mDay,mTime,title,description));
                Calendar today = Calendar.getInstance();
                uriCalendar = Uri.parse("content://com.android.calendar/time/" + String.valueOf(today.getTimeInMillis()));
//                intentCalendar = null;
//                intentCalendar = new Intent(Intent.ACTION_VIEW,uriCalendar);
            } catch (Exception e) {
                e.printStackTrace();
            }
            startActivity(new Intent(CalendarActivity.this, CourtBooking1Activity.class));
            //startActivity(intentCalendar);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.calendar, menu);
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

//    /**
//     * A placeholder fragment containing a simple view.
//     */
//    public static class PlaceholderFragment extends Fragment {
//
//        public PlaceholderFragment() {
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                Bundle savedInstanceState) {
//           // View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);
//            return null;
//        }
//    }

}
