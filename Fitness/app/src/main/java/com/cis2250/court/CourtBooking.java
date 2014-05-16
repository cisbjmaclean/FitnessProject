package com.cis2250.court;

import org.json.JSONObject;

/**
 * Created by BJ on 30/01/14.
 */
public class CourtBooking {

public static String bookDetails = "";

    private static JSONObject courtBookingJSON = null;

    //*********************************************************************
    // These could be bundled and passed, but doing this until time permits.
    //*************************************************************************

    public static String startTimeStatic, endTimeStatic, courtNameStatic;
    public static String courtNumberStatic;
    public static String bookingDateStatic;
    public static boolean bookedStatic;
    public static String opponentIdStatic;
    public static String opponentName;
    public static String startTimeRequestedStatic;
    public static String lastNameStatic, firstNameStatic;

    public static String getBookingConfirmationString(){
        return "Are you sure you want to book "+courtNameStatic+" on "+CourtBooking.bookingDateStatic+" at "+startTimeStatic+" to play "+opponentName;
    }


    private String startTime, endTime, courtName;
    private int courtNumber;
    private String bookingDate;
    boolean booked;
    String lastName, firstName;
    int memberId;
    private String firstCourtName; //This is used to know when to have a line break.


    public static String getBookDetails() {
        return bookDetails;
    }

    public static void setBookDetails(String bookDetails) {
        CourtBooking.bookDetails = bookDetails;
    }

    public String getFirstCourtName() {
        return firstCourtName;
    }

    public void setFirstCourtName(String firstCourtName) {
        this.firstCourtName = firstCourtName;
    }



    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCourtName() {
        return courtName;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    public int getCourtNumber() {
        return courtNumber;
    }

    public void setCourtNumber(int courtNumber) {
        this.courtNumber = courtNumber;
    }

}
