package com.mystihgreeh.go4lunch.ViewModel;

import com.mystihgreeh.go4lunch.repository.RestaurantRepository;
import com.mystihgreeh.go4lunch.repository.WorkmatesRepository;

public class ViewModelFactory {


    private RestaurantRepository restaurantRepository;
    private WorkmatesRepository workmatesRepository;


    public ViewModelFactory(RestaurantRepository restaurantRepository,
                            WorkmatesRepository workmatesRepository) {
        this.restaurantRepository = restaurantRepository;
        this.workmatesRepository = workmatesRepository;

    }
}
