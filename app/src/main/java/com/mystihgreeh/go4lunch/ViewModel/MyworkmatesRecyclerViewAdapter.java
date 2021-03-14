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

import java.util.Objects;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Workmate}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyworkmatesRecyclerViewAdapter extends RecyclerView.Adapter<MyworkmatesRecyclerViewAdapter.ViewHolder> {

    private final MutableLiveData mWorkmate;

    public MyworkmatesRecyclerViewAdapter(MutableLiveData items) {
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
        Workmate workmate = (Workmate) mWorkmate.getValue();
        holder.mItem = (Workmate) mWorkmate.getValue();
        Glide.with(holder.mWorkmateAvatar.getContext())
                .load(Objects.requireNonNull(workmate).getUrlPicture())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mWorkmateAvatar);
        holder.mWorkmateName.setText((CharSequence) mWorkmate.getValue());

    }

    @Override
    public int getItemCount() {
        return (int) Objects.requireNonNull(mWorkmate.getValue());
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