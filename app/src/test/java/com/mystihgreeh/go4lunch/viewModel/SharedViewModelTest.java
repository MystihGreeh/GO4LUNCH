package com.mystihgreeh.go4lunch.viewModel;

import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.model.LatLng;
import com.mystihgreeh.go4lunch.UtilsTest.LiveDataTestUtils;
import com.mystihgreeh.go4lunch.model.Restaurants.Result;
import com.mystihgreeh.go4lunch.model.Workmates.Workmate;
import com.mystihgreeh.go4lunch.repository.RestaurantRepository;
import com.mystihgreeh.go4lunch.repository.WorkmatesRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class SharedViewModelTest {

    public ArrayList<Result> restaurants;
    public Result restaurant1;
    public LatLng location;

    @InjectMocks
    public WorkmatesRepository workmateRepository = Mockito.mock(WorkmatesRepository.class);
    @InjectMocks
    public RestaurantRepository restaurantRepository = Mockito.mock(RestaurantRepository.class);

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();
    public SharedViewModel viewModel;
    public Workmate user1;
    public Workmate user2;
    public Workmate user3;
    public Workmate user4;
    public Workmate user5;
    ArrayList<Workmate> workmates = new ArrayList<>();

    @Before
    public void setup() {
        initMocks(this);
        viewModel = new SharedViewModel(workmateRepository, restaurantRepository);
        user1 = new Workmate("001", "Jean DUPONT",
                "https://i.pinimg.com/originals/cd/71/1f/cd711fca60134229d08e3f8e6604674b.jpg",
                "jean@google.com", null, null, null);
        user2 = new Workmate("002", "Francois ALBERT",
                "https://pbs.twimg.com/profile_images/1040539611624341504/MEwklEJL_400x400.jpg",
                "francois@google.com", "La Criée", "0001", null);
        user3 = new Workmate("003", "Lucie RENARD",
                "https://pakistani.pk/uploads/reviews/photos/original/04/d6/58/Black-Widow-2-10-1582135965.jpg",
                "jean@google.com", "Le Bird", "0002", null);
        user4 = new Workmate("004", "Michel SARDOU",
                "https://pakistani.pk/uploads/reviews/photos/original/04/d6/58/Black-Widow-2-10-1582135965.jpg",
                "michel@google.com", null, null, null);
        user5 = new Workmate("005", "Aurélien DURAND",
                "https://pakistani.pk/uploads/reviews/photos/original/04/d6/58/Black-Widow-2-10-1582135965.jpg",
                "aurelien@google.com", null, null, null);

        workmates.add(user1);
        workmates.add(user2);
        workmates.add(user3);
        workmates.add(user4);

        restaurant1 = new Result("", null, "", "",
                "", "", null, null, "123456789", null, null, null, null, null, null, null, null, null);
        restaurants = new ArrayList<>();
        restaurants.add(restaurant1);
        location = new LatLng(48.00, 2.00);
    }



    @Test
    public void testGetUserRestaurant() {
        Mockito.when(workmateRepository.getPickedRestaurant()).thenReturn(restaurant1.getName());
        Assert.assertSame(viewModel.getUserRestaurant(), restaurant1.getName());
    }


    //Test Livedata
    @Test
    public void testGetRestaurantMutableLiveData() {
        MutableLiveData<List<Result>> restaurantsLiveData = new MutableLiveData<>();
        restaurantsLiveData.setValue(restaurants);
        Mockito.when(restaurantRepository.getRestaurants(location.latitude, location.longitude)).thenReturn(restaurantsLiveData);
        viewModel.fetchRestaurants(location.latitude, location.longitude);
        LiveDataTestUtils.observeForTesting(viewModel.getRestaurantMutableLiveData(), livedata -> assertEquals(viewModel.getRestaurantMutableLiveData(), restaurantsLiveData)
                );
    }


}