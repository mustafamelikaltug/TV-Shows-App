package com.example.tvshowsapp.network;

import com.example.tvshowsapp.models.TVShows;
import com.example.tvshowsapp.responses.TVShowDetailsResponse;
import com.example.tvshowsapp.responses.TVShowsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("most-popular")
    Call<TVShowsResponse> getMostPopularTVShows(@Query("page") int page);


    @GET("show-details")
    Call<TVShowDetailsResponse> getTVShowDetails(@Query("q") String tvShowId);

    @GET("search")
    Call<TVShowsResponse> getQueriedTVShow(@Query("q")String query,@Query("page") int page);
}
