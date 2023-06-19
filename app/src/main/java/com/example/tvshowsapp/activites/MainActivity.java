package com.example.tvshowsapp.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import com.example.tvshowsapp.R;
import com.example.tvshowsapp.adapters.TVShowsAdapter;
import com.example.tvshowsapp.databinding.ActivityMainBinding;
import com.example.tvshowsapp.models.TVShows;
import com.example.tvshowsapp.viewmodels.MostPopularTVShowsViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MostPopularTVShowsViewModel viewModel;
    private ActivityMainBinding binding;
    private ArrayList<TVShows> tvShows;
    private TVShowsAdapter adapter;

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
        getMostPopularTVShows();
    }

    private void getMostPopularTVShows() {
        binding.setIsLoading(true);
        viewModel.getMostPopularTVShows(1).observe(this, mostPopularTVShowsResponse -> {
        binding.setIsLoading(false);
        if (mostPopularTVShowsResponse != null){
            if (mostPopularTVShowsResponse.getTvShowsList() != null){
                tvShows.addAll(mostPopularTVShowsResponse.getTvShowsList());
                adapter.notifyDataSetChanged();
            }
        }
        });
    }
}