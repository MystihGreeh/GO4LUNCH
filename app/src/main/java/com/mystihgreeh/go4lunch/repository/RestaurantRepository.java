package com.mystihgreeh.go4lunch.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.mystihgreeh.go4lunch.R;
import com.mystihgreeh.go4lunch.api.GooglePlacesApi;
import com.mystihgreeh.go4lunch.api.RetrofitService;
import com.mystihgreeh.go4lunch.model.NearbySearchResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantRepository {

    private static GooglePlacesApi googlePlacesApi;
    private final MutableLiveData<NearbySearchResponse> listOfRestaurants = new MutableLiveData<>();
    int KEY = R.string.google_key;
    String LOCATION = "48.68478497157418, 2.4085079624649626";
    String TYPE = "Restaurant";
    int RADIUS = 300;

    private static RestaurantRepository restaurantRepository;

    public static RestaurantRepository getInstance(){
        if (restaurantRepository == null){
            restaurantRepository = new RestaurantRepository();
        }
        return restaurantRepository;
    }

    public RestaurantRepository(){
        googlePlacesApi = RetrofitService.getInterface();
    }

    public MutableLiveData<NearbySearchResponse> getListOfRestaurants() {
        Call<NearbySearchResponse> listOfRestaurantsOutput = googlePlacesApi.getNearByPlaces(KEY, LOCATION, TYPE, RADIUS);
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
