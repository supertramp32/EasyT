package com.seshra.everestcab.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.seshra.everestcab.data.AppRepository;

public class ActiveRideFragmentViewModel extends AndroidViewModel {


    AppRepository appRepository;

    public ActiveRideFragmentViewModel(@NonNull Application application) {
        super(application);

        appRepository = AppRepository.getOurInstance(application);

    }



    public void getActiveHistoryRides(){
        appRepository.getActiveHistoryRides();
    }

}
