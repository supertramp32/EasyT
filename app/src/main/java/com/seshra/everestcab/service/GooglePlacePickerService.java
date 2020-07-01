package com.seshra.everestcab.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.seshra.everestcab.models.ModelResultCheck;
import com.seshra.everestcab.utils.SingletonGson;

import org.json.JSONObject;


public class GooglePlacePickerService extends IntentService {

    private static final String ACTION_SEARCH_PLACES = "com.seshra.user.service.action.SEARCH_PLACES";

    private static final String URL = "com.seshra.user.service.extra.URL";
    private static final String SEARCH_TEXT = "com.seshra.user.service.extra.PARAM2";


    public static final String SEARCH_MESSAGE ="SearchMessage" ;
    public static final String SEARCH_KEY = "message" ;


    public GooglePlacePickerService() {
        super("GooglePlacePickerService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionSearch(Context context, String url, String searchText) {
        Intent intent = new Intent(context, GooglePlacePickerService.class);
        intent.setAction(ACTION_SEARCH_PLACES);
        intent.putExtra(URL, url);
        intent.putExtra(SEARCH_TEXT, searchText);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SEARCH_PLACES.equals(action)) {
                final String url = intent.getStringExtra(URL);
                final String searchText = intent.getStringExtra(SEARCH_TEXT);
                handleActionSearchPlaces(url, searchText);
            }
        }
    }

    /**
     * Handle action Search for places in the provided background thread with the provided
     * parameters.
     */
    private void handleActionSearchPlaces(String url, String searchText) {

        AndroidNetworking.get("" + url)
                .setTag(this)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(final JSONObject jsonObject) {
                        ModelResultCheck modelResultCheck = SingletonGson.getInstance().fromJson("" + jsonObject,
                                ModelResultCheck.class);



                        sendMessage(jsonObject);


                    }

                    @Override
                    public void onError(ANError anError) {
                       sendMessage(null);
                    }
                });

    }


    public void sendMessage(JSONObject result){

        Intent intent = new Intent(SEARCH_MESSAGE);
        intent.putExtra(SEARCH_KEY,result.toString());
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

    }

}
