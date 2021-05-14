package com.mystihgreeh.go4lunch.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mystihgreeh.go4lunch.R;
import com.mystihgreeh.go4lunch.ViewModel.MyrestaurantRecyclerViewAdapter;
import com.mystihgreeh.go4lunch.ViewModel.SharedViewModel;
import com.mystihgreeh.go4lunch.model.NearbySearchResponse;

import java.util.Collections;


/**
 * A fragment representing a list of Items.
 */
public class RestaurantsListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private NearbySearchResponse nearbySearchResponse;
    private SharedViewModel sharedViewModel;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RestaurantsListFragment() {
    }

    // TODO: Customize parameter initialization
    public static RestaurantsListFragment newInstance(int columnCount) {
        RestaurantsListFragment fragment = new RestaurantsListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            sharedViewModel.getRestaurantRepository().observe(getViewLifecycleOwner(), nearbySearchResponse -> recyclerView.setAdapter(new MyrestaurantRecyclerViewAdapter(Collections.singletonList(nearbySearchResponse))));
        }
        return view;
    }
}