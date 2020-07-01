package com.seshra.everestcab.service;

import android.app.Application;
import android.content.Context;

import com.onesignal.OneSignal;


public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();


        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();








    }







    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);

    }
}