package tn.isi.ussef.retrofit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import tn.isi.ussef.retrofit.R;
import tn.isi.ussef.retrofit.model.Movie;

/**
 * Created by Ussef on 3/8/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(Movie item);
    }
    private List<Movie> movies;
    private int rowLayout;
    private Context context;
    private final OnItemClickListener listener;


    public class MovieViewHolder extends RecyclerView.ViewHolder{
        LinearLayout moviesLayout;
        TextView movieTitle;
        TextView data;
        TextView movieDescription;
        TextView rating;

        public MovieViewHolder(View v) {
            super(v);
            moviesLayout = (LinearLayout) v.findViewById(R.id.movies_layout);
            movieTitle = (TextView) v.findViewById(R.id.title);
            data = (TextView) v.findViewById(R.id.subtitle);
            movieDescription = (TextView) v.findViewById(R.id.description);
            rating = (TextView) v.findViewById(R.id.rating);
        }
        public void bind(final Movie item, final OnItemClickListener listener) {
            // loading toolbar header image
            movieTitle.setText(item.getTitle());
            data.setText(item.getReleaseDate());
            movieDescription.setText(item.getOverview());
            rating.setText(item.getVoteAverage().toString());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public MoviesAdapter(List<Movie> movies, int rowLayout, Context context, OnItemClickListener listener) {
        this.movies = movies;
        this.rowLayout = rowLayout;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie item = movies.get(position);
        holder.bind(item, listener);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }


}
