package com.fina.musta.movietracker.model;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by musta on 12/28/2017.
 */

public class MovieList {
    @SerializedName("results")
    private ArrayList<Movies> movieList;
    public ArrayList<Movies> getMovieArrayList() {
        return movieList;
    }
    public void setMovieArrayList(ArrayList<Movies> movieList) {
        this.movieList = movieList;
    }


    }
