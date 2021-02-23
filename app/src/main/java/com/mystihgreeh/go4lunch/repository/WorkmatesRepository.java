package com.mystihgreeh.go4lunch.repository;


import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.mystihgreeh.go4lunch.model.Restaurant;
import com.mystihgreeh.go4lunch.model.Workmate;

import java.util.List;

public class WorkmatesRepository {

    public CollectionReference getCollectionWorkmate() {
        return FirebaseFirestore.getInstance().collection("workmates");
    }


    public Query getListWorkmates() {
        return getCollectionWorkmate().orderBy("chooseRestaurant", Query.Direction.DESCENDING);

    }


    public Task<Void> createWorkmate(String workmateId, String workmateName, String workmateEmail, String wormateUrlPicture) {
        Workmate toCreate = new Workmate(workmateId, workmateName, workmateEmail, wormateUrlPicture);
        return getCollectionWorkmate().document(workmateId).set(toCreate);
    }


    public Task<DocumentSnapshot> getWorkmates(String workmateId) {
        return getCollectionWorkmate().document(workmateId).get();
    }


    public Task<Void> updateWorkmateChooseRestaurant(String workmateId, Boolean isChooseRestaurant) {
        return getCollectionWorkmate().document(workmateId).update("chooseRestaurant", isChooseRestaurant);

    }


    public Task<Void> updateWorkmateRestaurant(String workmateId, Restaurant restaurantChoose) {
        return getCollectionWorkmate().document(workmateId).update("restaurantChoose", restaurantChoose);

    }


    public Task<Void> updateWorkmateRestaurantListFavorites(String workmateId, List<Restaurant> restaurantListFavorites) {
        return getCollectionWorkmate().document(workmateId).update("restaurantListFavorites", restaurantListFavorites);

    }
}
