package com.mystihgreeh.go4lunch.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mystihgreeh.go4lunch.R;
import com.mystihgreeh.go4lunch.adapter.RestaurantAdapter;
import com.mystihgreeh.go4lunch.viewModel.SharedViewModel;

import java.util.ArrayList;


/**
 * A fragment representing a list of Items.
 */
public class RestaurantsListFragment extends Fragment {

    private SharedViewModel sharedViewModel;



    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RestaurantsListFragment() {
    }

    // TODO: Customize parameter initialization
    public static RestaurantsListFragment newInstance() {
       return new RestaurantsListFragment(); }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_list, container, false);

        // Set the adapter
        Context context = view.getContext();
        RecyclerView recyclerView = view.findViewById(R.id.restaurant_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        configureViewModel();
        sharedViewModel.getRestaurantMutableLiveData().observe(getViewLifecycleOwner(),
                restaurants -> recyclerView.setAdapter(new RestaurantAdapter(new ArrayList<>(restaurants), sharedViewModel.fetchedWorkmates.getValue())));

        return view;
    }


    public void configureViewModel() {
        sharedViewModel = ViewModelProviders.of(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.fetchWorkmateIsGoing();
    }



    @Override
    public void onResume()
    {
        super.onResume();


    }





}