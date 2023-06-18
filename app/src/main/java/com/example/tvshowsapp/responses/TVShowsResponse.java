package com.example.tvshowsapp.responses;

import com.example.tvshowsapp.models.TVShows;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TVShowsResponse implements Serializable {
    @SerializedName("page")
    private int page;

    @SerializedName("pages")
    private int totalPages;

    @SerializedName("tv_shows")
    private List<TVShows> tvShowsList;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<TVShows> getTvShowsList() {
        return tvShowsList;
    }

    public void setTvShowsList(List<TVShows> tvShowsList) {
        this.tvShowsList = tvShowsList;
    }
}
