package tn.isi.ussef.retrofit.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.isi.ussef.retrofit.R;
import tn.isi.ussef.retrofit.adapter.MoviesAdapter;
import tn.isi.ussef.retrofit.model.Movie;
import tn.isi.ussef.retrofit.model.MoviesResponse;
import tn.isi.ussef.retrofit.rest.ApiClient;
import tn.isi.ussef.retrofit.rest.ApiInterface;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private static  String API_KEY = "2f18ee53056581fef218be9c031ab9fe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY first from themoviedb.org", Toast.LENGTH_LONG).show();
            return;
        }
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<MoviesResponse> call = apiService.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                int statusCode = response.code();
                List<Movie> movies = response.body().getResults();
                recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.movies_card_item, getApplicationContext(), new MoviesAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Movie item) {
                        goToDetails(item);
                    }
                }));

                Log.d(TAG, "Number of movie Received: " + movies.size());
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.d(TAG, t.toString());
            }
        });
    }

    void  goToDetails(Movie movie){
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("movie_id", movie.getId().toString());
        intent.putExtra("api_key", API_KEY);
        startActivity(intent);
    }
}
