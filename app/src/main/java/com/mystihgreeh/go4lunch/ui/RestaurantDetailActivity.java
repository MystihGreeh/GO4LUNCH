package com.mystihgreeh.go4lunch.ui;

import static com.mystihgreeh.go4lunch.api.GooglePlacesApi.BASE_URL_GOOGLE;
import static com.mystihgreeh.go4lunch.api.GooglePlacesApi.KEY_GOOGLE;
import static com.mystihgreeh.go4lunch.api.GooglePlacesApi.MAX_WIDTH_GOOGLE;
import static com.mystihgreeh.go4lunch.api.GooglePlacesApi.PHOTO_REF_GOOGLE;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mystihgreeh.go4lunch.BuildConfig;
import com.mystihgreeh.go4lunch.R;
import com.mystihgreeh.go4lunch.adapter.DetailsWorkmatesAdapter;
import com.mystihgreeh.go4lunch.api.Injection;
import com.mystihgreeh.go4lunch.databinding.ActivityRestaurantDetailBinding;
import com.mystihgreeh.go4lunch.model.RestaurantsDetails.DetailsResult;
import com.mystihgreeh.go4lunch.viewModel.RestaurantDetailsViewModel;
import com.mystihgreeh.go4lunch.viewModel.ViewModelFactory;

import java.util.ArrayList;

public class RestaurantDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private RestaurantDetailsViewModel restaurantDetailsViewModel;
    ActivityRestaurantDetailBinding binding;
    String restaurantId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAll();
    }


    private void initAll(){
        initView();
        initViewModel();
        getPlaceId();
    }

    private void initView() {
        binding = ActivityRestaurantDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    private void displayInfoRestaurant(DetailsResult restaurantDetailsResult) {
        //PHOTO
        if(restaurantDetailsResult.getPhotos() != null){
            Glide.with(binding.restaurantPicture.getContext())
                    .load(getPhoto(restaurantDetailsResult.getPhotos().get(0).getPhotoReference()))
                    .into(binding.restaurantPicture);
        } else  Glide.with(binding.restaurantPicture.getContext())
                .load(R.drawable.restaurantinside)
                .apply(RequestOptions.centerCropTransform())
                .into(binding.restaurantPicture);
        //NAME
        if (restaurantDetailsResult.getName() != null){
            binding.restaurantName.setText(restaurantDetailsResult.getName());
        } else binding.restaurantName.setText(R.string.no_name);
        //ADDRESS
        if (restaurantDetailsResult.getFormattedAddress() != null){
            binding.restaurantAddress.setText(restaurantDetailsResult.getFormattedAddress());
        } else binding.restaurantAddress.setText(R.string.no_address);
        //PHONE NUMBER
        if (restaurantDetailsResult.getFormattedPhoneNumber() != null){
            binding.call.setOnClickListener(v -> openDialer(restaurantDetailsResult.getFormattedPhoneNumber()));
        } else binding.call.setText(R.string.no_phone_number);
        //WEBSITE
        if (restaurantDetailsResult.getWebsite() != null){
            binding.website.setOnClickListener(v -> openWebSite(restaurantDetailsResult.getWebsite()));
        } else binding.website.setText(R.string.no_website);
        //RATING
        if(restaurantDetailsResult.getRating() != null){
            binding.restaurantRate.setRating((restaurantDetailsResult.getRating().floatValue()));
            binding.restaurantRate.setNumStars(3);
        } else binding.restaurantRate.setNumStars(0);
        //LIKE AND CHOOSE
        binding.likeImg.setOnClickListener(view ->
                restaurantDetailsViewModel.updateRestaurantLiked(restaurantDetailsResult));
                //restaurantDetailsViewModel.getLikedRestaurant(restaurantDetailsViewModel.getUserId(), restaurantDetailsResult.getPlaceId());

        binding.favoriteButton.setOnClickListener(v ->
                restaurantDetailsViewModel.updatePickedRestaurant(restaurantDetailsResult));

    }

    private void getPlaceId(){
            restaurantId = getIntent().getStringExtra("restaurantId");
            getRestaurantDetails(restaurantId);

    }

    private void getRestaurantDetails(String restaurantId){
        restaurantDetailsViewModel.fetchRestaurantsDetails(restaurantId);
        restaurantDetailsViewModel.getRestaurantDetailsMutableLiveData(restaurantId).observe(this,this::displayInfoRestaurant);
        restaurantDetailsViewModel.fetchInfoRestaurant(restaurantId);
        initRecyclerView(restaurantId);
        initButton(restaurantId);

    }


    private void initViewModel() {
        ViewModelFactory viewModelFactory = Injection.viewModelFactory();
        restaurantDetailsViewModel = ViewModelProviders.of(this, viewModelFactory).get(RestaurantDetailsViewModel.class);
        restaurantDetailsViewModel.fetchWorkmateIsGoing();

    }

    private void initRecyclerView(String restaurantId){
        //Setting the adapter
        RecyclerView mRecyclerView = binding.workmateJoiningRecyclerView;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        restaurantDetailsViewModel.fetchWorkmateEatingThere(restaurantId).observe(this,
                workmates -> mRecyclerView.setAdapter(new DetailsWorkmatesAdapter(new ArrayList<>(workmates), this)));
    }


    private void initButton(String restaurantId){
        restaurantDetailsViewModel.getLikedRestaurant(restaurantDetailsViewModel.getUserId(), restaurantId).observe(this, this::changeLikeStatus);
        restaurantDetailsViewModel.isRestaurantPicked.observe(this, this::changePickedStatus);

    }

    private void changeLikeStatus(Boolean isLiked){
        int likedImg = (isLiked) ? R.drawable.ic_baseline_star_24 : R.drawable.ic_baseline_star_border_24;
        Drawable drawable = ContextCompat.getDrawable(this, likedImg);
        binding.likeImg.setImageDrawable(drawable);
    }

    private void changePickedStatus(Boolean isPicked){
        int pickedImg = (isPicked) ? R.drawable.ic_baseline_check_circle_24 : R.drawable.ic_baseline_check_circle_outline_24;
        int checkColor = (isPicked) ? Color.GREEN : Color.GRAY;
        Drawable drawable = ContextCompat.getDrawable(this, pickedImg);
        drawable.mutate().setColorFilter(checkColor, PorterDuff.Mode.MULTIPLY);
        binding.favoriteButton.setImageDrawable(drawable);
        restaurantDetailsViewModel.clearRestaurantId();
        restaurantDetailsViewModel.getRestaurantId(restaurantDetailsViewModel.getUserId());
    }

    /**
     * Open the website
     *
     * @param webSite : string : url to open
     */
    private void openWebSite(String webSite) {
        if (webSite != null) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(webSite));
            startActivity(intent);
        } else {
            Toast.makeText(RestaurantDetailActivity.this, getString(R.string.no_website), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Open the dialer
     *
     * @param phone : string : phone number to display
     */
    private void openDialer(String phone) {
        if ((phone != null) && (phone.trim().length() > 0)) {
            Intent lIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(phone)));
            startActivity(lIntent);
        } else {
            Toast.makeText(RestaurantDetailActivity.this, getString(R.string.no_phone_number), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Get the photo from Google
     */
    private String getPhoto(String photoReference) {
        return BASE_URL_GOOGLE + PHOTO_REF_GOOGLE + photoReference
                + MAX_WIDTH_GOOGLE + 400 + KEY_GOOGLE + BuildConfig.API_KEY;
    }


    @Override
    public void onClick(View view) {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getSupportFragmentManager().beginTransaction().add(android.R.id.content, new MapViewFragment()).commit();
    }



}