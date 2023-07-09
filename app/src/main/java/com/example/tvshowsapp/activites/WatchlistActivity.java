package com.example.tvshowsapp.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import com.example.tvshowsapp.R;
import com.example.tvshowsapp.adapters.WatchlistAdapter;
import com.example.tvshowsapp.databinding.ActivityWatchlistBinding;
import com.example.tvshowsapp.models.TVShows;
import com.example.tvshowsapp.viewmodels.WatchlistViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class WatchlistActivity extends AppCompatActivity {
    private ActivityWatchlistBinding binding;
    private WatchlistViewModel viewModel;

    private WatchlistAdapter adapter;
    private ArrayList<TVShows> watchlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_watchlist);
        doInitialization();
    }

    private void doInitialization(){
        binding.setWatchlistActivity(this);
        viewModel = new ViewModelProvider(this).get(WatchlistViewModel.class);
        watchlist = new ArrayList<>();
       // loadWatchlist();



    }

    private void loadWatchlist(){
        binding.setIsLoading(true);
        CompositeDisposable disposable = new CompositeDisposable();
        disposable.add(viewModel.loadWatchlist().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<TVShows>>() {
            @Override
            public void accept(List<TVShows> tvShows) throws Exception {
                binding.setIsLoading(false);

                if (watchlist.size() >0){
                    watchlist.clear();
                }
                watchlist.addAll(tvShows);
                adapter = new WatchlistAdapter(watchlist,WatchlistActivity.this,viewModel);
                binding.setWatchlistAdapter(adapter);

                disposable.dispose();


            }
        }));

    }

    public void backButton(){
        onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadWatchlist();
    }
}