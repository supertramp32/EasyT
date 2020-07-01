package com.seshra.everestcab.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
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
 */
public class CheckStatusService extends IntentService {
    private static final String ACTION_CHECK_BOOKING = "com.seshra.user.service.action.CHECK_BOOKING";

    private static final String BOOKING_ID = "com.seshra.user.service.extra.BOOKING_ID";

    public CheckStatusService() {
        super("CheckStatusService");
    }

    SessionManager sessionManager;


    public static final String CHECK_BOOKING_STATUS_MESSAGE ="CheckBookingStatusMessage" ;
    public static final String CHECK_BOOKING_STATUS_KEY = "bookingMessage" ;

    /**
     * Starts this service to perform action CHECK_BOOKING_STATUS with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionBookingStatus(Context context, String booking_id) {
        context = context;

        Intent intent = new Intent(context, CheckStatusService.class);
        intent.setAction(ACTION_CHECK_BOOKING);
        intent.putExtra(BOOKING_ID, booking_id);
        context.startService(intent);
    }



    /**
     * Handle action CHECK_BOOKING_STATUS   in the provided background thread with the provided
     * parameters.
     */
    private void handleActionCheckBookingStatus(String bookingId) {

        String url = API_S_NEW.Endpoints.CHECK_STATUS;
        HashMap<String, String> b_id = new HashMap<>();
        b_id.put("booking_id", bookingId);

        sessionManager = new SessionManager(getApplicationContext());

try {
    // okHttpClient.networkInterceptors().add(REWRITE_CACHE_CONTROL_INTERCEPTOR);
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
                    ModelResultCheck modelResultCheck = SingletonGson.getInstance().fromJson("" + jsonObject, ModelResultCheck.class);


                    Log.d("**MODEL CHECK ::", "" + modelResultCheck.result);

                    if(modelResultCheck.getResult().equals("999"))
                        new Logout(getApplicationContext());

                    if (modelResultCheck.result.equals("0")) {
                        sendMessage("0");
                    } else if (modelResultCheck.result.equals("2")) {
                        Log.d("**MODEL CHECK == ::", "" + modelResultCheck.result);

                        sendMessage("0");

                        //     apifetcher_for_google.onFetchResultTwo(""+modelResultCheck.result, "SOCIAL_LOGIN");

//                        if (tag.equals("SOCIAL_LOGINGoogle") || tag.equals("SOCIAL_LOGINfacebook")) {
//
//                            apifetcher.onFetchResultZero("" + modelResultCheck.result, tag);
//
//                        } else {
//
//                            apifetcher.onFetchResultZero("" + modelResultCheck.message, tag);
//
//                        }
                    } else if (modelResultCheck.result.equals("999")) {
                        sendMessage("0");

//                        logoutUnAuthorizedDriver(sessionManager);
                    } else {
                        sendMessage(jsonObject);
//                        apifetcher.onFetchComplete(jsonObject, tag);
                    }
                }

                @Override
                public void onError(ANError anError) {

                    Toast.makeText(getApplicationContext(), "" + anError.getErrorDetail(), Toast.LENGTH_SHORT).show();

                }
            });
}catch (Exception e){
    e.printStackTrace();

}
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_CHECK_BOOKING.equals(action)) {
                final String bookingId = intent.getStringExtra(BOOKING_ID);
                handleActionCheckBookingStatus(bookingId);
            }
        }
    }


    public void sendMessage(String result){

        Intent intent = new Intent(CHECK_BOOKING_STATUS_MESSAGE);
        intent.putExtra(CHECK_BOOKING_STATUS_KEY,result);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

    }
}
