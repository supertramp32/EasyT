package com.seshra.everestcab.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.seshra.everestcab.data.AppRepository;

public class SaveLocationActivityViewModel extends AndroidViewModel {

    AppRepository appRepository;


    public SaveLocationActivityViewModel(@NonNull Application application) {
        super(application);
        appRepository = AppRepository.getOurInstance(application);
    }


    public void saveLocation(double lat, double lon, String locationName, String favName) {
        appRepository.saveLocation(lat,lon,locationName,favName);
    }
}
