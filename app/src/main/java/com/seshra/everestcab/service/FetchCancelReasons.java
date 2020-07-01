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
import com.seshra.everestcab.models.ModelCancelReasons;
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
public class FetchCancelReasons extends IntentService {
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FETCH_CANCEL_REASONS = "com.seshra.user.service.action.CANCEL_REASONS";

    private static final String BOOKING_ID = "com.seshra.user.service.extra.PARAM1";

    public FetchCancelReasons() {
        super("FetchCancelReasons");
    }

    public static final String CANCEL_REASON_MESSAGE ="RideNowMessage" ;
    public static final String CANCEL_REASON_MESSAGE_KEY = "rideMessage" ;

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionFoo(Context context, String bookingId) {
        Intent intent = new Intent(context, FetchCancelReasons.class);
        intent.setAction(ACTION_FETCH_CANCEL_REASONS);
        intent.putExtra(BOOKING_ID, bookingId);
        context.startService(intent);
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FETCH_CANCEL_REASONS.equals(action)) {
                final String bookingId = intent.getStringExtra(BOOKING_ID);
                handleActionFetchCancelReasons(bookingId);
            }
        }
    }

    /**
     * Handle action Fetch Cancel Reasons in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFetchCancelReasons(String bookingId) {


        String url = API_S_NEW.Endpoints.CANCEL_REASION;
        HashMap<String, String> b_id = new HashMap<>();
        b_id.put("booking_id", bookingId);

        SessionManager sessionManager = new SessionManager(getApplicationContext());

        try {
            AndroidNetworking.initialize(getApplicationContext());
            AndroidNetworking.post("" + url)
                    .addBodyParameter(b_id)
                    .addHeaders(sessionManager.getHeader())
                    .setMaxAgeCacheControl(10, TimeUnit.SECONDS)
                    .addHeaders("locale", "" + sessionManager.getLanguage())
                    .setTag(this)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsString(new StringRequestListener() {
                        @Override
                        public void onResponse(final String jsonObject) {
                          ModelCancelReasons modelCancelReasion = SingletonGson.getInstance().fromJson("" + jsonObject,
                                  ModelCancelReasons.class);
                          if(modelCancelReasion!=null){

                              if(modelCancelReasion.getResult().equals("999"))
                                  new Logout(getApplicationContext());

                              sendMessage(jsonObject);

                          }else {
                              sendMessage("0");
                          }







                        }

                        @Override
                        public void onError(ANError anError) {
                            Toast.makeText(getApplicationContext(), "" + anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                            sendMessage("0");

                        }
                    });
        }catch (Exception e){
            e.printStackTrace();

        }




    }



    public void sendMessage(String result){

        Intent intent = new Intent(CANCEL_REASON_MESSAGE);
        intent.putExtra(CANCEL_REASON_MESSAGE_KEY,result);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

    }

}
