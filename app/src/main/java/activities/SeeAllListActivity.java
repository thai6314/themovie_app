package activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.btl_android.R;

import java.util.ArrayList;
import java.util.List;

import adapter.RcSeeAllList;
import api.MovieNowPlayingServices;
import api.MoviePupular;
import models.Movie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeeAllListActivity extends AppCompatActivity implements RcSeeAllList.ItemListener {
    private RecyclerView rcPopular;
    private RcSeeAllList rcSeeAllList;
    private List<Movie>movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_see_all_list);

        rcPopular = findViewById(R.id.rcPopular);
        rcSeeAllList = new RcSeeAllList();
        movieList  = new ArrayList<>();
        MoviePupular.moviePupular.getMoviePopular()
                .enqueue(new Callback<List<Movie>>() {
                    @Override
                    public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                        movieList = response.body();
                        rcSeeAllList.setList(movieList);
                        GridLayoutManager manager=new GridLayoutManager(getApplicationContext(), 3);
                        rcPopular.setLayoutManager(manager);
                        rcPopular.setAdapter(rcSeeAllList);
                    }

                    @Override
                    public void onFailure(Call<List<Movie>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
        rcSeeAllList.setItemListener(this);

    }

    @Override
    public void onItemClick(View view, int pos) {
        Movie movie=rcSeeAllList.getMovie(pos);
        Intent intent=new Intent(this, MovieDetailActivity.class);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }
}