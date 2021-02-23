package com.mystihgreeh.go4lunch.ViewModel;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mystihgreeh.go4lunch.R;
import com.mystihgreeh.go4lunch.model.Workmate;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Workmate}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyworkmatesRecyclerViewAdapter extends RecyclerView.Adapter<MyworkmatesRecyclerViewAdapter.ViewHolder> {

    private final List<Workmate> mValues;

    public MyworkmatesRecyclerViewAdapter(List<Workmate> items) {
        mValues = items;
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
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getWorkmateName());
        holder.mContentView.setText(mValues.get(position).getWorkmateEmail());

        /*final Reunion reunion = mReunion.get(position);

        holder.mRoom.setText(reunion.getObject()+" - "+reunion.getTime()+ " - "+reunion.getRoom());
        holder.mEmails.setText(reunion.getEmails());*/
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Workmate mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.item_number);
            mContentView = view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

}