package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * Edit User Profile page
 */
public class ChangeUserProfileController implements Initializable {

    String username;
    @FXML
    private TextField NewPasswordInput,NewNameInput,NewEmailInput;
    @FXML
    private Label LabelText,UsernameDisplay;

    /**
     * get username from session and display username at the beginning
     * @param location none
     * @param resources none
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        username = Session.INSTANCE.getUsername();
        UsernameDisplay.setText(username);
    }

    /**
     * Back to user profile page and save username to session when user click back button
     * @param actionEvent event
     * @throws IOException when couldn't load "UserProfile.fxml"
     */
    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Session.INSTANCE.put("user",username);
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("UserProfile.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    /**
     * Update the user's informaton when user click submit button
     */
    public void submitAction() {
        checkInputUpdate(NewPasswordInput,"password");
        checkInputUpdate(NewNameInput,"name");
        checkInputUpdate(NewEmailInput,"email");
        setText();
    }

    /**
     * Update the user information only user enter the valid input
     * @param input userinput String
     * @param column database column String
     */
    public void checkInputUpdate(TextField input, String column){
        if (!input.getText().isEmpty()) {
            updateUserProfile(input, column);
        }
    }

    /**
     * Set text for update success message
     */
    public void setText(){
        LabelText.setText("User profile update succeed!");

    }

    /**
     * Update the user information to database
     * @param input userinput String
     * @param column database column String
     */
    public void updateUserProfile(TextField input, String column){
        String NewData = input.getText();

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String update = "Update user_info SET "+ column +" ='"+NewData+"' WHERE user_name='"+username+"'";

        try{
            Statement statement= connectDB.createStatement();
            statement.executeUpdate(update);
            connectDB.close();
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
}

