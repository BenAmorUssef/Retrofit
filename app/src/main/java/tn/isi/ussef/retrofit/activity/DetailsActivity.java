package tn.isi.ussef.retrofit.activity;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.isi.ussef.retrofit.R;
import tn.isi.ussef.retrofit.adapter.MoviesAdapter;
import tn.isi.ussef.retrofit.model.Genre;
import tn.isi.ussef.retrofit.model.Movie;
import tn.isi.ussef.retrofit.model.MoviesResponse;
import tn.isi.ussef.retrofit.rest.ApiClient;
import tn.isi.ussef.retrofit.rest.ApiInterface;

public class DetailsActivity extends AppCompatActivity {
    private ImageView imgHeader;
    private TextView title;
    private TextView overview;
    private TextView vote_count;
    private TextView movie_runtime;
    private TextView releasing_year;
    private TextView movie_genres;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        String movieid = getIntent().getStringExtra("movie_id");
        String api_key = getIntent().getStringExtra("api_key");
        imgHeader = (ImageView) findViewById(R.id.backdrop);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = (TextView) findViewById(R.id.movie_title);
        overview = (TextView) findViewById(R.id.movie_overview);
        movie_runtime = (TextView) findViewById(R.id.movie_runtime);
        releasing_year = (TextView) findViewById(R.id.movie_release_year);
        vote_count = (TextView) findViewById(R.id.movie_vote_count);
        movie_genres = (TextView) findViewById(R.id.movie_genres);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<Movie> call = apiService.getMovieDetails(Integer.valueOf(movieid), api_key);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                // loading toolbar header image
                Movie movie = response.body();
                initCollapsingToolbar(movie);
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });
        }
    private void initCollapsingToolbar(final Movie movie) {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.setExpanded(true);

        // hiding & showing the txtPostTitle when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(movie.getTitle());
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });

        // loading toolbar header image
        Glide.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w600/" + movie.getPosterPath())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .animate(R.xml.annimate_image_zoom)
                .into(imgHeader);

        title.setText(movie.getTitle().toString());
        overview.setText(movie.getOverview().toString());
        vote_count.setText(movie.getVoteCount().toString());
        List<Genre> genres ;
        genres = movie.getGenres();     //categories.setText(genres);
        String cat = "";
        for (Genre g : genres) {
            cat += g.getName().toString()+", ";
        }
        cat =  cat.substring(0,  cat.lastIndexOf(","));
        Toast.makeText(this, cat, Toast.LENGTH_SHORT).show();
        vote_count.setText(movie.getVoteCount().toString());
        movie_runtime.setText(FromMinToHoursAndMin(movie.getRuntime()));
        releasing_year.setText("(" + String.copyValueOf(movie.getReleaseDate().toString().toCharArray(), 0, 4) + ")");
        movie_genres.setText(cat);

    }

    public String FromMinToHoursAndMin(int min){
        int nbh = min / 60;
        int mini = min - (nbh * 60);
        return nbh + "h " + mini + "min";
    }

    }
