package com.mystihgreeh.go4lunch.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mystihgreeh.go4lunch.R;
import com.mystihgreeh.go4lunch.model.Workmate;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Workmate}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyworkmatesRecyclerViewAdapter extends RecyclerView.Adapter<MyworkmatesRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<Workmate> mWorkmate;

    public MyworkmatesRecyclerViewAdapter(ArrayList<Workmate> items) {
        mWorkmate = items;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_workmates, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Workmate workmate = (Workmate) mWorkmate.get(position);
        holder.mItem = (Workmate) mWorkmate.get(position);
        Glide.with(holder.mWorkmateAvatar.getContext())
                .load(Objects.requireNonNull(workmate).getUrlPicture())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mWorkmateAvatar);
        holder.mWorkmateName.setText((CharSequence) mWorkmate.get(position).getUsername());
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

        @NotNull
        @Override
        public String toString() {
            return super.toString() + " '" + mWorkmateName.getText() + "'";
        }
    }

}