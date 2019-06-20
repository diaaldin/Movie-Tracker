package com.fina.musta.movietracker.fragments;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.fina.musta.movietracker.Adapters.MoviesAdapter;
import com.fina.musta.movietracker.R;
import com.fina.musta.movietracker.model.MovieDBClient;
import com.fina.musta.movietracker.model.MovieList;
import com.fina.musta.movietracker.model.Movies;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by musta on 12/21/2017.
 */

public class MoviesListFragment extends Fragment {
    final String BASE_URL = "https://api.themoviedb.org";
    RecyclerView recyclerView;
    View view;
    MoviesAdapter adapter;
    ProgressBar bar;
    Activity mainActicty;
    int visibleItemCount, totalItemCount = 1;
    int firstVisiblesItems = 0;
    int totalPages = 80; // get your total pages from web service first response
    int current_page = 2;

    boolean canLoadMoreData = true;
    List movieFinalList = new ArrayList();

    public MoviesListFragment(){


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainActicty = (Activity) getContext();
        bar = mainActicty.findViewById(R.id.loading_progress_bar);

        if (isAdded()) {
            recyclerView = mainActicty.findViewById(R.id.recyclerview_id);

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit = builder.build();
            MovieDBClient client = retrofit.create(MovieDBClient.class);
            Call<MovieList> call = client.MovieList(1);

            call.enqueue(new Callback<MovieList>() {
                @Override
                public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                    setMovieList(response.body().getMovieArrayList());

                }

                @Override
                public void onFailure(Call<MovieList> call, Throwable t) {
                    Log.d("error", "connection");
                }
            });

        }

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.movies_list,container,false);
        return view;
    }
    public void addMovieList(List<Movies> movie)
    {
        for(Movies m :movie)
        {
            movieFinalList.add(m);
        }
        adapter.notifyDataSetChanged();
    }
    public void setMovieList(List<Movies> movie){
        movieFinalList = new ArrayList();
        current_page = 2;
        for(Movies m :movie)
        {
            movieFinalList.add(m);
        }
        Log.d("hi","tester");
        adapter  = new MoviesAdapter(mainActicty,movieFinalList);
       LinearLayoutManager Manager = new GridLayoutManager(mainActicty, 2);

        if(mainActicty.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(Manager =new GridLayoutManager(mainActicty, 2));
        }
        else{
            recyclerView.setLayoutManager(Manager =new GridLayoutManager(mainActicty, 3));
        }
        recyclerView.setLayoutManager(Manager);
        final LinearLayoutManager manager = Manager;

        //        if(mainActicty.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
//            manager =new GridLayoutManager(mainActicty, 2);
//            recyclerView.setLayoutManager(manager);
//        }
//        else{
//            manager = new GridLayoutManager(mainActicty, 3);
//            recyclerView.setLayoutManager(manager);
//        }


        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy > 0) //check for scroll down
                {
                    visibleItemCount = manager.getChildCount();
                    totalItemCount = manager.getItemCount();
                    firstVisiblesItems = manager.findFirstVisibleItemPosition();
                    if (canLoadMoreData) {
                        if ((visibleItemCount + firstVisiblesItems) >= totalItemCount) {
                            if (current_page < totalPages) {

                                canLoadMoreData = false;
                                bar.setVisibility(View.VISIBLE);
                                Retrofit.Builder builder = new Retrofit.Builder()
                                        .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create());
                                Retrofit retrofit = builder.build();
                                MovieDBClient client = retrofit.create(MovieDBClient.class);
                                Call<MovieList> call = client.MovieList(current_page);
                                call.enqueue(new Callback<MovieList>() {
                                    @Override
                                    public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                                        addMovieList(response.body().getMovieArrayList());
                                        canLoadMoreData=true;
                                        current_page++;
                                        bar.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public void onFailure(Call<MovieList> call, Throwable t) {
                                        Log.d("error", "connection");
                                    }
                                });

                            }
                        }
                    }
                }
            }
        });


    }
}

