package com.mystihgreeh.go4lunch.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.TextViewCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.mystihgreeh.go4lunch.BuildConfig;
import com.mystihgreeh.go4lunch.R;
import com.mystihgreeh.go4lunch.model.Restaurants.NearbySearchResponse;
import com.mystihgreeh.go4lunch.model.Restaurants.Result;
import com.mystihgreeh.go4lunch.model.Workmates.Workmate;
import com.mystihgreeh.go4lunch.repository.WorkmatesRepository;
import com.mystihgreeh.go4lunch.ui.RestaurantDetailActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import static com.mystihgreeh.go4lunch.api.GooglePlacesApi.BASE_URL_GOOGLE;
import static com.mystihgreeh.go4lunch.api.GooglePlacesApi.KEY_GOOGLE;
import static com.mystihgreeh.go4lunch.api.GooglePlacesApi.MAX_WIDTH_GOOGLE;
import static com.mystihgreeh.go4lunch.api.GooglePlacesApi.PHOTO_REF_GOOGLE;

/**
 * {@link RecyclerView.Adapter} that can display a {@link NearbySearchResponse}.
 * TODO: Replace the implementation with code for your data type.
 */
public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    private final ArrayList<Result> mValues;
    ArrayList<Workmate> fetchedWorkmates;
    ArrayList<String> workmateGoingInARestaurant;
    ArrayList<ArrayList<String>> workmateGoingInThatRestaurant = new ArrayList<>();
    public final MutableLiveData<ArrayList<String>> workmateId = new MutableLiveData<>();
    public final MutableLiveData<ArrayList<Workmate>> fetchedWorkmatesHere = new MutableLiveData<>();





    public RestaurantAdapter(ArrayList<Result> items, ArrayList<Workmate> workmates) {
        mValues = items;
        fetchedWorkmates = workmates;

    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_restaurants, parent, false);


        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        int counter = checkCoworkersEatingHere(mValues.get(position).getPlaceId(), fetchedWorkmates);
        holder.mItem = mValues.get(position);
        holder.mRestaurantName.setText(mValues.get(position).getName());
        holder.mContentView.setText(mValues.get(position).getVicinity());
        if(mValues.get(position).getRating() != null){
            holder.mRestaurantRating.setRating((mValues.get(position).getRating().floatValue()));
            holder.mRestaurantRating.setNumStars(3);
            } else holder.mRestaurantRating.setNumStars(0);
        if(mValues.get(position).getGeometry().getLocation().getLat() != null){
            holder.mDistance.setText(mValues.get(position).getDistance() + "m");
        } else holder.mDistance.setText("-");
        if (mValues.get(position).getOpeningHours() != null){
            holder.mOpeningHour.setText(mValues.get(position).getOpeningHours().getOpenNow() ? R.string.open : R.string.closed);
            if (mValues.get(position).getOpeningHours().getOpenNow()){
                TextViewCompat.setTextAppearance(holder.mOpeningHour,  R.style.TimeRestaurantOpen);
            } else TextViewCompat.setTextAppearance(holder.mOpeningHour,  R.style.TimeRestaurantClosed);
        }else {
            holder.mOpeningHour.setText(R.string.no_opening_hours);
            TextViewCompat.setTextAppearance(holder.mOpeningHour,  R.style.TimeRestaurantClosed);
        }
        holder.mWorkmateHere.setText("(" + counter + ")");


        if(mValues.get(position).getPhotos() != null){
            Glide.with(holder.mRestaurantPicture.getContext())
                    .load(getPhoto(mValues.get(position).getPhotos().get(0).getPhotoReference()))
                    .apply(RequestOptions.centerCropTransform())
                    .into(holder.mRestaurantPicture);
        } else
            Glide.with(holder.mRestaurantPicture.getContext())
                    .load(R.drawable.restaurantinside)
                    .apply(RequestOptions.centerCropTransform())
                    .into(holder.mRestaurantPicture);
        

        holder.mRestaurantConstraintLayout.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), RestaurantDetailActivity.class);
            intent.putExtra("restaurantId", mValues.get(position).getPlaceId());
            v.getContext().startActivity(intent);
        });

    }



    /**
     * Get the photo from Google
     */
    private String getPhoto(String photoReference) {
        return BASE_URL_GOOGLE + PHOTO_REF_GOOGLE + photoReference
                + MAX_WIDTH_GOOGLE + 400 + KEY_GOOGLE + BuildConfig.API_KEY;
    }


    @Override
    public int getItemCount() {
        return mValues.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mRestaurantPicture;
        public final TextView mContentView;
        public Result mItem;
        public final TextView mRestaurantName;
        public final TextView mOpeningHour;
        public RatingBar mRestaurantRating;
        public ConstraintLayout mRestaurantConstraintLayout;
        public TextView mDistance;
        public TextView mWorkmateHere;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            mRestaurantPicture = view.findViewById(R.id.restaurant_picture);
            mContentView = view.findViewById(R.id.restaurant_item_list_address);
            mRestaurantName = view.findViewById(R.id.restaurant_name);
            mOpeningHour = view.findViewById(R.id.restaurant_item_list_info);
            mRestaurantRating = view.findViewById(R.id.restaurantItemListRate);
            mRestaurantConstraintLayout = view.findViewById(R.id.restaurant_constraint_layout);
            mDistance = view.findViewById(R.id.restaurant_item_list_distance);
            mWorkmateHere = view.findViewById(R.id.restaurant_item_list_participants_number);
        }

        @NotNull
        @Override
        public String toString() {
            return super.toString() + " '" + mRestaurantName.getText() + "'";
        }
    }


    private int checkCoworkersEatingHere(String restaurantId, ArrayList<Workmate> workmatesIds) {
        int counter = 0;
            /*for (Workmate id: workmatesIds) {
                if (id.getRestaurantUid().equals(restaurantId)) {
                    counter++;
                }
            }*/
        System.out.println(workmatesIds);
        return counter;
    }


}