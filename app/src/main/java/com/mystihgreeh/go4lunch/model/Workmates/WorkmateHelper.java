package com.mystihgreeh.go4lunch.model.Workmates;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;

public class WorkmateHelper {

    private static WorkmateHelper workmateHelper;
    String restaurantUser;
    ArrayList<String> likedRestaurantIds;
    MutableLiveData<Boolean> isLiked = new MutableLiveData<>();



    public static WorkmateHelper getInstance() {
        if (workmateHelper == null) {
            workmateHelper = new WorkmateHelper();

        }
        return workmateHelper;
    }
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    public CollectionReference workmatesFirestore = db.collection("users");

    public Task<QuerySnapshot> getAllWorkmates(){
        return workmatesFirestore.get();
    }


    public void getRestaurantUser(String userId) {
        workmatesFirestore.document(userId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    Workmate user = documentSnapshot.toObject(Workmate.class);
                    restaurantUser = null;
                    if (user != null && user.getRestaurantUid() != null) {
                        restaurantUser = user.getRestaurantUid();
                    }
                });
    }

    public String getRestaurantUserId(){
        return restaurantUser;
    }


    public void clearRestaurantId() {
        restaurantUser = null;
    }

    public MutableLiveData<Boolean> getLikedRestaurants(String userId, String restaurantId) {
        checkIfLiked(userId, restaurantId);
        return isLiked;
    }

    public void checkIfLiked(String userId, String restaurantId){
        isLiked.setValue(false);
        workmatesFirestore.document(userId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()){
                        Workmate user = documentSnapshot.toObject(Workmate.class);
                        if (user.getLikedRestaurants() != null) {
                            for (String uid : user.getLikedRestaurants()) {
                                if (uid.contains(restaurantId)) {
                                    isLiked.setValue(true);
                                    break;
                                } else isLiked.setValue(false);
                            }
                        } else isLiked.setValue(false);
                    }

                });

    }

}
