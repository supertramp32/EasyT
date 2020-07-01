package com.seshra.everestcab.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.seshra.everestcab.data.AppRepository;

public class MainActivityViewModel extends AndroidViewModel {

    AppRepository appRepository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        appRepository = AppRepository.getOurInstance(application);
    }

    public void rideNow(String pickObj, String dropObj) {
        appRepository.rideNow(pickObj, dropObj);
    }


    public void getCurrentLocationName(Double lattitude, Double longitude){
        appRepository.getCurrentLocationName(lattitude,longitude);
    }

    public void confirmBooking( int checkOutId) {
        appRepository.confirmBooking(checkOutId);

    }

    public void logOutUser() {

        appRepository.logOutUser();
    }

    public void checkServiceAvailability(double latitude, double longitude) {
        appRepository.checkServiceAvailability(latitude,longitude);
    }

    public void applyPromoCode(int bookingId, String promoCode) {
        appRepository.applyPromoCode(Integer.toString(bookingId),promoCode);
    }
}
