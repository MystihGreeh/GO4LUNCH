package com.mystihgreeh.go4lunch.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mystihgreeh.go4lunch.R;
import com.mystihgreeh.go4lunch.model.Workmates.Workmate;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mWorkmateName.setText(mValues.get(position).getUsername() + " " +mContext.getString(R.string.is_joining));
        if(!Objects.equals(mValues.get(position).getUrlPicture(), "null")){
            Glide.with(mContext)
                    .load(Objects.requireNonNull(mValues.get(position)).getUrlPicture())
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.mWorkmatePicture);
        } else{Glide.with(mContext)
                .load(R.drawable.avatar)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mWorkmatePicture);
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mWorkmatePicture;
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
