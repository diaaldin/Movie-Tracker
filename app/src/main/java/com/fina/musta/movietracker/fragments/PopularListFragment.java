package com.fina.musta.movietracker.fragments;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fina.musta.movietracker.model.MovieDBClient;
import com.fina.musta.movietracker.model.MovieList;
import com.fina.musta.movietracker.model.Movies;
import com.fina.musta.movietracker.Adapters.MoviesAdapter;
import com.fina.musta.movietracker.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by musta on 12/21/2017.
 */

public class PopularListFragment extends Fragment {
    final String BASE_URL = "https://api.themoviedb.org";
    RecyclerView recyclerView;
    View view;
    Activity mainActicty;
    public PopularListFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.popular_list,container,false);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(isAdded()) {
            mainActicty = getActivity();
            recyclerView = mainActicty.findViewById(R.id.popular_recycler_view_id);

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit = builder.build();
            MovieDBClient client = retrofit.create(MovieDBClient.class);
            Call<MovieList> call = client.TopRatedList();

            call.enqueue(new Callback<MovieList>() {
                @Override
                public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                    setMovieList(response.body().getMovieArrayList());
                }

                @Override
                public void onFailure(Call<MovieList> call, Throwable t) {

                }
            });
        }
    }
    public void setMovieList(List<Movies> movie){

        MoviesAdapter adapter = new MoviesAdapter(mainActicty,movie);
        if(mainActicty.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(mainActicty, 2));
        }
        else{
            recyclerView.setLayoutManager(new GridLayoutManager(mainActicty, 3));
        }



        recyclerView.setAdapter(adapter);

    }

}
