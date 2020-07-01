package com.seshra.everestcab.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.seshra.everestcab.data.AppRepository;

public class DriverWaitingActivityViewModel extends AndroidViewModel {

    AppRepository appRepository;

    public DriverWaitingActivityViewModel(@NonNull Application application) {
        super(application);
        appRepository = AppRepository.getOurInstance(application);
    }

    public void checkBookingStatus( String b_id) {
        appRepository.checkBookingStatus(b_id);
    }

    public void callRideInfoApi(String bookingId) {
        appRepository.callRideInfoApi(bookingId);
    }

    public void startTracking(int bookingId) {
        appRepository.startTracking(bookingId);
    }

    public void checkAutoCancel(String id) {
        appRepository.checkAutoCancel(id);
    }

    public void fetchCancelReasons(String bookingId) {
        appRepository.fetchCancelReasons(bookingId);
    }

    public void cancelRide(int bookingId, int cancelId, String cancel_charges) {

        appRepository.cancelRide(bookingId,cancelId,cancel_charges);

    }

    public void customCancelRide(String bookingId) {

        appRepository.cancelRide(Integer.parseInt(bookingId),401,"0");


    }

//    public void FetchReciept(String bookingId) {
//        appRepository.getReciept(bookingId);
//    }
}
