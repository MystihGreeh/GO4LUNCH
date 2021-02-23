package com.mystihgreeh.go4lunch.helper;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mystihgreeh.go4lunch.model.Workmate;

public class WorkmateHelper {

    /*private static final String COLLECTION_NAME = "workmate";

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getWorkmatesCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---

    public static Task<Void> createWorkmate(String wid, String workmateName, String workmateEmail, String urlPicture) {
        Workmate workmateToCreate = new Workmate(workmateName, workmateEmail, urlPicture);
        return WorkmateHelper.getWorkmatesCollection().document(wid).set(workmateToCreate);
    }

    // --- GET ---

    public static Task<DocumentSnapshot> getWorkmate(String wid){
        return WorkmateHelper.getWorkmatesCollection().document(wid).get();
    }

    // --- UPDATE ---

    public static Task<Void> updateWorkmateName(String workmateName, String wid) {
        return WorkmateHelper.getWorkmatesCollection().document(wid).update("workmateName", workmateName);
    }

    /*public static Task<Void> updateIsMentor(String wid, Boolean isMentor) {
        return WorkmateHelper.getWorkmatesCollection().document(wid).update("isMentor", isMentor);
    }*/

    // --- DELETE ---

 /*   public static Task<Void> deleteWorkmate(String wid) {
        return WorkmateHelper.getWorkmatesCollection().document(wid).delete();
    }

    */

}
