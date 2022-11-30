package com.mystihgreeh.go4lunch.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.mystihgreeh.go4lunch.repository.RestaurantRepository;
import com.mystihgreeh.go4lunch.repository.WorkmatesRepository;

public class ViewModelFactory extends SharedViewModel implements ViewModelProvider.Factory {


    private final RestaurantRepository restaurantRepository;
    private final WorkmatesRepository workmatesRepository;

    public ViewModelFactory(WorkmatesRepository mWorkmatesRepository, RestaurantRepository mRestaurantRepository) {
        super(mWorkmatesRepository, mRestaurantRepository);
        this.restaurantRepository = mRestaurantRepository;
        this.workmatesRepository = mWorkmatesRepository;


    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(SharedViewModel.class)) {
            return (T) new SharedViewModel(workmatesRepository, restaurantRepository);
        }

        if (modelClass.isAssignableFrom(RestaurantDetailsViewModel.class)) {
            return (T) new RestaurantDetailsViewModel(workmatesRepository, restaurantRepository);
        }

        if (modelClass.isAssignableFrom(SettingsViewModel.class)) {
            return (T) new SettingsViewModel();
        }

        throw new IllegalArgumentException("Problem with ViewModelFactory");
    }


}
