package com.mystihgreeh.go4lunch.api;

import com.mystihgreeh.go4lunch.model.Restaurants.NearbySearchResponse;
import com.mystihgreeh.go4lunch.model.RestaurantsDetails.DetailsRestaurantResponseApi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GooglePlacesApi {


    String BASE_URL_GOOGLE = "https://maps.googleapis.com/maps/api/place/";
    String PHOTO_REF_GOOGLE = "photo?photoreference=";
    String MAX_WIDTH_GOOGLE = "&maxwidth=";
    String KEY_GOOGLE = "&key=";

    @GET("nearbysearch/json")
    Call<NearbySearchResponse> getNearByPlaces(
            @Query("key") String key,
            @Query("type") String type,
            @Query("location") String location,
            @Query("radius") int radius);

    @GET("details/json")
    Call<DetailsRestaurantResponseApi> getRestaurantDetail(
            @Query("key") String pKey,
            @Query("place_id") String pPlaceId,
            @Query("fields") String pFields);

}
