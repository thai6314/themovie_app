package fragments;



import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.btl_android.R;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import activities.MainActivity;
import activities.MovieDetailActivity;
import activities.SeeAllListActivity;
import adapter.ImageAdapter;
import adapter.RcHomeAdapter;
import api.MovieNowPlayingServices;
import api.MoviePupular;
import models.Movie;
import models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentHome extends Fragment implements RcHomeAdapter.ItemListener{
    private RecyclerView rcHome;
    private RcHomeAdapter recyclerViewAdapter;
    private List<Movie> movieList;
    private ViewPager viewPager;
    private TextView seeAllList;
    private TextView tvNameUser;
    private User user;
    private GoogleSignInAccount account;
    private MainActivity mainActivity;
    private ImageAdapter imageAdapter;

    int currentPage =0;
    Timer timer;
    final long DELAY_MS = 500;// do tre mili giay truoc tac vu duoc thuc thi
    final long PERIOD_MS = 3000;// thoi gian mili giay giua cac lan thuc thi tac vu lien tiep


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();
        user = mainActivity.getUser();
        account = mainActivity.getAccount();
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcHome = view.findViewById(R.id.rcHome);
        viewPager = view.findViewById(R.id.slideImage);
        seeAllList = view.findViewById(R.id.seeAllList);
        tvNameUser = view.findViewById(R.id.tvNameUser);

        if(user != null){
            tvNameUser.setText(user.getName());
        }
        else if(account!= null){
            tvNameUser.setText(account.getDisplayName());
        }


        ImageAdapter adapterView = new ImageAdapter(FragmentHome.this);
        viewPager.setAdapter(adapterView);

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == 6) {
                    currentPage = 0;
                }

                viewPager.setCurrentItem(currentPage++, true);
            }
        };


        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

        seeAllList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), SeeAllListActivity.class);
                startActivity(intent);
            }
        });

        recyclerViewAdapter = new RcHomeAdapter();
        movieList  = new ArrayList<>();
        MoviePupular.moviePupular.getMoviePopular()
                .enqueue(new Callback<List<Movie>>() {
                    @Override
                    public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                        movieList = response.body();
                        recyclerViewAdapter.setList(movieList);
                        LinearLayoutManager manager=new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
                        rcHome.setLayoutManager(manager);
                        rcHome.setAdapter(recyclerViewAdapter);
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
                        rcHome.setLayoutManager(manager);
                        rcHome.setAdapter(recyclerViewAdapter);

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
