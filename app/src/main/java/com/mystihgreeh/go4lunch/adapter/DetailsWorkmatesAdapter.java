package com.mystihgreeh.go4lunch.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.mystihgreeh.go4lunch.R;
import com.mystihgreeh.go4lunch.databinding.ActivityRestaurantDetailBinding;
import com.mystihgreeh.go4lunch.model.Restaurants.Result;
import com.mystihgreeh.go4lunch.model.Workmates.Workmate;
import com.mystihgreeh.go4lunch.adapter.WorkmatesAdapter;
import com.mystihgreeh.go4lunch.ui.MainActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DetailsWorkmatesAdapter extends RecyclerView.Adapter<DetailsWorkmatesAdapter.ViewHolder> {

    private ArrayList<Workmate> mValues;

    public DetailsWorkmatesAdapter(ArrayList<Workmate> items) {
        mValues = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.workmates_joining, parent, false);
        return new DetailsWorkmatesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mWorkmateName.setText(mValues.get(position).getUsername());
        System.out.println(mValues.get(position).getUsername());

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mWorkmatePicture;
        //public final TextView mContentView;
        public Workmate mItem;
        public final TextView mWorkmateName;
        //public final TextView mOpeningHour;
        //public RatingBar mRestaurantRating;
        //public ConstraintLayout mRestaurantConstraintLayout;
        //public TextView mDistance;
        //public TextView mWorkmateHere;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            mWorkmatePicture = view.findViewById(R.id.workmate_picture);
            //mContentView = view.findViewById(R.id.restaurant_item_list_address);
            mWorkmateName = view.findViewById(R.id.workmate_name);
            //mOpeningHour = view.findViewById(R.id.restaurant_item_list_info);
            //mRestaurantRating = view.findViewById(R.id.restaurantItemListRate);
            //mRestaurantConstraintLayout = view.findViewById(R.id.restaurant_constraint_layout);
            //mDistance = view.findViewById(R.id.restaurant_item_list_distance);
            //mWorkmateHere = view.findViewById(R.id.restaurant_item_list_participants_number);
        }

        @NotNull
        @Override
        public String toString() {
            return super.toString() + " '" + mWorkmateName.getText() + "'";
        }
    }
}
