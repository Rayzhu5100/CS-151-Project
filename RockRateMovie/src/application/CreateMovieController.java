package application;

import com.mysql.jdbc.PreparedStatement;
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
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;


public class CreateMovieController implements Initializable {
    String username,path=null,movieNameInput,yearInput,DirectorInput,StarsInput,StorylineInput;

    @FXML
    Label pathLabel,Label;
    @FXML
    Button upload;
    @FXML
    TextField name,year,director,stars,storyline;
    @FXML
    ImageView im;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        username = Session.INSTANCE.getUsername();
    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Session.INSTANCE.put("user",username);
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("ViewMovieRating.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    public void createbuttonAction() throws IOException, SQLException {
        movieNameInput = name.getText();
        yearInput = year.getText();
        DirectorInput = director.getText();
        StarsInput = stars.getText();
        StorylineInput = storyline.getText();

        if (!movieNameInput.equals("") || !yearInput.equals("") || !DirectorInput.equals("") || !StarsInput.equals("") || !StorylineInput.equals("") || path != null) {
            if (!checkIfMovieExist(movieNameInput)) {
                storeMovieInfo();
                Label.setText("Movie created success!");
            } else {
                Label.setText("Movie Already exist!");
            }
        } else {
            Label.setText("fields cannot be empty!");
        }
    }


        public void uploadButtonAction () {
            FileChooser fc = new FileChooser();
            // if we want to open fixed location
            //fc.setInitialDirectory(new File("D:\\\\Books"));

            // if we want to fixed file extension
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG Files", "*.jpg"));
            File selectedFile = fc.showOpenDialog(null);

            if (selectedFile != null) {
                path = selectedFile.getAbsolutePath();
                pathLabel.setText(path);
            } else {
                System.out.println("File is not valid!");
            }
        }


        public void storeMovieInfo () throws IOException, SQLException {

            PreparedStatement statement;
            DatabaseConnection connectNow = new DatabaseConnection();

            Connection conn = connectNow.getConnection();

            byte[] bytearray = convertFileContentToBlob(path);
            String query = "INSERT INTO movie_info VALUES(?,?,?,?,?,?,?,?);";
            statement = (PreparedStatement) conn.prepareStatement(query);
            statement.setString(1, movieNameInput);
            statement.setString(2, yearInput);
            statement.setString(3, DirectorInput);
            statement.setString(4, StarsInput);
            statement.setString(5, StorylineInput);
            statement.setBytes(6, bytearray);
            statement.setString(7, null);
            statement.setString(8, null);
            statement.execute();
        }

        /**
         * Check if the Movie is already taken
         * @param movieName movieName
         * @return return true if duplicate, return false if not duplicate.
         */
        public boolean checkIfMovieExist(String movieName){
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();

            String verifyLogin = "SELECT name from movie_info WHERE name='" + movieName+ "'";

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

    public static byte[] convertFileContentToBlob(String filePath) throws IOException {
        // create file object
        File file = new File(filePath);
        // initialize a byte array of size of the file
        byte[] fileContent = new byte[(int) file.length()];
        try (FileInputStream inputStream = new FileInputStream(file)) {
            // create an input stream pointing to the file
            // read the contents of file into byte array
            inputStream.read(fileContent);
        } catch (IOException e) {
            throw new IOException("Unable to convert file to byte array. " +
                    e.getMessage());
        }
        // close input stream
        return fileContent;
    }
}

