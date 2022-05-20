package activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.btl_android.R;

import java.util.ArrayList;
import java.util.List;

import adapter.RcEpisode;
import api.MovieServices;
import models.Movie;
import models.MovieDetail;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WatchMovieActivity extends AppCompatActivity implements RcEpisode.ItemListener{
    private WebView webView;
    private MovieDetail movieDetail;
    private TextView tvTitle,tvReleaseDate,watchStar,movieDescription;
    private RecyclerView rcEpisode;
    private RcEpisode rcEpisodeAdapter;
    private List<MovieDetail>movieDetailList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_watch_movie);

        initView();

        movieDetail = new MovieDetail();
        Intent intent = getIntent();
        movieDetail =  (MovieDetail) intent.getSerializableExtra("movieDetail");

        webView = findViewById(R.id.webView);
                WebView webView = (WebView) findViewById(R.id.webView);
        String url ="https://www.2embed.ru/embed/tmdb/movie?id="+movieDetail.getId();
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(url);

        tvTitle.setText(movieDetail.getTitle());
        tvReleaseDate.setText(movieDetail.getRelease_date());
        watchStar.setText(movieDetail.getVote_average());
        movieDescription.setText(movieDetail.getDescription());
        movieDescription.setMovementMethod(new ScrollingMovementMethod());

        movieDetailList = new ArrayList<>();
        rcEpisodeAdapter = new RcEpisode();
        MovieServices.movieServices.getSearchMovie(movieDetail.getTitle())
                .enqueue(new Callback<List<MovieDetail>>() {
                    @Override
                    public void onResponse(Call<List<MovieDetail>> call, Response<List<MovieDetail>> response) {
                        movieDetailList = response.body();
                        rcEpisodeAdapter.setList(movieDetailList);
                        LinearLayoutManager manager =
                                new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
                        rcEpisode.setLayoutManager(manager);
                        rcEpisode.setAdapter(rcEpisodeAdapter);
                    }

                    @Override
                    public void onFailure(Call<List<MovieDetail>> call, Throwable t) {

                    }
                });
        rcEpisodeAdapter.setItemListener(this);
    }
    public void initView(){
        tvTitle = findViewById(R.id.tvTitleWatch);
        tvReleaseDate = findViewById(R.id.tvReleaseDateWatch);
        watchStar = findViewById(R.id.watchStar);
        movieDescription = findViewById(R.id.movieDescription);
        rcEpisode = findViewById(R.id.rcEpisode);
    }

    @Override
    public void onItemClick(View view, int pos) {
        MovieDetail movieDetail=rcEpisodeAdapter.getMovie(pos);
        Intent intent=new Intent(getApplicationContext(), WatchMovieActivity.class);
        intent.putExtra("movieDetail", movieDetail);
        startActivity(intent);
    }
}