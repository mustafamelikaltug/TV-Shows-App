package com.example.tvshowsapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.tvshowsapp.models.TVShows;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface ITVShowDao {
    @Query("Select * from tvShows")
    Flowable<List<TVShows>> getWatchList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addToWatchList(TVShows tvShow);

    @Delete
    Completable removeFromWatchList(TVShows tvShow);


}
