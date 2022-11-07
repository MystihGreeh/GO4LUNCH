package com.mystihgreeh.go4lunch.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.libraries.places.api.model.Place;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.mystihgreeh.go4lunch.model.Restaurants.Result;
import com.mystihgreeh.go4lunch.model.Workmates.Workmate;
import com.mystihgreeh.go4lunch.repository.RestaurantRepository;
import com.mystihgreeh.go4lunch.repository.WorkmatesRepository;

import java.util.ArrayList;
import java.util.List;

public class SharedViewModel extends ViewModel {

    private final WorkmatesRepository mWorkmateRepository;
    public final MutableLiveData<ArrayList<Workmate>> workmates = new MutableLiveData<>();
    public MutableLiveData<String> selectedRestaurantId = new MutableLiveData<>();
    public final MutableLiveData<ArrayList<String>> workmateId = new MutableLiveData<>();
    public final MutableLiveData<ArrayList<Workmate>> fetchedWorkmates = new MutableLiveData<>();
    public final MutableLiveData<Place> autoCompleteResult = new MutableLiveData<>();




    @SuppressWarnings({"FieldCanBeLocal"})



    //----------------------------------------------------------------------------------------------
    //--------------------------------------- WORKMATES --------------------------------------------
    //----------------------------------------------------------------------------------------------

    public LiveData<ArrayList<Workmate>> getWorkmates() { return workmates; }

    public void getUserListFromFirebase() {
        mWorkmateRepository.getAllUsers().addOnSuccessListener(queryDocumentSnapshots -> {
            ArrayList<Workmate> fetchedUsers = new ArrayList<>();
            Log.d("users", "fetchListUsersFromFirebase: "+queryDocumentSnapshots.getDocuments().size());
            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){
                Workmate userFetched = documentSnapshot.toObject(Workmate.class);
                fetchedUsers.add(userFetched);
            }
            workmates.setValue(fetchedUsers);
        })
                .addOnFailureListener(this.onFailureListener());
    }

    private OnFailureListener onFailureListener() {
        return e -> {

        };
    }

    public void onRefreshUserList(){
        this.getUserListFromFirebase();
    }

    /*public void getcurrentWorkmate(){
        mWorkmateRepository.getActualUser();
    }*/

    public void createWorkmate(String uid, String username, String urlPicture,
                               String useremail, String restaurantName, String restaurantUid, String restaurantAddress){
        mWorkmateRepository.createUser(uid, username, urlPicture, useremail, restaurantName, restaurantUid, restaurantAddress);
    }



    public String getUserRestaurant() {
         return mWorkmateRepository.getPickedRestaurant();
    }

    public void getRestaurantId(String userId){
        mWorkmateRepository.getRestaurantId(userId);
    }

    public boolean checkIfUserExist(String userId){
        mWorkmateRepository.checkIfUserDoesExist(userId);
        return false;
    }





    //----------------------------------------------------------------------------------------------
    //------------------------------------- RESTAURANTS --------------------------------------------
    //----------------------------------------------------------------------------------------------

    RestaurantRepository mRestaurantRepository;
    MutableLiveData<List<Result>> mRestaurantMutableLiveData;


    public SharedViewModel() {
        mWorkmateRepository = new WorkmatesRepository();
        mRestaurantRepository = new RestaurantRepository();
        mRestaurantMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<Result>> getRestaurantMutableLiveData(){
        return mRestaurantMutableLiveData;
    }

    public void fetchRestaurants(double currentlatitude, double currentLongitude){
        mRestaurantMutableLiveData = mRestaurantRepository.getRestaurants(currentlatitude, currentLongitude);
    }




    //----------------------------------------------------------------------------------------------
    //------------------------------------- SELECTED RESTAURANTS -----------------------------------
    //----------------------------------------------------------------------------------------------

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


    /*public int getWorkmatesEatingThere(String restaurantId){
        final ArrayList<Workmate> workmateList = new ArrayList<>();
        mWorkmateRepository.getAllUsers().addOnSuccessListener(queryDocumentSnapshots -> {
            for (DocumentSnapshot documentSnapshot: queryDocumentSnapshots.getDocuments()){
                Workmate workmate = documentSnapshot.toObject(Workmate.class);
                if (workmate != null && workmate.getWorkmatePickedRestaurant() != null && workmate.getWorkmatePickedRestaurant().getRestaurantId() != null){
                    String restaurantPickedId= workmate.getWorkmatePickedRestaurant().getRestaurantId();
                    if (restaurantPickedId.equals(restaurantId)){
                        fetchedWorkmates.postValue(workmateList);
                    }
                }
            }
        });
        return fetchedWorkmates.getValue().size();
    }*/

    public void fetchWorkmateIsGoing() {
        ArrayList<Workmate> workmateGoing = new ArrayList<>();
        mWorkmateRepository.getAllUsers().addOnSuccessListener(queryDocumentSnapshots -> {
            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                Workmate workmate = documentSnapshot.toObject(Workmate.class);
                if (workmate != null && workmate.getRestaurantUid() != null) {
                    workmateGoing.add(workmate);
                    fetchedWorkmates.postValue(workmateGoing);
                }
            }
        });
    }

    public void fetchPickedRestaurants(){
        ArrayList<String> restaurantPicked = new ArrayList<>();
        mWorkmateRepository.getAllUsers().addOnSuccessListener(queryDocumentSnapshots -> {
            for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){
                Workmate workmate = documentSnapshot.toObject(Workmate.class);
                /*if (workmate != null && workmate.getWorkmatePickedRestaurant() != null && workmate.getWorkmatePickedRestaurant().getRestaurantId() != null){
                    String restaurantId = workmate.getWorkmatePickedRestaurant().getRestaurantId();
                    restaurantPicked.add(restaurantId);
                }*/
            }
            workmateId.setValue(restaurantPicked);
        });
    }

    public void disconnect() {
        mWorkmateRepository.disconnect();
    }

    public String getupdatedRestaurant() {
       return mWorkmateRepository.getUpdatedRestaurant();
    }


    //----------------------------------------------------------------------------------------------
    //------------------------------------- AUTOCOMPLETE -------------------------------------------
    //----------------------------------------------------------------------------------------------


}