package com.seshra.everestcab.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.seshra.everestcab.BuildConfig;
import com.seshra.everestcab.models.ModelLogin;
import com.seshra.everestcab.utils.API_S_NEW;
import com.seshra.everestcab.utils.BaseConfig;
import com.seshra.everestcab.utils.IntentKeys;
import com.seshra.everestcab.utils.Logout;
import com.seshra.everestcab.utils.SessionManager;
import com.seshra.everestcab.utils.SingletonGson;
import com.onesignal.OSPermissionSubscriptionState;
import com.onesignal.OneSignal;

import org.json.JSONObject;

import java.util.HashMap;


public class FetchConfigurationService extends IntentService {

    private static final String ACTION_GET_CONFIG = "com.seshra.user.service.action.CONFIGURATION";


    SessionManager sessionManager;
    String url = API_S_NEW.Endpoints.APP_CONFIGURATIONS;

    String PLAYER_ID;



    public static final String FETCH_CONFIGURATION_MESSAGE ="ConfigurationMessage" ;
    public static final String FETCH_CONFIG_KEY = "configMessage" ;




    public FetchConfigurationService() {
        super("FetchConfigurationService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */


    public static void startActionFetchConfig(Context context) {
        Intent intent = new Intent(context, FetchConfigurationService.class);
        intent.setAction(ACTION_GET_CONFIG);
        context.startService(intent);
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GET_CONFIG.equals(action)) {
                handleActionFetchConfig();
            }
        }
    }

    /**
     * Handle action fetch config in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFetchConfig() {




        sessionManager = new SessionManager(this);

        OSPermissionSubscriptionState status = OneSignal.getPermissionSubscriptionState();

        String UUID = status.getSubscriptionStatus().getUserId();




        HashMap<String, String> data = new HashMap<>();
        data.put("unique_no", "" + Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID));

        if(!sessionManager.getUserDetails().get(SessionManager.USER_PHONE).equals(""))
            data.put("username", sessionManager.getUserDetails().get(SessionManager.USER_PHONE));

        data.put("merchant_id", "136");
        data.put("login_type", "PHONE");
        data.put("package_name", "" + BuildConfig.APPLICATION_ID);
        data.put("player_id","UUID");
        data.put("apk_version", BuildConfig.VERSION_NAME);
        data.put("device", "1");  // that is for android
        data.put("operating_system", "" + Build.VERSION.SDK_INT);
        data.put("manufacture", "" + Build.MANUFACTURER);
        data.put("model", "" + Build.MODEL);



        AndroidNetworking.initialize(getApplicationContext());

        HashMap<String, String> headers = new HashMap<>();
        headers.put(IntentKeys.PUBLIC_KEY, "" + BaseConfig.PUBLIC_KEY);
        headers.put(IntentKeys.SECRET_KEY, "" + BaseConfig.SECRET_KEY);
        headers.put("Content-Type","application/json");
        headers.put("Accept","application/json");

        if(!sessionManager.getAccessToken().equals(""))
            headers.put("Authorization",sessionManager.getAccessToken());



        try {

            AndroidNetworking.post("" + url)
                    .addBodyParameter(data)
                    .addHeaders(headers)
                    .setTag(this)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(final JSONObject jsonObject) {
                            try {
                                ModelLogin modelResultCheck = SingletonGson.getInstance().fromJson("" + jsonObject, ModelLogin.class);

                                if(modelResultCheck.getResult().equals("999"))
                                    new Logout(getApplicationContext());

                                if(modelResultCheck.getResult().equals("1"))
                                    saveConfig(jsonObject.toString());
                            } catch (Exception e) {

                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(ANError anError) {

                            anError.printStackTrace();

                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }

        }





    public void saveConfig(String configJson){

        sessionManager.setAppConfig(configJson);




    }


}
