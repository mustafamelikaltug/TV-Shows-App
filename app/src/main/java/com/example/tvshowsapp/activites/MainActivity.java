package com.example.tvshowsapp.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.tvshowsapp.R;
import com.example.tvshowsapp.adapters.TVShowsAdapter;
import com.example.tvshowsapp.databinding.ActivityMainBinding;
import com.example.tvshowsapp.databinding.CardContainerTvShowBinding;
import com.example.tvshowsapp.models.TVShows;
import com.example.tvshowsapp.viewmodels.MostPopularTVShowsViewModel;
import com.example.tvshowsapp.viewmodels.TVShowDetailsViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private MostPopularTVShowsViewModel viewModel;
    private TVShowDetailsViewModel tvShowDetailsViewModel;
    private CardContainerTvShowBinding cardBinding;
    private ActivityMainBinding binding;
    private ArrayList<TVShows> tvShows;
    private TVShowsAdapter adapter;

    private int currentPage = 1;

    private int totalAvailablePages = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        doInitialization();
    }


    public void doInitialization(){
        binding.setMainActivity(this);

        viewModel = new ViewModelProvider(this).get(MostPopularTVShowsViewModel.class);

        getMostPopularTVShows();
        tvShows = new ArrayList<>();
        adapter = new TVShowsAdapter(tvShows,this);
        binding.setTvShowsAdapter(adapter);

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


            if (binding.getIsLoading() != null && binding.getIsLoading()){

                binding.setIsLoading(false);
            }else {

                binding.setIsLoading(true);
            }
        }else {

            if (binding.getIsLoadingMore() != null && binding.getIsLoadingMore()){

                binding.setIsLoadingMore(false);
            }else {

                binding.setIsLoadingMore(true);
            }
        }
    }

    public void scrolledVerticalInfinite(){
        if (!binding.recyclerViewTvShows.canScrollVertically(1)){
            if (currentPage<=totalAvailablePages){
                currentPage+=1;
                getMostPopularTVShows();
            }
        }
    }

}