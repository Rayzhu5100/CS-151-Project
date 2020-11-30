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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Main Menu page
 */
public class MainMenuController implements Initializable {
    String username, WelcomeText, name;
    @FXML
    public Button logoutButton, UserProfileButton;
    @FXML
    public Label WelcomeLabel;

    ArrayList<user> nameArrayList = new ArrayList<>();

    /**
     * Set a welcome label
     *
     * @param location  none
     * @param resources none
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        username = Session.INSTANCE.getUsername();
        try {
            getName();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        name = nameArrayList.get(0).getName();
        WelcomeText = "Welcome, " + name + " :)";
        WelcomeLabel.setText(WelcomeText);

        Session.INSTANCE.put("name", name);

    }

    /**
     * When user click back button, go back to login page
     *
     * @param actionEvent event
     * @throws IOException if couldn't load "login.fxml"
     */
    public void exitButtonOnAction(ActionEvent actionEvent) throws IOException {

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    /**
     * When user click user profile button, jump to user profile page
     *
     * @param actionEvent action
     * @throws IOException if couldn't load "UserProfile.fxml"
     */
    public void userProfileOnAction(ActionEvent actionEvent) throws IOException {

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("UserProfile.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    /**
     * When user click user movie rank button, jump to user ranking page
     *
     * @param actionEvent action
     * @throws IOException if couldn't load "MovieRank.fxml"
     */
    public void movieRankOnAction(ActionEvent actionEvent) throws IOException {

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("MovieRank.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    /**
     * When user click user movie rate button, jump to user rating page
     *
     * @param actionEvent action
     * @throws IOException if couldn't load "ViewMovieRating.fxml"
     */
    public void movieRatingOnAction(ActionEvent actionEvent) throws IOException {

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("ViewMovieRating.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    public void getName() throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String query = "SELECT name from user_info WHERE user_name='" + username + "'";

        Statement statement = connectDB.createStatement();
        ResultSet queryResult = statement.executeQuery(query);
        if (queryResult.next()) {
            user user = new user();
            user.setName(queryResult.getString("name"));
            nameArrayList.add(user);
        }
        connectDB.close();

    }
}




