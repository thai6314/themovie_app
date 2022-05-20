package models;

import java.io.Serializable;
import java.util.List;

public class MovieDetail implements Serializable {
    private int id;
    private String backdrop_path;
    private int runtime;
    private String release_date;
    private String title;
    private String description;
    private String vote_average;
    private List<Cast> cast;

    public MovieDetail(int id, String backdrop_path, int runtime, String release_date,
                       String title, String description, String vote_average,
                       List<Cast> cast) {
        this.id = id;
        this.backdrop_path = backdrop_path;
        this.runtime = runtime;
        this.release_date = release_date;
        this.title = title;
        this.description = description;
        this.vote_average = vote_average;
        this.cast = cast;
    }

    public MovieDetail() {
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

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
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

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public List<Cast> getCast() {
        return cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }
}
