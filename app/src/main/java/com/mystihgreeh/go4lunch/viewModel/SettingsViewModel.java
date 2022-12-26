package com.mystihgreeh.go4lunch.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mystihgreeh.go4lunch.model.Workmates.Workmate;

import java.util.ArrayList;

public class SettingsViewModel extends ViewModel {


    public final MutableLiveData<ArrayList<Workmate>> workmates = new MutableLiveData<>();


    public LiveData<ArrayList<Workmate>> getWorkmates() { return workmates; }



}
