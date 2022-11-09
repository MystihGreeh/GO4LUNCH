package com.mystihgreeh.go4lunch.repository;

import androidx.lifecycle.MutableLiveData;

import com.mystihgreeh.go4lunch.BuildConfig;
import com.mystihgreeh.go4lunch.api.GooglePlacesApi;
import com.mystihgreeh.go4lunch.api.RetrofitService;
import com.mystihgreeh.go4lunch.model.Restaurants.NearbySearchResponse;
import com.mystihgreeh.go4lunch.model.Restaurants.Result;
import com.mystihgreeh.go4lunch.model.RestaurantsDetails.DetailsRestaurantResponseApi;
import com.mystihgreeh.go4lunch.model.RestaurantsDetails.DetailsResult;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantRepository {

    private static final GooglePlacesApi myInterface = RetrofitService.getInterface();
    private final String key = BuildConfig.API_KEY;
    public MutableLiveData<List<Result>> restaurantListMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<DetailsResult> restaurantDetailsMutableLiveData = new MutableLiveData<>();
    private final List<Result> restaurants = new ArrayList<>();

    private static volatile RestaurantRepository INSTANCE;

    public static RestaurantRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RestaurantRepository();
        }
        return INSTANCE;
    }

    public MutableLiveData<List<Result>> getRestaurants(double currentLatitude, double currentLongitude) {
        int radius = 3000;
        String type = "restaurant";
        Call<NearbySearchResponse> restaurantCall = myInterface.getNearByPlaces(key, type, currentLatitude +","+ currentLongitude, radius);
        restaurantCall.enqueue(new Callback<NearbySearchResponse>() {

            @Override
            public void onResponse(@NotNull Call<NearbySearchResponse> call, @NotNull Response<NearbySearchResponse> response) {
                if (response.body() != null){
                for (int i =0; i<response.body().getResults().size(); i++)
                    response.body().getResults().get(i).calculateDistance(currentLatitude, currentLongitude);
                restaurants.addAll(response.body().getResults());
                restaurantListMutableLiveData.setValue(restaurants);}
            }

            @Override
            public void onFailure(@NotNull Call<NearbySearchResponse> call, @NotNull Throwable t) {
            }
        });
        return restaurantListMutableLiveData;
    }

    public MutableLiveData<DetailsResult> getRestaurantDetails(String placeId) {

        String fields = ("name,formatted_address,photos,formatted_phone_number,website,rating,place_id");
        Call<DetailsRestaurantResponseApi> restaurantResponseApiCall = myInterface.getRestaurantDetail(key, placeId, fields);
        restaurantResponseApiCall.enqueue(new Callback<DetailsRestaurantResponseApi>() {
            @Override
            public void onResponse(@NotNull Call<DetailsRestaurantResponseApi> call, @NotNull Response<DetailsRestaurantResponseApi> response) {
                restaurantDetailsMutableLiveData.postValue(response.body().getResult());
            }

            @Override
            public void onFailure(@NotNull Call<DetailsRestaurantResponseApi> call, @NotNull Throwable t) {
                System.out.println("call failed");
            }
        });
        return restaurantDetailsMutableLiveData;
    }

}
