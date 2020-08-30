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
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class CheckStopLocationService extends IntentService {


    private static final String ACTION_CHECK_STOP_LOCATION = "com.seshra.everestcab.service.action.CHECK_STOP_LOCATION";

    private static final String LATTITUDE = "com.seshra.everestcab.service.extra.LATITUDE";
    private static final String LONGITUDE = "com.seshra.everestcab.service.extra.LONGITUDE";


    SessionManager sessionManager;


    public static final String CHECK_STOP_LOCATION_MESSAGE ="CheckStopLocationMessage" ;
    public static final String CHECK_STOP_LOCATION_MESSAGE_KEY = "cStopLMessage" ;

    public CheckStopLocationService() {
        super("CheckStopLocationService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionCheckStopLocation(Context context, String latitude, String longitude) {
        Intent intent = new Intent(context, CheckStopLocationService.class);
        intent.setAction(ACTION_CHECK_STOP_LOCATION);
        intent.putExtra(LATTITUDE, latitude);
        intent.putExtra(LONGITUDE, longitude);
        context.startService(intent);
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_CHECK_STOP_LOCATION.equals(action)) {
                final String param1 = intent.getStringExtra(LATTITUDE);
                final String param2 = intent.getStringExtra(LONGITUDE);
                handleActionFoo(param1, param2);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String lat, String lon) {

        String url = API_S_NEW.Endpoints.CHECK_STOP_LOCATION;
        HashMap<String, String> b_id = new HashMap<>();
        b_id.put("latitude", lat);
        b_id.put("longitude",lon);
        b_id.put("area_id","310");
        b_id.put("service_type","1");

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
                            ModelResultCheck modelResultCheck = SingletonGson.getInstance().fromJson("" + jsonObject,
                                    ModelResultCheck.class);


                            Log.d("**MODEL CHECK ::", "" + modelResultCheck.result);

                            if(modelResultCheck.getResult().equals("999"))
                                new Logout(getApplicationContext());

                            if (modelResultCheck.getResult().equals("1")) {
                                sendMessage("1");
                            } else {
                                sendMessage("0");
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            sendMessage("0");
                            Toast.makeText(getApplicationContext(), "" + anError.getErrorDetail(), Toast.LENGTH_SHORT).show();

                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
            sendMessage("0");

        }

    }




    public void sendMessage(String result){

        Intent intent = new Intent(CHECK_STOP_LOCATION_MESSAGE);
        intent.putExtra(CHECK_STOP_LOCATION_MESSAGE_KEY,result);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

    }


}
