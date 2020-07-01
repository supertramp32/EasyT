package com.seshra.everestcab.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.seshra.everestcab.data.AppRepository;

public class SearchLocationViewModel extends AndroidViewModel {


    AppRepository appRepository;

    public SearchLocationViewModel(@NonNull Application application) {
        super(application);
        appRepository = AppRepository.getOurInstance(application);
    }


    public void getGooglePlacesApi(String searchLocation, String url) {
        appRepository.getGooglePlaces(url, searchLocation);
    }

    public void fetchFavouritesLocations() {
        appRepository.fetchFavouritesLocations();
    }

    public void fetchPopularLocations(String LATITUDE, String LONGITUDE) {
        appRepository.fetchPopularLocations(LATITUDE, LONGITUDE);
    }
}
