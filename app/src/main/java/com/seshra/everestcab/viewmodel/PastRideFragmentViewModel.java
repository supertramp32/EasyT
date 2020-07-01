package com.seshra.everestcab.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.seshra.everestcab.data.AppRepository;

public class PastRideFragmentViewModel extends AndroidViewModel {

    AppRepository appRepository;


    public PastRideFragmentViewModel(@NonNull Application application) {
        super(application);

        appRepository = AppRepository.getOurInstance(application);
    }

    public void getPastRides(String pageNo){
        appRepository.getPastRides(pageNo);
    }


}
