package com.example.tvshowsapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvshowsapp.databinding.ItemContainerSliderBinding;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder> {
    private String[] imageURLs ;
    private Context mContext;

    public ImageSliderAdapter(String[] imageURLs, Context mContext) {
        this.imageURLs = imageURLs;
        this.mContext = mContext;
    }

    public class ImageSliderViewHolder extends RecyclerView.ViewHolder {
        private ItemContainerSliderBinding binding;
        public ImageSliderViewHolder(ItemContainerSliderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


    @NonNull
    @Override
    public ImageSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContainerSliderBinding binding = ItemContainerSliderBinding.inflate(LayoutInflater.from(mContext),parent,false);
        return new ImageSliderViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSliderViewHolder holder, int position) {
        String imageUrl = imageURLs[position];
        holder.binding.setImageURL(imageUrl);
    }

    @Override
    public int getItemCount() {
        return imageURLs.length;
    }


}
