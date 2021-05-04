package com.mystihgreeh.go4lunch.ViewModel;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mystihgreeh.go4lunch.model.NearbySearchResponse;
import com.mystihgreeh.go4lunch.model.Restaurant;
import com.mystihgreeh.go4lunch.model.Workmate;
import com.mystihgreeh.go4lunch.repository.RestaurantRepository;
import com.mystihgreeh.go4lunch.repository.WorkmatesRepository;

import java.util.ArrayList;

public class SharedViewModel extends AndroidViewModel {


    private MutableLiveData<ArrayList<Workmate>> workmates;
    private MutableLiveData<ArrayList<Restaurant>> restaurants;
    private RestaurantRepository restaurantRepository;

    //----------------------------- Get all workmates ----------------------------------------------
    public void initAllWorkmates(Context context){
        workmates = WorkmatesRepository.getInstance(context).getAllWorkmates();
    }

    public LiveData<ArrayList<Workmate>> getAllWorkmatesData(){
        return workmates;
    }
    //----------------------RESTAURANT-----------------------//

    private final RestaurantRepository repository;

    @SuppressWarnings({"FieldCanBeLocal"})
    private MutableLiveData<NearbySearchResponse> listOfMovies = new MutableLiveData<>();
    public SharedViewModel(Application application) {
        super(application);
        repository = new RestaurantRepository();
    }
    public MutableLiveData<NearbySearchResponse> getRestaurantRepository() {
        listOfMovies = loadMoviesData();
        return listOfMovies;
    }
    private MutableLiveData<NearbySearchResponse> loadMoviesData() {
        return repository.getListOfRestaurants();
    }


}