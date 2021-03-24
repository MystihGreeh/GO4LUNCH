package com.mystihgreeh.go4lunch.view;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mystihgreeh.go4lunch.R;
import com.mystihgreeh.go4lunch.ViewModel.MyworkmatesRecyclerViewAdapter;
import com.mystihgreeh.go4lunch.ViewModel.SharedViewModel;
import com.mystihgreeh.go4lunch.model.Workmate;

import java.util.ArrayList;


public class WorkmatesListFragment extends Fragment implements View.OnClickListener {


    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private SharedViewModel sharedViewModel;
    public WorkmatesListFragment() {
    }


    public static WorkmatesListFragment newInstance(int columnCount) {
        WorkmatesListFragment fragment = new WorkmatesListFragment();
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
        View view = inflater.inflate(R.layout.fragment_workmates_list, container, false);


        // Set the adapter
        Context context = view.getContext();
        RecyclerView recyclerView = view.findViewById(R.id.wormates_recyclerView);
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        sharedViewModel.getAllWorkmatesData().observe(getViewLifecycleOwner(), workmates -> recyclerView.setAdapter(new MyworkmatesRecyclerViewAdapter(workmates)));

        return view;
    }

    @Override
    public void onClick(View v) {

    }
}