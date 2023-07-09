package com.example.tvshowsapp.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.tvshowsapp.R;
import com.example.tvshowsapp.adapters.EpisodesAdapter;
import com.example.tvshowsapp.adapters.ImageSliderAdapter;
import com.example.tvshowsapp.databinding.ActivityTvshowDetailsBinding;
import com.example.tvshowsapp.databinding.LayoutEpisodesBottomSheetBinding;
import com.example.tvshowsapp.models.Episode;
import com.example.tvshowsapp.models.TVShows;
import com.example.tvshowsapp.viewmodels.TVShowDetailsViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class TVShowDetailsActivity extends AppCompatActivity {

    private ActivityTvshowDetailsBinding binding;
    private TVShowDetailsViewModel viewModel;
    private TVShows tvShow;

    private BottomSheetDialog episodesBottomSheetDialog;

    private LayoutEpisodesBottomSheetBinding episodesBinding;

    private List<Episode> episodeList;

    private Animation watchlistAnimation;
    private AnimationDrawable watchListDrawableAnimation;

    private Boolean isExistInWatchList = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tvshow_details);

        doInitialization();


    }

    private void doInitialization() {
        binding.setTvShowDetailsActivity(this);
        viewModel = new ViewModelProvider(this).get(TVShowDetailsViewModel.class);
        getTVShowDetails();
        checkTVShowExistenceInWatchList();

        watchlistAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_add_watchlist);

    }

    public void checkTVShowExistenceInWatchList() {
        CompositeDisposable disposable = new CompositeDisposable();
        disposable.add(viewModel.getTVShowFromWatchlist(tvShow.getId()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                tvShow ->{
                        isExistInWatchList = true;
                        binding.imageWatchlist.setBackgroundResource(R.drawable.remove_watch_list_animation_list);
                        watchListDrawableAnimation = (AnimationDrawable) binding.imageWatchlist.getBackground();
                },
                throwable -> {
                    isExistInWatchList = false;
                    binding.imageWatchlist.setBackgroundResource(R.drawable.add_watch_list_animation_list);
                    watchListDrawableAnimation = (AnimationDrawable) binding.imageWatchlist.getBackground();
                }
        ));
    }


    private void getTVShowDetails() {
        tvShow = (TVShows) getIntent().getSerializableExtra("TVShow");
        binding.setTvShow(tvShow);

        binding.setIsLoading(true);
        viewModel.getTVShowDetails(String.valueOf(tvShow.getId())).observe(this, tvShowDetailsResponse -> {
            binding.setIsLoading(false);
            if (tvShowDetailsResponse.getTvShowDetails() != null) {
                if (tvShowDetailsResponse.getTvShowDetails().getPictures() != null) {
                    loadImages(tvShowDetailsResponse.getTvShowDetails().getPictures());
                    episodeList = tvShowDetailsResponse.getTvShowDetails().getEpisodes();
                }
                binding.setTvShowDetails(tvShowDetailsResponse.getTvShowDetails());
            }
        });
    }

    private void loadImages(String[] imageURLs) {
        binding.viewPagerSliderImages.setOffscreenPageLimit(1);

        binding.setViewPagerAdapter(new ImageSliderAdapter(imageURLs, this));

        setupSliderIndicators(imageURLs.length);
    }

    private void setupSliderIndicators(int count) {
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(8, 0, 8, 0);
        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_slider_indicator_inactive));
            indicators[i].setLayoutParams(layoutParams);
            binding.layoutSliderIndicators.addView(indicators[i]);
        }
        setCurrentSliderIndicator(0);
    }

    public void setCurrentSliderIndicator(int position) {
        int childCount = binding.layoutSliderIndicators.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) binding.layoutSliderIndicators.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_slider_indicator_acitve));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_slider_indicator_inactive));
            }
        }
    }

    public void backButton() {
        onBackPressed();
    }

    public void readMore() {
        if (binding.textReadMore.getText().toString().equals("Read More")) {
            binding.textDescription.setMaxLines(Integer.MAX_VALUE);
            binding.textDescription.setEllipsize(null);
            binding.textReadMore.setText(R.string.read_less);
        } else {
            binding.textDescription.setMaxLines(4);
            binding.textDescription.setEllipsize(TextUtils.TruncateAt.END);
            binding.textReadMore.setText(R.string.read_more);
        }
    }

    public void intentToGoWebsite(String websiteURL) {
        Intent intentToGoWebsite = new Intent(Intent.ACTION_VIEW);
        intentToGoWebsite.setData(Uri.parse(websiteURL));
        startActivity(intentToGoWebsite);
    }

    public void showEpisodes() {
        episodesBinding = DataBindingUtil.inflate(LayoutInflater.from(TVShowDetailsActivity.this)
                , R.layout.layout_episodes_bottom_sheet
                , findViewById(R.id.linearLayoutEpisodesContainer), false);
        episodesBinding.setTvShowDetailsActivity(TVShowDetailsActivity.this);
        episodesBinding.setTvShow(tvShow);
        episodesBottomSheetDialog = new BottomSheetDialog(TVShowDetailsActivity.this);
        episodesBottomSheetDialog.setContentView(episodesBinding.getRoot());
        episodesBinding.setEpisodesAdapter(new EpisodesAdapter(episodeList));

        episodesBottomSheetDialog.show();
    }


    public void closeButton() {
        episodesBottomSheetDialog.dismiss();
    }


    public void addToWatchList() {
       CompositeDisposable disposable= new CompositeDisposable();
        if (isExistInWatchList) {
            disposable.add(viewModel.removeTVShowFromWatchlist(tvShow).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                    ()->{
                        binding.imageWatchlist.startAnimation(watchlistAnimation);
                        watchListDrawableAnimation.start();
                        Toast.makeText(this, "Removed from watchlist", Toast.LENGTH_SHORT).show();
                        checkTVShowExistenceInWatchList();
                        disposable.dispose();
                    },throwable -> {
                        Toast.makeText(this, "An error occurred during removing from the watchlist. Error: " + throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    disposable.dispose();
                    }
            ));
        } else {
           disposable.add(viewModel.addToWatchList(tvShow).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                    ()->{
                binding.imageWatchlist.startAnimation(watchlistAnimation);
                watchListDrawableAnimation.start();
                Toast.makeText(this, "Added to watchlist", Toast.LENGTH_SHORT).show();
                        checkTVShowExistenceInWatchList();
                        disposable.dispose();
                    },throwable -> {
                        Toast.makeText(this, "An error occurred during adding to the watchlist. Error: " + throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        disposable.dispose();
                    }

            ));
        }

    }


}