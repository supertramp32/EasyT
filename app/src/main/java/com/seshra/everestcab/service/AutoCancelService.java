package com.seshra.everestcab.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.seshra.everestcab.MainActivity;
import com.seshra.everestcab.models.ModelAutoCancel;
import com.seshra.everestcab.utils.API_S_NEW;
import com.seshra.everestcab.utils.Logout;
import com.seshra.everestcab.utils.STATUS;
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
public class AutoCancelService extends IntentService {
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_CHECK_AUTO_CANCE = "com.seshra.user.service.action.CHECK_AUTO_CANCEL";

    private static final String BOOKING_ID = "com.seshra.user.service.extra.BOOKING_ID";

    public AutoCancelService() {
        super("AutoCancelService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */

    public static void startActionAutoCancel(Context context, String bookingID) {
        Intent intent = new Intent(context, AutoCancelService.class);
        intent.setAction(ACTION_CHECK_AUTO_CANCE);
        intent.putExtra(BOOKING_ID, bookingID);
        context.startService(intent);
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_CHECK_AUTO_CANCE.equals(action)) {
                final String bookingId = intent.getStringExtra(BOOKING_ID);
                handleActionAutoCancel(bookingId);
            }
        }
    }

    /**
     * Handle action AutoCancel in the provided background thread with the provided
     * parameters.
     */
    private void handleActionAutoCancel(String bookingId) {



        String url = API_S_NEW.Endpoints.AUTO_CANCEL;
        HashMap<String, String> b_id = new HashMap<>();
        b_id.put("booking_id", bookingId);

        SessionManager sessionManager = new SessionManager(getApplicationContext());

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


                            ModelAutoCancel modelAutoCancel = SingletonGson.getInstance().fromJson("" + jsonObject,
                                    ModelAutoCancel.class);

                            if(modelAutoCancel.getResult().equals("999"))
                                new Logout(getApplicationContext());

                            if (Integer.parseInt(modelAutoCancel.getData().getBooking_status()) == STATUS.ACCEPTED) {

                                // TODO: think for a while if this needs to be checked


                            }
                            if (Integer.parseInt(modelAutoCancel.getData().getBooking_status()) == STATUS.USER_AUTO_CANCALLED) {


                                startActivity(new Intent(getApplicationContext(), MainActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                );

                                Toast.makeText(getApplicationContext(),modelAutoCancel.getMessage(),Toast.LENGTH_LONG).show();


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


}
