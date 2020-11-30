package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

public class MovieRankController implements Initializable {

    String username;
    @FXML
    Label name1, name2, name3, name4, name5, name6,name7,name8,score1, score2, score3, score4, score5, score6,score7,score8,
            voters1, voters2, voters3, voters4, voters5, voters6,voters7,voters8,rank1,rank2,rank3,rank4,rank5,rank6,rank7,rank8;

    ArrayList<movie> movieArrayList = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        username = Session.INSTANCE.getUsername();
        try {
            getMovieRanking();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        name1.setText(movieArrayList.get(0).getName());
        name2.setText(movieArrayList.get(1).getName());
        name3.setText(movieArrayList.get(2).getName());
        name4.setText(movieArrayList.get(3).getName());
        name5.setText(movieArrayList.get(4).getName());
        name6.setText(movieArrayList.get(5).getName());
        name7.setText(movieArrayList.get(6).getName());
        name8.setText(movieArrayList.get(7).getName());

        score1.setText(Float.toString(movieArrayList.get(0).getScore()));
        score2.setText(Float.toString(movieArrayList.get(1).getScore()));
        score3.setText(Float.toString(movieArrayList.get(2).getScore()));
        score4.setText(Float.toString(movieArrayList.get(3).getScore()));
        score5.setText(Float.toString(movieArrayList.get(4).getScore()));
        score6.setText(Float.toString(movieArrayList.get(5).getScore()));
        score7.setText(Float.toString(movieArrayList.get(6).getScore()));
        score8.setText(Float.toString(movieArrayList.get(7).getScore()));

        voters1.setText(Integer.toString(movieArrayList.get(0).getVoters()));
        voters2.setText(Integer.toString(movieArrayList.get(1).getVoters()));
        voters3.setText(Integer.toString(movieArrayList.get(2).getVoters()));
        voters4.setText(Integer.toString(movieArrayList.get(3).getVoters()));
        voters5.setText(Integer.toString(movieArrayList.get(4).getVoters()));
        voters6.setText(Integer.toString(movieArrayList.get(5).getVoters()));
        voters7.setText(Integer.toString(movieArrayList.get(6).getVoters()));
        voters8.setText(Integer.toString(movieArrayList.get(7).getVoters()));

        rank1.setText("#"+movieArrayList.get(0).getRank());
        rank2.setText("#"+movieArrayList.get(1).getRank());
        rank3.setText("#"+movieArrayList.get(2).getRank());
        rank4.setText("#"+movieArrayList.get(3).getRank());
        rank5.setText("#"+movieArrayList.get(4).getRank());
        rank6.setText("#"+movieArrayList.get(5).getRank());
        rank7.setText("#"+movieArrayList.get(6).getRank());
        rank8.setText("#"+movieArrayList.get(7).getRank());

    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Session.INSTANCE.put("user",username);
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    public void viewMovie(ActionEvent actionEvent) throws IOException {
        Session.INSTANCE.put("user",username);
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("MovieView.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    public void getMovieRanking() throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String query = "select * from rank_view;";

        Statement statement = connectDB.createStatement();
        ResultSet queryResult = statement.executeQuery(query);
        while(queryResult.next()){
            movie movie = new movie();
            movie.setName(queryResult.getString("name"));
            movie.setScore(queryResult.getFloat("Score"));
            movie.setVoters(queryResult.getInt("voters"));
            movie.setRank(queryResult.getString("rank"));
            movieArrayList.add(movie);
        }
        connectDB.close();
    }

    public void jump1(ActionEvent actionEvent) throws IOException {
        Session.INSTANCE.put("movieName", movieArrayList.get(0).getName());
        viewMovie(actionEvent);
    }
    public void jump2(ActionEvent actionEvent) throws IOException {
        Session.INSTANCE.put("movieName", movieArrayList.get(1).getName());
        viewMovie(actionEvent);
    }
    public void jump3(ActionEvent actionEvent) throws IOException {
        Session.INSTANCE.put("movieName", movieArrayList.get(2).getName());
        viewMovie(actionEvent);
    }
    public void jump4(ActionEvent actionEvent) throws IOException {
        Session.INSTANCE.put("movieName", movieArrayList.get(3).getName());
        viewMovie(actionEvent);
    }
    public void jump5(ActionEvent actionEvent) throws IOException {
        Session.INSTANCE.put("movieName", movieArrayList.get(4).getName());
        viewMovie(actionEvent);
    }
    public void jump6(ActionEvent actionEvent) throws IOException {
        Session.INSTANCE.put("movieName", movieArrayList.get(5).getName());
        viewMovie(actionEvent);
    }
    public void jump7(ActionEvent actionEvent) throws IOException {
        Session.INSTANCE.put("movieName", movieArrayList.get(6).getName());
        viewMovie(actionEvent);
    }
    public void jump8(ActionEvent actionEvent) throws IOException {
        Session.INSTANCE.put("movieName", movieArrayList.get(7).getName());
        viewMovie(actionEvent);
    }


}
