package com.mystihgreeh.go4lunch.viewModel;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.DocumentSnapshot;
import com.mystihgreeh.go4lunch.model.RestaurantsDetails.DetailsResult;
import com.mystihgreeh.go4lunch.model.Workmates.Workmate;
import com.mystihgreeh.go4lunch.repository.RestaurantRepository;
import com.mystihgreeh.go4lunch.repository.WorkmatesRepository;

import java.util.ArrayList;

public class RestaurantDetailsViewModel extends ViewModel {

    LiveData<DetailsResult> mRestaurantDetailsMutableLiveData;
    RestaurantRepository mRestaurantRepository;
    WorkmatesRepository mWorkmateRepository;
    Workmate workmate;
    String user;
    public MutableLiveData<Boolean> isRestaurantLiked = new MutableLiveData<>();
    public MutableLiveData<Boolean> isRestaurantPicked = new MutableLiveData<>();
    public final MutableLiveData<ArrayList<String>> workmateId = new MutableLiveData<>();
    public final MutableLiveData<ArrayList<Workmate>> fetchedWorkmates = new MutableLiveData<>();

    public RestaurantDetailsViewModel(WorkmatesRepository workmatesRepository, RestaurantRepository restaurantRepository) {
        mRestaurantRepository = restaurantRepository;
        mWorkmateRepository = workmatesRepository;

    }

    public void initViewModel(){
        user = mWorkmateRepository.getActualUser().getUid();
        workmate = mWorkmateRepository.user();
    }

    public LiveData<DetailsResult> getRestaurantDetailsMutableLiveData(String placeId){
        isRestaurantLiked.setValue(mWorkmateRepository.getLikedRestaurant(getUserId(), placeId).getValue());
        return mRestaurantDetailsMutableLiveData;
    }

    public void fetchRestaurantsDetails(String placeId){
        mRestaurantDetailsMutableLiveData = mRestaurantRepository.getRestaurantDetails(placeId);
    }

    public void fetchInfoRestaurant(String restaurantId) {
        fetchWorkmateIsGoing();
        String pickedRestaurantUid = mWorkmateRepository.getPickedRestaurant();

        if (pickedRestaurantUid != null) {
            if (pickedRestaurantUid.equals(restaurantId)) {
                isRestaurantPicked.setValue(true);
            } else {
                isRestaurantPicked.setValue(false);
            }
        }else {
            isRestaurantPicked.setValue(false);}

    }


    public void updateRestaurantLiked(DetailsResult restaurant) {
        if (mWorkmateRepository.isLiked().getValue() != null) {
            if (mWorkmateRepository.isLiked().getValue()) {
                mWorkmateRepository.removeLikedRestaurant(restaurant.getPlaceId());
                isRestaurantLiked.setValue(mWorkmateRepository.getLikedRestaurant(getUserId(), restaurant.getPlaceId()).getValue());
            } else {
                mWorkmateRepository.addLikedRestaurant(restaurant.getPlaceId());
                isRestaurantLiked.setValue(mWorkmateRepository.getLikedRestaurant(getUserId(), restaurant.getPlaceId()).getValue());
            }
        } else isRestaurantLiked.setValue(mWorkmateRepository.getLikedRestaurant(getUserId(), restaurant.getPlaceId()).getValue());
    }

    public void updatePickedRestaurant(DetailsResult restaurant) {
        if (isRestaurantPicked.getValue() ) {
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
                getWorkmatesEatingThere(workmate, workmateGoing, restaurantId);
            }
        });
        return fetchedWorkmates;

    }

    public void getWorkmatesEatingThere(Workmate workmate, ArrayList<Workmate> workmateGoing, String restaurantId) {
        if (workmate != null && workmate.getRestaurantUid() != null) {
            if (workmate.getRestaurantUid().equals(restaurantId)) {
                workmateGoing.add(workmate);
                fetchedWorkmates.postValue(workmateGoing);
            }
        }
    }


    public MutableLiveData<Boolean> getLikedRestaurant(String userId, String restaurantId){
       return mWorkmateRepository.getLikedRestaurant(userId, restaurantId);
    }


    public String getUserId(){
        return workmate.getUid();
    }

    public void clearRestaurantId() {
        mWorkmateRepository.clearRestaurantId();
    }

    public void getRestaurantId(String userId){
        mWorkmateRepository.getRestaurantId(userId);
    }



}
