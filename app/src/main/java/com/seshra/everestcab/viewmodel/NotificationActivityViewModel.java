package com.seshra.everestcab.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.seshra.everestcab.data.AppRepository;

public class NotificationActivityViewModel extends AndroidViewModel {

    AppRepository appRepository;

    public NotificationActivityViewModel(@NonNull Application application) {
        super(application);
        appRepository = AppRepository.getOurInstance(application);
    }

    public void getGeneralNotifications() {
        appRepository.getGeneralNotifications();
    }
}
