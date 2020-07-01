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

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class TrackRideService extends IntentService {
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_TRACK_RIDE = "com.seshra.user.service.action.TRACK_RIDE";

    // TODO: Rename parameters
    private static final String BOOKING_ID = "com.seshra.user.service.extra.BOOKING_ID";

    public TrackRideService() {
        super("TrackRideService");
    }


    public static final String TRACKING_MESSAGE ="TrackingMessage" ;
    public static final String TRACKING_KEY = "tMessage" ;

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionTrackRide(Context context, String bookingId) {
        Intent intent = new Intent(context, TrackRideService.class);
        intent.setAction(ACTION_TRACK_RIDE);
        intent.putExtra(BOOKING_ID, bookingId);
        context.startService(intent);
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_TRACK_RIDE.equals(action)) {
                final String bookingId = intent.getStringExtra(BOOKING_ID);
                handleActionTrackRide(bookingId);
            }
        }
    }


    private void handleActionTrackRide(String bookingId) {

        HashMap<String,String> data = new HashMap<>();
        data.put("booking_id", bookingId);

        String url = API_S_NEW.Endpoints.TRACK_RIDE;
        SessionManager sessionManager = new SessionManager(this);

        try {
            AndroidNetworking.post("" + url)
                    .addBodyParameter(data)
                    .addHeaders(sessionManager.getHeader())
                    .addHeaders("Accept","application/json")
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

                            if (modelResultCheck.result.equals("2")) {
                                sendMessage("0");
                            }
    //                        else if(modelResultCheck.result.equals("999")){
    //                            logoutUnAuthorizedDriver(sessionManager);
    //                        }
                            else {
                                sendMessage(jsonObject);
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                           sendMessage("0");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }




    public void sendMessage(String result){

        Intent intent = new Intent(TRACKING_MESSAGE);
        intent.putExtra(TRACKING_KEY,result);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }


}
