package com.mystihgreeh.go4lunch.api.helper;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mystihgreeh.go4lunch.model.Workmate;

public class WorkmateHelper {

    private static final String COLLECTION_NAME = "users";

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getUsersCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---

    public static Task<Void> createUser(String uid, String username, String urlPicture, String useremail) {
        Workmate userToCreate = new Workmate(uid, username, urlPicture, useremail);
        return WorkmateHelper.getUsersCollection().document(uid).set(userToCreate);
    }

    // --- GET ---

    public static Task<DocumentSnapshot> getUser(String uid){
        return WorkmateHelper.getUsersCollection().document(uid).get();
    }

    // --- UPDATE ---

    public static Task<Void> updateUsername(String username, String uid) {
        return WorkmateHelper.getUsersCollection().document(uid).update("username", username);
    }


    // --- DELETE ---

    public static Task<Void> deleteUser(String uid) {
        return WorkmateHelper.getUsersCollection().document(uid).delete();
    }



}
