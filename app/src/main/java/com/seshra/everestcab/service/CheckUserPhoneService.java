package com.seshra.everestcab.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.AnalyticsListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.seshra.everestcab.R;
import com.seshra.everestcab.models.ModelCheckPhoneNumber;
import com.seshra.everestcab.utils.API_S_NEW;
import com.seshra.everestcab.utils.BaseConfig;
import com.seshra.everestcab.utils.IntentKeys;
import com.seshra.everestcab.utils.Logout;
import com.seshra.everestcab.utils.SingletonGson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class CheckUserPhoneService extends IntentService {

    private static final String ACTION_CHECK_PHONE = "com.seshra.user.service.action.CHECK_PHONE";

    String url = API_S_NEW.Endpoints.CHECK_USER_PHONE;

    public static final String CHECK_PHONE_MESSAGE ="LoginMessage" ;
    public static final String CHECK_MESSAGE_KEY = "message" ;

    private static final String USER_PHONE = "com.seshra.user.service.extra.PARAM1";

    public CheckUserPhoneService() {
        super("CheckUserPhoneService");
    }


    public static void startActionCheckPhone(Context context, String phone) {
        Intent intent = new Intent(context, CheckUserPhoneService.class);
        intent.setAction(ACTION_CHECK_PHONE);
        intent.putExtra(USER_PHONE, phone);
        context.startService(intent);
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_CHECK_PHONE.equals(action)) {
                final String phone = intent.getStringExtra(USER_PHONE);
                handleActionCheckPhone(phone);
            }
        }
    }


    private void handleActionCheckPhone(String phone) {

        Map<String, String> data=new HashMap<>();
        data.put("username", "+977"+phone);
        data.put("merchant_id","136");



        AndroidNetworking.post("" + url)
                .addBodyParameter(data)
                .addHeaders("Accept", "application/json")
                .addHeaders(IntentKeys.PUBLIC_KEY, "" + BaseConfig.PUBLIC_KEY)
                .addHeaders(IntentKeys.SECRET_KEY, "" + BaseConfig.SECRET_KEY)
                .setTag(this)
                .setPriority(Priority.HIGH)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {

                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(final JSONObject jsonObject) {
                        try{
                            ModelCheckPhoneNumber modelResultCheck = SingletonGson.getInstance().fromJson("" + jsonObject, ModelCheckPhoneNumber.class);
                            if(modelResultCheck!=null) {

                                if(modelResultCheck.getResult().equals("999"))
                                    new Logout(getApplicationContext());

                                sendMessage(jsonObject.toString());
                            }


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





    }


    public void sendMessage(String result){

        Intent intent = new Intent(CHECK_PHONE_MESSAGE);
        intent.putExtra(CHECK_MESSAGE_KEY,result);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

    }

}

