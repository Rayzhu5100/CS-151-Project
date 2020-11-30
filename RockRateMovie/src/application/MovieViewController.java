package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;


public class MovieViewController implements Initializable {
    String username, movieName, rank, ScoreToShow;
    Blob blob;
    float Score;
    int Voters;
    @FXML
    ImageView movieImage;
    @FXML
    Label YearLabel, DirectorLabel, StarsLabel, StorylineLabel, ScoreLabel, RankLabel, movieNameLabel, noReview;
    ArrayList<movie> MovieInfo = new ArrayList<movie>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        username = Session.INSTANCE.getUsername();
        movieName = Session.INSTANCE.getMovieName();
        movieNameLabel.setText(movieName);

        try {
            getMovieInfo();
            blob = MovieInfo.get(0).getImage();
            InputStream inputStream = blob.getBinaryStream();
            Image image = new Image(inputStream);
            movieImage.setImage(image);

            if(MovieInfo.get(0).getScore() == 0){
                ScoreLabel.setText("N/A");
            }else{
                ScoreLabel.setText(MovieInfo.get(0).getScore() + "/10");;
            }

            YearLabel.setText(MovieInfo.get(0).getYear());
            DirectorLabel.setText(MovieInfo.get(0).getDirector());
            StarsLabel.setText(MovieInfo.get(0).getStars());
            StorylineLabel.setText(MovieInfo.get(0).getStoryline());
            RankLabel.setText("#" + MovieInfo.get(0).getRank());

            Score = MovieInfo.get(0).getScore();
            Voters = MovieInfo.get(0).getVoters();
            rank = MovieInfo.get(0).getRank();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void getMovieInfo() throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String query = "select * from rank_view where name=" + "'" + movieName + "';";

        Statement statement = connectDB.createStatement();
        ResultSet queryResult = statement.executeQuery(query);
        while (queryResult.next()) {
            movie movie = new movie();
            movie.setYear(queryResult.getString("year"));
            movie.setDirector(queryResult.getString("director"));
            movie.setStars(queryResult.getString("stars"));
            movie.setStoryline(queryResult.getString("Storyline"));
            movie.setScore(queryResult.getFloat("Score"));
            movie.setRank(queryResult.getString("rank"));
            movie.setImage(queryResult.getBlob("image"));
            movie.setVoters(queryResult.getInt("Voters"));
            MovieInfo.add(movie);
        }
        connectDB.close();
    }


    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Session.INSTANCE.put("user", username);
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }


    public void ratePage(ActionEvent actionEvent) throws IOException {
        Session.INSTANCE.put("user", username);
        Session.INSTANCE.put("blob", blob);
        Session.INSTANCE.put("score", Score);
        Session.INSTANCE.put("voters", Voters);
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("Rating.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    public void viewCommentPage(ActionEvent actionEvent) throws IOException, SQLException {
        if(CheckReview(movieName)) {
            Session.INSTANCE.put("user", username);
            Session.INSTANCE.put("blob", blob);
            Session.INSTANCE.put("score", Score);
            Session.INSTANCE.put("voters", Voters);
            Session.INSTANCE.put("rank", rank);
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("ViewComment.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        }else{
            noReview.setText("This movie have zero review, click rate this movie to write the first review!");
        }
    }

    public boolean CheckReview(String movieName) throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String query = "select * from movie_comment where movie_name='" + movieName + "' order by RAND() LIMIT 1;";
        System.out.println(query);

        Statement statement = connectDB.createStatement();
        ResultSet queryResult = statement.executeQuery(query);
        if (queryResult.next()) {
            connectDB.close();
            return true;
        }
        return false;

    }
}

