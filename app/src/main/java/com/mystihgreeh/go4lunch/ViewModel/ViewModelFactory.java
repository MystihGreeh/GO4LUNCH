package com.mystihgreeh.go4lunch.ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.mystihgreeh.go4lunch.repository.RestaurantRepository;
import com.mystihgreeh.go4lunch.repository.WorkmatesRepository;

import org.jetbrains.annotations.NotNull;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final RestaurantRepository restaurantRepository;
    private final WorkmatesRepository userRepository;
    private RestaurantRepository restaurantPlacesRepository;

    public ViewModelFactory(RestaurantRepository restaurantRepository,
                            WorkmatesRepository userRepository) {
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @NonNull
    @NotNull
    @Override
    public <T extends ViewModel> T create(@NonNull @NotNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SharedViewModel.class))
        {
            return (T) new SharedViewModel(this.restaurantRepository, this.userRepository, this.restaurantPlacesRepository);
        }
        throw new IllegalArgumentException("Problem with ViewModelFactory");
    }
}
