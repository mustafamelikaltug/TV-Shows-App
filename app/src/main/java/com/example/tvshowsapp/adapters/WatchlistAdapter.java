package com.example.tvshowsapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvshowsapp.activites.TVShowDetailsActivity;
import com.example.tvshowsapp.databinding.CardContainerTvShowBinding;
import com.example.tvshowsapp.models.TVShows;
import com.example.tvshowsapp.viewmodels.WatchlistViewModel;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WatchlistAdapter extends RecyclerView.Adapter<WatchlistAdapter.TVShowsViewHolder> {
    private ArrayList<TVShows> showsArrayList;
    private Context mContext;
    private WatchlistViewModel viewModel;


    public WatchlistAdapter(ArrayList<TVShows> showsArrayList, Context mContext, WatchlistViewModel viewModel) {
        this.showsArrayList = showsArrayList;
        this.mContext = mContext;
        this.viewModel = viewModel;
    }

    public class TVShowsViewHolder extends RecyclerView.ViewHolder {
        CardContainerTvShowBinding binding;

        public TVShowsViewHolder(@NonNull CardContainerTvShowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

    }


    @NonNull
    @Override
    public WatchlistAdapter.TVShowsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardContainerTvShowBinding binding = CardContainerTvShowBinding.inflate(LayoutInflater.from(mContext), parent, false);
        return new TVShowsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TVShowsViewHolder holder, int position) {
        TVShows tvShow = showsArrayList.get(position);
        holder.binding.setTvShow(tvShow);
        holder.binding.executePendingBindings();
        holder.binding.setWatchlistAdapter(WatchlistAdapter.this);
        holder.binding.imageDelete.setVisibility(View.VISIBLE);


    }

    @Override
    public int getItemCount() {
        return showsArrayList.size();
    }


    public void deleteTVShow(TVShows tvShow) {
        CompositeDisposable disposable = new CompositeDisposable();
        disposable.add(viewModel.removeTVShowFromWatchlist(tvShow).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(() -> {
            showsArrayList.remove(tvShow);
            notifyDataSetChanged();
            Toast.makeText(mContext, tvShow.getName() + " removed from watchlist", Toast.LENGTH_SHORT).show();
                }, throwable -> {
                    Toast.makeText(mContext, "An error occurred during delete process. Error :" + throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
        ));
    }

}


