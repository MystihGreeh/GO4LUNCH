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

    private final WorkmatesRepository mWorkmateRepository;
    public final MutableLiveData<ArrayList<Workmate>> workmates = new MutableLiveData<>();
    public MutableLiveData<String> selectedRestaurantId = new MutableLiveData<>();
    public final MutableLiveData<ArrayList<String>> workmateId = new MutableLiveData<>();
    public final MutableLiveData<ArrayList<Workmate>> fetchedWorkmates = new MutableLiveData<>();

    public LiveData<ArrayList<Workmate>> getWorkmates() { return workmates; }

    RestaurantRepository mRestaurantRepository;
    MutableLiveData<List<Result>> mRestaurantMutableLiveData;


    public SettingsViewModel() {
        mWorkmateRepository = new WorkmatesRepository();
    }

    public void getSelectedRestaurant() {
        String uid = (getCurrentUser() != null) ? getCurrentUser().getUid() : "default";
        mWorkmateRepository.getUser(uid)
                .addOnSuccessListener(documentSnapshot -> {
                    Workmate coworker = documentSnapshot.toObject(Workmate.class);
                    if (coworker != null && coworker.getRestaurantUid() != null) {
                        selectedRestaurantId.setValue(coworker.getRestaurantUid());
                    }
                });
    }


    public FirebaseUser getCurrentUser(){
        return FirebaseAuth.getInstance().getCurrentUser();
    }
}
