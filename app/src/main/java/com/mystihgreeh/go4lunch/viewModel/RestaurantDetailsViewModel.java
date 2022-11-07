package com.mystihgreeh.go4lunch.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.mystihgreeh.go4lunch.model.RestaurantsDetails.DetailsResult;
import com.mystihgreeh.go4lunch.model.Workmates.Workmate;
import com.mystihgreeh.go4lunch.repository.RestaurantRepository;
import com.mystihgreeh.go4lunch.repository.WorkmatesRepository;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class RestaurantDetailsViewModel extends ViewModel {

    LiveData<DetailsResult> mRestaurantDetailsMutableLiveData;
    RestaurantRepository mRestaurantRepository;
    WorkmatesRepository mWorkmateRepository;
    Workmate workmate;
    String user;
    DetailsResult restaurantResult;
    public final MutableLiveData<List<Workmate>> mWorkmatesList = new MutableLiveData<>();
    public MutableLiveData<Boolean> isRestaurantLiked = new MutableLiveData<>();
    public MutableLiveData<Boolean> isRestaurantPicked = new MutableLiveData<>();
    public final MutableLiveData<ArrayList<String>> workmateId = new MutableLiveData<>();
    public final MutableLiveData<ArrayList<Workmate>> fetchedWorkmates = new MutableLiveData<>();

    public RestaurantDetailsViewModel() {
        mRestaurantRepository = new RestaurantRepository();
        mWorkmateRepository = new WorkmatesRepository();
        user = mWorkmateRepository.getActualUser().getUid();
        workmate = mWorkmateRepository.user();
    }

    public LiveData<DetailsResult> getRestaurantDetail(String placeId) {
        return mRestaurantRepository.getRestaurantDetails(placeId);
    }

    public LiveData<DetailsResult> getRestaurantDetailsMutableLiveData(String placeId){
        return mRestaurantDetailsMutableLiveData;
    }

    public void fetchRestaurantsDetails(String placeId){
        mRestaurantDetailsMutableLiveData = mRestaurantRepository.getRestaurantDetails(placeId);
    }

    public void fetchInfoRestaurant(String restaurantId) {
        fetchWorkmateIsGoing();
        //isRestaurantLiked.setValue(checkIfRestaurantIsLiked(restaurantId));
        if (workmate.getRestaurantUid() != null) {
            String uidSelection = workmate.getRestaurantUid();
            if (uidSelection.equals(restaurantId))
                isRestaurantPicked.setValue(true);
        } else {
            isRestaurantPicked.setValue(false);
        }
    }

    public void updateRestaurantLiked(DetailsResult restaurant) {
        if (isRestaurantLiked.getValue()) {
            isRestaurantLiked.setValue(false);
            mWorkmateRepository.removeLikedRestaurant(restaurant.getPlaceId());
        } else {
            isRestaurantLiked.setValue(true);
            mWorkmateRepository.addLikedRestaurant(restaurant.getPlaceId());
        }
    }

    public void updatePickedRestaurant(DetailsResult restaurant) {
        if (isRestaurantPicked.getValue()) {
            isRestaurantPicked.setValue(false);
            mWorkmateRepository.updateRestaurantPicked(null, null, null, workmate.getUid())
                    .addOnCompleteListener(result -> Log.i(TAG, "restaurant not picked yet"));
        } else {
            isRestaurantPicked.setValue(true);
            mWorkmateRepository.updateRestaurantPicked(restaurant.getPlaceId(), restaurant.getName(),
                    restaurant.getFormattedAddress(), workmate.getUid())
                    .addOnCompleteListener(result -> Log.i(TAG, "restaurant selected"));
        }
        fetchWorkmateIsGoing();
    }

    public void fetchWorkmateLikedRestaurant(String restaurantId) {
        if (workmate.getLikedRestaurants() != null) {
            List<String> likedRestaurant = workmate.getLikedRestaurants();
            String restaurantUid = restaurantResult.getPlaceId();
            if (likedRestaurant != null && restaurantUid != null && likedRestaurant.contains(restaurantUid)) {
                isRestaurantLiked.setValue(true);
            }
        }
    }

    public void fetchWorkmateIsGoing(){
        ArrayList<String> workmateGoing = new ArrayList<>();
        mWorkmateRepository.getAllUsers().addOnSuccessListener(queryDocumentSnapshots -> {
            for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){
                Workmate workmate = documentSnapshot.toObject(Workmate.class);
                if (workmate != null && workmate.getRestaurantUid() != null && workmate.getRestaurantUid() != null){
                    String restaurantId = workmate.getRestaurantUid();
                    workmateGoing.add(restaurantId);
                }
            }
            workmateId.setValue(workmateGoing);
        });
    }

    public MutableLiveData<ArrayList<Workmate>> fetchWorkmateEatingThere(String restaurantId) {
        ArrayList<Workmate> workmateGoing = new ArrayList<>();
        mWorkmateRepository.getAllUsers().addOnSuccessListener(queryDocumentSnapshots -> {
            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                Workmate workmate = documentSnapshot.toObject(Workmate.class);
                if (workmate != null && workmate.getRestaurantUid() != null) {
                    if (workmate.getRestaurantUid().equals(restaurantId)) {
                        workmateGoing.add(workmate);
                        fetchedWorkmates.postValue(workmateGoing);
                    }
                }
            }
        });
        return fetchedWorkmates;

    }

    public boolean checkIfRestaurantIsPicked(String restaurantId) {
        String restaurantPicked = workmate.getRestaurantUid();
        if (restaurantPicked != null) {
                if (restaurantPicked.equals(restaurantId)) return true;
        }
        return false;
    }

}
