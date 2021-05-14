package com.mystihgreeh.go4lunch.api;

import com.mystihgreeh.go4lunch.ViewModel.ViewModelFactory;
import com.mystihgreeh.go4lunch.repository.RestaurantRepository;
import com.mystihgreeh.go4lunch.repository.WorkmatesRepository;

public class Injection {




    private static WorkmatesRepository createWorkmateRepository ()
    {
        return new WorkmatesRepository();
    }

    private static RestaurantRepository createRestaurantRepository ()
    {
        return new RestaurantRepository();
    }

    public static ViewModelFactory viewModelFactory ()
    {
        return new ViewModelFactory(createRestaurantRepository(), createWorkmateRepository());
    }

}
