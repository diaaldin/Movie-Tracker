package com.fina.musta.movietracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.fina.musta.movietracker.Adapters.MoviesAdapter;
import com.fina.musta.movietracker.R;
import com.fina.musta.movietracker.model.MovieDBClient;
import com.fina.musta.movietracker.model.MovieList;
import com.fina.musta.movietracker.model.Movies;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {
    final String BASE_URL = "https://api.themoviedb.org";
    private MoviesAdapter adapter;
    private RecyclerView recyclerView;
    private Toolbar toolBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        recyclerView = findViewById(R.id.search_recycler_view);
        toolBar = findViewById(R.id.search_toolbar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        Intent i = getIntent();
        String searchWord = i.getStringExtra("search");
        getSupportActionBar().setTitle("Results for " + searchWord);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        MovieDBClient client = retrofit.create(MovieDBClient.class);
        Call<MovieList> call = client.getSearchResult(searchWord);

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
    public void setMovieList(List<Movies> movie) {

        Log.d("hi", "tester");
        adapter = new MoviesAdapter(this, movie);
        final LinearLayoutManager manager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
