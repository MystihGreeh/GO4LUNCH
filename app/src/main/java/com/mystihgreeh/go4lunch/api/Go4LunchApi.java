package com.mystihgreeh.go4lunch.api;

import android.location.Location;

import com.mystihgreeh.go4lunch.model.Restaurant;

import java.util.List;

public interface Go4LunchApi {

    void setRestaurantId(String mRestaurantId);

    String getRestaurantId();

    void setRestaurant(Restaurant mRestaurant);

    Restaurant getRestaurant();

    void setRestaurantList(List<Restaurant> mRestaurantList);

    List<Restaurant> getRestaurantList();

    void setLocation(Location mLocation);

    Location getLocation();
}
