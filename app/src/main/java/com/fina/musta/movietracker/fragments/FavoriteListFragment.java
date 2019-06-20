package com.fina.musta.movietracker.fragments;

import android.app.Activity;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fina.musta.movietracker.Adapters.MoviesAdapter;
import com.fina.musta.movietracker.R;
import com.fina.musta.movietracker.model.Movies;
import com.fina.musta.movietracker.sql.DbHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by musta on 12/21/2017.
 */

public class FavoriteListFragment extends Fragment {
    final String BASE_URL = "https://api.themoviedb.org";
    RecyclerView recyclerView;
    TextView EmptyView;
    View view;
    Activity mainActicty;
    DbHelper dbHelper;
    SQLiteDatabase db;
    List<Movies> arr;
    MoviesAdapter adapter;
    String LINKPOSTER = "https://image.tmdb.org/t/p/w154";
    String LINKBACKDROP = "https://image.tmdb.org/t/p/w500";
    public FavoriteListFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.favorite_list,container,false);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("fragment","stopped");
    }

    @Override
    public void onResume() {
        super.onResume();
        if(adapter!=null)
        addtoList();
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainActicty = getActivity();
        recyclerView = mainActicty.findViewById(R.id.fav_recyclerview_id);
        EmptyView = mainActicty.findViewById(R.id.empty_view);
        addtoList();

    }

    private void addtoList() {
        List<Movies> list = new ArrayList<>();
        dbHelper =new DbHelper(getContext());
        db  = dbHelper.getReadableDatabase();
        arr = new ArrayList<>();

        String selection = "user_name" + " =?";

        String[] selectionArgs = {getActivity().getIntent().getStringExtra("user")};
        Cursor mCursor = db.query("favorites",null,selection,selectionArgs,null,null,null,null);

        int posterpathint = mCursor.getColumnIndex("posterpath");
        int likedint = mCursor.getColumnIndex("liked");
        int voteaverageint = mCursor.getColumnIndex("movierating");
        int movieidint = mCursor.getColumnIndex("movieid");
        int backdroppathint = mCursor.getColumnIndex("backdroppath");
        int overviewint = mCursor.getColumnIndex("movieoverview");
        int releasedateint = mCursor.getColumnIndex("releasedate");
        while(mCursor.moveToNext()) {

            String posterpath = mCursor.getString(posterpathint);
            Log.d("asdasd",posterpath.replace(LINKPOSTER,""));
            String liked = mCursor.getString(likedint);
            String voteaverage = mCursor.getString(voteaverageint);
            String movieid = mCursor.getString(movieidint);
            String backdroppath = mCursor.getString(backdroppathint);
            String overview = mCursor.getString(overviewint);
            String releasedate = mCursor.getString(releasedateint);
//            // The Cursor is now set to the right position
            arr.add(new Movies(posterpath.replace(LINKPOSTER,""),liked.replaceAll("_"," "),Float.parseFloat(voteaverage),movieid,"",backdroppath.replace(LINKBACKDROP,""),overview,releasedate));
        }
        if(arr.isEmpty()) {
            EmptyView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
        else {
            EmptyView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        setMovieList(arr);
//if(isAdded()) {
//     mainActicty = (Activity)getContext();
//
//    recyclerView = mainActicty.findViewById(R.id.recyclerview_id);
//
//    Retrofit.Builder builder = new Retrofit.Builder()
//            .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create());
//    Retrofit retrofit = builder.build();
//    MovieDBClient client = retrofit.create(MovieDBClient.class);
//    Call<MovieList> call = client.MovieList();
//
//    call.enqueue(new Callback<MovieList>() {
//        @Override
//        public void onResponse(Call<MovieList> call, Response<MovieList> response) {
//            setMovieList(response.body().getMovieArrayList());
//        }
//
//        @Override
//        public void onFailure(Call<MovieList> call, Throwable t) {
//
//        }
//    });
//}
    }

    public void setMovieList(List<Movies> movie){
         adapter = new MoviesAdapter(mainActicty,movie);
        if(mainActicty.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(mainActicty, 2));
        }
        else{
            recyclerView.setLayoutManager(new GridLayoutManager(mainActicty, 3));
        }



        recyclerView.setAdapter(adapter);

    }
}
