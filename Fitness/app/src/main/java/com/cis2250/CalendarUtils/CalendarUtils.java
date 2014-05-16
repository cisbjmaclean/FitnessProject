package com.cis2250.CalendarUtils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;

import java.util.Calendar;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.TargetApi;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.CalendarContract;
import android.view.Display;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import javax.xml.datatype.Duration;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by radams29981 on 1/30/14.
 */
public class CalendarUtils {
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public ContentValues addEvent(AccountManager aM,int mYear,int mMonth,int mDay,int mTime,String title, String description){
        requestCalendarSync(aM);
        Calendar today = Calendar.getInstance();
        today.set(Calendar.DAY_OF_MONTH, mDay);
        today.set(Calendar.MONTH, mMonth);
        today.set(Calendar.YEAR, mYear);
        today.set(Calendar.HOUR_OF_DAY, mTime);
        Long lDate = today.getTimeInMillis();
        Uri uriCalendar = null;
        //put values into a collection to store in the calendar
        ContentValues values = new ContentValues();
        try {
            //System.out.println(lDate);
            values.put(CalendarContract.Events.DTSTART,lDate);
            // System.out.println("Test2");
            values.put(CalendarContract.Events.DURATION, "+P40M");
            //  System.out.println("Test3");
            values.put(CalendarContract.Events.TITLE, title);
            //  System.out.println(title);
            values.put(CalendarContract.Events.DESCRIPTION, description);
            // System.out.println(description);
            values.put(CalendarContract.Events.CALENDAR_ID, 1); //hardcoded to 1, which is my default calendar
            //    System.out.println("Test6");

            values.put(CalendarContract.Events.EVENT_TIMEZONE, String.valueOf(today.getTimeZone()));
            //  System.out.println(String.valueOf(today.getTimeZone()));
            values.put(CalendarContract.Events.STATUS, CalendarContract.Events.STATUS_CONFIRMED);
            //  System.out.println("Test8");

            values.put(CalendarContract.Events.HAS_ALARM, true);
            //System.out.println("Test9");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return values;
    }
    private static void requestCalendarSync(AccountManager aM)
    {
        Account[] accounts = new Account[0];
        try {
            accounts = aM.getAccounts();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Account account : accounts)
        {
            int isSyncable = ContentResolver.getIsSyncable(account, CalendarContract.AUTHORITY);

            if (isSyncable > 0)
            {
                Bundle extras = new Bundle();
                extras.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
                ContentResolver.requestSync(accounts[0], CalendarContract.AUTHORITY, extras);
            }
        }
    }
}

