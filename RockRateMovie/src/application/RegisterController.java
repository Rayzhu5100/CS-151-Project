package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;




public class RegisterController {


    @FXML
    private Button Back;
    @FXML
    private Label RegisterationMessageLabel;
    @FXML
    private TextField usernameInput,passwordInput,confirmPasswordInput,NameInput,EmailInput;

    public void registerButtonOnAction(ActionEvent event){
        registerUserCheck();

    }

    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    public void createLoginPage(){
        try{
            Pane root = (Pane) FXMLLoader.load(getClass().getResource("Login.fxml"));
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

    public void registerUser(){

        String username = usernameInput.getText();
        String password = passwordInput.getText();
        String name = usernameInput.getText();
        String email = EmailInput.getText();

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


