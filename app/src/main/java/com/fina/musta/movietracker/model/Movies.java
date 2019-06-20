package com.fina.musta.movietracker.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by musta on 12/28/2017.
 */

public class Movies {
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("vote_average")
    private float voteAverage;
    @SerializedName("id")
    private String id;
    @SerializedName("key")
    private String key;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("overview")
    private String overview;
    @SerializedName("release_date")
    private String releaseDate;

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public Movies(String posterPath, String originalTitle, float voteAverage, String id, String key, String backdropPath, String overview, String releaseDate) {
        this.posterPath = posterPath;
        this.originalTitle = originalTitle;
        this.voteAverage = voteAverage;
        this.id = id;
        this.backdropPath = backdropPath;

        this.key = key;

        this.overview = overview;

        this.releaseDate = releaseDate;

    }
    public float getVoteAverage(){
        return voteAverage;
    }
    public String getPosterPath() {

        return "https://image.tmdb.org/t/p/w154"+posterPath;
    }
    public String getBackdropPath(){return "https://image.tmdb.org/t/p/w500"+backdropPath;}

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }
}
