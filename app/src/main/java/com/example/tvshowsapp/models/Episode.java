package com.example.tvshowsapp.models;

import com.google.gson.annotations.SerializedName;

public class Episode {
    @SerializedName("season")
    private String season;

    @SerializedName("episode")
    private String episode;

    @SerializedName("name")
    private String episodeName;

    @SerializedName("air_date")
    private String airDate;

    public String getSeason() {
        return season;
    }

    public String getEpisode() {
        return episode;
    }

    public String getEpisodeName() {
        return episodeName;
    }

    public String getAirDate() {
        return airDate;
    }
}
