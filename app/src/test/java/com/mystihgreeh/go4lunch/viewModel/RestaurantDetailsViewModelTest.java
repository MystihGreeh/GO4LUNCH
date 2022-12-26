package com.mystihgreeh.go4lunch.viewModel;

import static org.mockito.MockitoAnnotations.initMocks;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.model.LatLng;
import com.mystihgreeh.go4lunch.UtilsTest.LiveDataTestUtils;
import com.mystihgreeh.go4lunch.model.RestaurantsDetails.DetailsResult;
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

public class RestaurantDetailsViewModelTest  {

    public ArrayList<DetailsResult> restaurants;
    public DetailsResult restaurant1;
    public LatLng location;

    @InjectMocks
    public WorkmatesRepository workmateRepository = Mockito.mock(WorkmatesRepository.class);
    @InjectMocks
    public RestaurantRepository restaurantRepository = Mockito.mock(RestaurantRepository.class);


    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();
    public RestaurantDetailsViewModel viewModel;
    public Workmate user1;
    public Workmate user2;
    public Workmate user3;
    public Workmate user4;
    public Workmate user5;
    ArrayList<Workmate> workmatesList = new ArrayList<>();
    MutableLiveData<ArrayList<Workmate>> workmatesEatingThere = new MutableLiveData<>();


    @Before
    public void setup(){
        initMocks(this);
        viewModel = new RestaurantDetailsViewModel(workmateRepository, restaurantRepository);
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

        workmatesList.add(user1);
        workmatesList.add(user2);
        workmatesList.add(user3);
        workmatesList.add(user4);
        workmatesEatingThere.setValue(workmatesList);

        restaurant1 = new DetailsResult(null, null, null , null, null, null, null);
        restaurants = new ArrayList<>();
        restaurants.add(restaurant1);
        location = new LatLng(48.00, 2.00);
    }

    //Test Livedata
    @Test
    public void testGetRestaurantDetailsMutableLiveData() {
        MutableLiveData<DetailsResult> restaurantLiveData = new MutableLiveData<>();
        MutableLiveData<Boolean> booleanLiveData = new MutableLiveData<>();
        restaurantLiveData.setValue(restaurant1);
        Mockito.when(restaurantRepository.getRestaurantDetails(restaurant1.getPlaceId())).thenReturn(restaurantLiveData);
        Mockito.when(workmateRepository.getActualUser()).thenReturn(user1);
        Mockito.when(workmateRepository.user()).thenReturn(user1);
        Mockito.when(workmateRepository.getLikedRestaurant(user1.getUid(), restaurant1.getPlaceId())).thenReturn(booleanLiveData);
        viewModel.initViewModel();
        viewModel.fetchRestaurantsDetails(restaurant1.getPlaceId());
        LiveDataTestUtils.observeForTesting(viewModel.getRestaurantDetailsMutableLiveData(restaurant1.getPlaceId()),
                livedata -> Assert.assertEquals(viewModel.getRestaurantDetailsMutableLiveData(restaurant1.getPlaceId()), restaurantLiveData)
        );
    }


    @Test
    public void testGetUserId() {
        Mockito.when(workmateRepository.getActualUser()).thenReturn(user1);
        Mockito.when(workmateRepository.user()).thenReturn(user1);
        viewModel.initViewModel();
        Assert.assertSame(viewModel.getUserId(), user1.getUid());
    }


}