package application;
import java.sql.Connection;
import java.sql.DriverManager;


public class DatabaseConnection {
	public Connection databaseLink;
	
	public Connection getConnection() {
		String databaseName = "RockRateMovie";
		String databaseUser = "admin";
		String databasePassword= "password";
		String url = "jdbc:mysql://rockratemovie.czanuyerjjgh.us-east-1.rds.amazonaws.com/";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			databaseLink = DriverManager.getConnection(url+databaseName, databaseUser, databasePassword);
			System.out.println("Database connect success!");
		}catch(Exception e) {
			e.printStackTrace();
			e.getCause();
			System.out.println("Database Connect failed!");
		}
		
		return databaseLink;
	}

}
