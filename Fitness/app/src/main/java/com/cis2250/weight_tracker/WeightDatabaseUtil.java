package com.cis2250.weight_tracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Chad Paquet on 1/29/14.
 */
public class WeightDatabaseUtil extends SQLiteOpenHelper
{
    static final String dbName="weightTrackerDB";
    static final String weightTrackingTable="WeightTracking";
    static final String ID="ID";
    static final String colStarting="StartingWeight";
    static final String colCurrent="CurrentWeight";
    static final String colGoal="GoalWeight";
    static final String colCurrentProg="CurrentProgress";
    static final String colGoalProg="GoalProgress";


    public WeightDatabaseUtil(Context context) {
        super(context, dbName, null,8);
    }

    public void onCreate(SQLiteDatabase db)
    {
        // TODO Auto-generated method stub

        db.execSQL("CREATE TABLE "+weightTrackingTable+"("+ID+" Integer PRIMARY KEY AUTOINCREMENT, " +colStarting+" Integer, " +
        colCurrent+" Integer, " +colGoal+" Integer, " + colCurrentProg+" Integer, " + colGoalProg+")");

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // TODO Auto-generated method stub

        db.execSQL("DROP TABLE IF EXISTS "+weightTrackingTable);
        onCreate(db);
    }

    public void insertValues(String starting, String current, String goal, int currentProg, int gProg)
    {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(colStarting, starting);
        cv.put(colCurrent,current);
        cv.put(colGoal,goal);
        cv.put(colCurrentProg, currentProg);
        cv.put(colGoalProg, gProg);

        db.insert(weightTrackingTable, null, cv);

        db.close();
    }

    public List<Results> getAllResults()
    {
        List<Results> results = new LinkedList<Results>();

        String query = "SELECT * FROM " + weightTrackingTable;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        Results result = null;
        if (cursor.moveToFirst())
        {
            do
            {
                result = new Results();
                result.setStaWeight(cursor.getString(1));
                result.setCurWeight(cursor.getString(2));
                result.setGWeight(cursor.getString(3));
                result.setCurWeightProg(cursor.getString(4));
                result.setgWeightProg(cursor.getString(5));


                results.add(result);
            }
            while (cursor.moveToNext());
        }

        return results;
    }

    public int count()
    {
        String query = "SELECT COUNT(ID) FROM "+weightTrackingTable;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        return count;
    }

    public String staticStartWeight(int id)
    {
        String query = "SELECT "+colStarting+" FROM "+weightTrackingTable+" WHERE "+ID+"="+id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        String weight = cursor.getString(0);
        cursor.close();

        return weight;
    }

    public String staticGoalWeight(int id)
    {
        String query = "SELECT "+colGoal+" FROM "+weightTrackingTable+" WHERE "+ID+"="+id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        String weight = cursor.getString(0);
        cursor.close();

        return weight;
    }


}
