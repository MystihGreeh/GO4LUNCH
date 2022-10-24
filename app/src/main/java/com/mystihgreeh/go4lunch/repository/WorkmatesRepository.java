package com.mystihgreeh.go4lunch.repository;


import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.mystihgreeh.go4lunch.model.Workmates.Workmate;
import com.mystihgreeh.go4lunch.model.Workmates.WorkmateHelper;

import java.util.ArrayList;
import java.util.List;

public class WorkmatesRepository {

    private final WorkmateHelper workmateHelper;
    private static Workmate user;
    private static volatile WorkmatesRepository INSTANCE;

    public static WorkmatesRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new WorkmatesRepository();
        }
        return INSTANCE;
    }

    public WorkmatesRepository() {
        workmateHelper = WorkmateHelper.getInstance();
    }

    public Workmate getActualUser() {
        //if user == null
        return user;
    }



    // --- COLLECTION REFERENCE ---

    public static CollectionReference getUsersCollection() {
        return FirebaseFirestore.getInstance().collection("users");
    }

    //-------------------------------------- CREATE ------------------------------------------------

    public  Task<Void> createUser(String uid, String username, String urlPicture,
                                        String useremail, String restaurantName, String restaurantUid, String restaurantAddress) {
        Workmate userToCreate = new Workmate(uid, username, urlPicture, useremail, restaurantName, restaurantUid, restaurantAddress);
        user = userToCreate;
        return workmateHelper.workmatesFirebase.document(uid).set(userToCreate);
    }




    //------------------------------------- GET ----------------------------------------------------

    /*public Workmate getUser() {
        return user;
    }*/

    public Task<DocumentSnapshot> getUser(String uid) {
        return workmateHelper.workmatesFirebase.document(uid).get();
    }


    public Task<QuerySnapshot> getAllUsers() {
        return workmateHelper.getAllWorkmates();
    }

    public void checkIfUserDoesExist(String userId){
        workmateHelper.workmatesFirebase.document(String.valueOf(getUser(userId).addOnSuccessListener(tast ->
        {
            if (!tast.exists()) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
             createUser(user.getUid(), user.getDisplayName(), user.getPhotoUrl().toString(), user.getEmail(), null, null, null);
            }
        })));
    }
    /*public void checkIfUSerDoesExist(Workmate userId) {
        workmateHelper.workmatesFirebase.document(userId.getUid()).get()
                .addOnCompleteListener( task -> {
                    if (task.isSuccessful ()) {
                        if (!task.getResult().exists()){
                            createUser(user.getUid(), userId.getUsername(), userId.getUrlPicture(), userId.getUseremail(), null, null, null);
                        }

                    }
                });
    }*/
    //---------------------------- RESTAURANTS PICKED AND LIKED ------------------------------------


    public Task<Void> updateRestaurantPicked(String id, String name, String address, String userUid){
        user.setRestaurantName(name);
        //user.setRestaurantAddress(address);
        user.setRestaurantUid(id);
        //user.setWorkmatePickedRestaurant(choice);
        return workmateHelper.workmatesFirebase.document(userUid).update("restaurantUid", id, "restaurantName", name, "restaurantAddress", address);
    }



    public void addLikedRestaurant(String likedRestaurant) {
        user.addLikedRestaurant(likedRestaurant);
        updateLikedRestaurants(user.getUid());
    }



    public void removeLikedRestaurant(String likedRestaurant) {
        user.removeLikedRestaurant(likedRestaurant);
        updateLikedRestaurants(user.getUid());
    }

    private void updateLikedRestaurants(String uid) {
        List<String> likedRestaurantsList = user.getLikedRestaurants();
        workmateHelper.workmatesFirebase.document(uid).update("likedRestaurants", likedRestaurantsList);
    }

    public String getPickedRestaurant(){
        if (user != null){
        return user.getRestaurantUid();}
        else return null;

    }


    public MutableLiveData<ArrayList<Workmate>> fetchWorkmateEatingThere(String restaurantId) {
        MutableLiveData<ArrayList<Workmate>> workmateList = new MutableLiveData<>();
        workmateHelper.workmatesFirebase.whereEqualTo("WorkmatePickedRestaurant.restaurantId", restaurantId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                ArrayList<Workmate> list = new ArrayList<>();
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                    list.add(documentSnapshot.toObject(Workmate.class));
                }
                workmateList.setValue(list);
            }
        });
        return workmateList;
    }
}