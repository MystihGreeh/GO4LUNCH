package com.mystihgreeh.go4lunch.repository;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;
import com.mystihgreeh.go4lunch.UtilsTest.LiveDataTestUtils;
import com.mystihgreeh.go4lunch.model.Restaurants.Result;
import com.mystihgreeh.go4lunch.model.Workmates.Workmate;
import com.mystihgreeh.go4lunch.model.Workmates.WorkmateHelper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;

public class WorkmatesRepositoryTest {

    @Mock
    public WorkmatesRepository workmatesRepository = Mockito.mock(WorkmatesRepository.class);

    public WorkmateHelper workmateHelper = Mockito.mock(WorkmateHelper.class);


    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();
    public Workmate user1;
    public Workmate user2;
    public Workmate user3;
    public Workmate user4;
    public Workmate user5;
    public Result restaurant1;
    ArrayList<Workmate> workmates = new ArrayList<>();
    @Mock
    private Task<QuerySnapshot> mockedUserTask;


    @Before
    public void setup() throws Exception {
        given(workmateHelper.getAllWorkmates()).willReturn(mockedUserTask);

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

        restaurant1 = new Result("", null, "", "",
                "", "", null, null, "123456789", null, null, null, null, null, null, null, null, null);
    }



    @Test
    public void getPickedRestaurant() {
        Mockito.when(workmateHelper.getRestaurantUserId()).thenReturn(user1.getRestaurantUid());
        Assert.assertEquals(workmatesRepository.getPickedRestaurant(), user1.getRestaurantUid());
    }

    

    @Test
    public void testGetLikedRestaurant() {
        MutableLiveData<Boolean> likedRestaurant = new MutableLiveData<>();
        workmateHelper.checkIfLiked(user1.getUid(), restaurant1.getPlaceId());
        likedRestaurant.setValue(null);
        Mockito.when(workmateHelper.getLikedRestaurants(user1.getUid(), restaurant1.getPlaceId())).thenReturn(likedRestaurant);
        Mockito.when(workmateHelper.isLiked()).thenReturn(likedRestaurant);
        LiveDataTestUtils.observeForTesting(workmateHelper.isLiked(), livedata ->
                assertEquals(workmateHelper.isLiked, likedRestaurant.getValue())
        );
    }

}