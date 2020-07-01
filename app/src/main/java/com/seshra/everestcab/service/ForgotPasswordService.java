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
import com.seshra.everestcab.models.ModelResultCheck;
import com.seshra.everestcab.utils.API_S_NEW;
import com.seshra.everestcab.utils.BaseConfig;
import com.seshra.everestcab.utils.IntentKeys;
import com.seshra.everestcab.utils.Logout;
import com.seshra.everestcab.utils.SingletonGson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * helper methods.
 */
public class ForgotPasswordService extends IntentService {

    private static final String ACTION_FORGOT_PASSWORD = "com.seshra.user.service.action.FOO";

    // TODO: Rename parameters
    private static final String PHONE_NUMBER = "com.seshra.user.service.extra.PHONE_NUMBER";
    private static final String PASSWORD = "com.seshra.user.service.extra.PASSWORD";

    String url = API_S_NEW.Endpoints.FORGOT_PASSWORD;

    public static final String FORGOT_PASSWORD_MESSAGE ="ForgotPasswordMessage" ;
    public static final String FORGOT_PASSWORD_MESSAGE_KEY = "forgotMessage" ;



    public ForgotPasswordService() {
        super("ForgotPasswordService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionForgotPassword(Context context, String phone, String password) {
        Intent intent = new Intent(context, ForgotPasswordService.class);
        intent.setAction(ACTION_FORGOT_PASSWORD);
        intent.putExtra(PHONE_NUMBER, phone);
        intent.putExtra(PASSWORD, password);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FORGOT_PASSWORD.equals(action)) {
                final String param1 = intent.getStringExtra(PHONE_NUMBER);
                final String param2 = intent.getStringExtra(PASSWORD);
                handleActionForgotPassword(param1, param2);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionForgotPassword(String phone, String password) {


        Map<String, String> data=new HashMap<>();
        data.put("mobile_number","+977" + phone);
        data.put("password",password);
        data.put("for", "PHONE");

        AndroidNetworking.post("" + url)
                .addBodyParameter(data)
                .addHeaders("Content-Type", "application/json")
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
                            ModelResultCheck modelResultCheck = SingletonGson.getInstance().fromJson("" + jsonObject, ModelResultCheck.class);
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

        Intent intent = new Intent(FORGOT_PASSWORD_MESSAGE);
        intent.putExtra(FORGOT_PASSWORD_MESSAGE_KEY,result);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

    }




}
