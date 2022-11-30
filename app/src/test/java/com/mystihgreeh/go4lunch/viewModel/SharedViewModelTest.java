package com.mystihgreeh.go4lunch.viewModel;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.model.LatLng;
import com.mystihgreeh.go4lunch.model.Restaurants.Result;
import com.mystihgreeh.go4lunch.model.Workmates.Workmate;
import com.mystihgreeh.go4lunch.repository.RestaurantRepository;
import com.mystihgreeh.go4lunch.repository.WorkmatesRepository;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;


public class SharedViewModelTest extends TestCase {

    public SharedViewModel viewModel;
    public Workmate user1;
    public Workmate user2;
    public Workmate user3;
    public Workmate user4;
    public Workmate user5;
    ArrayList<Workmate> workmates = new ArrayList<>();
    public MutableLiveData<List<Workmate>> workmatesListMutableLiveData = new MutableLiveData<>();
    public ArrayList<Result> restaurants;
    public Result restaurant1;
    public String restaurantId;
    public LatLng location;

    @Mock
    private WorkmatesRepository workmateRepository;
    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private Context context;

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setup() {
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
        workmates.add(user5);

        //Mockito.doReturn(user1).when(workmateRepository).getActualUser();


        restaurantId = "0001";
        restaurant1 = new Result("", null, "", "",
                "", "", null, null, null, null, null, null, null, null, null, null, null, null);
        restaurants = new ArrayList<>();
        restaurants.add(restaurant1);
        location = new LatLng(48.00, 2.00);
    }

    public void testGetCurrentUser() {
        //workmatesListMutableLiveData.setValue(workmates);
        Mockito.doReturn(workmates).when(workmateRepository.getAllUsers());
        //Mockito.doReturn(user1.getRestaurantUid()).when(workmateRepository).getRestaurantId(user1.getUid());
        viewModel = new SharedViewModel(workmateRepository, restaurantRepository);
        Assert.assertSame(workmates, viewModel.getWorkmates());
    }


    public void testGetWorkmates() {

    }


    public void testCreateWorkmate() {

    }

    public void testGetSelectedRestaurant() {

    }


    public void testGetUserRestaurant() {

    }


    public void testGetRestaurantId() {

    }


    public void testGetUserListFromFirebase() {

    }


    public void testCheckIfUserExist() {

    }


    public void testGetRestaurantMutableLiveData() {
    }

    public void testFetchRestaurants() {
    }


    public void testFetchWorkmateIsGoing() {
    }

    public void testFetchPickedRestaurants() {
    }

    public void testDisconnect() {
    }

    public void testGetupdatedRestaurant() {
    }
}