package adapter;

import android.util.Log;
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

import models.Movie;
import models.MovieDetail;

public class RcEpisode extends RecyclerView.Adapter<RcEpisode.RecyclerViewHolder> {
    private List<MovieDetail> movieList;
    private RcEpisode.ItemListener itemListener;
    public RcEpisode() {
        this.movieList = new ArrayList<>();
    }

    public void setList(List<MovieDetail> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }
    public void setItemListener(RcEpisode.ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public MovieDetail getMovie(int position) {
        return movieList.get(position);
    }

    @NonNull
    @Override
    public RcEpisode.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_episode, parent, false);
        return new RcEpisode.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RcEpisode.RecyclerViewHolder holder, int position) {
        MovieDetail movie = movieList.get(position);
        Picasso.get().load("https://image.tmdb.org/t/p/w500"+movie.getBackdrop_path()).into(holder.imgEpisode);
        holder.numEpisode.setText("Episode "+(position+1));
        holder.titleEpisode.setText(movie.getTitle());
        Log.e("runtime",movie.getRuntime()+"" );
        holder.runtime.setText(movie.getRuntime()+" min");
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imgEpisode;
        private TextView numEpisode,titleEpisode,runtime;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imgEpisode = itemView.findViewById(R.id.imgEpisode);
            numEpisode = itemView.findViewById(R.id.numEpisode);
            titleEpisode = itemView.findViewById(R.id.titleEpisode);
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
