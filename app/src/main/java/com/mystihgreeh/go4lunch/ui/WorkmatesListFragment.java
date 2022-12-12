package com.mystihgreeh.go4lunch.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mystihgreeh.go4lunch.R;
import com.mystihgreeh.go4lunch.adapter.WorkmatesAdapter;
import com.mystihgreeh.go4lunch.viewModel.SharedViewModel;
import com.mystihgreeh.go4lunch.viewModel.ViewModelFactory;
import com.mystihgreeh.go4lunch.api.Injection;

public class WorkmatesListFragment extends Fragment implements View.OnClickListener {

    private SharedViewModel sharedViewModel;


    public WorkmatesListFragment() {}
    public static WorkmatesListFragment newInstance() {
        return new WorkmatesListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workmates_list, container, false);
        setHasOptionsMenu(false);

        // Set the adapter
        Context context = view.getContext();
        RecyclerView recyclerView = view.findViewById(R.id.wormates_recyclerView);
        int mColumnCount = 1;
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }

        configureViewModel();
        sharedViewModel.getUserListFromFirebase();
        sharedViewModel.emptyAutoCompleteResult();
        sharedViewModel.getWorkmates().observe(getViewLifecycleOwner(), workmates -> recyclerView.setAdapter(new WorkmatesAdapter( workmates, requireContext())));


        return view;
    }

    @Override
    public void onClick(View v) {

    }

    public void configureViewModel() {
        ViewModelFactory factory = Injection.viewModelFactory();
        sharedViewModel = new ViewModelProvider(requireActivity(), factory).get(SharedViewModel.class);
    }



    @Override
    public void onResume()
    {
        super.onResume();
        configureViewModel();
    }

}