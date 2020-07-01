package com.seshra.everestcab.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
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


public class FetchSpecificTripDetailService extends IntentService {

    private static final String ACTION_SPECIFIC_TRIP_DETAILS = "com.seshra.user.service.action.TRIP_DETAILS";

    private static final String BOOKING_ID = "com.seshra.user.service.extra.BOOKING_ID";

    String url = API_S_NEW.Endpoints.TRIP_SPECIFIC_DETAIL;



    public static final String TRIP_HISTORY_DETAIL_MESSAGE ="TripHistoryDetailMessage" ;
    public static final String TRIP_HISTORY_DETAIL_MESSAGE_KEY = "tripDetailMessage" ;


    public FetchSpecificTripDetailService() {
        super("FetchSpecificTripDetailService");
    }

    /**
     * Starts this service to perform action SPECIFIC TRIP DETAILS with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionSpecificTripDetails(Context context, String bookingId) {
        Intent intent = new Intent(context, FetchSpecificTripDetailService.class);
        intent.setAction(ACTION_SPECIFIC_TRIP_DETAILS);
        intent.putExtra(BOOKING_ID, bookingId);
        context.startService(intent);
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SPECIFIC_TRIP_DETAILS.equals(action)) {
                final String bookingId = intent.getStringExtra(BOOKING_ID);
                handleActionTripDetails(bookingId);
            }
        }
    }

    /**
     * Handle action TripDetails in the provided background thread with the provided
     * parameters.
     */
    private void handleActionTripDetails(String bookingId) {

        HashMap<String, String> data = new HashMap<>();
        data.put("booking_id", bookingId);

        SessionManager sessionManager = new SessionManager(this);

        HashMap<String, String> headers = new HashMap<>();
        try {
            headers = sessionManager.getHeader();
            headers.put("content-type","application/json");
            headers.put("accept","application/json");
        } catch (Exception e) {
            e.printStackTrace();
        }


        // okHttpClient.networkInterceptors().add(REWRITE_CACHE_CONTROL_INTERCEPTOR);
        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.post("" + url)
                .addBodyParameter(data)
                .addHeaders(headers)
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


                        if(modelResultCheck.getResult().equals("999"))
                            new Logout(getApplicationContext());

                        if (modelResultCheck.result.equals("0")) {

                            sendMessage(jsonObject);

                        }
                        else if (modelResultCheck.result.equals("2")) {
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


    }


    public void sendMessage(String result){

        Intent intent = new Intent(TRIP_HISTORY_DETAIL_MESSAGE);
        intent.putExtra(TRIP_HISTORY_DETAIL_MESSAGE_KEY,result);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

    }


}
