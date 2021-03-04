package com.mystihgreeh.go4lunch.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mystihgreeh.go4lunch.model.Workmate;
import com.mystihgreeh.go4lunch.repository.WorkmatesRepository;

import java.util.ArrayList;

public class ViewModelWorkmates extends androidx.lifecycle.ViewModel {

    private MutableLiveData<ArrayList<Workmate>> workmates;

    //----------------------------- Get all workmates ----------------------------------------------
    public void initAllWorkmates(Context context){
        workmates = WorkmatesRepository.getInstance(context).getAllWorkmates();
    }

    public LiveData<ArrayList<Workmate>> getAllWorkmatesData(){
        return workmates;
    }

}
