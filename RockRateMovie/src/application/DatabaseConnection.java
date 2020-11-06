package application;
import java.sql.Connection;
import java.sql.DriverManager;


public class DatabaseConnection {
	public Connection databaseLink;
	
	public Connection getConnection() {
		String databaseName = "rockmovie";
		String databaseUser = "admin";
		String databasePassword= "password";
		String url = "rockmovie.czanuyerjjgh.us-east-1.rds.amazonaws.com:3306/"+databaseName;
		
		try {
			Class.forName("org.mysql.jdbc.Driver");
			databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
		}catch(Exception e) {
			e.printStackTrace();
			e.getCause();
		}
		
		return databaseLink;
	}

}
