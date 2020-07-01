package com.seshra.everestcab.utils;

import android.content.Context;
import android.content.Intent;

import com.seshra.everestcab.SplashActivity;

public class Logout {


    SessionManager sessionManager;

    public Logout(Context context) {

        sessionManager = new SessionManager(context);
        sessionManager.logoutUser();
        sessionManager.cleartAccessToken("");
        context.startActivity(new Intent(context, SplashActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}
