package com.cis2250.weight_tracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cis2250.app.R;


/**
 * Created by Chad Paquet on 1/30/14.
 */
public class WeightTrackerActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_weightmain);

        final EditText  edCurrentWeight  = (EditText) findViewById(R.id.currentWeightEntry);
        final EditText  edGoalWeight  = (EditText)findViewById(R.id.goalWeightEntry);
        final EditText  edStartWeight  = (EditText)findViewById(R.id.startWeightEntry);
        int rowCount;
        String startWeight;
        String goalWeight;

        WeightDatabaseUtil db = new WeightDatabaseUtil(this);

        rowCount = db.count();

        if (rowCount > 0)
        {
            startWeight = db.staticStartWeight(rowCount);
            edStartWeight.setText(startWeight);
            edStartWeight.setEnabled(false);
            goalWeight = db.staticGoalWeight(rowCount);
            edGoalWeight.setText(goalWeight);
            edGoalWeight.setEnabled(false);

        }

        Button enterStartButton = (Button)findViewById(R.id.buttonEnterStart);
        enterStartButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View View)
            {
                edStartWeight.setEnabled(true);
            }
        }
        );

        Button enterGoalButton = (Button)findViewById(R.id.buttonEnterGoal);
        enterGoalButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View View)
            {
                edGoalWeight.setEnabled(true);
            }
        }
        );

        Button submitButton = (Button)findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                //String currentWeight  = edCurrentWeight.getText().toString();
                //String goalWeight = edGoalWeight.getText().toString();
                Intent intent = new Intent(WeightTrackerActivity.this, WeightTrackerResultsActivity.class);
                intent.putExtra("startingWeight", edStartWeight.getText().toString());
                intent.putExtra("currentWeight", edCurrentWeight.getText().toString());
                intent.putExtra("goalWeight", edGoalWeight.getText().toString());
                startActivity(intent);
            }
        }
        );

    }
}
