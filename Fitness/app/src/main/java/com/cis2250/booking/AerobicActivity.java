package com.cis2250.booking;

/**
 * Created by tmahar10730 on 2/5/14.
 */
public class AerobicActivity {

    public static String date;
    public static String time;
    public static String staticDate;
    public static String staticTime;
    public static String staticSeats;
    public static String staticCategory;
    public static String staticTrainer;

    public static String getBookingConfirmationString(){
        return "You are booking: \n Lesson: "+staticCategory+"\n Date: "+staticDate+"\n Time: "+staticTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
