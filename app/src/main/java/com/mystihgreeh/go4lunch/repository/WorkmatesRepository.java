package com.mystihgreeh.go4lunch.repository;


import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mystihgreeh.go4lunch.model.Workmates.Workmate;
import com.mystihgreeh.go4lunch.model.Workmates.WorkmateHelper;

import java.util.List;

public class WorkmatesRepository {

    private final WorkmateHelper workmateHelper;
    public static Workmate user;
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
        if (user == null){
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            String useremail= firebaseUser.getEmail();
            List<? extends UserInfo> provider = firebaseUser.getProviderData();
            for (UserInfo p : provider) {
                if (p.getEmail() != null) {
                    useremail = p.getEmail();
                }
            }
            user = new Workmate(firebaseUser.getUid(), firebaseUser.getDisplayName(),
                    String.valueOf(firebaseUser.getPhotoUrl()), useremail, null, null, null);
        }
        return user;
    }

    //-------------------------------------- CREATE ------------------------------------------------

    public void createUser(String uid, String username, String urlPicture,
                           String useremail, String restaurantName, String restaurantUid, String restaurantAddress) {
        Workmate userToCreate = new Workmate(uid, username, urlPicture, useremail, restaurantName, restaurantUid, restaurantAddress);
        user = userToCreate;
        workmateHelper.workmatesFirestore.document(uid).set(userToCreate);
    }


    //------------------------------------- GET ----------------------------------------------------


    public Task<DocumentSnapshot> getUser(String uid) {
        return workmateHelper.workmatesFirestore.document(uid).get();
    }


    public Task<QuerySnapshot> getAllUsers() {
        return workmateHelper.getAllWorkmates();
    }

    public void checkIfUserDoesExist(String userId){
        workmateHelper.workmatesFirestore.document(String.valueOf(getUser(userId).addOnSuccessListener(task ->
        {
            if (!task.exists()) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String useremail= user.getEmail();
                List<? extends UserInfo> provider = user.getProviderData();
                for (UserInfo p : provider) {
                    if (p.getEmail() != null) {
                        useremail = p.getEmail();
                    }
                }
             createUser(user.getUid(), user.getDisplayName(), user.getPhotoUrl().toString(), useremail, null, null, null);
            }
        })));
    }

    //---------------------------- RESTAURANTS PICKED AND LIKED ------------------------------------


    public Task<Void> updateRestaurantPicked(String id, String name, String address, String userUid){
        String userid = FirebaseAuth.getInstance().getUid();
        workmateHelper.workmatesFirestore.document(userid).get();
        user.setRestaurantName(name);
        user.setRestaurantAddress(address);
        user.setRestaurantUid(id);
        return workmateHelper.workmatesFirestore.document(userUid).update("restaurantUid", id, "restaurantName", name, "restaurantAddress", address);
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
        workmateHelper.workmatesFirestore.document(uid).update("likedRestaurants", likedRestaurantsList);
    }


    public String getPickedRestaurant(){
        return workmateHelper.getRestaurantUserId();
    }


    public String getUpdatedRestaurant(){
        if (user != null){
            return user.getRestaurantUid();}
        else return null;
    }


    public void getRestaurantId(String userId){
            workmateHelper.getRestaurantUser(userId);
    }


    public void disconnect() {
        user = null;
    }


    public Workmate user(){
        return user;
    }

    public void clearRestaurantId() {
        workmateHelper.clearRestaurantId();
    }

    public MutableLiveData<Boolean> getLikedRestaurant(String userId, String restaurantId){
        return workmateHelper.getLikedRestaurants(userId, restaurantId);
    }

    public MutableLiveData<Boolean> isLiked(){
        return workmateHelper.isLiked();
    }


}