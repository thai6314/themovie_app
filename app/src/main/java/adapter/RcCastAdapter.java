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

import models.Cast;
import models.Movie;
import models.MovieDetail;

public class RcCastAdapter extends RecyclerView.Adapter<RcCastAdapter.RecyclerViewHolder>{
    private List<Cast> casts;
    private RcSeeAllList.ItemListener itemListener;
    public RcCastAdapter() {
        this.casts = new ArrayList<>();
    }

    public void setList(List<Cast> casts) {
        this.casts = casts;
        notifyDataSetChanged();
    }
    public void setItemListener(RcSeeAllList.ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public Cast getCast(int position) {
        return casts.get(position);
    }

    @NonNull
    @Override
    public RcCastAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.avatar_cast, parent, false);
        return new RcCastAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RcCastAdapter.RecyclerViewHolder holder, int position) {
        Cast cast = casts.get(position);
        Picasso.get().load("https://image.tmdb.org/t/p/w500"+cast.getProfile_path()).into(holder.avtCast);
        holder.tvNameCast.setText(cast.getName());
    }

    @Override
    public int getItemCount() {
        return casts.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView avtCast;
        private TextView tvNameCast;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvNameCast = itemView.findViewById(R.id.tvNameCast);
            avtCast = itemView.findViewById(R.id.avtCast);
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


