package fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.btl_android.R;

import java.util.ArrayList;
import java.util.List;

import activities.MovieDetailActivity;
import activities.SeeAllListActivity;
import adapter.RcExploreAdapter;
import adapter.RcHomeAdapter;
import adapter.SearchAdapter;
import api.MovieNowPlayingServices;
import api.MoviePupular;
import api.MovieServices;
import models.Movie;
import models.MovieDetail;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentExplore extends Fragment implements RcHomeAdapter.ItemListener,
        RcExploreAdapter.ItemExploreListener, SearchAdapter.ItemListener{
    private List<Movie>movieList;
    private List<Movie>movieListTopRate;
    private List<Movie>movieListComingSoon;
    private List<Movie>movieListTrending;
    private List<MovieDetail>movieListSearches;
    private RecyclerView rcExplore;
    private RecyclerView rcPopularExpore;
    private RecyclerView rcSearch;
    private TextView seeAllList;
    private RcHomeAdapter recyclerViewAdapter;
    private RcExploreAdapter rcExploreAdapter;
    private SearchAdapter searchAdapter;
    private EditText etSearch;
    private ImageView imgBtSearch;
    private RelativeLayout content;
    private ImageView imgback;
    private Button btnTopRate, btnComingSoon, btnTrending;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_explore, container, false);

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcPopularExpore = view.findViewById(R.id.rcPopularExpore);
        seeAllList = view.findViewById(R.id.seeAllList);
        rcExplore = view.findViewById(R.id.rcExplore);
        rcSearch = view.findViewById(R.id.rcSearch);
        btnComingSoon = view.findViewById(R.id.btnComingSoon);
        btnTopRate = view.findViewById(R.id.btnTopRate);
        btnTrending = view.findViewById(R.id.btnTrending);
        etSearch= view.findViewById(R.id.etSearch);
        imgBtSearch = view.findViewById(R.id.imgBtSearch);
        content = view.findViewById(R.id.content);
        imgback = view.findViewById(R.id.imgBack);

        rcSearch.setVisibility(View.GONE);

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rcSearch.setVisibility(View.GONE);
                content.setVisibility(View.VISIBLE);
            }
        });

        imgBtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("-------------","oooo");
                rcSearch.setVisibility(View.VISIBLE);
                content.setVisibility(View.GONE);

                movieListSearches = new ArrayList<>();
                searchAdapter = new SearchAdapter();

                String tSearch = etSearch.getText().toString();
                Log.e("Text Search",tSearch);
                if(!tSearch.equals("")){
                    MovieServices.movieServices.getSearchMovie(tSearch)
                            .enqueue(new Callback<List<MovieDetail>>() {
                                @Override
                                public void onResponse(Call<List<MovieDetail>> call, Response<List<MovieDetail>> response) {
                                    movieListSearches = response.body();
                                    Log.e("-------------",movieListSearches.size()+"");
                                    searchAdapter.setList(movieListSearches);
                                    LinearLayoutManager manager =
                                            new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
                                    rcSearch.setLayoutManager(manager);
                                    rcSearch.setAdapter(searchAdapter);
                                }

                                @Override
                                public void onFailure(Call<List<MovieDetail>> call, Throwable t) {
                                    Log.e("-------------",t.toString());

                                    t.printStackTrace();
                                }

                            });
                    searchAdapter.setItemListener(FragmentExplore.this);
                }else {
                    Log.e("-------------","Else");
                }

            }
        });

        btnTopRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  btnTopSearches.setBackground(btnTopSearches.getContext().getResources().getDrawable(R.drawable.border_search));
                btnComingSoon.setBackground(btnComingSoon.getContext().getResources().getDrawable(R.drawable.border_search));
                btnTrending.setBackground(btnTrending.getContext().getResources().getDrawable(R.drawable.border_search));
                btnTopRate.setBackground(btnTopRate.getContext().getResources().getDrawable(R.drawable.button_explore));

                rcExploreAdapter = new RcExploreAdapter();
                movieListTopRate = new ArrayList<>();
                MovieServices.movieServices.getTopRate()
                        .enqueue(new Callback<List<Movie>>() {
                            @Override
                            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                                movieListTopRate = response.body();
                                rcExploreAdapter.setList(movieListTopRate);
                                LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
                                rcExplore.setLayoutManager(manager);
                                rcExplore.setAdapter(rcExploreAdapter);

                                btnTopRate.setEnabled(true);
                                btnTopRate.setBackground(btnTopRate.getContext().getResources().getDrawable(R.drawable.button_explore));
                            }

                            @Override
                            public void onFailure(Call<List<Movie>> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
                rcExploreAdapter.setItemListener(FragmentExplore.this);
            }

        });

        btnComingSoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // btnTopSearches.setBackground(btnTopSearches.getContext().getResources().getDrawable(R.drawable.border_search));
                btnComingSoon.setBackground(btnComingSoon.getContext().getResources().getDrawable(R.drawable.button_explore));
                btnTopRate.setBackground(btnTopRate.getContext().getResources().getDrawable(R.drawable.border_search));
                btnTrending.setBackground(btnTrending.getContext().getResources().getDrawable(R.drawable.border_search));

                rcExploreAdapter = new RcExploreAdapter();
                movieListComingSoon = new ArrayList<>();
                MovieServices.movieServices.getComingSoon()
                        .enqueue(new Callback<List<Movie>>() {
                            @Override
                            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                                movieListComingSoon = response.body();
                                rcExploreAdapter.setList(movieListComingSoon);
                                LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
                                rcExplore.setLayoutManager(manager);
                                rcExplore.setAdapter(rcExploreAdapter);

                            }

                            @Override
                            public void onFailure(Call<List<Movie>> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
                rcExploreAdapter.setItemListener(FragmentExplore.this);
            }
        });
        btnTrending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnComingSoon.setBackground(btnComingSoon.getContext().getResources().getDrawable(R.drawable.border_search));
                btnTopRate.setBackground(btnTopRate.getContext().getResources().getDrawable(R.drawable.border_search));
                btnTrending.setBackground(btnTrending.getContext().getResources().getDrawable(R.drawable.button_explore));

                rcExploreAdapter = new RcExploreAdapter();
                movieListTrending = new ArrayList<>();
                MovieServices.movieServices.getTrending()
                        .enqueue(new Callback<List<Movie>>() {
                            @Override
                            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                                movieListTrending = response.body();
                                rcExploreAdapter.setList(movieListTrending);
                                LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
                                rcExplore.setLayoutManager(manager);
                                rcExplore.setAdapter(rcExploreAdapter);

                            }

                            @Override
                            public void onFailure(Call<List<Movie>> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
                rcExploreAdapter.setItemListener(FragmentExplore.this);
            }
        });


        seeAllList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SeeAllListActivity.class);
                startActivity(intent);
            }
        });
        rcExploreAdapter = new RcExploreAdapter();
        movieListTopRate = new ArrayList<>();
        MovieServices.movieServices.getTopRate()
                .enqueue(new Callback<List<Movie>>() {
                    @Override
                    public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                        movieListTopRate = response.body();
                        rcExploreAdapter.setList(movieListTopRate);
                        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
                        rcExplore.setLayoutManager(manager);
                        rcExplore.setAdapter(rcExploreAdapter);
                        btnTopRate.setBackground(btnTopRate.getContext().getResources().getDrawable(R.drawable.button_explore));

                    }

                    @Override
                    public void onFailure(Call<List<Movie>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
        rcExploreAdapter.setItemListener(this);

        recyclerViewAdapter = new RcHomeAdapter();
        movieList = new ArrayList<>();
        MovieNowPlayingServices.movieNowPlayingServices.getMovieNowPlaying()
                .enqueue(new Callback<List<Movie>>() {
                    @Override
                    public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                        movieList = response.body();
                        recyclerViewAdapter.setList(movieList);
                        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
                        rcPopularExpore.setLayoutManager(manager);
                        rcPopularExpore.setAdapter(recyclerViewAdapter);
                    }

                    @Override
                    public void onFailure(Call<List<Movie>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
        recyclerViewAdapter.setItemListener(this);
    }
    @Override
    public void onResume() {
        super.onResume();
        movieList= new ArrayList<>();
        MoviePupular.moviePupular.getMoviePopular()
                .enqueue(new Callback<List<Movie>>() {
                    @Override
                    public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                        movieList = response.body();
                        recyclerViewAdapter.setList(movieList);
                        LinearLayoutManager manager=new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
                        rcPopularExpore.setLayoutManager(manager);
                        rcPopularExpore.setAdapter(recyclerViewAdapter);

                    }

                    @Override
                    public void onFailure(Call<List<Movie>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
        recyclerViewAdapter.setItemListener(this);
        recyclerViewAdapter.setList(movieList);
    }

    @Override
    public void onItemClick(View view, int pos) {
        Movie movie=recyclerViewAdapter.getMovie(pos);
        Intent intent=new Intent(getActivity(), MovieDetailActivity.class);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }

}
