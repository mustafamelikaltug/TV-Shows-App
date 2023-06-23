package com.example.tvshowsapp.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tvshowsapp.network.ApiService;
import com.example.tvshowsapp.network.ApiUtils;
import com.example.tvshowsapp.responses.TVShowDetailsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVShowDetailsRepository {
    private ApiService apiService;
    private MutableLiveData<TVShowDetailsResponse> tvShowDetailsResponseData;

    public TVShowDetailsRepository() {
        apiService = ApiUtils.getApiServiceInterface();
    }

    public LiveData<TVShowDetailsResponse> getTVShowDetails (String TVShowId){
        tvShowDetailsResponseData = new MutableLiveData<>();
        apiService.getTVShowDetails(TVShowId).enqueue(new Callback<TVShowDetailsResponse>() {
            @Override
            public void onResponse(Call<TVShowDetailsResponse> call, Response<TVShowDetailsResponse> response) {
                tvShowDetailsResponseData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<TVShowDetailsResponse> call, Throwable t) {
                tvShowDetailsResponseData.setValue(null);

            }
        });
    return tvShowDetailsResponseData;
    }
}
