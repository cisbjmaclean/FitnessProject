package com.cis2250.network;

import android.content.Intent;

/**
 * Created by Dmitri Tulonen For Jordan Lea on 2/5/14.
 */
public class Email {
    private Intent sendEmailFunctionJordan(String[] to, String message, String date) {

        String subject = "Booking request for " + date;

        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);
        emailIntent.setType("message/rfc822");

        return emailIntent;
    }

    /**
     * To use this code anywhere use the below line in your main UI thread
     * startActivity(Intent.createChooser(Email.sendEmailFunctionAll(String eMail,String subject, String message), "Email"));
     * @param eMail The Persons E-mail you would like to send to
     * @param subject The Subject of the email
     * @param message The message body of the email
     * @return Intent
     */
    public static Intent sendEmailFunctionAll(String[] eMail,String subject, String message) {

        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.putExtra(Intent.EXTRA_EMAIL, eMail);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);
        emailIntent.setType("message/rfc822");

        return emailIntent;
    }
}
