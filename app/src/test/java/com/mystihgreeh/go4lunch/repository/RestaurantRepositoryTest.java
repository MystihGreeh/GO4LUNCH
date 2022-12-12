package com.mystihgreeh.go4lunch.repository;

import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.initMocks;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.google.android.gms.maps.model.LatLng;
import com.mystihgreeh.go4lunch.model.Restaurants.Result;
import com.mystihgreeh.go4lunch.model.Workmates.Workmate;
import com.mystihgreeh.go4lunch.viewModel.SharedViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import java.util.ArrayList;

public class RestaurantRepositoryTest {

    public ArrayList<Result> restaurants;
    public Result restaurant1;
    public LatLng location;

    @InjectMocks
    public RestaurantRepository restaurantRepository = Mockito.mock(RestaurantRepository.class);

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setup() throws Exception {
        initMocks(this);

        restaurant1 = new Result("", null, "", "",
                "", "", null, null, null, null, null, null, null, null, null, null, null, null);
        restaurants = new ArrayList<>();
        restaurants.add(restaurant1);
        location = new LatLng(48.00, 2.00);
    }

    @Test
    public void getInstance() {
    }

    @Test
    public void getRestaurants() {
    }

    @Test
    public void getRestaurantDetails() {
    }
}