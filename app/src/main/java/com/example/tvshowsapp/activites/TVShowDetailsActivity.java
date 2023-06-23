package com.example.tvshowsapp.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.tvshowsapp.R;
import com.example.tvshowsapp.adapters.ImageSliderAdapter;
import com.example.tvshowsapp.databinding.ActivityTvshowDetailsBinding;
import com.example.tvshowsapp.models.TVShows;
import com.example.tvshowsapp.viewmodels.TVShowDetailsViewModel;

public class TVShowDetailsActivity extends AppCompatActivity {

    private ActivityTvshowDetailsBinding binding;
    private TVShowDetailsViewModel viewModel;
    private TVShows tvShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_tvshow_details);
        doInitialization();
    }

    private void doInitialization(){
        binding.setTvShowDetailsActivity(this);
        viewModel = new ViewModelProvider(this).get(TVShowDetailsViewModel.class);
        getTVShowDetails();
    }


    private void getTVShowDetails(){
            tvShow = (TVShows) getIntent().getSerializableExtra("TVShow");
            binding.setTvShow(tvShow);
            Log.e("Id Detail",String.valueOf(tvShow.getId()));

        binding.setIsLoading(true);
        viewModel.getTVShowDetails(String.valueOf(tvShow.getId())).observe(this, tvShowDetailsResponse -> {
            binding.setIsLoading(false);
            if (tvShowDetailsResponse.getTvShowDetails() !=null){
                if (tvShowDetailsResponse.getTvShowDetails().getPictures() != null){
                    loadImages(tvShowDetailsResponse.getTvShowDetails().getPictures());
                }
                binding.setTvShowDetails(tvShowDetailsResponse.getTvShowDetails());
            }
        });
    }

    private void loadImages(String[] imageURLs){
        binding.viewPagerSliderImages.setOffscreenPageLimit(1);

        binding.setViewPagerAdapter(new ImageSliderAdapter(imageURLs,this));

        setupSliderIndicators(imageURLs.length);
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
        setCurrentSliderIndicator(0);
    }

    public void setCurrentSliderIndicator(int position){
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
    public void backButton(){
        onBackPressed();
    }

    public void readMore(){
        if (binding.textReadMore.getText().toString().equals("Read More")){
            binding.textDescription.setMaxLines(Integer.MAX_VALUE);
            binding.textDescription.setEllipsize(null);
            binding.textReadMore.setText(R.string.read_less);
        }else {
            binding.textDescription.setMaxLines(4);
            binding.textDescription.setEllipsize(TextUtils.TruncateAt.END);
            binding.textReadMore.setText(R.string.read_more);
        }
    }

    public void intentToGoWebsite(String websiteURL){
        Intent intentToGoWebsite = new Intent(Intent.ACTION_VIEW);
        intentToGoWebsite.setData(Uri.parse(websiteURL));
        startActivity(intentToGoWebsite);
    }
}