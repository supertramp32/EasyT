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

    public void rideNow(String pickObj, String dropObj,String stopObj) {
        appRepository.rideNow(pickObj, dropObj,stopObj);
    }


    public void getCurrentLocationName(Double lattitude, Double longitude){
        appRepository.getCurrentLocationName(lattitude,longitude);
    }

    public void confirmBooking( int checkOutId, String rideType) {
        appRepository.confirmBooking(checkOutId,rideType);

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
