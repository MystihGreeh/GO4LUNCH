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
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;

public class WorkmatesRepositoryTest {

    @Mock
    public WorkmatesRepository workmatesRepository = Mockito.mock(WorkmatesRepository.class);


    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();
    public Workmate user1;
    public Workmate user2;
    public Workmate user3;
    public Workmate user4;
    public Workmate user5;
    ArrayList<Workmate> workmates = new ArrayList<>();

    @Before
    public void setup() throws Exception {
        initMocks(this);
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
    }


    @Test
    public void getInstance() {
    }

    @Test
    public void getActualUser() {
    }

    @Test
    public void createUser() {
    }

    @Test
    public void getUser() {
        Mockito.when(workmatesRepository.getActualUser()).thenReturn(user1);
    }

    @Test
    public void getPickedRestaurant() {
    }

    @Test
    public void getUpdatedRestaurant() {
    }





    @Test
    public void getAllUsers() {
    }

    @Test
    public void checkIfUserDoesExist() {
    }

    @Test
    public void updateRestaurantPicked() {
    }

    @Test
    public void addLikedRestaurant() {
    }

    @Test
    public void removeLikedRestaurant() {
    }


}