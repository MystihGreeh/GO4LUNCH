package com.mystihgreeh.go4lunch.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    /*private static final String BASE_URL = "https://maps.googleapis.com/maps/api/place/";
    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(.getClient())
            .build();

    public static ApiInterface getInterface() {
        return retrofit.create(ApiInterface.class);
    }*/


    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/place/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static Go4LunchApi getInterface() {
        return retrofit.create(Go4LunchApi.class);
    }


}
