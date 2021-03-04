package com.mystihgreeh.go4lunch.ViewModel;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mystihgreeh.go4lunch.R;
import com.mystihgreeh.go4lunch.model.Restaurant;


import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link com.mystihgreeh.go4lunch.model.Restaurant}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyrestaurantRecyclerViewAdapter extends RecyclerView.Adapter<MyrestaurantRecyclerViewAdapter.ViewHolder> {

    private final List<Restaurant> mValues;

    public MyrestaurantRecyclerViewAdapter(List<Restaurant> items) {
        mValues = items;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_restaurant_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getPlaceId());
        holder.mContentView.setText(mValues.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Restaurant mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.restaurant_avatar);
            mContentView = view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}