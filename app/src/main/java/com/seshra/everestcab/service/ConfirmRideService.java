package com.seshra.everestcab.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.seshra.everestcab.models.ModelResultCheck;
import com.seshra.everestcab.utils.API_S_NEW;
import com.seshra.everestcab.utils.Logout;
import com.seshra.everestcab.utils.SessionManager;
import com.seshra.everestcab.utils.SingletonGson;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

import static com.seshra.everestcab.service.CheckOutService.REWRITE_RESPONSE_INTERCEPTOR_OFFLINE;


public class ConfirmRideService extends IntentService {

    private static final String ACTION_CONFIRM_RIDE = "com.seshra.user.service.action.CONFIRM_RIDE";

    private static final String CHECKOUT_ID = "com.seshra.user.service.extra.CHECKOUT_ID";
    private static final String RIDE_TYPE = "com.seshra.user.service.extra.RIDE_TYPE";


    public ConfirmRideService() {
        super("ConfirmRideService");
    }

    public static final String CONFIRM_RIDE_MESSAGE ="ConfirmRideMessage" ;
    public static final String CONFIRM_RIDE_MESSAGE_KEY = "confirmMessage" ;


    SessionManager sessionManager;

    public static void startActionConfirmRide(Context context, int checkoutId, String rideType) {
        Intent intent = new Intent(context, ConfirmRideService.class);
        intent.setAction(ACTION_CONFIRM_RIDE);
        intent.putExtra(CHECKOUT_ID, checkoutId);
        intent.putExtra(RIDE_TYPE, rideType);
        context.startService(intent);
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_CONFIRM_RIDE.equals(action)) {
                final int checkoutId = intent.getIntExtra(CHECKOUT_ID,0);
                final String rideType = intent.getStringExtra(RIDE_TYPE);
                handleActionConfirmRide(checkoutId,rideType);
            }
        }
    }


    private void handleActionConfirmRide(int checkoutId, String rideType) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sessionManager = new SessionManager(getApplicationContext());

        HashMap<String, String> data = new HashMap<>();
        data.put("checkout", "" + checkoutId);
        data.put("billing_type", rideType);


        String additionalText = sharedPreferences.getString("details", "null");

        if(additionalText!="null"){

        data.put("additional_notes",additionalText);

        }





        File httpCacheDirectory = new File(getApplicationContext().getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 Mi

        TimeUnit time = TimeUnit.MILLISECONDS;
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .addInterceptor(REWRITE_RESPONSE_INTERCEPTOR_OFFLINE)
                .cache(new Cache(httpCacheDirectory,cacheSize))
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                . writeTimeout(60, TimeUnit.SECONDS)
                .build();
try {
    // okHttpClient.networkInterceptors().add(REWRITE_CACHE_CONTROL_INTERCEPTOR);
    AndroidNetworking.post(API_S_NEW.Endpoints.CONFIRM_BOOKING)
            .addBodyParameter(data)
            .addHeaders(sessionManager.getHeader())
//            .setOkHttpClient(okHttpClient)
//            .setMaxAgeCacheControl(10, TimeUnit.SECONDS)
            .addHeaders("locale", "" + sessionManager.getLanguage())
            .setTag(this)
            .setPriority(Priority.HIGH)
            .build()
            .getAsString(new StringRequestListener() {
                @Override
                public void onResponse(final String jsonObject) {
                    ModelResultCheck modelResultCheck = SingletonGson.getInstance().fromJson(""
                                    + jsonObject,
                            ModelResultCheck.class);


                    Log.d("**MODEL CHECK ::", "" + modelResultCheck.result);
                    if(modelResultCheck!=null){

                        if(modelResultCheck.getResult().equals("999"))
                            new Logout(getApplicationContext());


                        sendMessage(jsonObject);
                    }else {
                        sendMessage("0");
                    }


//
//                    if (modelResultCheck.result.equals("0")) {
//                        sendMessage("0");
//                    } else if (modelResultCheck.result.equals("2")) {
//                        Log.d("**MODEL CHECK == ::", "" + modelResultCheck.result);
//                        //     apifetcher_for_google.onFetchResultTwo(""+modelResultCheck.result, "SOCIAL_LOGIN");
//
//                        sendMessage("0");
//
//                    } else if (modelResultCheck.result.equals("999")) {
////                        logoutUnAuthorizedDriver(sessionManager);
//                        sendMessage("0");
//
//                    } else {
//                        sendMessage(jsonObject);
//                    }
                }

                @Override
                public void onError(ANError anError) {
                    sendMessage("0");
                    Toast.makeText(getApplicationContext(), "" + anError.getErrorDetail(), Toast.LENGTH_SHORT).show();

                }
            });
}catch (Exception e){
    e.printStackTrace();
}


    }



    public void sendMessage(String result){
       Intent intent = new Intent(CONFIRM_RIDE_MESSAGE);
        intent.putExtra(CONFIRM_RIDE_MESSAGE_KEY,result);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

    }



}
