package com.seshra.everestcab.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

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

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * helper methods.
 */
public class CheckServiceAvailabilityService extends IntentService {
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_CHECK_SERVICE = "com.seshra.user.service.action.CHECK_SERVICE";
    private static final String LATTITUDE = "com.seshra.user.service.extra.LATTITUDE";
    private static final String LONGITUDE = "com.seshra.user.service.extra.LONGITUDE";


    public static final String CHECK_SERVICE_AVAILABILTY_MESSAGE ="CheckServiceMessage" ;
    public static final String CHECK_SERVICE_AVAILABILITY_KEY = "checkMessage" ;

    public CheckServiceAvailabilityService() {
        super("CheckServiceAvailabilityService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */

    public static void startActionCheckService(Context context, String lat, String lon) {
        Intent intent = new Intent(context, CheckServiceAvailabilityService.class);
        intent.setAction(ACTION_CHECK_SERVICE);
        intent.putExtra(LATTITUDE, lat);
        intent.putExtra(LONGITUDE, lon);
        context.startService(intent);
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_CHECK_SERVICE.equals(action)) {
                final String lat = intent.getStringExtra(LATTITUDE);
                final String lon = intent.getStringExtra(LONGITUDE);
                handleActionCheckService(lat, lon);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionCheckService(String lat, String lon) {


        String url = API_S_NEW.Endpoints.VIEW_CARS_AND_SERVICES;
        HashMap<String, String> data = new HashMap<>();
        data.put("latitude", lat);
        data.put("longitude", lon);


        SessionManager sessionManager = new SessionManager(getApplicationContext());

        try {
            AndroidNetworking.initialize(getApplicationContext());
            AndroidNetworking.post("" + url)
                    .addBodyParameter(data)
                    .addHeaders(sessionManager.getHeader())
                    .setMaxAgeCacheControl(10, TimeUnit.SECONDS)
                    .addHeaders("locale", "" + sessionManager.getLanguage())
                    .setTag(this)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsString(new StringRequestListener() {
                        @Override
                        public void onResponse(final String jsonObject) {
                            ModelResultCheck modelResultCheck = SingletonGson.getInstance()
                                    .fromJson("" + jsonObject, ModelResultCheck.class);

                            if(modelResultCheck!=null){
                                if(modelResultCheck.getResult().equals("999"))
                                    new Logout(getApplicationContext());
                                sendMessage(jsonObject);
                            }else {
                                sendMessage("0");
                            }


                        }

                        @Override
                        public void onError(ANError anError) {
//                            Toast.makeText(getApplicationContext(), "" +
//                                    anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                            sendMessage("0");

                        }
                    });
        }catch (Exception e){
            e.printStackTrace();

        }



    }



    public void sendMessage(String result){

        Intent intent = new Intent(CHECK_SERVICE_AVAILABILTY_MESSAGE);
        intent.putExtra(CHECK_SERVICE_AVAILABILITY_KEY,result);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

    }

}
