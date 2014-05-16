package com.cis2250.weight_tracker;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cis2250.app.R;

import java.util.List;


/**
 * Created by Chad Paquet on 1/31/14.
 */
public class WeightTrackerResultsActivity extends Activity
{


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        String startingWeight = getIntent().getExtras().getString("startingWeight");
        String currentWeight = getIntent().getExtras().getString("currentWeight");
        String goalWeight = getIntent().getExtras().getString("goalWeight");
        int start = Integer.parseInt(startingWeight);
        int current = Integer.parseInt(currentWeight);
        int goal = Integer.parseInt(goalWeight);
        int currentProg;
        int goalProg;

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_weightresult);

        WeightDatabaseUtil db = new WeightDatabaseUtil(this);

        currentProg = start - current;
        goalProg = current - goal;

        db.insertValues(startingWeight,currentWeight, goalWeight, currentProg, goalProg );

        TextView tv = (TextView) findViewById(R.id.goalProgress);

        tv.setText("Welcome! Your current weight is " + current + ". You have currently lost " + currentProg
        + " pounds. You are " + goalProg + " pounds away from your goal of " + goal + " pounds!");

        ListView lv = (ListView) findViewById(R.id.listView);

        List<Results> list = db.getAllResults();

        ArrayAdapter<Results> arrayAdapter = new ArrayAdapter<Results>
                (this,
                 android.R.layout.simple_list_item_1,
                 list );

        lv.setAdapter(arrayAdapter);


    }


}
