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


public class ChangeUserProfileController implements Initializable {

    String username;
    @FXML
    private TextField NewPasswordInput,NewNameInput,NewEmailInput;
    @FXML
    private Label LabelText,UsernameDisplay;



    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Session.INSTANCE.put("user",username);
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("UserProfile.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    public void submitAction(ActionEvent actionEvent) {
        checkInput(NewPasswordInput,"password");
        checkInput(NewNameInput,"name");
        checkInput(NewEmailInput,"email");
        setText();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        username = Session.INSTANCE.getUsername(username);
        UsernameDisplay.setText(username);
    }

    public void checkInput(TextField input, String column){
        if (!input.getText().isEmpty()) {
            updateUserProfile(input, column);
        }
    }

    public void setText(){
        LabelText.setText("User profile update succeed!");

    }

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

