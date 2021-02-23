package com.mystihgreeh.go4lunch.ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.mystihgreeh.go4lunch.repository.RestaurantRepository;
import com.mystihgreeh.go4lunch.repository.WorkmatesRepository;

public class ViewModelFactory {

    private RestaurantRepository restaurantRepository;
    private WorkmatesRepository workmatesRepository;


    public ViewModelFactory(RestaurantRepository restaurantRepository,
                            WorkmatesRepository workmatesRepository) {
        this.restaurantRepository = restaurantRepository;

    }

}
