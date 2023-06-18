package com.example.tvshowsapp.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tvshowsapp.models.TVShows;
import com.example.tvshowsapp.network.ApiClient;
import com.example.tvshowsapp.network.ApiService;
import com.example.tvshowsapp.network.ApiUtils;
import com.example.tvshowsapp.responses.TVShowsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MostPopularTVShowsRepository {
    private ApiService apiService;
    private MutableLiveData<TVShowsResponse> tvShowsResponse;

    public MostPopularTVShowsRepository() {
       apiService = ApiUtils.getApiServiceInterface();
    }

    public LiveData<TVShowsResponse> getMostPopularTVShows(int page){
        tvShowsResponse = new MutableLiveData<>();
        apiService.getMostPopularTVShows(page).enqueue(new Callback<TVShowsResponse>() {
        @Override
        public void onResponse(Call<TVShowsResponse> call, Response<TVShowsResponse> response) {
            tvShowsResponse.setValue(response.body());
        }

        @Override
        public void onFailure(Call<TVShowsResponse> call, Throwable t) {
            tvShowsResponse.setValue(null);
        }
    });
    return tvShowsResponse;
    }
}

