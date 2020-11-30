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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ViewCommentController implements Initializable {
    String username,movieName,rank,name;
    float Score;
    @FXML
    Label ScoreLabel,RankLabel,reviewerName,reviewTime,content,movieNameLabel;
    @FXML
    ImageView movieImage;
    Blob blob;

    ArrayList<MovieReview> movieReviewList = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        username = Session.INSTANCE.getUsername();
        movieName = Session.INSTANCE.getMovieName();
        Score = Session.INSTANCE.getScore();
        rank = Session.INSTANCE.getRank();

        ScoreLabel.setText(String.valueOf(Score)+"/10");
        movieNameLabel.setText(movieName);
        RankLabel.setText("#"+rank);


        blob = Session.INSTANCE.getImageBlob();
        InputStream inputStream = null;
        try {
            inputStream = blob.getBinaryStream();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assert inputStream != null;
        javafx.scene.image.Image image = new Image(inputStream);
        movieImage.setImage(image);

        try {
            getMovieReview(movieName,movieReviewList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        reviewerName.setText(movieReviewList.get(0).getName());
        reviewTime.setText(movieReviewList.get(0).getTime());
        content.setText(movieReviewList.get(0).getReview());

    }

    public void getMovieReview(String movieName, ArrayList<MovieReview> movieReviewArrayList) throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String query = "select * from movie_comment where movie_name='"+ movieName +"' order by RAND() LIMIT 1;";

        Statement statement = connectDB.createStatement();
        ResultSet queryResult = statement.executeQuery(query);
        while(queryResult.next()){
            MovieReview MovieReview = new MovieReview();
            MovieReview.setReview(queryResult.getString("review_content"));
            MovieReview.setName(queryResult.getString("name"));
            MovieReview.setTime(queryResult.getString("time"));
            movieReviewArrayList.add(MovieReview);
        }
        connectDB.close();
    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Session.INSTANCE.put("user", username);
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("MovieView.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    public void nextCommentButton(ActionEvent actionEvent) throws IOException {
        Session.INSTANCE.put("user", username);
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("ViewComment.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

}
