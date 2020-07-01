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
import com.seshra.everestcab.utils.SingletonGson;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * helper methods.
 */
public class GetOTPService extends IntentService {
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String GET_OTP = "com.seshra.user.service.action.OTP";

    private static final String PHONE_NUMBER = "com.seshra.user.service.extra.PHONE_NUMBER";
    private static final String TYPE = "com.seshra.user.service.extra.TYPE";

    public GetOTPService() {
        super("GetOTPService");
    }

    public static final String OTP_MESSAGE ="OtpMessage" ;
    public static final String OTP_KEY = "oMessage" ;

    String url = API_S_NEW.Endpoints.OTP;


    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionGetOtp(Context context, String phone, String type) {
        Intent intent = new Intent(context, GetOTPService.class);
        intent.setAction(GET_OTP);
        intent.putExtra(PHONE_NUMBER, phone);
        intent.putExtra(TYPE, type);
        context.startService(intent);
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (GET_OTP.equals(action)) {
                final String param1 = intent.getStringExtra(PHONE_NUMBER);
                final String param2 = intent.getStringExtra(TYPE);
                handleActionFoo(param1, param2);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String phone, String type) {


        HashMap<String, String> data = new HashMap<>();
        data.put("mobile_number", "+977"+phone);
        data.put("for", "PHONE");
        data.put("type", type);








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

                            ModelResultCheck modelResultCheck = SingletonGson.getInstance()
                                    .fromJson("" + jsonObject, ModelResultCheck.class);

                            if(modelResultCheck!=null){
                                if(modelResultCheck.getResult().equals("999"))
                                    new Logout(getApplicationContext());

                                sendMessage(jsonObject.toString());
                            }else
                                sendMessage("0");
//                            if(modelResultCheck.getResult().equals("1")){
//                                Toast.makeText(getApplicationContext(),modelResultCheck.getMessage(),Toast.LENGTH_LONG).show();
//                            }else {
//                                Toast.makeText(getApplicationContext(),modelResultCheck.getMessage(),Toast.LENGTH_LONG).show();
//                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            sendMessage("0");
                        }

                    }

                    @Override
                    public void onError(ANError anError) {

                        sendMessage("0");

                        anError.printStackTrace();
                    }
                });


    }



    public void sendMessage(String result){

        Intent intent = new Intent(OTP_MESSAGE);
        intent.putExtra(OTP_KEY,result);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }



}
