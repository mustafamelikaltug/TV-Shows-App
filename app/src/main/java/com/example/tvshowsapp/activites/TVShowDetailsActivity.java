package com.example.tvshowsapp.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.tvshowsapp.R;
import com.example.tvshowsapp.adapters.ImageSliderAdapter;
import com.example.tvshowsapp.databinding.ActivityTvshowDetailsBinding;
import com.example.tvshowsapp.viewmodels.TVShowDetailsViewModel;

public class TVShowDetailsActivity extends AppCompatActivity {
    private ActivityTvshowDetailsBinding binding;
    private TVShowDetailsViewModel viewModel;
    private String tvShowId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTvshowDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        doInitialization();
    }

    private void doInitialization(){
        viewModel = new ViewModelProvider(this).get(TVShowDetailsViewModel.class);
        getTVShowDetails();
    }


    private void getTVShowDetails(){
        tvShowId = String.valueOf(getIntent().getIntExtra("TvShowId",0));
        binding.setIsLoading(true);
        viewModel.getTVShowDetails(tvShowId).observe(this, tvShowDetailsResponse -> {
            binding.setIsLoading(false);
            if (tvShowDetailsResponse.getTvShowDetails() !=null){
                if (tvShowDetailsResponse.getTvShowDetails().getPictures() != null){
                    loadImages(tvShowDetailsResponse.getTvShowDetails().getPictures());
                }
            }
        });
    }

    private void loadImages(String[] imageURLs){
        binding.viewPagerSliderImages.setVisibility(View.VISIBLE);
        binding.viewFadingEdge.setVisibility(View.VISIBLE);
        binding.viewPagerSliderImages.setOffscreenPageLimit(1);
        binding.viewPagerSliderImages.setAdapter(new ImageSliderAdapter(imageURLs,this));

        setupSliderIndicators(imageURLs.length);
        binding.viewPagerSliderImages.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentSliderIndicator(position);
            }
        });

    }

    private void setupSliderIndicators(int count){
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(8,0,8,0);
        for (int i=0;i<indicators.length;i++){
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.background_slider_indicator_inactive));
            indicators[i].setLayoutParams(layoutParams);
            binding.layoutSliderIndicators.addView(indicators[i]);
        }
        binding.layoutSliderIndicators.setVisibility(View.VISIBLE);
        setCurrentSliderIndicator(0);
    }

    private void setCurrentSliderIndicator(int position){
        int childCount = binding.layoutSliderIndicators.getChildCount();
        for (int i =0 ; i<childCount; i++){
            ImageView imageView = (ImageView) binding.layoutSliderIndicators.getChildAt(i);
            if (i == position){
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.background_slider_indicator_acitve));
            }else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.background_slider_indicator_inactive));
            }
        }
    }
}