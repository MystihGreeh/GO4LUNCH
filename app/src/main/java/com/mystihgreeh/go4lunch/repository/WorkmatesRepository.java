package com.mystihgreeh.go4lunch.repository;


import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mystihgreeh.go4lunch.model.Workmate;

import java.util.ArrayList;
import java.util.List;

public class WorkmatesRepository {

    static WorkmatesRepository instance;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "Repository";

    public static WorkmatesRepository getInstance(Context context){
        if (instance == null){
            instance = new WorkmatesRepository();
        }
        return instance;
    }

    public MutableLiveData<ArrayList<Workmate>> getAllWorkmates(){
        MutableLiveData<ArrayList<Workmate>> allWorkmates = new MutableLiveData<>();
        db.collection("users")
                .get().addOnSuccessListener(queryDocumentSnapshots -> {
            if(!queryDocumentSnapshots.isEmpty()){
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                ArrayList<Workmate> workmates = new ArrayList<>();
                for (DocumentSnapshot documentSnapshot : list) {
                    workmates.add(documentSnapshot.toObject(Workmate.class));
                }
                allWorkmates.setValue(workmates);
            }
        }).addOnFailureListener(e -> Log.d(TAG, "Impossible to get workmates list", e));
        return allWorkmates;
    }
}