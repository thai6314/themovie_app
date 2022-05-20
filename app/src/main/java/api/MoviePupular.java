package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import models.Movie;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface MoviePupular {
    Gson gson = new GsonBuilder().create();
    MoviePupular moviePupular = new Retrofit.Builder()
            .baseUrl("https://api-movie.thangld-dev.tech/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(MoviePupular.class);
    @GET("api/movie/popular")
    Call<List<Movie>> getMoviePopular();
}
