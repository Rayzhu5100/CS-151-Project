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
    Label name1, name2, name3, name4, name5, name6, name7, name8, score1, score2, score3, score4, score5, score6, score7, score8,
            voters1, voters2, voters3, voters4, voters5, voters6, voters7, voters8, rank1, rank2, rank3, rank4, rank5, rank6, rank7, rank8;

    ArrayList<movie> movieArrayList = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        username = Session.INSTANCE.getUsername();
        try {
            getMovieRanking();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (movieArrayList.size() > 0) {
            name1.setText(movieArrayList.get(0).getName());
            score1.setText(movieArrayList.get(0).getScore() + "/10");
            voters1.setText(Integer.toString(movieArrayList.get(0).getVoters()));
            rank1.setText("#" + movieArrayList.get(0).getRank());
        }

        if (movieArrayList.size() > 1) {
            name2.setText(movieArrayList.get(1).getName());
            score2.setText(movieArrayList.get(1).getScore() + "/10");
            voters2.setText(Integer.toString(movieArrayList.get(1).getVoters()));
            rank2.setText("#" + movieArrayList.get(1).getRank());

        }

        if (movieArrayList.size() > 2) {
            name3.setText(movieArrayList.get(2).getName());
            score3.setText(movieArrayList.get(2).getScore() + "/10");
            voters3.setText(Integer.toString(movieArrayList.get(2).getVoters()));
            rank3.setText("#" + movieArrayList.get(2).getRank());
        }

        if (movieArrayList.size() > 3) {
            name4.setText(movieArrayList.get(3).getName());
            score4.setText(movieArrayList.get(3).getScore() + "/10");
            voters4.setText(Integer.toString(movieArrayList.get(3).getVoters()));
            rank4.setText("#" + movieArrayList.get(3).getRank());
        }

        if (movieArrayList.size() > 4) {
            name5.setText(movieArrayList.get(4).getName());
            score5.setText(movieArrayList.get(4).getScore() + "/10");
            voters5.setText(Integer.toString(movieArrayList.get(4).getVoters()));
            rank5.setText("#" + movieArrayList.get(4).getRank());
        }

        if (movieArrayList.size() > 5) {
            name6.setText(movieArrayList.get(5).getName());
            score6.setText(movieArrayList.get(5).getScore() + "/10");
            voters6.setText(Integer.toString(movieArrayList.get(5).getVoters()));
            rank6.setText("#" + movieArrayList.get(5).getRank());
        }

        if (movieArrayList.size() > 6) {
            name7.setText(movieArrayList.get(6).getName());
            score7.setText(movieArrayList.get(6).getScore() + "/10");
            voters7.setText(Integer.toString(movieArrayList.get(6).getVoters()));
            rank7.setText("#" + movieArrayList.get(6).getRank());
        }

        if (movieArrayList.size() > 7) {
            name8.setText(movieArrayList.get(7).getName());
            score8.setText(movieArrayList.get(7).getScore() + "/10");
            voters8.setText(Integer.toString(movieArrayList.get(7).getVoters()));
            rank8.setText("#" + movieArrayList.get(7).getRank());
        }
    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Session.INSTANCE.put("user", username);
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    public void viewMovie(ActionEvent actionEvent) throws IOException {
        Session.INSTANCE.put("user", username);
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("MovieView.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    public void getMovieRanking() throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String query = "select * from rank_view;";

        Statement statement = connectDB.createStatement();
        ResultSet queryResult = statement.executeQuery(query);
        while (queryResult.next()) {
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
        if (movieArrayList.size() > 0) {
            Session.INSTANCE.put("movieName", movieArrayList.get(0).getName());
            viewMovie(actionEvent);
        }
    }

    public void jump2(ActionEvent actionEvent) throws IOException {
        if (movieArrayList.size() > 1) {
            Session.INSTANCE.put("movieName", movieArrayList.get(1).getName());
            viewMovie(actionEvent);
        }
    }

    public void jump3(ActionEvent actionEvent) throws IOException {
        if (movieArrayList.size() > 2) {
            Session.INSTANCE.put("movieName", movieArrayList.get(2).getName());
            viewMovie(actionEvent);
        }
    }

    public void jump4(ActionEvent actionEvent) throws IOException {
        if (movieArrayList.size() > 3) {
            Session.INSTANCE.put("movieName", movieArrayList.get(3).getName());
            viewMovie(actionEvent);
        }
    }

    public void jump5(ActionEvent actionEvent) throws IOException {
        if (movieArrayList.size() > 4) {
            Session.INSTANCE.put("movieName", movieArrayList.get(4).getName());
            viewMovie(actionEvent);
        }
    }

    public void jump6(ActionEvent actionEvent) throws IOException {
        if (movieArrayList.size() > 5) {
            Session.INSTANCE.put("movieName", movieArrayList.get(5).getName());
            viewMovie(actionEvent);
        }
    }

    public void jump7(ActionEvent actionEvent) throws IOException {
        if (movieArrayList.size() > 6) {
            Session.INSTANCE.put("movieName", movieArrayList.get(6).getName());
            viewMovie(actionEvent);
        }
    }

    public void jump8(ActionEvent actionEvent) throws IOException {
        if (movieArrayList.size() > 7) {
            Session.INSTANCE.put("movieName", movieArrayList.get(7).getName());
            viewMovie(actionEvent);
        }
    }


}
