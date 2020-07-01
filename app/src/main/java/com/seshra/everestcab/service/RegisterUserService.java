package com.seshra.everestcab.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

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

import org.json.JSONObject;

import java.util.HashMap;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * helper methods.
 */
public class RegisterUserService extends IntentService {

    private static final String ACTION_REGISTER_USER = "com.seshra.user.service.action.REGISTER_USER";

    private static final String FIRST_NAME = "com.seshra.user.service.extra.FIRST_NAME";
    private static final String LAST_NAME = "com.seshra.user.service.extra.LAST_NAME";
    private static final String PHONE = "com.seshra.user.service.extra.PHONE";
    private static final String PASSWORD = "com.seshra.user.service.extra.PASSWORD";


    String url = API_S_NEW.Endpoints.REGISTER;


    SessionManager sessionManager;


    public static final String REGISTER_MESSAGE ="RegisterMessage" ;
    public static final String REGISTER_KEY = "rMessage" ;


    public RegisterUserService() {
        super("RegisterUserService");
    }


    public static void startActionRegister(Context context, String fName, String lname, String phone, String password) {
        Intent intent = new Intent(context, RegisterUserService.class);
        intent.setAction(ACTION_REGISTER_USER);
        intent.putExtra(FIRST_NAME, fName);
        intent.putExtra(LAST_NAME, lname);
        intent.putExtra(PHONE,phone);
        intent.putExtra(PASSWORD,password);
        context.startService(intent);
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_REGISTER_USER.equals(action)) {
                final String fName = intent.getStringExtra(FIRST_NAME);
                final String lName = intent.getStringExtra(LAST_NAME);
                final String phone = intent.getStringExtra(PHONE);
                final  String password = intent.getStringExtra(PASSWORD);
                handleActionFoo(fName, lName, phone, password);
            }
        }
    }

    /**
     * Handle action register user in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String fName, String lName, String phone, String password) {

        sessionManager = new SessionManager(getApplicationContext());


        HashMap<String, String> data = new HashMap<>();

        data.put("first_name", fName);
        data.put("last_name",lName);
        data.put("mobile_number","+977"+phone);
        data.put("password",password);
        data.put("country_id","227");
        data.put("unique_no", "" + Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID));
        data.put("merchant_id", "136");
        data.put("package_name", "" + BuildConfig.APPLICATION_ID);
        data.put("player_id","static_text_for_test_one_signal_not_properly_subscribed_haha");
        data.put("apk_version", BuildConfig.VERSION_NAME);
        data.put("device", "1");  // that is for android
        data.put("operating_system", "" + Build.VERSION.SDK_INT);


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
                            ModelLogin modelResultCheck = SingletonGson.getInstance()
                                    .fromJson("" + jsonObject, ModelLogin.class);

                            if(modelResultCheck.getResult().equals("999"))
                                new Logout(getApplicationContext());

                            if (modelResultCheck.getData().getUser()!=null) {

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

        Intent intent = new Intent(REGISTER_MESSAGE);
        intent.putExtra(REGISTER_KEY,result);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }



}
