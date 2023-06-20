package com.example.tvshowsapp.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.tvshowsapp.adapters.TVShowsAdapter;
import com.example.tvshowsapp.databinding.ActivityMainBinding;
import com.example.tvshowsapp.models.TVShows;
import com.example.tvshowsapp.viewmodels.MostPopularTVShowsViewModel;
import com.example.tvshowsapp.viewmodels.TVShowDetailsViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private MostPopularTVShowsViewModel viewModel;
    private TVShowDetailsViewModel tvShowDetailsViewModel;
    private ActivityMainBinding binding;
    private ArrayList<TVShows> tvShows;
    private TVShowsAdapter adapter;

    private int currentPage = 1;

    private int totalAvailablePages = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        doInitialization();
    }


    public void doInitialization(){
        binding.recyclerViewTvShows.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this).get(MostPopularTVShowsViewModel.class);

        getMostPopularTVShows();
        tvShows = new ArrayList<>();
        adapter = new TVShowsAdapter(tvShows,this);
        binding.recyclerViewTvShows.setAdapter(adapter);
        binding.recyclerViewTvShows.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!binding.recyclerViewTvShows.canScrollVertically(1)){
                    if (currentPage<=totalAvailablePages){
                        currentPage+=1;
                        getMostPopularTVShows();
                    }
                }
            }
        });
        getMostPopularTVShows();
    }

    private void getMostPopularTVShows() {
        toggleIsLoading();
        viewModel.getMostPopularTVShows(currentPage).observe(this, mostPopularTVShowsResponse -> {
        toggleIsLoading();
        if (mostPopularTVShowsResponse != null){
            totalAvailablePages = mostPopularTVShowsResponse.getTotalPages();
            if (mostPopularTVShowsResponse.getTvShowsList() != null){
                int oldCount = tvShows.size();
                tvShows.addAll(mostPopularTVShowsResponse.getTvShowsList());
                adapter.notifyItemRangeInserted(oldCount,tvShows.size());
            }
        }
        });
    }

    private void toggleIsLoading(){
        if (currentPage == 1){
            System.out.println("if bloğuna girdi");

            if (binding.getIsLoading() != null && binding.getIsLoading()){
                System.out.println("if if bloğuna girdi");
                binding.setIsLoading(false);
            }else {
                System.out.println("if else bloğuna girdi");
                binding.setIsLoading(true);
            }
        }else {
            System.out.println("else bloğuna girdi");
            if (binding.getIsLoadingMore() != null && binding.getIsLoadingMore()){
                System.out.println("else if  bloğuna girdi");
                binding.setIsLoadingMore(false);
            }else {
                System.out.println("else else  bloğuna girdi");
                binding.setIsLoadingMore(true);
            }
        }
    }
}