package tn.isi.ussef.retrofit.rest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import tn.isi.ussef.retrofit.model.Movie;
import tn.isi.ussef.retrofit.model.MoviesResponse;

/**
 * Created by Ussef on 3/8/2017.
 */

public interface ApiInterface {

    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<Movie> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);
}
