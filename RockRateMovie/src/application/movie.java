package application;

import java.sql.Blob;

public class movie {
    private String name;
    private String year;
    private String director;
    private String stars;
    private String Storyline;
    private Blob image;
    private float Score;
    private int voters;
    private String rank;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear(){
        return year;
    }

    public void setYear(String year){
        this.year = year;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getStoryline() {
        return Storyline;
    }

    public void setStoryline(String Storyline) {
        this.Storyline = Storyline;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public float getScore() {
        return Score;
    }

    public void setScore(float Score) {
        this.Score = Score;
    }

    public int getVoters(){
        return voters;
    }

    public void setVoters(int voters) {
        this.voters = voters;
    }

    public String getRank(){
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
