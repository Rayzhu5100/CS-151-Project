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
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ViewMovieRatingController implements Initializable {
    String username, searchtext;
    @FXML
    TextField searchMovieInput;
    @FXML
    Label label;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        username = Session.INSTANCE.getUsername();
    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Session.INSTANCE.put("user", username);
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    public void createButtonAction(ActionEvent actionEvent) throws IOException {
        Session.INSTANCE.put("user", username);
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("CreateMovie.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    public void searchMovie(ActionEvent actionEvent) {
        searchtext = searchMovieInput.getText();
        if(!searchtext.equals("")) {
            //connect database
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();

            String verifyLogin = "SELECT * from movie_info WHERE name like'" + searchtext + "';";

            try {

                Statement statement = connectDB.createStatement();
                ResultSet queryResult = statement.executeQuery(verifyLogin);

                if (queryResult.next()) {
                    //close the database connection
                    connectDB.close();
                    Session.INSTANCE.put("movieName", searchtext);

                    viewMoviePage(actionEvent);
                } else {
                    label.setText("Movie name you entered doesn't not exist, you can create it.");
                }

            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }
        }else{
            label.setText("Search bar can not be empty!");
        }
    }

    public void viewMoviePage(ActionEvent actionEvent) throws IOException {
        Session.INSTANCE.put("user",username);
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("MovieView.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
}

