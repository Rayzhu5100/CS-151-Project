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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

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
			validateLogin(event);

		}else {
			loginMessageLabel.setText("Please enter username and password!");
		}
	}

	public void exitButtonOnAction(ActionEvent event) {
		Stage stage = (Stage) EXIT.getScene().getWindow();
		stage.close();
	}

	public void validateLogin(ActionEvent actionEvent) {
		String username = enterUsernameField.getText();
		String password = enterPasswordField.getText();

		DatabaseConnection connectNow = new DatabaseConnection();
		Connection connectDB = connectNow.getConnection();

	String verifyLogin = "SELECT user_name from user_info WHERE user_name='" + username + "' AND password='" + password + "'";


	try {

		Statement statement = connectDB.createStatement();
		ResultSet queryResult = statement.executeQuery(verifyLogin);

		if(queryResult.next()) {
			connectDB.close();
			Session.INSTANCE.put("user",username);
			Parent tableViewParent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
			Scene tableViewScene = new Scene(tableViewParent);
			Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
			window.setScene(tableViewScene);
			window.show();
		}else {
			loginMessageLabel.setText("Invalid login. Please try again.");
		}

	}catch(Exception e) {
		e.printStackTrace();
		e.getCause();
	}
}


	public void createAccountForm(ActionEvent actionEvent) throws IOException {
		Parent tableViewParent = FXMLLoader.load(getClass().getResource("register.fxml"));
		Scene tableViewScene = new Scene(tableViewParent);
		Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
		window.setScene(tableViewScene);
		window.show();
	}

	public void createMainMenu(ActionEvent actionEvent) throws IOException {

		Parent tableViewParent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
		Scene tableViewScene = new Scene(tableViewParent);
		Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
		window.setScene(tableViewScene);
		window.show();
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
}
