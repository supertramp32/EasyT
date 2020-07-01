package com.seshra.everestcab.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.seshra.everestcab.R;
import com.seshra.everestcab.models.ModelResultCheck;
import com.seshra.everestcab.utils.API_S_NEW;
import com.seshra.everestcab.utils.Logout;
import com.seshra.everestcab.utils.SessionManager;
import com.seshra.everestcab.utils.SingletonGson;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * helper methods.
 */
public class FetchPopularLocations extends IntentService {

    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.seshra.user.service.action.FOO";


    private static final String LATTITUDE = "com.seshra.user.service.extra.LATTITUDE";
    private static final String LONGITUDE = "com.seshra.user.service.extra.LONGITUDE";


    String url = API_S_NEW.Endpoints.POPULAR_LOCATIONS;

    public static final String FETCH_POLULAR_LOCATION_MESSAGE ="FetchPopular" ;
    public static final String FETCH_POLULAR_LOCATION_MESSAGE_KEY = "fetchPopularMessage" ;

    public FetchPopularLocations() {
        super("FetchPopularLocations");
    }


    public static void startActionFetchPopularLocations(Context context, String lattitude, String longitude) {
        Intent intent = new Intent(context, FetchPopularLocations.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(LATTITUDE, lattitude);
        intent.putExtra(LONGITUDE, longitude);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
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

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String,String> data = new HashMap<>();
        data.put("latitude",lat);
        data.put("longitude",lon);

        try {
            AndroidNetworking.post("" + url)
                    .addHeaders("Content-Type", "application/json")
                    .addHeaders(sessionManager.getHeader())
                    .addBodyParameter(data)
                    .setTag(this)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(final JSONObject jsonObject) {
                            try{
                                ModelResultCheck modelResultCheck = SingletonGson.getInstance()
                                        .fromJson("" + jsonObject, ModelResultCheck.class);
                                if(modelResultCheck!=null) {
                                    if(modelResultCheck.getResult().equals("999"))
                                        new Logout(getApplicationContext());
                                    sendMessage(jsonObject.toString());
                                }
                                else
                                    sendMessage("0");


                            }catch (Exception e){

                                e.printStackTrace();
                                sendMessage("0");
                            }

                        }

                        @Override
                        public void onError(ANError anError) {

                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show();
                            sendMessage("0");

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }



    public void sendMessage(String result){

        Intent intent = new Intent(FETCH_POLULAR_LOCATION_MESSAGE);
        intent.putExtra(FETCH_POLULAR_LOCATION_MESSAGE_KEY,result);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

    }

}
