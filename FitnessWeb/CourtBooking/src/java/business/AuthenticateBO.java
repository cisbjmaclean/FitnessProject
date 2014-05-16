package business;

import beans.Member;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import util.ConnectionUtils;
import util.DbUtils;

public class AuthenticateBO  {

    private static final long serialVersionUID = 1L;

    
    /**
     * This method will authenticate a user.  It will check the member table for their 
     * password and member id.
     * 
     * @param member
     * @return whether authenticated successfully or not.
     * @throws Exception 
     * 
     * @author BJ MacLean
     * @since 20131129
     */
    
    public static boolean authenticate(Member member) throws Exception {
        PreparedStatement psAuthenticate = null;
        String sqlAuthenticate = null;
        boolean authenticatedSuccessfully = false;
        Connection conn = ConnectionUtils.getConnection();
        try {

            //Prepare the sql to get the info from the database.
            sqlAuthenticate = "select password from member where member_id = ?";

            //setup the prepared statement
            psAuthenticate = conn.prepareStatement(sqlAuthenticate);
            psAuthenticate.setString(1, member.getMemberId());
            ResultSet rs = psAuthenticate.executeQuery();

            //Loop through the results to get information about user credentials.
            while (rs.next()) {
                // It is possible to get the columns via name
                // also possible to get the columns via the column number
                // which starts at 1
                // e.g. resultSet.getSTring(2);
                String password = rs.getString("password");
                if (password.equals(member.getPassword())) {
                    authenticatedSuccessfully = true;
                }
            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            e.printStackTrace();

        }

        DbUtils.close(psAuthenticate, conn);
        return authenticatedSuccessfully;
    }
}
