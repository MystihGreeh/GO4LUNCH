package com.mystihgreeh.go4lunch.model.Workmates;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class WorkmateHelper {

    private static WorkmateHelper workmateHelper;

    public static WorkmateHelper getInstance() {
        if (workmateHelper == null) {
            workmateHelper = new WorkmateHelper();
        }
        return workmateHelper;
    }
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    public CollectionReference workmatesFirebase = db.collection("users");

    public Task<QuerySnapshot> getAllWorkmates(){
        return workmatesFirebase.get();
    }

    public void checkIfUserExist(String userId) {
        db.collection("users").whereEqualTo("uid", userId)
                .limit(1).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            boolean isEmpty = task.getResult().isEmpty();
                        }
                    }
                });
    }
}
