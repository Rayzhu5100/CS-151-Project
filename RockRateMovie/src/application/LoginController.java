package application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.EmptyStackException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.StageStyle;

public class LoginController {

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
				Stage stage = (Stage) EXIT.getScene().getWindow();
				stage.close();
				createMainMenu();
			}else {
				loginMessageLabel.setText("Invalid login. Please try again.");
				}

		}catch(Exception e) {
			e.printStackTrace();
			e.getCause();
		}
	}


	public void createAccountForm(){
		Stage stage = (Stage) RegisterButton.getScene().getWindow();
		stage.close();
		try{
			Pane root = (Pane) FXMLLoader.load(getClass().getResource("register.fxml"));
			Stage registerStage = new Stage();
			registerStage.initStyle(StageStyle.UNIFIED);
			Scene scene = new Scene(root,900,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			registerStage.setScene(scene);
			registerStage.show();

		}catch(Exception e){
			e.printStackTrace();
			e.getCause();
		}
	}

	public void createMainMenu(){
		Stage stage = (Stage) LoginButton.getScene().getWindow();
		stage.close();
		try{
			Pane root = (Pane) FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
			Stage registerStage = new Stage();
			registerStage.initStyle(StageStyle.UNIFIED);
			Scene scene = new Scene(root,900,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			registerStage.setScene(scene);
			registerStage.show();

		}catch(Exception e){
			e.printStackTrace();
			e.getCause();
		}
	}



}