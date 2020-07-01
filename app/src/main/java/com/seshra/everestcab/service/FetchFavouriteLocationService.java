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

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class FetchFavouriteLocationService extends IntentService {

    private static final String ACTION_FETCH_FAVOURITE_LOCATIONS = "com.seshra.user.service.action.FETCH_FAVOURITES";


    public static final String FETCH_FAVOURITE_MESSAGE ="FavouriteMessage" ;
    public static final String FETCH_FAVOURITE_MESSAGE_KEY = "fMessage" ;

    String url = API_S_NEW.Endpoints.VIEW_FAVOURITE_LOCATION;




    public FetchFavouriteLocationService() {
        super("FetchFavouriteLocationService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */

    public static void startActionFoo(Context context) {
        Intent intent = new Intent(context, FetchFavouriteLocationService.class);
        intent.setAction(ACTION_FETCH_FAVOURITE_LOCATIONS);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FETCH_FAVOURITE_LOCATIONS.equals(action)) {
                handleActionFoo();
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo() {



        SessionManager sessionManager = new SessionManager(getApplicationContext());


        try {
            AndroidNetworking.post("" + url)
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
                                ModelResultCheck modelResultCheck = SingletonGson.getInstance().fromJson("" + jsonObject, ModelResultCheck.class);
                                if (modelResultCheck!=null) {


                                    if(modelResultCheck.getResult().equals("999"))
                                        new Logout(getApplicationContext());

                                    sendMessage(jsonObject.toString());
                                }else {


                                    sendMessage("0");
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

        Intent intent = new Intent(FETCH_FAVOURITE_MESSAGE);
        intent.putExtra(FETCH_FAVOURITE_MESSAGE_KEY,result);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }


}
