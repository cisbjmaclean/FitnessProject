package com.cis2250.util;

import com.cis2250.business.CheckInCheckOut;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by bjmaclean on 1/29/14.
 */
public class Utility {
    private static CheckInCheckOut checkInCheckOut = new CheckInCheckOut();
    public static String memberId = "";
    public static String memberType = "";
    public static String memberFirstName = "";
    public static String memberLastName = "";
    public static boolean isAdmin = false;
    public static boolean checkedIn = checkInCheckOut.isCheckedIn();

    public static boolean testing = false;


//    public static final String urlString = "http://ns1.hccis.info//~bjmac2//fitness//";
    public static final String urlString = "http://107.6.20.137//~bjmac2//fitness//";

}
