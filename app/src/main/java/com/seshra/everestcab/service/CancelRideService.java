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
import com.seshra.everestcab.R;
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
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class CancelRideService extends IntentService {
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_CANCEL_RIDE = "com.seshra.user.service.action.CANCEL_RIDE";

    private static final String BOOKING_ID = "com.seshra.user.service.extra.BOOKING_ID";
    private static final String CANCEL_REASON_ID = "com.seshra.user.service.extra.CANCEL_REASON_ID";
    private static final String CANCEL_CHARGES = "com.seshra.user.service.extra.CANCEL_CHARGES";


    public static final String CANCEL_RIDE_MESSAGE ="CancelRideMess" ;
    public static final String CANCEL_RIDE_MESSAGE_KEY = "cancelmess" ;


    public CancelRideService() {
        super("CancelRideService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */

    public static void startCanceRideService(Context context, String bookingId,
                                             int cancelReasonId, String cancelCharges) {
        Intent intent = new Intent(context, CancelRideService.class);
        intent.setAction(ACTION_CANCEL_RIDE);
        intent.putExtra(BOOKING_ID, bookingId);
        intent.putExtra(CANCEL_REASON_ID,cancelReasonId);
        intent.putExtra(CANCEL_CHARGES,cancelCharges);
        context.startService(intent);
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_CANCEL_RIDE.equals(action)) {
                final String bookingId = intent.getStringExtra(BOOKING_ID);
                final int cancel_reason_id = intent.getIntExtra(CANCEL_REASON_ID,0);
                final String cancelCharges = intent.getStringExtra(CANCEL_CHARGES);
                handleActionCancelRide(bookingId, cancel_reason_id, cancelCharges);
            }
        }
    }

    /**
     * Handle action CANCEL_RIDE in the provided background thread with the provided
     * parameters.
     */
    private void handleActionCancelRide(String bookingId, int reasonId, String cancelCharges) {

        String url = API_S_NEW.Endpoints.CANCEL_RIDE;
        HashMap<String, String> data = new HashMap<>();
        data.put("booking_id", "" + bookingId);
        data.put("cancel_reason_id", "" + reasonId);
        data.put("cancel_charges", "" + cancelCharges);

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
                            ModelResultCheck modelResultCheck = SingletonGson.getInstance().fromJson("" + jsonObject,
                                    ModelResultCheck.class);
                            if(modelResultCheck!=null){

                                if(modelResultCheck.getResult().equals("999"))
                                    new Logout(getApplicationContext());


                                sendMessage(jsonObject);


//                                if(modelResultCheck.getResult().equals("1")) {
//                                    startActivity(new Intent(getApplicationContext(), MainActivity.class)
//                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                                    );
//
//                                    Toast.makeText(getApplicationContext(), modelResultCheck.getMessage(), Toast.LENGTH_LONG).show();
//                                }else {
//                                    Toast.makeText(getApplicationContext(), modelResultCheck.getMessage(), Toast.LENGTH_LONG).show();
//
//                                }



                            }else {
                                sendMessage("0");
                            }







                        }

                        @Override
                        public void onError(ANError anError) {
                            Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.error_cancelling), Toast.LENGTH_LONG).show();
                            sendMessage("0");

                        }
                    });
        }catch (Exception e){
            e.printStackTrace();

        }





    }


    public void sendMessage(String result){

        Intent intent = new Intent(CANCEL_RIDE_MESSAGE);
        intent.putExtra(CANCEL_RIDE_MESSAGE_KEY,result);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

    }




}
