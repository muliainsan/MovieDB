package com.project.muliains.newstoday;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.project.muliains.newstoday.Adapter.MoviesAdapter;
import com.project.muliains.newstoday.Java.Movie;
import com.project.muliains.newstoday.Java.MovieResponse;
import com.project.muliains.newstoday.Services.ApiClient;
import com.project.muliains.newstoday.Services.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private final static String API_Key = "b22f426e41174091c4c6bfa16086e1db";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (API_Key.isEmpty()){
            Toast.makeText(this, "Please obtain your API KEY first", Toast.LENGTH_SHORT).show();
            return;
        }
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<MovieResponse> call = apiService.getTopRatedMovies(API_Key);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                int statusCode = response.code();
                List<Movie> movies = response.body().getResults();
                recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));
                Log.d(TAG, "Number of movies received : "+ movies.size());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });

    }
}
