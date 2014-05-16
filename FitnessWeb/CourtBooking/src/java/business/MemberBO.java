package business;

import beans.CourtBooking;
import forms.MemberForm;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import util.ConnectionUtils;
import util.DbUtils;

/**
 * This will contain business functionality associated with members.
 *
 * @author BJ
 * @since 20131202
 */
public class MemberBO {

    /**
     * This method will get all of the members from the members table.  
     * 
     * @return an array list of the MemberForm class
     * @author BJ
     * @since 20131202
     */
    
    public static ArrayList<MemberForm> getAllMembers() {
        //return the members from the member table.
        ArrayList<MemberForm> members = new ArrayList();

        PreparedStatement ps = null;
        String sql = null;
        Connection conn = null;
        boolean firstCourtNameProcessed = false;

        try {
            conn = ConnectionUtils.getConnection();

            sql = "SELECT * FROM `member` WHERE 1";

            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // It is possible to get the columns via name
                // also possible to get the columns via the column number
                // which starts at 1

                // e.g. resultSet.getSTring(2);
                MemberForm memberForm = new MemberForm();
                memberForm.setMemberId(rs.getInt(1));
                memberForm.setUserType(rs.getInt(2));
                memberForm.setLastName(rs.getString(3));
                memberForm.setFirstName(rs.getString(4));
                memberForm.setPassword(rs.getString(5));
                memberForm.setEmail(rs.getString(6));
                memberForm.setPhoneCell(rs.getString(7));
                memberForm.setPhoneHome(rs.getString(8));
                memberForm.setPhoneWork(rs.getString(9));
                memberForm.setAddress(rs.getString(10));
                memberForm.setStatus(rs.getInt(11));
                memberForm.setMemberShipType(rs.getInt(12));
                memberForm.setMemberShipDate(null);
                members.add(memberForm);
            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            e.printStackTrace();
        } finally {
            DbUtils.close(ps, conn);
        }

        return members;
    }

    /**
     * This method will update or insert a member based on the updateFlag.
     * 
     * @return a boolean indicating success
     * @author BJ
     * @since 20131202
     */
    public static boolean updateMember(MemberForm memberForm) {
        //Have to update the member based on member id.

        System.out.println("updating member");
        PreparedStatement psMember = null;
        String sql = null;
        Connection conn = null;

        /*
         * Setup the sql to update or insert the row (based on update flag).
         */
        try {
            conn = ConnectionUtils.getConnection();

            if (memberForm.getUpdateFlag() != null && memberForm.getUpdateFlag().equals("true")) {
                sql = "UPDATE `member` SET `user_type`=?,`last_name`=?,`first_name`=?,`password`=?,`email`=?,"
                        + "`phone_cell`=?,`phone_home`=?,`phone_work`=?,`address`=?,`status`=?,`membership_type`=?,"
                        + "`membership_date`=? "
                        + " WHERE `member_id`=? ORDER BY last_name desc, first_name desc";

                psMember = conn.prepareStatement(sql);
                psMember.setInt(1, memberForm.getUserType());
                psMember.setString(2, memberForm.getLastName());
                psMember.setString(3, memberForm.getFirstName());
                psMember.setString(4, memberForm.getPassword());
                psMember.setString(5, memberForm.getEmail());
                psMember.setString(6, memberForm.getPhoneCell());
                psMember.setString(7, memberForm.getPhoneHome());
                psMember.setString(8, memberForm.getPhoneWork());
                psMember.setString(9, memberForm.getAddress());
                psMember.setInt(10, memberForm.getStatus());
                psMember.setInt(11, memberForm.getMemberShipType());
                psMember.setDate(12, null);
                psMember.setInt(13, memberForm.getMemberId());

            } else {
                //Have to insert the new member.

                sql = "INSERT INTO `member`(`user_type`, `last_name`, `first_name`, `password`, `email`, "
                        + "`phone_cell`, `phone_home`, `phone_work`,"
                        + " `address`, `status`, `membership_type`, `member_id`, `membership_date`) "
                        + " values (?,?,?,?,?,?,?,?,?,?,?,?,?)";

                psMember = conn.prepareStatement(sql);
                psMember.setInt(1, memberForm.getUserType());
                psMember.setString(2, memberForm.getLastName());
                psMember.setString(3, memberForm.getFirstName());
                psMember.setString(4, memberForm.getPassword());
                psMember.setString(5, memberForm.getEmail());
                psMember.setString(6, memberForm.getPhoneCell());
                psMember.setString(7, memberForm.getPhoneHome());
                psMember.setString(8, memberForm.getPhoneWork());
                psMember.setString(9, memberForm.getAddress());
                psMember.setInt(10, memberForm.getStatus());
                psMember.setInt(11, memberForm.getMemberShipType());
                psMember.setInt(12, memberForm.getNewMemberId());
                psMember.setDate(13, null);

            }

            psMember.executeUpdate();


        } catch (Exception e) {
            String errorMessage = e.getMessage();
            e.printStackTrace();
            return false;
        } finally {
            DbUtils.close(psMember, conn);
        }
        return true;

    }

    public static MemberForm loadDetails(MemberForm memberForm) {
        //Get the details for the given member based on memberId.
        Connection conn = null;
        PreparedStatement psMember = null;
        String sql;
        try {
            conn = ConnectionUtils.getConnection();
            sql = "SELECT * "
                    + "FROM member m "
                    + "WHERE m.member_id = ? ";

            psMember = conn.prepareStatement(sql);
            psMember.setInt(1, memberForm.getMemberId());
            ResultSet rs = psMember.executeQuery();
            boolean foundMember = false;
            while (rs.next()) {
                foundMember = true;
                memberForm.setUpdateFlag("true");
                // It is possible to get the columns via name
                // also possible to get the columns via the column number
                // which starts at 1

                memberForm.setUserType(rs.getInt("user_type"));
                memberForm.setLastName(rs.getString("last_name"));
                memberForm.setFirstName(rs.getString("first_name"));
                memberForm.setPassword(rs.getString("password"));
                memberForm.setEmail(rs.getString("email"));
                memberForm.setPhoneCell(rs.getString("phone_cell"));
                memberForm.setPhoneHome(rs.getString("phone_home"));
                memberForm.setPhoneWork(rs.getString("phone_work"));
                memberForm.setAddress(rs.getString("address"));
                memberForm.setStatus(rs.getInt("status"));
                memberForm.setMemberShipType(rs.getInt("membership_type"));
                memberForm.setMemberShipDate(rs.getDate("membership_date"));
            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            e.printStackTrace();
        } finally {
            DbUtils.close(psMember, conn);
            return memberForm;
        }

    }
}
