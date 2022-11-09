package com.mystihgreeh.go4lunch.api;

import com.mystihgreeh.go4lunch.viewModel.ViewModelFactory;
import com.mystihgreeh.go4lunch.repository.RestaurantRepository;
import com.mystihgreeh.go4lunch.repository.WorkmatesRepository;

public class Injection {

    public static WorkmatesRepository createWorkmateRepository ()
    {
        return new WorkmatesRepository();
    }

    public static RestaurantRepository createRestaurantRepository() {
        RestaurantRepository.getInstance();

        return new RestaurantRepository();
    }


    public static ViewModelFactory viewModelFactory ()
    {
        return new ViewModelFactory(createWorkmateRepository(), createRestaurantRepository());
    }




}
