package com.mystihgreeh.go4lunch.ViewModel;

import android.app.Application;
import android.database.Observable;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.firebase.ui.auth.ui.idp.WelcomeBackIdpPrompt;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.mystihgreeh.go4lunch.model.Restaurant;
import com.mystihgreeh.go4lunch.model.Workmate;
import com.mystihgreeh.go4lunch.repository.RestaurantRepository;
import com.mystihgreeh.go4lunch.repository.WorkmatesRepository;

import java.util.ArrayList;
import java.util.List;

public class ViewModelWorkmates extends androidx.lifecycle.ViewModel {

    /*private final WorkmatesRepository repository;

    @SuppressWarnings({"FieldCanBeLocal"})
    private MutableLiveData<Workmate> listOfMovies = new MutableLiveData<>();
    public ViewModelWorkmates(@NonNull Application application) {
        super();
        repository = new WorkmatesRepository();
    }
    public MutableLiveData<Workmate> getMoviesRepository(String category, int page) {
        listOfMovies = loadMoviesData(category, page);
        return listOfMovies;
    }
    private MutableLiveData<Workmate> loadMoviesData(String category, int page) {
        return repository.getListOfWorkmates(category, page);
    }*/

}
