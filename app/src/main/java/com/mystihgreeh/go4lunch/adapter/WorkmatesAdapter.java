package com.mystihgreeh.go4lunch.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.annotations.NotNull;
import com.mystihgreeh.go4lunch.R;
import com.mystihgreeh.go4lunch.model.Workmates.Workmate;

import java.util.ArrayList;
import java.util.Objects;

public class WorkmatesAdapter extends RecyclerView.Adapter<WorkmatesAdapter.ViewHolder>  {
    private final ArrayList<Workmate> mWorkmate;
    private final Context mContext;

    public WorkmatesAdapter(ArrayList<Workmate> items, Context context) {
        mWorkmate = items;
        this.mContext = context;
    }


    @org.jetbrains.annotations.NotNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_workmates, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Workmate workmate = (Workmate) mWorkmate.get(position);
        holder.mItem = (Workmate) mWorkmate.get(position);
         if(workmate.getUrlPicture() != null){
             Glide.with(mContext)
                     .load(Objects.requireNonNull(workmate).getUrlPicture())
                     .apply(RequestOptions.circleCropTransform())
                     .into(holder.mWorkmateAvatar);
         } else{Glide.with(mContext)
                 .load(R.drawable.avatar)
                 .apply(RequestOptions.circleCropTransform())
                 .into(holder.mWorkmateAvatar);
         }


        if(workmate.getRestaurantName() != null){
            holder.mWorkmateName.setText(workmate.getUsername() + " " +
                    mContext.getString(R.string.is_eating) +
                    " (" + workmate.getRestaurantName() + ")");
        } else {
            holder.mWorkmateName.setTypeface(holder.mWorkmateName.getTypeface(), Typeface.ITALIC);
            holder.mWorkmateName.setTextColor(mContext.getResources().getColor(R.color.grey));
            holder.mWorkmateName.setText(workmate.getUsername() + " " + mContext.getString(R.string.has_not_decided));
        }
    }

    @Override
    public int getItemCount() {
        return (int)(mWorkmate.size());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mWorkmateAvatar;
        public final TextView mWorkmateName;
        public Workmate mItem;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            mWorkmateAvatar = view.findViewById(R.id.workmate_avatar);
            mWorkmateName = view.findViewById(R.id.workmate_name);

        }

        @org.jetbrains.annotations.NotNull
        @NotNull
        @Override
        public String toString() {
            return super.toString() + " '" + mWorkmateName.getText() + "'";
        }
    }


}
