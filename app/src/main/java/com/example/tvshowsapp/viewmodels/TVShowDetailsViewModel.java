package com.example.tvshowsapp.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.tvshowsapp.database.TVShowDatabase;
import com.example.tvshowsapp.models.TVShows;
import com.example.tvshowsapp.repositories.TVShowDetailsRepository;
import com.example.tvshowsapp.responses.TVShowDetailsResponse;

import io.reactivex.Completable;
import io.reactivex.Single;

public class TVShowDetailsViewModel extends AndroidViewModel {

    private TVShowDetailsRepository repo;
    private TVShowDatabase tvShowDatabase;
    public TVShowDetailsViewModel(Application application){
        super(application);
        repo = new TVShowDetailsRepository();
        tvShowDatabase = TVShowDatabase.getTvShowDatabase(application);
    }

    public LiveData<TVShowDetailsResponse> getTVShowDetails(String tvShowId){
        return repo.getTVShowDetails(tvShowId);
    }


    public Completable addToWatchList(TVShows tvShows){
        return tvShowDatabase.tvShowDao().addToWatchList(tvShows);
    }

    public Single getTVShowFromWatchlist(int tvShowId){
        return tvShowDatabase.tvShowDao().getTVShowFromWatchlist(tvShowId);
    }

    public Completable removeTVShowFromWatchlist(TVShows tvShow){
        return tvShowDatabase.tvShowDao().removeFromWatchList(tvShow);
    }
}
