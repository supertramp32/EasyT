package com.seshra.everestcab.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.seshra.everestcab.data.AppRepository;

public class CheckUserPhoneActivityViewModel extends AndroidViewModel {

    AppRepository appRepository;

    public CheckUserPhoneActivityViewModel(@NonNull Application application) {
        super(application);
        appRepository = AppRepository.getOurInstance(application);
    }


    public void checkUserPhone(String phone){

        appRepository.checkUserPhone(phone);

    }


    public void getOtp(String phone, String type) {
        appRepository.getOtp(phone, type);
    }
}
