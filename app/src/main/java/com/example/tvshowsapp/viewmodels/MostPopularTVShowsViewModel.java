package com.example.tvshowsapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tvshowsapp.models.TVShows;
import com.example.tvshowsapp.repositories.MostPopularTVShowsRepository;
import com.example.tvshowsapp.responses.TVShowsResponse;

public class MostPopularTVShowsViewModel extends ViewModel {
    private MostPopularTVShowsRepository repo;

    public MostPopularTVShowsViewModel(){
        repo = new MostPopularTVShowsRepository();
    }

    public LiveData<TVShowsResponse> getMostPopularTVShows(int page){
        return repo.getMostPopularTVShows(page);
    }
}
