package com.example.tvshowsapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvshowsapp.databinding.CardContainerEpisodeBinding;
import com.example.tvshowsapp.models.Episode;

import java.util.List;

public class EpisodesAdapter extends RecyclerView.Adapter<EpisodesAdapter.EpisodesViewHolder> {

    private List<Episode> episodeList;

    public EpisodesAdapter(List<Episode> episodeList) {
        this.episodeList = episodeList;
    }

    public class EpisodesViewHolder extends RecyclerView.ViewHolder {
        CardContainerEpisodeBinding binding;
        public EpisodesViewHolder(@NonNull CardContainerEpisodeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public EpisodesAdapter.EpisodesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardContainerEpisodeBinding binding = CardContainerEpisodeBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new EpisodesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodesAdapter.EpisodesViewHolder holder, int position) {
        Episode episode = episodeList.get(position);
        holder.binding.setEpisodes(episode);
    }

    @Override
    public int getItemCount() {
        return episodeList.size();
    }


}
