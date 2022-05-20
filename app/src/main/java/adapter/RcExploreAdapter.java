package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.btl_android.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import models.Movie;

public class RcExploreAdapter extends RecyclerView.Adapter<RcExploreAdapter.RecyclerViewHolder> {
    private List<Movie> movieList;
    private RcExploreAdapter.ItemExploreListener itemListener;
    public RcExploreAdapter() {
        this.movieList = new ArrayList<>();
    }



    public void setList(List<Movie> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }
    public void setItemListener(RcExploreAdapter.ItemExploreListener itemListener) {
        this.itemListener = itemListener;
    }

    public Movie getMovie(int position) {
        return movieList.get(position);
    }

    @NonNull
    @Override
    public RcExploreAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_explore, parent, false);
        return new RcExploreAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RcExploreAdapter.RecyclerViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        Picasso.get().load("https://image.tmdb.org/t/p/w500"+movie.getPoster_path()).into(holder.imgExplore);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imgExplore;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imgExplore = itemView.findViewById(R.id.imgExplore);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if(itemListener!=null) itemListener.onItemClick(view, getAdapterPosition());
        }
    }
    public interface ItemExploreListener {
        void onItemClick(View view, int pos);
    }
}
