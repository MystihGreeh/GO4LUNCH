package com.mystihgreeh.go4lunch.repository;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mockStatic;

import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.model.LatLng;
import com.mystihgreeh.go4lunch.BuildConfig;
import com.mystihgreeh.go4lunch.UtilsTest.LiveDataTestUtils;
import com.mystihgreeh.go4lunch.api.GooglePlacesApi;
import com.mystihgreeh.go4lunch.api.RetrofitService;
import com.mystihgreeh.go4lunch.model.Restaurants.NearbySearchResponse;
import com.mystihgreeh.go4lunch.model.Restaurants.Result;
import com.mystihgreeh.go4lunch.model.RestaurantsDetails.DetailsResult;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class RestaurantRepositoryTest {

    private static final String TYPE_KEYWORD = "restaurant";
    public List<Result> restaurants;
    public Result restaurant1;
    public NearbySearchResponse nearbySearch;
    public DetailsResult restaurant2;
    public LatLng location;
    public String key = BuildConfig.API_KEY;
    MutableLiveData<DetailsResult> detailResult = new MutableLiveData<>();
    MutableLiveData<List<Result>> restaurantList = new MutableLiveData<>();
    @InjectMocks
    public RestaurantRepository restaurantRepository = Mockito.mock(RestaurantRepository.class);
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @InjectMocks
    public GooglePlacesApi apiInterface = Mockito.mock(GooglePlacesApi.class);

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @InjectMocks
    private final Call mockedCall = Mockito.mock(Call.class);

    @InjectMocks
    private final Response mockedResponse = Mockito.mock(Response.class);

    @InjectMocks
    private final NearbySearchResponse mockedRestaurantResult = Mockito.mock(NearbySearchResponse.class);

    @Before
    public void setUp() {



        restaurant1 = new Result("", null, "", "",
                "", "TestRestaurant1", null, null, "123456789", null, null, null, null, null, null, null, null, null);
        restaurants = new ArrayList<>();
        restaurant2 = new DetailsResult(null, null, "TestRestaurant2", null, "987654321", null, null);
        detailResult.setValue(restaurant2);
        restaurants.add(restaurant1);
        location = new LatLng(48.00, 2.00);
        restaurantList.setValue(restaurants);
        nearbySearch = new NearbySearchResponse(null, restaurants, null);
        Mockito.when(apiInterface.getNearByPlaces(key, TYPE_KEYWORD, location.toString(), 10))
                .thenReturn(mockedCall);
        Mockito.when(apiInterface.getRestaurantDetail(key, restaurant2.getPlaceId(),  "name,formatted_address,photos,formatted_phone_number,website,rating,place_id"))
                .thenReturn(mockedCall);
        Mockito.when(mockedResponse.body()).thenReturn(mockedRestaurantResult);
        Mockito.when(restaurantRepository.getRestaurants(location.latitude, location.longitude)).thenReturn(restaurantList);
        Mockito.when(restaurantRepository.getRestaurantDetails(restaurant2.getPlaceId())).thenReturn(detailResult);

        Mockito.when(mockedRestaurantResult.getResults()).thenReturn(restaurants);
    }


    // TESTING FOR THE RESULT RESPONSE
    @Test
    public void nominal_case() {
        try(MockedStatic<RetrofitService> retrofit = mockStatic(RetrofitService.class)) {
            retrofit.when(RetrofitService :: getInterface).thenReturn(apiInterface);
            MutableLiveData<List<Result>> result = restaurantRepository.getRestaurants(location.latitude, location.longitude);
            LiveDataTestUtils.observeForTesting(result, liveData ->
                    assertEquals(restaurantList, liveData));
        }
    }

    @Test
    public void onFailure_case() {
        restaurants.clear();
        nearbySearch.setResults(restaurants);
        try(MockedStatic<RetrofitService> retrofit = mockStatic(RetrofitService.class);
            MockedStatic<Log> log = mockStatic(Log.class)) {
            retrofit.when(RetrofitService :: getInterface).thenReturn(apiInterface);
            // Manage log in the onFailure
            log.when(() -> Log.e(anyString(), anyString(), any())).thenReturn(0);
            // Put the enqueued callback on failure
            lenient().when(mockedCall.isCanceled()).thenReturn(true);

            // Given
            // Let's call the repository method
            MutableLiveData<List<Result>> result = restaurantRepository.getRestaurants(location.latitude, location.longitude);

            // Then
            // Assert the result is posted to the LiveData
            LiveDataTestUtils.observeForTesting(result, liveData -> assertEquals(new ArrayList<>(), liveData.getValue()));
        }

    }


    // TESTING FOR THE DETAILS RESULT RESPONSE

    @Test
    public void details_nominal_case() {
        try(MockedStatic<RetrofitService> retrofit = mockStatic(RetrofitService.class)) {
            retrofit.when(RetrofitService :: getInterface).thenReturn(apiInterface);
            MutableLiveData<DetailsResult> result = restaurantRepository.getRestaurantDetails(restaurant2.getPlaceId());
            LiveDataTestUtils.observeForTesting(result, liveData ->
                    assertEquals(detailResult, liveData));
        }
    }

    @Test
    public void detail_result_onFailure_case() {
       detailResult.setValue(restaurant2);
        try(MockedStatic<RetrofitService> retrofit = mockStatic(RetrofitService.class);
            MockedStatic<Log> log = mockStatic(Log.class)) {
            retrofit.when(RetrofitService :: getInterface).thenReturn(apiInterface);
            // Manage log in the onFailure
            log.when(() -> Log.e(anyString(), anyString(), any())).thenReturn(0);
            // Put the enqueued callback on failure
            lenient().when(mockedCall.isCanceled()).thenReturn(true);

            // Given
            // Let's call the repository method
            MutableLiveData<DetailsResult> result = restaurantRepository.getRestaurantDetails(restaurant2.getPlaceId());

            // Then
            // Assert the result is posted to the LiveData
            LiveDataTestUtils.observeForTesting(result, liveData -> assertEquals(detailResult, liveData));
        }

    }
}