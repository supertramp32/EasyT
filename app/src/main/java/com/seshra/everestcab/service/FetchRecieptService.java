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

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class FetchRecieptService extends IntentService {
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FETCH_RECIEPT = "com.seshra.user.service.action.FETCH_RECIEPT";

    private static final String BOOKING_ID = "com.seshra.user.service.extra.BOOKING_ID";

    String url = API_S_NEW.Endpoints.RECEIPT;

    public static final String RECIEPT_MESSAGE ="RecieptMessage" ;
    public static final String RECIEPT_MESSAGE_KEY = "recieptMessage" ;



    public FetchRecieptService() {
        super("FetchRecieptService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     *
     */
    public static void startActionFoo(Context context, String bookingID) {
        Intent intent = new Intent(context, FetchRecieptService.class);
        intent.setAction(ACTION_FETCH_RECIEPT);
        intent.putExtra(BOOKING_ID, bookingID);
        context.startService(intent);
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FETCH_RECIEPT.equals(action)) {
                final String param1 = intent.getStringExtra(BOOKING_ID);
                handleActionFoo(param1);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String bookingId) {


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

                        if(modelResultCheck!=null) {

                            if (modelResultCheck.getResult().equals("999"))
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


    }


    public void sendMessage(String result){

        Intent intent = new Intent(RECIEPT_MESSAGE);
        intent.putExtra(RECIEPT_MESSAGE_KEY,result);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

    }


}
