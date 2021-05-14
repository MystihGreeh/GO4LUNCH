package com.mystihgreeh.go4lunch.api;

import com.mystihgreeh.go4lunch.model.NearbySearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GooglePlacesApi {


    String BASE_URL_GOOGLE = "https://maps.googleapis.com/maps/api/place/nearbysearch/json/";
    String PHOTO_REF_GOOGLE = "photo?photoreference=";
    String MAX_WIDTH_GOOGLE = "&maxwidth=";
    String KEY_GOOGLE = "&key=";

    @GET("nearbysearch/json")
    Call<NearbySearchResponse> getNearByPlaces(
            @Query("key") int key,
            @Query("type") String type,
            @Query("location") String location,
            @Query("radius") int radius);

}
