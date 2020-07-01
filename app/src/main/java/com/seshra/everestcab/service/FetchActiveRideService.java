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
public class FetchActiveRideService extends IntentService {
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_TRIP_HISTORY_ACTIVE = "com.seshra.user.service.action.TRIP_HISTORY_ACTIVE";

    static Context con;

    SessionManager sessionManager;



    public FetchActiveRideService() {
        super("FetchActiveRideService");
    }


    public static final String TRIP_HISTORY_ACTIVE_MESSAGE ="TripHistoryActiveMessage" ;
    public static final String TRIP_HISTORY_ACTIVE_MESSAGE_KEY = "tripActiveMessage" ;

    /**
     * Starts this service to perform action ACTION_TRIP_HISTORY_ACTIVE with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */

    public static void startActionTripHistoryActive(Context context) {
        Intent intent = new Intent(context, FetchActiveRideService.class);
        intent.setAction(ACTION_TRIP_HISTORY_ACTIVE);
        context.startService(intent);
        con = context;
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_TRIP_HISTORY_ACTIVE.equals(action)) {
                handleActionTripHistoryActive();
            }
        }
    }

    /**
     * Handle action TripHistoryActive in the provided background thread with the provided
     * parameters.
     */
    private void handleActionTripHistoryActive() {

        String url = API_S_NEW.Endpoints.TRIP_HISTORY_ACTIVE;
         sessionManager = new SessionManager(getApplicationContext());



        HashMap<String, String> data = new HashMap<>();
        data.put("type", "1" );


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
                    .addBodyParameter(data)
                    .setMaxAgeCacheControl(30, TimeUnit.SECONDS)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsString(new StringRequestListener() {
                        @Override
                        public void onResponse(final String jsonObject) {
                            ModelResultCheck modelResultCheck = SingletonGson.getInstance().fromJson("" + jsonObject, ModelResultCheck.class);


                            Log.d("**MODEL CHECK ::", ""+modelResultCheck.result);

                            if(modelResultCheck.getResult().equals("999"))
                                new Logout(getApplicationContext());

                            if (modelResultCheck.result.equals("0")) {

                                sendMessage(jsonObject);

                            }
                            else if (modelResultCheck.result.equals("2")) {
                                Log.d("**MODEL CHECK == ::", ""+modelResultCheck.result);
                                //     apifetcher_for_google.onFetchResultTwo(""+modelResultCheck.result, "SOCIAL_LOGIN");

                                sendMessage(jsonObject);
                            }
                            else if(modelResultCheck.result.equals("999")){
                                sendMessage(jsonObject);
                            }
                            else {
                                sendMessage(jsonObject);
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

        Intent intent = new Intent(TRIP_HISTORY_ACTIVE_MESSAGE);
        intent.putExtra(TRIP_HISTORY_ACTIVE_MESSAGE_KEY,result);
        LocalBroadcastManager.getInstance(con).sendBroadcast(intent);

    }

}
