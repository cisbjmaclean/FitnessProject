package com.cis2250.app;
//System.out.println("!!!TEST!!!");
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.cis2250.network.JSONParser;
import com.cis2250.util.Utility;

public class TrainerBooking extends ActionBarActivity {

    String[] emails = new String[2];
    String[] trainers = new String[2];

    JSONParser jp = new JSONParser();
    JSONObject jo;
    String url = Utility.urlString + "select_sql.php?sql=trainers&fields=trainerName~trainerEmail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Thread t = new Thread() {
            public void run() {

                jo = jp.makeHttpRequest(url);
                try {
                    System.out.println(jo.getInt("success"));
                    int success = jo.getInt("success");
                    if (success == 1) {
                        JSONArray ja = jo.getJSONArray("CisFitness");
                        for (int i=0; i < ja.length(); i++) {
                            JSONObject jo2 = ja.getJSONObject(i);
                            trainers[i] = jo2.getString("trainerName");
                            emails[i] = jo2.getString("trainerEmail");
                        }
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

        super.onCreate(savedInstanceState);
        setContentView(R.layout.trainer_booking);

        Button sendEmail = (Button)findViewById(R.id.buttonSend);


        Spinner personalTrainer = (Spinner)findViewById(R.id.spinnerTrainer);
        personalTrainer.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, trainers));

        sendEmail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Spinner personalTrainer = (Spinner)findViewById(R.id.spinnerTrainer);
                String[] to = new String[] {emails[personalTrainer.getSelectedItemPosition()]};

                EditText comments = (EditText)findViewById(R.id.editTextComments);
                String message = String.valueOf(comments.getText());

                DatePicker datePicker = (DatePicker)findViewById(R.id.datePicker);
                String day = String.valueOf(datePicker.getDayOfMonth());
                int temp = datePicker.getMonth();
                temp += 1;
                String month = String.valueOf(temp);
                String year = String.valueOf(datePicker.getYear());
                String date = day + "/" + month + "/"+ year;

                sendEmailFunction(to, message, date);
            }
        });
    }

    private void sendEmailFunction(String[] to, String message, String date) {

        String subject = "Booking request for " + date;

        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);
        emailIntent.setType("message/rfc822");

        startActivity(Intent.createChooser(emailIntent, "Email"));
    }
}

