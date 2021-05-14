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
import com.mystihgreeh.go4lunch.repository.SaveDataRepository;
import com.mystihgreeh.go4lunch.repository.WorkmatesRepository;

import java.util.ArrayList;

public class SharedViewModel extends AndroidViewModel {


    private MutableLiveData<ArrayList<Workmate>> workmates;
    private MutableLiveData<ArrayList<Restaurant>> restaurants;
    private RestaurantRepository restaurantRepository;
    private SaveDataRepository saveDataRepository;
    private WorkmatesRepository workmatesRepository;



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
    private MutableLiveData<NearbySearchResponse> listOfRestaurants = new MutableLiveData<>();
    public SharedViewModel(Application application) {
        super(application);
        repository = new RestaurantRepository();
    }
    public MutableLiveData<NearbySearchResponse> getRestaurantRepository() {
        listOfRestaurants = loadRestaurantsData();
        return listOfRestaurants;
    }
    private MutableLiveData<NearbySearchResponse> loadRestaurantsData() {
        return repository.getListOfRestaurants();
    }



    // --------------------------SHARED PREFERENCES ------------------------------------------------


}