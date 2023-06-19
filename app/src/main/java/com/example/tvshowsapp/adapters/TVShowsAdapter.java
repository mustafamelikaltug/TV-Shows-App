package com.example.tvshowsapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvshowsapp.databinding.CardContainerTvShowBinding;
import com.example.tvshowsapp.models.TVShows;

import java.util.ArrayList;

public class TVShowsAdapter extends RecyclerView.Adapter<TVShowsAdapter.TVShowsViewHolder> {
    private ArrayList<TVShows> showsArrayList;
    private Context mContext;


    public TVShowsAdapter(ArrayList<TVShows> showsArrayList, Context mContext) {
        this.showsArrayList = showsArrayList;
        this.mContext = mContext;
    }

    public class TVShowsViewHolder extends RecyclerView.ViewHolder {
        CardContainerTvShowBinding binding;
        public TVShowsViewHolder(@NonNull CardContainerTvShowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindTVShow(TVShows tvShows){
            binding.setTvShow(tvShows);
            binding.executePendingBindings();

        }
    }



    @NonNull
    @Override
    public TVShowsAdapter.TVShowsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardContainerTvShowBinding binding = CardContainerTvShowBinding.inflate(LayoutInflater.from(mContext),parent,false);
        return new TVShowsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TVShowsAdapter.TVShowsViewHolder holder, int position) {
        holder.bindTVShow(showsArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return showsArrayList.size();
    }


}