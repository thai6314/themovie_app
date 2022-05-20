package adapter;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.btl_android.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import activities.MovieDetailActivity;
import api.MovieNowPlayingServices;
import fragments.FragmentHome;
import models.Movie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageAdapter extends PagerAdapter {
    FragmentHome mContext;
    private List<Movie> movieList;


    public ImageAdapter(FragmentHome context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((ImageView)object);
    }


    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext.getContext());
        TextView textView = new TextView(mContext.getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        movieList = new ArrayList<>();
        MovieNowPlayingServices.movieNowPlayingServices.getMovieNowPlaying()
                .enqueue(new Callback<List<Movie>>() {
                    @Override
                    public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                        movieList = response.body();
                        Picasso.get().load("https://image.tmdb.org/t/p/w500"+movieList.get(position)
                                .getPoster_path()).into(imageView);
                        ((ViewPager) container).addView(imageView, 0);

                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(mContext.getContext(),MovieDetailActivity.class);
                                intent.putExtra("movie",movieList.get(position));
                                mContext.startActivity(intent);
                            }
                        });


                    }

                    @Override
                    public void onFailure(Call<List<Movie>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });

        return imageView;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }




}
