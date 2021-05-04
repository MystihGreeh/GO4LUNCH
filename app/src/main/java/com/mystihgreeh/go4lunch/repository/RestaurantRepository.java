package com.mystihgreeh.go4lunch.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.mystihgreeh.go4lunch.api.GooglePlacesApi;
import com.mystihgreeh.go4lunch.api.RetrofitService;
import com.mystihgreeh.go4lunch.model.NearbySearchResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantRepository {

    private static GooglePlacesApi myInterface;
    private final MutableLiveData<NearbySearchResponse> listOfRestaurants = new MutableLiveData<>();
    String KEY = "key";
    String LOCATION = "location";
    String TYPE = "Restaurant";
    int RADIUS = 10;

    private static RestaurantRepository restaurantRepository;

    public static RestaurantRepository getInstance(){
        if (restaurantRepository == null){
            restaurantRepository = new RestaurantRepository();
        }
        return restaurantRepository;
    }

    public RestaurantRepository(){
        myInterface = RetrofitService.getInterface();
    }

    public MutableLiveData<NearbySearchResponse> getListOfRestaurants() {
        Call<NearbySearchResponse> listOfRestaurantsOutput = myInterface.getNearByPlaces(KEY, LOCATION, TYPE, RADIUS);
        listOfRestaurantsOutput.enqueue(new Callback<NearbySearchResponse>() {
            @Override
            public void onResponse(@NonNull Call<NearbySearchResponse> call, @NonNull Response<NearbySearchResponse> response) {
                listOfRestaurants.setValue(response.body());
            }
            @Override
            public void onFailure(@NonNull Call<NearbySearchResponse> call, @NonNull Throwable t) {
                listOfRestaurants.postValue(null);
            }
        });
        return listOfRestaurants;
    }
}
