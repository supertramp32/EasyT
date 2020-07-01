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
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ApplyPromoCodeServcie extends IntentService {

    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_PROMO_CODE = "com.seshra.user.service.action.PROMO_CODE";

    private static final String CHECKOUT_ID = "com.seshra.user.service.extra.CHECK_OUT_ID";
    private static final String PROMO_CODE = "com.seshra.user.service.extra.PROMO_CODE";


    public static final String PROMO_MESSAGE ="PromoMessage" ;
    public static final String PROMO_MESSAGE_KEY = "pMessage" ;


    String url = API_S_NEW.Endpoints.APPLY_COUPON;

    SessionManager sessionManager;

    public ApplyPromoCodeServcie() {
        super("ApplyPromoCodeServcie");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionApplyPromoCode(Context context, String checkoutId, String promoCode) {
        Intent intent = new Intent(context, ApplyPromoCodeServcie.class);
        intent.setAction(ACTION_PROMO_CODE);
        intent.putExtra(CHECKOUT_ID, checkoutId);
        intent.putExtra(PROMO_CODE, promoCode);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_PROMO_CODE.equals(action)) {
                final String param1 = intent.getStringExtra(CHECKOUT_ID);
                final String param2 = intent.getStringExtra(PROMO_CODE);
                handleActionFoo(param1, param2);
            }
        }
    }

    /**
     * Handle action apply Promo Code in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String checkOutId, String promoCode) {


        HashMap<String,String> data = new HashMap<>();
        data.put("checkout_id",checkOutId);
        data.put("promo_code",promoCode);

        sessionManager = new SessionManager(getApplicationContext());

        AndroidNetworking.post("" + url)
                .addHeaders(IntentKeys.PUBLIC_KEY, "" + BaseConfig.PUBLIC_KEY)
                .addHeaders(IntentKeys.SECRET_KEY, "" + BaseConfig.SECRET_KEY)
                .addBodyParameter(data)
                .addHeaders("Authorization",sessionManager.getAccessToken())
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
                          }else {
                              sendMessage("0");
                          }



                        }catch (Exception e){

                            e.printStackTrace();
                            sendMessage("0");


                        }

                    }

                    @Override
                    public void onError(ANError anError) {

                        anError.printStackTrace();

                    }
                });





    }
    public void sendMessage(String result){

        Intent intent = new Intent(PROMO_MESSAGE);
        intent.putExtra(PROMO_MESSAGE_KEY,result);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

}
