
package com.mystihgreeh.go4lunch.model;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Workmate implements Serializable {

    private String workmateId;
    private String workmateName;
    private String workmateEmail;
    private String workmatePhotoUrl;
    private CoworkerRestaurantChoice workmateRestaurantChosen;
    public static final List<Workmate> ITEMS = new ArrayList<>();
    private List<Favorites> workmateFavorites;

    public Workmate() {}

    public Workmate(String workmateId, List<Favorites> workmateFavorites) {
        this.workmateId = workmateId;
        this.workmateFavorites = workmateFavorites;
    }

    public Workmate(String workmateId, String workmateName) {
        this.workmateId = workmateId;
        this.workmateName = workmateName;
    }


    public Workmate(String workmateId,String workmateName, String workmateEmail, String workmatePhotoUrl) {
        this.workmateId = workmateId;
        this.workmateName = workmateName;
        this.workmateEmail = workmateEmail;
        this.workmatePhotoUrl = workmatePhotoUrl;
    }

    public String getWorkmateId() {
        return workmateId;
    }

    public void setWorkmateId(String workmateId) {
        this.workmateId = workmateId;
    }

    public String getWorkmateName() {
        return workmateName;
    }

    public void setWorkmateName(String workmateName) {
        this.workmateName = workmateName;
    }

    public String getWorkmateEmail() {
        return workmateEmail;
    }

    public void setWorkmateEmail(String workmateEmail) {
        this.workmateEmail = workmateEmail;
    }

    public String getWorkmatePhotoUrl() {
        return workmatePhotoUrl;
    }

    public void setWorkmatePhotoUrl(String workmatePhotoUrl) {
        this.workmatePhotoUrl = workmatePhotoUrl;
    }

    public CoworkerRestaurantChoice getWorkmateRestaurantChosen() {
        return workmateRestaurantChosen;
    }

    public void setWorkmateRestaurantChosen(CoworkerRestaurantChoice workmateRestaurantChosen) {
        this.workmateRestaurantChosen = workmateRestaurantChosen;
    }

    public List<Favorites> getWorkmateFavorites() {
        return workmateFavorites;
    }

    public void setWorkmateFavorites(List<Favorites> workmateFavorites) {
        this.workmateFavorites = workmateFavorites;
    }


    public static class Favorites {
        public String restaurantId;
        public String restaurantName;

        public Favorites() {}

        public Favorites(String mRestaurantId, String mRestaurantName) {
            restaurantId = mRestaurantId;
            restaurantName = mRestaurantName;
        }

        public String getRestaurantId() { return restaurantId; }

        public void setRestaurantId(String mRestaurantId) { restaurantId = mRestaurantId; }

        public String getRestaurantName() { return restaurantName; }

        public void setRestaurantName(String mRestaurantName) { restaurantName = mRestaurantName; }
    }

    public static class CoworkerRestaurantChoice {
        public String restaurantId;
        public String restaurantName;
        public Timestamp restaurantDateChoice;

        public CoworkerRestaurantChoice() {}

        public CoworkerRestaurantChoice(String mRestaurantId, String mRestaurantName, Timestamp mRestaurantDateChoice) {
            restaurantId = mRestaurantId;
            restaurantName = mRestaurantName;
            restaurantDateChoice = mRestaurantDateChoice;
        }

        public String getRestaurantId() {
            return restaurantId;
        }

        public void setRestaurantId(String mRestaurantId) {
            restaurantId = mRestaurantId;
        }

        public String getRestaurantName() {
            return restaurantName;
        }

        public void setRestaurantName(String mRestaurantName) {
            restaurantName = mRestaurantName;
        }

        public Timestamp getRestaurantDateChoice() {
            return restaurantDateChoice;
        }

        public void setRestaurantDateChoice(Timestamp mRestaurantDateChoice) {
            restaurantDateChoice = mRestaurantDateChoice;
        }
    }

    public enum Fields {
        Workmate,
        workmateId,
        workmateName,
        workmateEmail,
        workmatePhotoUrl,
        workmateRestaurantChosen,
        workmateFavorites
    }

}
