package com.project.muliains.newstoday.Services;

import com.project.muliains.newstoday.Java.MovieDetails;
import com.project.muliains.newstoday.Java.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Muliains on 08-Feb-18.
 */

public interface ApiInterface {
    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(
            @Query("api_key") String apiKey
    );


    @GET("movie/{id}")
    Call<MovieDetails> getMovieDetails(
            @Path("id") int id,
            @Query("api_key") String apiKey
    );


}
