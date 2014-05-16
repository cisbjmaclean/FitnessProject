package util;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.struts.util.MessageResources;

public class ConnectionUtils {
	
	private static MessageResources messages 
	 = MessageResources.getMessageResources("util.DatabaseResources");
	private static String USER_NAME_DB=messages.getMessage("db.user");
	private static String USER_PASSWORD_DB=messages.getMessage("db.password");
	private static String DB_NAME=messages.getMessage("db.dbname");
	private static String HOST_NAME=messages.getMessage("db.host");
	
	static{
		  try {
		   	  Class.forName("com.mysql.jdbc.Driver").newInstance();
		  } catch (Exception ex) {
		  }
	}
	
	public static Connection getConnection() throws Exception
	{
	 	  return getDBConnection();
	}
	
	 public static Connection getDBConnection() throws Exception 
	  {
	      Connection conn = null;
	      String URL="jdbc:mysql://"+HOST_NAME+":3306/"+DB_NAME;
              System.out.println("URL="+URL);
              System.out.println("User="+USER_NAME_DB);
              System.out.println("Pw="+USER_PASSWORD_DB);
		  try{
			  conn = DriverManager.getConnection(URL,USER_NAME_DB, USER_PASSWORD_DB);
		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
		  }
		  
		  return conn;
	  }
}
