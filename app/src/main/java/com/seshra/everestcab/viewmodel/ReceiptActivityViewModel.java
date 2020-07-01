package com.seshra.everestcab.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.seshra.everestcab.data.AppRepository;

public class ReceiptActivityViewModel extends AndroidViewModel {

    AppRepository appRepository;

    public ReceiptActivityViewModel(@NonNull Application application) {
        super(application);
        appRepository = AppRepository.getOurInstance(application);
    }

    public void getSpecificTripDetailApi(String bookingId) {

        appRepository.getSpecificTripDetailApi(bookingId);

    }


    public void getReciept(String bookingId) {

        appRepository.getReciept(bookingId);

    }

    public void rateDriver(String bookingId, float rating, String comments) {
        appRepository.rateDriver(bookingId,rating,comments);
    }
}
