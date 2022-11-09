
package com.mystihgreeh.go4lunch.model.Workmates;

import androidx.annotation.Nullable;

import com.google.firebase.database.PropertyName;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Workmate implements Serializable {


    private String uid;
    @PropertyName("username")
    private String username;
    @PropertyName("urlPicture")
    @Nullable private String urlPicture;
    @PropertyName("useremail")
    private String useremail;
    @Nullable
    private String restaurantUid;
    @Nullable
    private String restaurantName;
    @Nullable
    private String restaurantAddress;

    @Nullable
    private List<String> likedRestaurants;

    public Workmate() { }

    public Workmate(String uid, String username, @org.jetbrains.annotations.Nullable String urlPicture, String useremail, @Nullable String restaurantName, @Nullable String restaurantUid, @Nullable String restaurantAddress) {
        this.uid = uid;
        this.username = username;
        this.urlPicture = urlPicture;
        this.useremail = useremail;
        this.restaurantName = restaurantName;
        this.restaurantUid = restaurantUid;
        this.restaurantAddress = restaurantAddress;
    }

    // --- GETTERS ---

    public String getUid() { return uid; }
    @PropertyName("username")
    public String getUsername() { return username; }
    @org.jetbrains.annotations.Nullable
    @PropertyName("urlPicture")
    public String getUrlPicture() { return urlPicture; }
    @PropertyName("usermail")
    public String getUseremail() { return useremail; }
    @Nullable
    public String getRestaurantName() {return restaurantName;}
    @Nullable
    public String getRestaurantUid() {return restaurantUid;}
    @Nullable
    public String getRestaurantAddress() {return restaurantAddress;}



    // --- SETTERS ---

    public void setUid(String uid) { this.uid = uid; }
    @PropertyName("username")
    public void setUsername(String username) { this.username = username; }
    @PropertyName("urlPicture")
    public void setUrlPicture(@org.jetbrains.annotations.Nullable String urlPicture) { this.urlPicture = urlPicture; }
    public void setUseremail(String useremail){this.useremail = useremail;}
    //public void setRestaurantUid(@Nullable String restaurantUid) { this.restaurantUid = restaurantUid;}



    @Nullable
    public List<String> getLikedRestaurants() {
        return likedRestaurants;
    }

    public void addLikedRestaurant(String restaurantUid) {
        if (likedRestaurants == null) {
            this.likedRestaurants = new ArrayList<>();
        }
        this.likedRestaurants.add(restaurantUid);
    }

    public void removeLikedRestaurant(String restaurantUid){
        if(likedRestaurants != null) {
            int position = 0;
            for (String uid : likedRestaurants) {
                if (uid.equals(restaurantUid)) likedRestaurants.remove(position);
                position += 1;
            }
        }
    }


    public void setRestaurantName(@Nullable String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public void setRestaurantAddress(@Nullable String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

    public void setRestaurantUid(@Nullable String restaurantUid) {
        this.restaurantUid = restaurantUid;
    }


    @NotNull
    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", username='" + this.username + '\'' +
                ", email='" + this.useremail + '\'' +
                ", urlPicture='" + this.urlPicture + '\''+
                ", restaurantUid='" + restaurantUid + '\'' +
                ", likedRestaurants=" + likedRestaurants +
                '}';
    }


}