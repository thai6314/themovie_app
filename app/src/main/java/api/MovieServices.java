package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import models.Movie;
import models.MovieDetail;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieServices {
    OkHttpClient clientSetup = new OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.MINUTES)
        .writeTimeout(15, TimeUnit.MINUTES) // write timeout
        .readTimeout(15, TimeUnit.MINUTES) // read timeout
        .build();
    Gson gson = new GsonBuilder().create();
    MovieServices movieServices = new Retrofit.Builder()
            .baseUrl("https://api-movie.thangld-dev.tech/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(clientSetup)
            .build()
            .create(MovieServices.class);
    @GET("api/movie/now_playing")
    Call<List<Movie>> getNowPlaying();

    @GET("api/movie/top")
    Call<List<Movie>>getTopRate();

    @GET("api/movie/comingsoon")
    Call<List<Movie>> getComingSoon();

    @GET("api/movie/trending")
    Call<List<Movie>>getTrending();

    @GET("api/movie")
    Call<List<Movie>>getTopSearches();

    @GET("api/movie")
    Call<List<MovieDetail>> getSearchMovie(@Query("key") String key);



}
