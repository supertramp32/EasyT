package com.seshra.everestcab.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.seshra.everestcab.data.AppRepository;

public class CreatePasswordActivityViewModel extends AndroidViewModel {


    AppRepository appRepository;

    public CreatePasswordActivityViewModel(@NonNull Application application) {
        super(application);

        appRepository = AppRepository.getOurInstance(application);
    }


    public void registerUser(String phone, String firstName, String lastName, String password) {

        appRepository.registerUser(phone, firstName, lastName, password);
    }

    public void recoverPassword(String phone, String password) {
        appRepository.recoverPassword(phone, password);
    }
}
