package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * User login page
 */
public class LoginController implements Initializable {

	@FXML
	private Button EXIT;
	@FXML
	private TextField enterUsernameField;
	@FXML
	private TextField enterPasswordField;
	@FXML
	private Label loginMessageLabel;
	@FXML
	private ImageView im;

	/**
	 * Click username and password text field, if any of them are not empty call validate the data
	 * else throw error message
	 * @param event user click the login button
	 */
	public void loginButtonOnAction(ActionEvent event) {

		if(!enterUsernameField.getText().isEmpty() && !enterPasswordField.getText().isEmpty()) {
			validateLogin(event);
		}else {
			loginMessageLabel.setText("Please enter username and password!");
		}
	}

	/**
	 * Exit program if user click "Exit RockRateMovie" button
	 */
	public void exitButtonOnAction() {
		Stage stage = (Stage) EXIT.getScene().getWindow();
		stage.close();
	}

	/**
	 * Validate the username and password, if pass jump to main menu
	 * @param actionEvent event
	 */
	public void validateLogin(ActionEvent actionEvent) {
		String username = enterUsernameField.getText();
		String password = enterPasswordField.getText();

		//connect database
		DatabaseConnection connectNow = new DatabaseConnection();
		Connection connectDB = connectNow.getConnection();

	String verifyLogin = "SELECT user_name from user_info WHERE user_name='" + username + "' AND password='" + password + "'";

	try {

		Statement statement = connectDB.createStatement();
		ResultSet queryResult = statement.executeQuery(verifyLogin);

		if(queryResult.next()) {
			//close the database connection
			connectDB.close();
			//put username to Session
			Session.INSTANCE.put("user",username);
			//jump to Main Menu
			createMainMenu(actionEvent);
		}else {
			loginMessageLabel.setText("Invalid login. Please try again.");
		}

	}catch(Exception e) {
		e.printStackTrace();
		e.getCause();
	}
}

	/**
	 * jump to create Account page
	 * @param actionEvent event
	 * @throws IOException if unable to load "register.fxml"
	 */
	public void createAccountForm(ActionEvent actionEvent) throws IOException {
		Parent tableViewParent = FXMLLoader.load(getClass().getResource("register.fxml"));
		Scene tableViewScene = new Scene(tableViewParent);
		Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
		window.setScene(tableViewScene);
		window.show();
	}

	/**
	 * Jump to Main Menu page
	 * @param actionEvent event
	 * @throws IOException if unable to load "MainMenu.fxml"
	 */
	public void createMainMenu(ActionEvent actionEvent) throws IOException {
		Parent tableViewParent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
		Scene tableViewScene = new Scene(tableViewParent);
		Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
		window.setScene(tableViewScene);
		window.show();
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		File BrandingFile = new File("image/WechatIMG1.jpeg");
		Image brandingImage = new Image(BrandingFile.toURI().toString());
		im.setImage(brandingImage);

	}
}
