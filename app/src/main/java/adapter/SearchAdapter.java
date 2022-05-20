package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.btl_android.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import models.MovieDetail;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.RecyclerViewHolder>{
    private List<MovieDetail> movieList;
    private SearchAdapter.ItemListener itemListener;
    public SearchAdapter() {
        this.movieList = new ArrayList<>();
    }

    public void setList(List<MovieDetail> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }
    public void setItemListener(SearchAdapter.ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public MovieDetail getMovie(int position) {
        return movieList.get(position);
    }

    @NonNull
    @Override
    public SearchAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        return new SearchAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.RecyclerViewHolder holder, int position) {
        MovieDetail movie = movieList.get(position);
        Picasso.get().load("https://image.tmdb.org/t/p/w500"+movie.getBackdrop_path()).into(holder.imgSearch);
        holder.tvTitle.setText(movie.getTitle());
        holder.rateStar.setText(movie.getVote_average());
        holder.tvReleaseDate.setText(movie.getRelease_date());
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imgSearch;
        private TextView tvTitle, rateStar,tvReleaseDate;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imgSearch = itemView.findViewById(R.id.imgSearch);
            tvReleaseDate = itemView.findViewById(R.id.tvReleaseDate);
            rateStar = itemView.findViewById(R.id.vote);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if(itemListener!=null) itemListener.onItemClick(view, getAdapterPosition());
        }
    }
    public interface ItemListener {
        void onItemClick(View view, int pos);
    }
}
