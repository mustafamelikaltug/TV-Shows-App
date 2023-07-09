package com.example.tvshowsapp.utilities;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.tvshowsapp.activites.MainActivity;
import com.example.tvshowsapp.activites.TVShowDetailsActivity;
import com.example.tvshowsapp.activites.WatchlistActivity;
import com.example.tvshowsapp.adapters.WatchlistAdapter;
import com.example.tvshowsapp.models.TVShows;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class BindingAdapters {
    @BindingAdapter("android:imageURL")
    public static void setImageURL(ImageView imageView, String URL) {
        try {
            imageView.setAlpha(0f);
            Picasso.get().load(URL).noFade().into(imageView, new Callback() {
                @Override
                public void onSuccess() {
                    imageView.animate().setDuration(300).alpha(1f).start();
                }

                @Override
                public void onError(Exception e) {

                }
            });
        } catch (Exception ignored) {

        }
    }

    @BindingAdapter("android:onScrollListener")
    public static void onScrollListener(RecyclerView recyclerView, MainActivity mainActivity) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mainActivity.scrolledVerticalInfinite();
            }
        });
    }

    @BindingAdapter("android:onPageChange")
    public static void onPageChangedListener(ViewPager2 viewPager, TVShowDetailsActivity activity) {
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                activity.setCurrentSliderIndicator(position);
            }
        });
    }


    @BindingAdapter("android:checkVisibility")
    public static void checkVisibilty(View view, View viewCheck) {

        if (viewCheck.getVisibility() == View.INVISIBLE || viewCheck.getVisibility() == View.GONE) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    @BindingAdapter("android:additionalOnClickForIntent")
    public static void additionalOnClick(CardView cardView, TVShows tvShow) {
        cardView.setOnClickListener(v -> {
            Intent intent = new Intent(cardView.getContext(), TVShowDetailsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("TVShow",tvShow);
            cardView.getContext().startActivity(intent);
        });
    }
}



