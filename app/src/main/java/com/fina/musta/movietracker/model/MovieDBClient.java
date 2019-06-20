package com.fina.musta.movietracker.model;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by musta on 12/28/2017.
 */

public interface MovieDBClient {
    @GET("https://api.themoviedb.org/3/movie/popular?api_key=edeaa9e42b99b36a52d8dc696656fecf&language=en-US&")
    Call<MovieList> MovieList(@Query("page") int pageno);
    @GET("/3/movie/top_rated?api_key=edeaa9e42b99b36a52d8dc696656fecf&language=en-US&page=1")
    Call<MovieList> TopRatedList();
    @GET("/3/movie/{movie_id}/videos?api_key=edeaa9e42b99b36a52d8dc696656fecf&language=en-US")
    Call<MovieList> getYouTubeTrailer(@Path("movie_id") String movie);
    @GET("/3/search/movie?api_key=edeaa9e42b99b36a52d8dc696656fecf")
    Call<MovieList> getSearchResult(@Query("query")String movieName);
}
