package activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.accounts.Account;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.btl_android.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


import adapter.RcCastAdapter;
import api.MovieDetailServices;
import dal.SQLiteHelper;
import models.Favorite;
import models.Movie;
import models.MovieDetail;
import models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {
    private RelativeLayout rlPoster;
    private RcCastAdapter rcCastAdapter;
    private ImageView imgPlay;
    private ImageView avtCast;
    private TextView tvTitle;
    private TextView rateStar;
    private TextView tvDiscription;
    private Movie movie;
    private MovieDetail movieDetail;
    private RecyclerView rcCast;
    private TextView tvNameCast;
    private ImageView heartRed, heartBlack;
    private User user;
    private Account account;
    private SQLiteHelper sqLiteHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_movie_detail);
        sqLiteHelper = new SQLiteHelper(getApplicationContext());
        Intent intent = getIntent();


        initView();
        movie = new Movie();
        movieDetail = new MovieDetail();
        movie =(Movie)intent.getSerializableExtra("movie");

        rcCastAdapter = new RcCastAdapter();

        MovieDetailServices.movieDetailServices.getMovieDetail(movie.getId())
                .enqueue(new Callback<MovieDetail>() {
                    @Override
                    public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                        movieDetail = response.body();
                        rcCastAdapter.setList(movieDetail.getCast());
                        RecyclerView.LayoutManager manager =
                                new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL,false);
                        rcCast.setLayoutManager(manager);
                        rcCast.setAdapter(rcCastAdapter);
                        rateStar.setText(movieDetail.getVote_average());
                        imgPlay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(getApplicationContext(), WatchMovieActivity.class);
                                intent.putExtra("movieDetail", movieDetail);
                                startActivity(intent);
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<MovieDetail> call, Throwable t) {

                    }
                });
        Picasso.get().load("https://image.tmdb.org/t/p/w500"+movie.getBackdrop_path()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                rlPoster.setBackground(new BitmapDrawable(bitmap));
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
        tvTitle.setText(movie.getTitle());
        tvDiscription.setText(movie.getDescription());
        tvDiscription.setMovementMethod(new ScrollingMovementMethod());

        heartRed.setVisibility(View.GONE);
        heartBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                heartRed.setVisibility(View.VISIBLE);
                heartBlack.setVisibility(View.GONE);
                //sqLiteHelper.addFavorite(favorite);

            }
        });
        heartRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                heartBlack.setVisibility(View.VISIBLE);
                heartRed.setVisibility(View.GONE);
            }
        });

    }

    public void initView(){
        rlPoster = findViewById(R.id.rlPoster);
        imgPlay = findViewById(R.id.imgPlay);
        tvTitle = findViewById(R.id.tvTitle);
        rateStar = findViewById(R.id.rateStar);
        tvDiscription = findViewById(R.id.tvDiscription);
        tvNameCast = findViewById(R.id.tvNameCast);
        avtCast = findViewById(R.id.avtCast);
        rcCast = findViewById(R.id.rcCast);
        heartBlack = findViewById(R.id.heart_black);
        heartRed = findViewById(R.id.heart_red);

    }

}