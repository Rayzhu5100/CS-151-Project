package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


/**
 * User registration page
 */
public class RegisterController {


    @FXML
    private Label RegisterationMessageLabel;
    @FXML
    private TextField usernameInput,passwordInput,confirmPasswordInput,NameInput,EmailInput;

    /**
     * When user click register button, call registerUserCheck() to check the input
     */
    public void registerButtonOnAction(){
        registerUserCheck();
    }

    /**
     * When user click back button, back to login page
     * @param actionEvent event
     * @throws IOException when couldn't load "login.fxml"
     */
    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    /**
     * check the information from user input, if all the input are correct and no duplicate username, call registerUser()
     * to store user registration information to database
     * else prompt corresponding error message
     */
    public void registerUserCheck() {
        if(checkEmptyfield(usernameInput.getText(),passwordInput.getText(),confirmPasswordInput.getText(),
                NameInput.getText(),EmailInput.getText())){
            if (!checkDuplicate(usernameInput.getText())) {
                if (passwordInput.getText().equals(confirmPasswordInput.getText())) {
                    registerUser();
                    RegisterationMessageLabel.setText("You have been registered successfully!");
                } else {
                    RegisterationMessageLabel.setText("Password does not match. Please Enter again!");
                }
            } else {
                RegisterationMessageLabel.setText("User name already exist!");
            }
        }
    }

    /**
     * store user register information to database
     */
    public void registerUser(){

        String username = usernameInput.getText();
        String password = passwordInput.getText();
        String name = NameInput.getText();
        String email = EmailInput.getText();

        //connect database
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String insertData = "INSERT INTO user_info VALUES ('"+username+"','"+password+"','"+name+"','"+email+"')";

        try{
            Statement statement= connectDB.createStatement();
            statement.executeUpdate(insertData);
            connectDB.close();
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    /**
     * Check if the username already taken
     * @param usernameInput username
     * @return return true if duplicate, return false if not duplicate.
     */
    public boolean checkDuplicate(String usernameInput){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "SELECT user_name from user_info WHERE user_name='" + usernameInput+ "'";

        try {

            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            if(queryResult.next()) {
                return true;
            }
        }catch(Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return false;
    }

    /**
     * Check all user input
     * @param usernameInput String username
     * @param passwordInput String password
     * @param confirmPasswordInput String re-password
     * @param NameInput String name
     * @param EmailInput String email
     * @return return true if no fields are empty, return false if any of fields are empty and prompt error message.
     */
    public boolean checkEmptyfield(String usernameInput,String passwordInput,String confirmPasswordInput,
                                   String NameInput,String EmailInput){
        if (usernameInput.equals("")){
            RegisterationMessageLabel.setText("Username can not be empty!");
            return false;
        }
        if (passwordInput.equals("")){
            RegisterationMessageLabel.setText("Password can not be empty!");
            return false;
        }
        if (confirmPasswordInput.equals("")){
            RegisterationMessageLabel.setText("Confirm password can not be empty!");
            return false;
        }
        if (NameInput.equals("")){
            RegisterationMessageLabel.setText("Name can not be empty!");
            return false;
        }
        if (EmailInput.equals("")){
            RegisterationMessageLabel.setText("Email can not be empty!");
            return false;
        }
        return true;
    }


}


