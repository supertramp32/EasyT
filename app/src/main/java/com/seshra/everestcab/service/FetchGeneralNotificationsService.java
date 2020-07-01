package com.seshra.everestcab.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
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

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * helper methods.
 */
public class FetchGeneralNotificationsService extends IntentService {

    private static final String ACTION_GENERAL_NOTIFICATIONS = "com.seshra.user.service.action.GENERAL_NOTIFICATIONS";


    public FetchGeneralNotificationsService() {
        super("FetchGeneralNotificationsService");
    }

    public static final String GENERAL_NOTIFICATIONS_MESSAGE ="GeneralNotificationsMessage" ;
    public static final String GENERAL_NOTIFICATIONS_MESSAGE_KEY = "generalMessage" ;

    /**
     * Starts this service to perform action GENERAL NOTIFICATIONS with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionGeneralNotifications(Context context) {
        Intent intent = new Intent(context, FetchGeneralNotificationsService.class);
        intent.setAction(ACTION_GENERAL_NOTIFICATIONS);
        context.startService(intent);
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GENERAL_NOTIFICATIONS.equals(action)) {
                handleActionGeneralNotifications();
            }
        }
    }

    /**
     * Handle action general Notifications in the provided background thread with the provided
     * parameters.
     */
    private void handleActionGeneralNotifications() {



        String url = API_S_NEW.Endpoints.PROMOTIONAL_NOTIFICATION;
        SessionManager sessionManager = new SessionManager(getApplicationContext());




        HashMap<String, String> headers = new HashMap<>();
        try {
            headers = sessionManager.getHeader();
            headers.put("content-type","application/json");
            headers.put("accept","application/json");
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {

            AndroidNetworking.initialize(getApplicationContext());


            AndroidNetworking.post( url)
                    .addHeaders(headers)
                    .setMaxAgeCacheControl(30, TimeUnit.SECONDS)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsString(new StringRequestListener() {
                        @Override
                        public void onResponse(final String jsonObject) {
                            ModelResultCheck modelResultCheck = SingletonGson.getInstance()
                                    .fromJson("" + jsonObject, ModelResultCheck.class);


                            Log.d("**MODEL CHECK ::", ""+modelResultCheck.result);

                            if (modelResultCheck!=null) {

                                if(modelResultCheck.getResult().equals("999"))
                                    new Logout(getApplicationContext());

                                sendMessage(jsonObject);
                            }else {
                                sendMessage("0");
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            Toast.makeText(getApplicationContext(), ""+anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                            sendMessage("0");

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void sendMessage(String result){

        Intent intent = new Intent(GENERAL_NOTIFICATIONS_MESSAGE);
        intent.putExtra(GENERAL_NOTIFICATIONS_MESSAGE_KEY,result);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

    }


}
