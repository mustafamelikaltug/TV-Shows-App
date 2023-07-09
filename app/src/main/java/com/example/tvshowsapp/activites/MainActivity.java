package com.example.tvshowsapp.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.tvshowsapp.R;
import com.example.tvshowsapp.adapters.TVShowsAdapter;
import com.example.tvshowsapp.databinding.ActivityMainBinding;
import com.example.tvshowsapp.databinding.CardContainerTvShowBinding;
import com.example.tvshowsapp.models.TVShows;
import com.example.tvshowsapp.responses.TVShowsResponse;
import com.example.tvshowsapp.viewmodels.MostPopularTVShowsViewModel;
import com.example.tvshowsapp.viewmodels.TVShowDetailsViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private MostPopularTVShowsViewModel viewModel;

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
        setSupportActionBar(binding.toolbar);
        binding.setMainActivity(this);

        viewModel = new ViewModelProvider(this).get(MostPopularTVShowsViewModel.class);

        getMostPopularTVShows();
        tvShows = new ArrayList<>();
        adapter = new TVShowsAdapter(tvShows,this);
        binding.setTvShowsAdapter(adapter);

        getMostPopularTVShows();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);

        MenuItem itemSearch = menu.findItem(R.id.action_search);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) itemSearch.getActionView();
        searchView.setOnQueryTextListener(this);

        searchView.setQueryHint("Search TV Show");



        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {


        viewModel.searchTVShows(query,currentPage).observe(this, new Observer<TVShowsResponse>() {
            @Override
            public void onChanged(TVShowsResponse tvShowsResponse) {
                if (tvShowsResponse != null){
                    tvShows.clear();
                    adapter.notifyDataSetChanged();
                    totalAvailablePages = tvShowsResponse.getTotalPages();
                    if (tvShowsResponse.getTvShowsList() != null){
                        int oldCount = tvShows.size();
                        tvShows.addAll(tvShowsResponse.getTvShowsList());
                        adapter.notifyItemRangeInserted(oldCount,tvShows.size());
                        binding.setIsLoading(false);
                        binding.setIsLoadingMore(false);
                    }
                }
            }
        });


        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {


        viewModel.searchTVShows(newText,currentPage).observe(this, new Observer<TVShowsResponse>() {
            @Override
            public void onChanged(TVShowsResponse tvShowsResponse) {
                if (tvShowsResponse != null){
                    tvShows.clear();
                    adapter.notifyDataSetChanged();
                    totalAvailablePages = tvShowsResponse.getTotalPages();
                    if (tvShowsResponse.getTvShowsList() != null){
                        int oldCount = tvShows.size();
                        tvShows.addAll(tvShowsResponse.getTvShowsList());
                        adapter.notifyItemRangeInserted(oldCount,tvShows.size());
                        binding.setIsLoading(false);
                        binding.setIsLoadingMore(false);
                    }
                }
            }
        });

        return true;

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_watchlist){
            goToWatchlistActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);

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

    public void goToWatchlistActivity(){
        Intent intentToGoWatchlist = new Intent(MainActivity.this, WatchlistActivity.class);
        intentToGoWatchlist.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intentToGoWatchlist);
    }

}