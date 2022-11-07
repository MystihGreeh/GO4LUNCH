package com.mystihgreeh.go4lunch.model.Workmates;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class WorkmateHelper {

    private static WorkmateHelper workmateHelper;
    String restaurantUser;
    Workmate user;

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
        restaurantUser = null;
        workmatesFirestore.document(userId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    Workmate user = documentSnapshot.toObject(Workmate.class);
                    if (user != null && user.getRestaurantUid() != null) {
                        restaurantUser = user.getRestaurantUid();
                    }
                });
    }

    public String getRestaurantUserId(){
        return restaurantUser;
    }



}
