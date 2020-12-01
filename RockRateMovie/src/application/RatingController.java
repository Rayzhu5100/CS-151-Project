package application;

import com.mysql.jdbc.Blob;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

public class RatingController implements Initializable {

    String username,movieName,comment,Revewer;
    float enteredScore,Score,newScore;
    Blob blob;
    int voters,count=1;
    @FXML
    ImageView movieImage;
    @FXML
    Label name,Label;
    @FXML
    TextField ScoreInput;
    @FXML
    TextArea ReviewInput;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        username = Session.INSTANCE.getUsername();
        movieName = Session.INSTANCE.getMovieName();
        Score = Session.INSTANCE.getScore();
        voters = Session.INSTANCE.getVoters();
        Revewer = Session.INSTANCE.getName();
        name.setText(movieName);

        blob = Session.INSTANCE.getImageBlob();
        InputStream inputStream = null;
        try {
            inputStream = blob.getBinaryStream();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assert inputStream != null;
        Image image = new Image(inputStream);
        movieImage.setImage(image);
    }

    public void submitButtonAction(){
        enteredScore = 99999;
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

        if(!ScoreInput.getText().equals("")&&isVaildScoreInput(ScoreInput.getText())) {
            enteredScore = Float.parseFloat(ScoreInput.getText());

            if (enteredScore <= 0 || enteredScore <= 10) {
                newScore = getAvgScore(Score, enteredScore, voters);
                newScore = round(newScore);
                updateMovieScore(movieName, newScore, voters);
                Label.setText("Rate successful!");
            } else {
                Label.setText("Score input should in range 0-10! count: "+count++);
            }
        }else{
            Label.setText("Please enter a valid score! count: "+ count++);
        }

        comment = ReviewInput.getText();

        if(!comment.equals(""))
            addComment(movieName, comment, Revewer, timeStamp);
    }



    public float getAvgScore(float currentScore, float newScore, int voters){
        float AvgScore;
        AvgScore = (currentScore*voters+newScore)/(voters+1);
        return AvgScore;
    }

    /**
     * Update the score to movie_info database
     * @param moviename  String
     * @param score float current score after update
     */
    public void updateMovieScore(String moviename, float score,int voters){

        voters++;
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String update = "Update movie_info SET Score ="+score+",Voters ="+voters+" WHERE name='"+moviename+"';";

        try{
            Statement statement= connectDB.createStatement();
            statement.executeUpdate(update);
            connectDB.close();
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void addComment(String moviename, String comment, String name, String timestamp){

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String update = "insert into movie_comment(movie_name,review_content,name,time)values('"+moviename+"','"+comment+"','"+name+"','"+timestamp+"');";

        try{
            Statement statement= connectDB.createStatement();
            statement.executeUpdate(update);
            connectDB.close();
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }


    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Session.INSTANCE.put("user", username);
        Session.INSTANCE.put("movieName",movieName);
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("MovieView.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    public boolean isVaildScoreInput(String score){
        int j = 0;
        for (int i = score.length();--i>=0;){
            if(j > 1){
                return false;
            }
            if(score.charAt(i) == '.') {
                j++;
                continue;
            }
            if (!Character.isDigit(score.charAt(i))){
                return false;
            }
        }
        return true;
    }

    public float round(float a){
        return (float) Math.round(a * 10) / 10;
    }
}
