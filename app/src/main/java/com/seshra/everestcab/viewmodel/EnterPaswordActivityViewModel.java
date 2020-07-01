package com.seshra.everestcab.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.seshra.everestcab.data.AppRepository;

public class EnterPaswordActivityViewModel extends AndroidViewModel {

    AppRepository appRepository;

    public EnterPaswordActivityViewModel(@NonNull Application application) {
        super(application);
        appRepository = AppRepository.getOurInstance(application);
    }

    public void checkForLogin(String phone, String password){
        appRepository.checkForLogin(phone,password);
    }

    public void fetchConfig() {
        appRepository.fetchRemoteConfig();
    }
}
