package models;

import java.io.Serializable;

public class Favorite  implements Serializable{
    private int id;
    private String username;
    private int movieId;

    public Favorite() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Favorite(int id, String username, int movieId) {
        this.id = id;
        this.username = username;
        this.movieId = movieId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
}
