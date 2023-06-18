package com.example.tvshowsapp.network;

public class ApiUtils {
    public static final String BASE_URL = "https://www.episodate.com/api/";

    public static ApiService getApiServiceInterface(){
        return ApiClient.getRetrofit(BASE_URL).create(ApiService.class);
    }
}
