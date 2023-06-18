package com.example.tvshowsapp.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import com.example.tvshowsapp.R;
import com.example.tvshowsapp.viewmodels.MostPopularTVShowsViewModel;

public class MainActivity extends AppCompatActivity {
    MostPopularTVShowsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MostPopularTVShowsViewModel.class);
        getMostPopularTVShows();
    }

    private void getMostPopularTVShows() {
        viewModel.getMostPopularTVShows(1).observe(this, mostPopularTVShowsResponse -> {
            Toast.makeText(this, "Total Pages: " + mostPopularTVShowsResponse.getTotalPages(), Toast.LENGTH_SHORT).show();
        });
    }
}