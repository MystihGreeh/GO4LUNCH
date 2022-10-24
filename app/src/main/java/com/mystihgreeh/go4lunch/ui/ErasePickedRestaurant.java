package com.mystihgreeh.go4lunch.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.google.firebase.firestore.DocumentSnapshot;
import com.mystihgreeh.go4lunch.model.Workmates.Workmate;
import com.mystihgreeh.go4lunch.repository.WorkmatesRepository;

public class ErasePickedRestaurant extends BroadcastReceiver {

    private WorkmatesRepository workmatesRepository;


    @Override
    public void onReceive(Context context, Intent intent) {
        workmatesRepository = WorkmatesRepository.getInstance();
        this.erasePickedRestaurant();
    }

    private void erasePickedRestaurant() {
        workmatesRepository.getAllUsers().addOnSuccessListener(queryDocumentSnapshots -> {
            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){
                Workmate workmate = documentSnapshot.toObject(Workmate.class);
                if (workmate != null && workmate.getRestaurantUid() != null){
                    workmatesRepository.updateRestaurantPicked(null, null, null, workmate.getUid());
                }
            }
        });
    }
}


