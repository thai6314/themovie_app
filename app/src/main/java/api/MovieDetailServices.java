package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import models.MovieDetail;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieDetailServices {
    Gson gson = new GsonBuilder().create();
    MovieDetailServices movieDetailServices = new Retrofit.Builder()
            .baseUrl("https://api-movie.thangld-dev.tech/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(MovieDetailServices.class);
    @GET("api/movie/{id}")
    Call<MovieDetail> getMovieDetail(@Path("id") int id);
}
