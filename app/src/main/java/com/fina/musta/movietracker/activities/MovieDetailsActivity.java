package com.fina.musta.movietracker.activities;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fina.musta.movietracker.Adapters.TrailerAdapter;
import com.fina.musta.movietracker.R;
import com.fina.musta.movietracker.model.MovieDBClient;
import com.fina.musta.movietracker.model.MovieList;
import com.fina.musta.movietracker.model.Movies;
import com.fina.musta.movietracker.sql.DbHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.lang.Float.parseFloat;

public class MovieDetailsActivity extends AppCompatActivity {
    ImageView image,mainImage;
    RecyclerView recyclerView;
    List<String> imageUrlList = new ArrayList<>();
    TextView movieName,overview,releaseDateText,voteAverage;
    CollapsingToolbarLayout collapsingToolbar;
    FloatingActionButton fab ;
    DbHelper databaseHelper;
    Context context = this;
String BASE_URL = "https://api.themoviedb.org";
    SQLiteDatabase db;

    CollapsingToolbarLayout collapsingToolbarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Intent i = getIntent();
        initObjects();
        recyclerView= findViewById(R.id.recyclerview_details_id);
        movieName = findViewById(R.id.details_movie_name_id);
        Toolbar toolbar = findViewById(R.id.movie_details_tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.movie_details_collapsing_toolbar);
        collapsingToolbar.setTitle(i.getStringExtra("movieTitle"));
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);
        collapsingToolbar.setExpandedTitleColor(Color.argb(0,0,0,0));
        fab = findViewById(R.id.fab);

        image = findViewById(R.id.movie_image_id_details);
        mainImage=findViewById(R.id.main_image_detail_id);
        overview = findViewById(R.id.details_overview_id);
        releaseDateText = findViewById(R.id.details_movie_date_id);
        voteAverage = findViewById(R.id.vote_average_id);
        final String movieTitle = i.getStringExtra("movieTitle");
        final  String user = i.getStringExtra("user");
        final String id = i.getStringExtra("movieId");
        final   String date = i.getStringExtra("releaseDate");
        final String vote = i.getStringExtra("voteAverage");
        final String overviewString = i.getStringExtra("overview");
        final String imageString =  i.getStringExtra("imageUrl");
        final String backdroppath = i.getStringExtra("backdropPath");
        Picasso.with(this).load(i.getStringExtra("backdropPath")).into(image);
        Picasso.with(this).load(i.getStringExtra("imageUrl")).into(mainImage);

        if(databaseHelper.checkLike(user,movieTitle.trim().replaceAll(" ","_")))
            fab.setImageDrawable(getResources().getDrawable(R.drawable.heart_on,context.getTheme()));

        movieName.setText(movieTitle);
        overview.setText(overviewString);
        releaseDateText.setText(date);
//        Log.d("last log",vote);
        voteAverage.setText(parseFloat(vote)/2 + "");

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!databaseHelper.checkLike(user,movieTitle.trim().replaceAll(" ","_"))) {
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.heart_on));
                    db = databaseHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("user_name", user);
                    values.put("liked", movieTitle.trim().replaceAll(" ","_"));
                    values.put("movieid",id);
                    values.put("posterpath",imageString);
                    values.put("releasedate",date);
                    values.put("movieoverview",overviewString);
                    values.put("backdroppath",backdroppath);
                    values.put("movierating",vote);

                    db.insert("favorites", null, values);
                    db.close();
                    Snackbar.make(collapsingToolbar,"Added To Favorites",Snackbar.LENGTH_SHORT).show();
                }
                else
                {

                    db = databaseHelper.getWritableDatabase();
                    db.delete("favorites","user_name = '" +
                            ""+user+"' AND liked = '"+movieTitle.trim().replaceAll(" ","_")+"'",null);

                    fab.setImageDrawable(getResources().getDrawable(R.drawable.heart_off));
                    db.close();
                    Snackbar.make(collapsingToolbar,"Removed From Favorites",Snackbar.LENGTH_SHORT).show();

                }
            }
        });

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        MovieDBClient client = retrofit.create(MovieDBClient.class);
        Call<MovieList> call = client.getYouTubeTrailer(id);

        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {

                LinearLayoutManager linear = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(linear);

               for (Movies movie : response.body().getMovieArrayList()){

                imageUrlList.add(movie.getKey());
                Log.d("movieKey",movie.getKey());
               }
                TrailerAdapter trailerAdapter = new TrailerAdapter(context,imageUrlList);
                recyclerView.setAdapter(trailerAdapter);

            }


            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {

            }
        });





    }
    private void initObjects() {
        databaseHelper = new DbHelper(this);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
