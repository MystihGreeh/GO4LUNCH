package com.mystihgreeh.go4lunch.repository;


import android.support.v4.media.session.PlaybackStateCompat;

import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.mystihgreeh.go4lunch.model.Workmate;

public class WorkmatesRepository {

    private static final String COLLECTION_NAME = "coworker";
    private CollectionReference coworkerCollection;
    private DocumentReference coworkerDocumentReference;

    private Workmate coworker;
    private final MutableLiveData<PlaybackStateCompat.Actions> coworkerChoiceStatus = new MutableLiveData<>();
    //private Workmate.WorkmateRestaurantChoice workmateRestaurantChoice;

    private static volatile WorkmatesRepository INSTANCE;

    public static WorkmatesRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new WorkmatesRepository();
        }
        return INSTANCE;
    }

    public WorkmatesRepository() {
        this.coworkerCollection = getUsersCollection();

    }

    public Workmate getActualUser() {
        return coworker;
    }
    // --- COLLECTION REFERENCE ---

    public static CollectionReference getUsersCollection() {
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---

    public Task<Void> createCoworker(String uid, String username, String useremail, String urlPicture) {
        Workmate userToCreate = new Workmate(uid, username, useremail, urlPicture);
        this.coworker = userToCreate;
        return WorkmatesRepository.getUsersCollection().document(uid).set(userToCreate);
    }

    // --- GET ---

    public Workmate getCoworker() {
        return coworker;
    }

    public static Task<DocumentSnapshot> getCoworker(String uid) {
        return WorkmatesRepository.getUsersCollection().document(uid).get();
    }

    // --- GET ---

    public static Task<QuerySnapshot> getAllCoworker() {
        return WorkmatesRepository.getUsersCollection().get();
    }

    // --- UPDATE ---

    public static Task<Void> updateUsername(String username, String uid) {
        return WorkmatesRepository.getUsersCollection().document(uid).update("username", username);
    }

    /*public Task<Void> updateRestaurantPicked(String mRestaurantId, String restaurantName, String restaurantAddress, Workmate coworker) {
        Log.d("updateRestaurantPicked", "updateRestaurantPicked: pickedup3");
        if (coworker == null) {
            coworker = getActualUser();
        }

        /*if (coworker.getCoworkerRestaurantChosen() == null) {
            coworkerRestaurantChoice = new Coworker.CoworkerRestaurantChoice();
        }*/ /*else {
            //coworkerRestaurantChoice = coworker.getCoworkerRestaurantChosen();
        }
        coworkerRestaurantChoice.setRestaurantId(mRestaurantId);
        coworkerRestaurantChoice.setRestaurantName(restaurantName);
        coworkerRestaurantChoice.setRestaurantAddress(restaurantAddress);
        coworker.setCoworkerRestaurantChosen(coworkerRestaurantChoice);
        coworkerCollection.document(coworker.getUid()).update(
                "restaurantUid", mRestaurantId,
                "restaurantName", restaurantName,
                "restaurantAddress", restaurantAddress, "coworkerRestaurantChosen", coworker.getCoworkerRestaurantChosen());

        return coworkerCollection.document(coworker.getUid()).update(
                "restaurantUid", mRestaurantId,
                "restaurantName", restaurantName,
                "restaurantAddress", restaurantAddress, "coworkerRestaurantChosen", coworker.getCoworkerRestaurantChosen());*/


    public Task<Void> updateUrlPicture(String urlPicture, String uid){
        coworker.setUrlPicture(urlPicture);
        return coworkerCollection.document(uid).update("urlPicture",urlPicture);
        }


    /*public Task<Void> addLikedRestaurant(String likedRestaurant, Workmate coworker) {
        if (coworker == null) {
            coworker = getActualUser();
        }
        coworker.addLikedRestaurant(likedRestaurant);
        return updateLikedRestaurants(coworker);
    }*/

    /*public Task<Void> removeLikedRestaurant(String likedRestaurant, Workmate coworker) {
        if (coworker == null) {
            coworker = getActualUser();
        }
        /*coworker.removeLikedRestaurant(likedRestaurant);
        return updateLikedRestaurants(coworker);
    }*/

    /*private Task<Void> updateLikedRestaurants(Workmate coworker) {
        List<String> likedRestaurantsList = coworker.getLikedRestaurants();
        return coworkerCollection.document(coworker.getUid()).update("likedRestaurants", likedRestaurantsList);
    }*/

    // --- DELETE ---

    public static Task<Void> deleteCoworker(String uid) {
        return WorkmatesRepository.getUsersCollection().document(uid).delete();
    }

    public void updateCoworkerRepository(Workmate coworker) {
        this.coworker = coworker;
    }
}