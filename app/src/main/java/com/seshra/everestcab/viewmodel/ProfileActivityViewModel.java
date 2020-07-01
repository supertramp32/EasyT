package com.seshra.everestcab.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.seshra.everestcab.data.AppRepository;

public class ProfileActivityViewModel extends AndroidViewModel {

    AppRepository appRepository;


    public ProfileActivityViewModel(@NonNull Application application) {
        super(application);
        appRepository = AppRepository.getOurInstance(application);
    }

    public void editProfile(String fullName, String email, String gender,String image, int type) {
        appRepository.editProfile(fullName, email, gender,image,type);
    }
}
