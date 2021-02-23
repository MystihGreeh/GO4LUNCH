package com.mystihgreeh.go4lunch.api;

import com.mystihgreeh.go4lunch.ViewModel.ViewModelFactory;
import com.mystihgreeh.go4lunch.repository.RestaurantRepository;
import com.mystihgreeh.go4lunch.repository.WorkmatesRepository;

public class Injection {

    private static RestaurantRepository createRestaurantFirebaseRepository() {
        return new RestaurantRepository();
    }

    private static WorkmatesRepository createUserFirebaseRepository () {
        return new WorkmatesRepository();
    }


    public static ViewModelFactory getViewModelFactory() {
        return new ViewModelFactory(createRestaurantFirebaseRepository(), createUserFirebaseRepository());
    }

}
