package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import models.Login;
import models.User;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserServices {
    OkHttpClient clientSetup = new OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.MINUTES)
            .writeTimeout(15, TimeUnit.MINUTES) // write timeout
            .readTimeout(15, TimeUnit.MINUTES) // read timeout
            .build();
    Gson gson = new GsonBuilder().create();
    UserServices userServices = new Retrofit.Builder()
            .baseUrl("https://api-movie.thangld-dev.tech/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(clientSetup)
            .build()
            .create(UserServices.class);
    @FormUrlEncoded
    @POST("api/auth/login")
    Call<User>login(@Field("username") String username,
                    @Field("password") String password);
}
