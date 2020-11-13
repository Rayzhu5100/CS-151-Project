package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class UserProfileController implements Initializable {


    @FXML
    private Label UsernameDisplay, PasswordDisplay, NameDisplay, EmailDisplay;

    String username;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        username = Session.INSTANCE.getUsername(username);
        try {
            ResultSet queryResult = getUserProfile();
            NameDisplay.setText(queryResult.getString("name"));
            EmailDisplay.setText(queryResult.getString("email"));
            UsernameDisplay.setText(username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showPasswordOnAction(ActionEvent event) throws SQLException {
        PasswordDisplay.setText(getUserProfile().getString("password"));
    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Session.INSTANCE.put("user",username);
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    public void changeProfileOnAction(ActionEvent actionEvent) throws IOException {
        Session.INSTANCE.put("user", username);
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("ChangeUserProfile.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    public ResultSet getUserProfile() throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String query = "SELECT * from user_info WHERE user_name='" + username + "'";

            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(query);
            if(queryResult.next()){
                return queryResult;
            }
            connectDB.close();
            return queryResult;
        }
}
