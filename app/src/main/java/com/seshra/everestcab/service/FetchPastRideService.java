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
public class FetchPastRideService extends IntentService {

    private static final String ACTION_PAST_RIDE = "com.seshra.user.service.action.PAST_RIDE";


    private static final String PAGE_NUMBER = "com.seshra.user.service.extra.PAGE_NUMBER";
    public static final String TRIP_HISTORY_PAST_MESSAGE ="TripHistoryPastMessage" ;
    public static final String TRIP_HISTORY_PAST_MESSAGE_KEY = "tripPastMessage" ;



    public FetchPastRideService() {
        super("FetchPastRideService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFetchPastRide(Context context, String pageNo) {
        Intent intent = new Intent(context, FetchPastRideService.class);
        intent.setAction(ACTION_PAST_RIDE);
        intent.putExtra(PAGE_NUMBER,pageNo);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_PAST_RIDE.equals(action)) {

                final String pageNo = intent.getStringExtra(PAGE_NUMBER);
                handleActionFoo(pageNo);
            }
        }
    }


    private void handleActionFoo(String pageNo) {


        String url = API_S_NEW.Endpoints.TRIP_HISTORY_PAST+"?page="+pageNo;
        SessionManager sessionManager = new SessionManager(getApplicationContext());



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


                            if(modelResultCheck.getResult().equals("999"))
                                new Logout(getApplicationContext());

                            Log.d("**MODEL CHECK ::", ""+modelResultCheck.result);

                            if (modelResultCheck.result.equals("0")) {

                                sendMessage("0");

                            }
                            else if (modelResultCheck.result.equals("2")) {
                                Log.d("**MODEL CHECK == ::", ""+modelResultCheck.result);
                                //     apifetcher_for_google.onFetchResultTwo(""+modelResultCheck.result, "SOCIAL_LOGIN");

                                sendMessage("0");
                            }
                            else if(modelResultCheck.result.equals("999")){
                                sendMessage("0");
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

        Intent intent = new Intent(TRIP_HISTORY_PAST_MESSAGE);
        intent.putExtra(TRIP_HISTORY_PAST_MESSAGE_KEY,result);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

    }




}
