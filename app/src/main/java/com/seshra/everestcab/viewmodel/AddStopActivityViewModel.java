package com.seshra.everestcab.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.seshra.everestcab.data.AppRepository;

public class AddStopActivityViewModel extends AndroidViewModel {

    AppRepository appRepository;

    public AddStopActivityViewModel(@NonNull Application application) {
        super(application);
        appRepository = AppRepository.getOurInstance(application);
    }

    public void getCurrentLocationName(Double lattitude, Double longitude){
        appRepository.getCurrentLocationName(lattitude,longitude);
    }
}
