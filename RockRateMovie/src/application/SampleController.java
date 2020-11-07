package application;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.EmptyStackException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class SampleController{
	@FXML
	private Button LoginButton;
	@FXML
	private Button RegisterButton;
	@FXML
	private Button EXIT;
	@FXML
	private TextField enterUsernameField;
	@FXML
	private TextField enterPasswordField;
	@FXML
	private Label loginMessageLabel;
	
	public void loginButtonOnAction(ActionEvent event) {
		
		if(!enterUsernameField.getText().isEmpty() && !enterPasswordField.getText().isEmpty()) {
			validateLogin();
		}else {
			loginMessageLabel.setText("Please enter username and password!");
		}
	}
	
	public void exitButtonOnAction(ActionEvent event) {
		Stage stage = (Stage) EXIT.getScene().getWindow();
		stage.close();
	}
	
	public void validateLogin() {
		DatabaseConnection connectNow = new DatabaseConnection();
		Connection connectDB = connectNow.getConnection();
		
		String verifyLogin = "SELECT user_name from user_info WHERE user_name='" + enterUsernameField.getText() + "' AND password='" + enterPasswordField.getText()+ "'";
		
		try {
			
			Statement statement = connectDB.createStatement();
			ResultSet queryResult = statement.executeQuery(verifyLogin);
			
			if(queryResult.next()) {
				loginMessageLabel.setText("Congrats!");
			}else {
				loginMessageLabel.setText("Invalid login. Please try again.");
				}

		}catch(Exception e) {
			e.printStackTrace();
			e.getCause();
		}
		
		
	}
	
	
}
