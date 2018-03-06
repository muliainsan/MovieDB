package com.project.muliains.newstoday.Adapter;

import android.content.Context;

import com.project.muliains.newstoday.Java.Movie;
import com.project.muliains.newstoday.Java.MovieDetails;
import com.project.muliains.newstoday.Java.MovieResponse;
import com.project.muliains.newstoday.R;
import com.project.muliains.newstoday.Services.ApiClient;
import com.project.muliains.newstoday.Services.ApiInterface;
import com.squareup.picasso.Picasso;

import java.util.List;
//import manual
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Muliains on 08-Feb-18.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>{
    private List<Movie> movies;
    private int rowLayout;
    private Context context;
    String API_Key = "b22f426e41174091c4c6bfa16086e1db";

    @Override
    public MoviesAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout,parent,false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesAdapter.MovieViewHolder holder, final int position) {
        holder.movieTitle.setText(movies.get(position).getTitle());
        holder.data.setText(movies.get(position).getReleaseDate());
        holder.movieDesc.setText(movies.get(position).getOverview());
        holder.rating.setText(movies.get(position).getVoteAverage().toString());
//        holder.context.setWallpaper(movies.get(position).getPosterPath());
        Log.i("xx",""+movies.get(position).getImageUrl());
        Picasso picasso = Picasso.with(context);
        picasso.setIndicatorsEnabled(true);
        picasso.load("http://image.tmdb.org/t/p/w500/"+movies.get(position).getBackdropPath()) //Load the image
//                    .placeholder(R.drawable.ic_placeholder) //Image resource that act as placeholder
//                    .error(R.drawable.ic_error) //Image resource for error
//                    .resize(300, 500)  // Post processing - Resizing the image
                    .into(holder.image); // View where image is loaded.
        holder.moviesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                startActivity(browserIntent);
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<MovieDetails> call = apiService.getMovieDetails(movies.get(position).getId(),API_Key);
                call.enqueue(new Callback<MovieDetails>() {
                    @Override
                    public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                        String url = "http://www.imdb.com/title/"+response.body().getImdb_id();
                        Log.i("clickaa",""+context);
                        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                        CustomTabsIntent customTabsIntent = builder.build();
                        customTabsIntent.intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        builder.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary));
                        customTabsIntent.launchUrl(context, Uri.parse(url));
                    }

                    @Override
                    public void onFailure(Call<MovieDetails> call, Throwable t) {

                    }


                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        LinearLayout moviesLayout;
        TextView movieTitle;
        TextView data;
        TextView movieDesc;
        TextView rating;
        ImageView image;

        public MovieViewHolder(View itemView) {
            super(itemView);
            moviesLayout =  itemView.findViewById(R.id.movies_layout);
            movieTitle = itemView.findViewById(R.id.title);
            data =  itemView.findViewById(R.id.subtitle);
            movieDesc =  itemView.findViewById(R.id.description);
            rating =  itemView.findViewById(R.id.rating);
            image = itemView.findViewById(R.id.imageview);


        }
    }

    public MoviesAdapter(List<Movie> movies, int rowLayout, Context context) {
        this.movies = movies;
        this.rowLayout = rowLayout;
        this.context = context;
    }
}
