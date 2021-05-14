package com.mystihgreeh.go4lunch.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    static HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    static OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/place/nearbysearch/json/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static GooglePlacesApi getInterface() {
        return retrofit.create(GooglePlacesApi.class);
    }


}