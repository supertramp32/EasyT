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
public class FetchTermsAndConditionService extends IntentService {
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String FETCH_TERMS_CONDITIONS = "com.seshra.user.service.action.TERMS_CONDITIONS";

    String url = API_S_NEW.Endpoints.CMS_PAGES;


    public static final String TERMS_MESSAGE ="TermsMessage" ;
    public static final String TERMS_MESSAGE_KEY = "tMessage" ;

    private static final String SLUG = "com.seshra.user.service.extra.SLUG";




    public FetchTermsAndConditionService() {
        super("FetchTermsAndConditionService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionFetchTerms(Context context,String slug) {
        Intent intent = new Intent(context, FetchTermsAndConditionService.class);
        intent.setAction(FETCH_TERMS_CONDITIONS);
        intent.putExtra(SLUG,slug);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (FETCH_TERMS_CONDITIONS.equals(action)) {
                String slug = intent.getStringExtra(SLUG);
                handleActionFetchTerms(slug);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFetchTerms(String slug) {

        HashMap<String, String> data = new HashMap<>();
        data.put("slug", slug);
        data.put("merchant_id", "136");
        SessionManager sessionManager = new SessionManager(getApplicationContext());

        AndroidNetworking.post("" + url)
                .addHeaders(IntentKeys.PUBLIC_KEY, "" + BaseConfig.PUBLIC_KEY)
                .addHeaders(IntentKeys.SECRET_KEY, "" + BaseConfig.SECRET_KEY)
                .addHeaders("Authorization",sessionManager.getAccessToken())
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



                            if(modelResultCheck.getResult().equals("999"))
                                new Logout(getApplicationContext());


                            sendMessage(jsonObject.toString());



                        }catch (Exception e){

                            sendMessage("0");
                            e.printStackTrace();

                        }

                    }

                    @Override
                    public void onError(ANError anError) {

                        anError.printStackTrace();
                        sendMessage("0");

                    }
                });



    }



    public void sendMessage(String result){

        Intent intent = new Intent(TERMS_MESSAGE);
        intent.putExtra(TERMS_MESSAGE_KEY,result);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }


}
