package com.mystihgreeh.go4lunch.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.datatransport.Event;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.mystihgreeh.go4lunch.model.Workmate;
import com.mystihgreeh.go4lunch.repository.RestaurantRepository;
import com.mystihgreeh.go4lunch.repository.WorkmatesRepository;

import java.util.ArrayList;
import java.util.List;

public class SharedViewModel extends androidx.lifecycle.ViewModel {

    //----------------------WORKMATES-----------------------//

    private RestaurantRepository restaurantRepository;
    private WorkmatesRepository coworkerRepository;
    protected Workmate coworker;

    private MutableLiveData<List<Workmate>> coworkers = new MutableLiveData<>();
    private MutableLiveData<Event<Object>> openDetailRestaurant = new MutableLiveData<>();
    public final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public LiveData<Event<Object>> getOpenDetailRestaurant() {
        return openDetailRestaurant;
    }

    public LiveData<List<Workmate>> getCoworkers() { return coworkers; }

    public SharedViewModel(RestaurantRepository restaurantRepository, WorkmatesRepository mCoworkerRepository, RestaurantRepository mRestaurantRepository) {
        this.coworkerRepository = mCoworkerRepository;
        this.restaurantRepository = mRestaurantRepository;
    }

    void fetchListUsersFromFirebase() {
        WorkmatesRepository.getAllCoworker().addOnSuccessListener(queryDocumentSnapshots -> {
            List<Workmate> fetchedUsers = new ArrayList<>();
            Log.d("FireBAse", "fetchListUsersFromFirebase: "+queryDocumentSnapshots.getDocuments().size());
            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){
                Workmate userFetched = documentSnapshot.toObject(Workmate.class);
                fetchedUsers.add(userFetched);
            }
            coworkers.setValue(fetchedUsers);
            isLoading.setValue(false);
        })
                .addOnFailureListener(this.onFailureListener());
    }

    private OnFailureListener onFailureListener() {
        return e -> {
            isLoading.setValue(false);
        };
    }

    public void onRefreshUserList(){
        isLoading.setValue(true);
        this.fetchListUsersFromFirebase();
    }

    /*public void updateRestaurantToDisplay(Workmate coworker) {
        String uidRestaurant = coworker.getUid();
        if (uidRestaurant != null) {
            restaurantRepository.setRestaurantSelected(uidRestaurant);
            openDetailRestaurant.setValue(new Event<>(new Object()));
        }
    }*/

}