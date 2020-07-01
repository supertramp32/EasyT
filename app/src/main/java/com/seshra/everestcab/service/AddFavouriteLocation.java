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
import com.seshra.everestcab.utils.API_S_NEW;
import com.seshra.everestcab.utils.BaseConfig;
import com.seshra.everestcab.utils.IntentKeys;
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
public class AddFavouriteLocation extends IntentService {
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_SAVE_FAVOURITES = "com.seshra.user.service.action.SAVE_FAVOURITES";

    // TODO: Rename parameters
    private static final String LATITUDE = "com.seshra.user.service.extra.LATTITUDE";
    private static final String LONGITUDE = "com.seshra.user.service.extra.LONGITUDE";
    private static final String PLACE_NAME = "com.seshra.user.service.extra.PLACE_NAME";
    private static final String PLACE_TITLE = "com.seshra.user.service.extra.PLACE_TITLE";

    public static final String FAVOURITE_MESSAGE ="FavouriteMessage" ;
    public static final String FAVOURITE_MESSAGE_KEY = "fMessage" ;

    String url = API_S_NEW.Endpoints.ADD_FAVOURITE_LOCATION;


    public AddFavouriteLocation() {
        super("AddFavouriteLocation");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionSaveLocation(Context context, String lat, String lon, String placeName, String placeTitle) {
        Intent intent = new Intent(context, AddFavouriteLocation.class);
        intent.setAction(ACTION_SAVE_FAVOURITES);
        intent.putExtra(LATITUDE, lat);
        intent.putExtra(LONGITUDE, lon);
        intent.putExtra(PLACE_NAME, placeName);
        intent.putExtra(PLACE_TITLE, placeTitle);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SAVE_FAVOURITES.equals(action)) {
                final String param1 = intent.getStringExtra(LATITUDE);
                final String param2 = intent.getStringExtra(LONGITUDE);
                final String placeName = intent.getStringExtra(PLACE_NAME);
                final String placeTitle = intent.getStringExtra(PLACE_TITLE);
                handleActionFoo(param1, param2,placeName,placeTitle);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String lat, String lon, String placeName, String placeTitle) {

        HashMap<String, String> data = new HashMap<>();
        data.put("latitude", lat);
        data.put("longitude", lon);
        data.put("location", placeName);
        data.put("category", "3" );
        data.put("other_name", placeTitle);


        SessionManager sessionManager = new SessionManager(getApplicationContext());


        try {
            AndroidNetworking.post("" + url)
                    .addBodyParameter(data)
                    .addHeaders(sessionManager.getHeader())
                    .addHeaders(IntentKeys.PUBLIC_KEY, "" + BaseConfig.PUBLIC_KEY)
                    .addHeaders(IntentKeys.SECRET_KEY, "" + BaseConfig.SECRET_KEY)
                    .setTag(this)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(final JSONObject jsonObject) {
                            try{
                                ModelResultCheck modelResultCheck = SingletonGson.getInstance()
                                        .fromJson("" + jsonObject, ModelResultCheck.class);
                                if (modelResultCheck!=null) {

                                    if(modelResultCheck.getResult().equals("999"))
                                        new Logout(getApplicationContext());

                                    sendMessage(jsonObject.toString());
                                }else {


                                    sendMessage(jsonObject.toString());
                                }
                            }catch (Exception e){

                                sendMessage("0");
                            }

                        }

                        @Override
                        public void onError(ANError anError) {

                            sendMessage("0");

                        }
                    });
        } catch (Exception e) {

            sendMessage("0");
        }


    }


    public void sendMessage(String result){

        Intent intent = new Intent(FAVOURITE_MESSAGE);
        intent.putExtra(FAVOURITE_MESSAGE_KEY,result);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }


}
