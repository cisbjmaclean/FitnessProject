package business;

import beans.CourtBooking;
import beans.Member;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import util.ConnectionUtils;
import util.DbUtils;

public class CourtBookingBO {

    private static final long serialVersionUID = 1L;

    /**
     * This method will insert the court booking for the information provided.  
     * 
     * @param bookingDate
     * @param startTime
     * @param courtNumber
     * @param memberId
     * @param memberIdOpponent
     * @param notes
     * @return
     * @throws Exception 
     * 
     * @author BJ
     * @since 20131129
     */
    
    public static boolean bookCourt(String bookingDate, String startTime, int courtNumber, int memberId,
            int memberIdOpponent, String notes) throws Exception {
        System.out.println("booking a court");
        PreparedStatement psAuthenticate = null;
        String sql = null;
        Connection conn = ConnectionUtils.getConnection();

        /*
         * Setup the sql to insert the row.
         */
        
        try {
            sql = "INSERT INTO `court_bookings`(`court_number`, `booking_date`, `start_time`, `member_id`, "
                    + "`member_id_opponent`, `notes`, `created_date`) "
                    + "VALUES (?,?,?,?,?,?,sysdate())";

            psAuthenticate = conn.prepareStatement(sql);
            psAuthenticate.setInt(1, courtNumber);
            psAuthenticate.setString(2, bookingDate);
            psAuthenticate.setString(3, startTime);
            psAuthenticate.setInt(4, memberId);
            psAuthenticate.setInt(5, memberIdOpponent);
            psAuthenticate.setString(6, notes);
            psAuthenticate.executeUpdate();
            conn.commit();

        } catch (Exception e) {
            String errorMessage = e.getMessage();
            e.printStackTrace();
            return false;
        }

        DbUtils.close(psAuthenticate, conn);
        return true;
    }

    /**
     * This method will get the court times for a given date.  It will also get information about
     * the court bookings on that date.
     * 
     * @param bookingDate
     * @return
     * @throws Exception 
     * 
     * @author BJ
     * @since 20131129
     */
    public static ArrayList<CourtBooking> getCourtsForDate(String bookingDate) throws Exception {
        PreparedStatement psAuthenticate = null;
        String sql = null;
        Connection conn = ConnectionUtils.getConnection();
        ArrayList<CourtBooking> courtBookings = new ArrayList();
        boolean firstCourtNameProcessed = false;

        try {
            sql = "SELECT ct.start_time, c.court_name, c.court_number  "
                    + "FROM court_times ct, court c "
                    + "WHERE 1 "
                    + "ORDER BY ct.start_time, c.court_name";

            psAuthenticate = conn.prepareStatement(sql);
//            psAuthenticate.setString(1, bookingDate);
            ResultSet rs = psAuthenticate.executeQuery();
            String firstCourtName = "off"; //This is used to allow the jsp to know when to start a new line.
            while (rs.next()) {
                // It is possible to get the columns via name
                // also possible to get the columns via the column number
                // which starts at 1

                // e.g. resultSet.getSTring(2);
                CourtBooking courtBooking = new CourtBooking();
                String startTime = rs.getString("ct.start_time").trim();
                System.out.println("Start time=" + startTime);
                courtBooking.setStartTime(startTime);
                String courtName = rs.getString("c.court_name").trim();
                System.out.println("Court name=" + courtName);
                courtBooking.setCourtName(courtName);

                courtBooking.setCourtNumber(rs.getInt("c.court_number"));
                courtBooking.setBookingDate(bookingDate);
                System.out.println("Processed=" + firstCourtNameProcessed + " courtname=" + courtName + " firstCourtName=" + firstCourtName);
                if ((!firstCourtNameProcessed) || courtName.equals(firstCourtName)) {
                    /*
                     * Setting this information to allow the jsp to know when to create a new row.
                     * This will indicate when we are on a new time and displaying the first court again.  this
                     * is when the code should insert a newline on the jsp.
                     */
                    courtBooking.setFirstCourtName("yes");
                    firstCourtName = new String(courtName);
                    System.out.println("Set as first court");
                    firstCourtNameProcessed = true;  //This was not being set and caused ~an hour to debug...
                } else {
                    courtBooking.setFirstCourtName("");
                }
                
                
                courtBookings.add(courtBooking);
            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            e.printStackTrace();
        }

        /*
         * Also want to check to see if the court is booked for the given time.  Will loop through each 
         * court and get court booking information for that specific court/time/date.
         */
        sql = "SELECT m.member_id, m.last_name, m.first_name  "
                + "FROM court_bookings cb, member m "
                + "WHERE cb.court_number = ? AND cb.booking_date = ? AND cb.start_time = ? ";
        PreparedStatement psGetDetails = null;
        for (CourtBooking thisCourtBooking : courtBookings) {
            //PreparedStatement psGetDetails = conn.prepareStatement(sql);
            psGetDetails = conn.prepareStatement(sql);
            psGetDetails.setInt(1, thisCourtBooking.getCourtNumber());
            psGetDetails.setString(2, thisCourtBooking.getBookingDate());
            psGetDetails.setString(3, thisCourtBooking.getStartTime());
            ResultSet rsGetDetails = psGetDetails.executeQuery();
            while (rsGetDetails.next()) {
                thisCourtBooking.setMemberId(rsGetDetails.getInt("m.member_id"));
                thisCourtBooking.setLastName(rsGetDetails.getString("m.last_name"));
                thisCourtBooking.setFirstName(rsGetDetails.getString("m.first_name"));
            }

        }
        DbUtils.close(psGetDetails, conn);
        DbUtils.close(psAuthenticate, conn);
        return courtBookings;
    }
}
