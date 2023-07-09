package com.example.tvshowsapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.tvshowsapp.database.TVShowDatabase;
import com.example.tvshowsapp.models.TVShows;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class WatchlistViewModel extends AndroidViewModel {
    private TVShowDatabase database;

    public WatchlistViewModel(@NonNull Application application) {
        super(application);
        database = TVShowDatabase.getTvShowDatabase(application);
    }

    public Flowable<List<TVShows>> loadWatchlist(){
        return database.tvShowDao().getWatchList();
    }

    public Completable removeTVShowFromWatchlist(TVShows tvShow){
        return database.tvShowDao().removeFromWatchList(tvShow);
    }
}
