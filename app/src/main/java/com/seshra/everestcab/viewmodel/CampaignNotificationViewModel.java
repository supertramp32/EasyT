package com.seshra.everestcab.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.seshra.everestcab.data.AppRepository;

public class CampaignNotificationViewModel extends AndroidViewModel {

    AppRepository appRepository;

    public CampaignNotificationViewModel(@NonNull Application application) {
        super(application);
        appRepository = AppRepository.getOurInstance(application);
    }

    public void getCampaignNotification() {
        appRepository.getCampaignNotification();
    }


}
