package com.seshra.everestcab.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;


import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.seshra.everestcab.models.ModelLogin;
import com.seshra.everestcab.utils.API_S_NEW;
import com.seshra.everestcab.utils.BaseConfig;
import com.seshra.everestcab.utils.IntentKeys;
import com.seshra.everestcab.utils.Logout;
import com.seshra.everestcab.utils.SessionManager;
import com.seshra.everestcab.utils.SingletonGson;

import org.json.JSONObject;

import java.util.HashMap;


public class LoginService extends IntentService {

    private static final String ACTION_LOGIN = "com.seshra.user.service.action.LOGIN";

    String url = API_S_NEW.Endpoints.LOGIN;


    public static final String LOGIN_MESSAGE ="LoginMessage" ;
    public static final String LOGIN_KEY = "message" ;


    SessionManager sessionManager;

    private static final String PHONE = "com.seshra.user.service.extra.PHONE";
    private static final String PASSWORD = "com.seshra.user.service.extra.PASSWORD";

    public LoginService() {
        super("LoginService");
    }

    public static void startActionLogin(Context context, String phone, String password) {
        Intent intent = new Intent(context, LoginService.class);
        intent.setAction(ACTION_LOGIN);
        intent.putExtra(PHONE, phone);
        intent.putExtra(PASSWORD, password);
        context.startService(intent);
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_LOGIN.equals(action)) {
                final String phone = intent.getStringExtra(PHONE);
                final String password = intent.getStringExtra(PASSWORD);
                handleActionLogin(phone, password);
            }
        }
    }


    private void handleActionLogin(String phone, String password) {

        HashMap<String, String> data = new HashMap<>();
        data.put("username", "+977"+phone);
        data.put("password", password);
        data.put("merchant_id", "136");








        AndroidNetworking.post("" + url)
                .addBodyParameter(data)
                .addHeaders(IntentKeys.PUBLIC_KEY, "" + BaseConfig.PUBLIC_KEY)
                .addHeaders(IntentKeys.SECRET_KEY, "" + BaseConfig.SECRET_KEY)
                .setTag(this)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(final JSONObject jsonObject) {
                        try{
                            ModelLogin modelResultCheck = SingletonGson.getInstance().fromJson("" + jsonObject, ModelLogin.class);
                            if (modelResultCheck.getData().getUser()!=null) {

                                if(modelResultCheck.getResult().equals("999"))
                                    new Logout(getApplicationContext());

                                saveUserDetails(jsonObject);
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


    }

    private void saveUserDetails(JSONObject jsonObject) {


        ModelLogin modelResultCheck = SingletonGson.getInstance().fromJson("" + jsonObject, ModelLogin.class);

        sessionManager = new SessionManager(this);


        sessionManager.setAccessToken(modelResultCheck.getData().getAccess_token());

        sessionManager.createLoginSession(modelResultCheck.getData().getUser().getFirst_name(),
                modelResultCheck.getData().getUser().getLast_name(),
                modelResultCheck.getData().getUser().getEmail(),
                modelResultCheck.getData().getUser().getMobile_number(),
                modelResultCheck.getData().getUser().getProfile_image(),
                modelResultCheck.getData().getUser().getTotal_trips(),
                modelResultCheck.getData().getUser().getWallet_balance()
                );



        sessionManager.makUserLoggedIn();

        sendMessage(jsonObject.toString());

    }


    public void sendMessage(String result){

        Intent intent = new Intent(LOGIN_MESSAGE);
        intent.putExtra(LOGIN_KEY,result);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

}
