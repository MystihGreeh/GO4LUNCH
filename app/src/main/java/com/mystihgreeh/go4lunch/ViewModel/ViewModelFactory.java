package com.mystihgreeh.go4lunch.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.mystihgreeh.go4lunch.repository.RestaurantRepository;
import com.mystihgreeh.go4lunch.repository.WorkmatesRepository;

import org.jetbrains.annotations.NotNull;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final RestaurantRepository restaurantRepository;
    private final WorkmatesRepository workmatesRepository;
    private Application application;


    public ViewModelFactory(RestaurantRepository restaurantRepository, WorkmatesRepository workmatesRepository) {
        this.restaurantRepository = restaurantRepository;
        this.workmatesRepository = workmatesRepository;

    }

    @NonNull
    @NotNull
    @Override
    public <T extends ViewModel> T create(@NonNull @NotNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SharedViewModel.class)) {
            return (T) new SharedViewModel(application);
        }

        throw new IllegalArgumentException("Problem with ViewModelFactory");
    }
}
