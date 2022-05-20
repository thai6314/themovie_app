package models;

import java.io.Serializable;

public class Movie implements Serializable {
    private int id;
    private String backdrop_path;
    private String poster_path;
    private String title;
    private String description;

    public Movie() {
    }

    public Movie(int id, String backdrop_path, String poster_path, String title, String description) {
        this.id = id;
        this.backdrop_path = backdrop_path;
        this.poster_path = poster_path;
        this.title = title;
        this.description = description;
    }

    public Movie(String backdrop_path, String poster_path, String title, String description) {
        this.backdrop_path = backdrop_path;
        this.poster_path = poster_path;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
