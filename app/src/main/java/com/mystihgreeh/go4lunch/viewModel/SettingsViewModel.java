package com.mystihgreeh.go4lunch.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mystihgreeh.go4lunch.model.Restaurants.Result;
import com.mystihgreeh.go4lunch.model.Workmates.Workmate;
import com.mystihgreeh.go4lunch.repository.RestaurantRepository;
import com.mystihgreeh.go4lunch.repository.WorkmatesRepository;

import java.util.ArrayList;
import java.util.List;

public class SettingsViewModel extends ViewModel {


    public final MutableLiveData<ArrayList<Workmate>> workmates = new MutableLiveData<>();


    public LiveData<ArrayList<Workmate>> getWorkmates() { return workmates; }



}
