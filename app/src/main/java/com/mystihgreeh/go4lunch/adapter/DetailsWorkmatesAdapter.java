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

    private final ArrayList<Workmate> mValues;
    private final Context mContext;

    public DetailsWorkmatesAdapter(ArrayList<Workmate> items, Context context) {
        mValues = items;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.workmates_joining, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Workmate item = this.mValues.get(position);
        holder.mWorkmateName.setText(item.getUsername());
        System.out.println(mValues.get(1).getUsername());
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mWorkmatePicture;
        public Workmate mItem;
        public final TextView mWorkmateName;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            mWorkmatePicture = view.findViewById(R.id.workmate_joining_picture);
            mWorkmateName = view.findViewById(R.id.workmate_joining_name);
        }

        @NotNull
        @Override
        public String toString() {
            return super.toString() + " '" + mWorkmateName.getText() + "'";
        }
    }
}
