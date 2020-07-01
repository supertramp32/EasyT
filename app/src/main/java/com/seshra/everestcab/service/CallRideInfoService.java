package com.seshra.everestcab.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.seshra.everestcab.models.ModelResultCheck;
import com.seshra.everestcab.utils.API_S_NEW;
import com.seshra.everestcab.utils.Logout;
import com.seshra.everestcab.utils.SessionManager;
import com.seshra.everestcab.utils.SingletonGson;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>

 */
public class CallRideInfoService extends IntentService {
    private static final String ACTION_CALL_RIDE_INFO = "com.seshra.user.service.action.RIDE_INFO";

    private static final String BOOKING_ID = "com.seshra.user.service.extra.BOOKING_ID";

    public CallRideInfoService() {
        super("CallRideInfoService");
    }


    SessionManager sessionManager;


    public static final String RIDE_INFO_MESSAGE ="RideIMess" ;
    public static final String RIDE_INFO_MESSAGE_KEY = "rImess" ;

    /**
     * Starts this service to perform action startActionCallRideInfo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionCallRideInfo(Context context, String bookingId) {

        Intent intent = new Intent(context, CallRideInfoService.class);
        intent.setAction(ACTION_CALL_RIDE_INFO);
        intent.putExtra(BOOKING_ID, bookingId);
        context.startService(intent);
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_CALL_RIDE_INFO.equals(action)) {
                final String param1 = intent.getStringExtra(BOOKING_ID);
                handleActionCallRideInfo(param1);
            }
        }
    }

    /**
     * Handle action callRideInfo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionCallRideInfo(String bookingId) {

        try {
//
//            File httpCacheDirectory = new File(context.getCacheDir(), "responses");
//            int cacheSize = 10 * 1024 * 1024; // 10 Mi
//
//            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
//                    .addInterceptor(REWRITE_RESPONSE_INTERCEPTOR_OFFLINE)
//                    .cache(new Cache(httpCacheDirectory, cacheSize))
//                    .connectTimeout(60, TimeUnit.SECONDS)
//                    .readTimeout(60, TimeUnit.SECONDS)
//                    .writeTimeout(60, TimeUnit.SECONDS)
//                    .build();


            sessionManager = new SessionManager(getApplicationContext());

            String url = API_S_NEW.Endpoints.RIDE_INFO;
            HashMap<String, String> b_id = new HashMap<>();
            b_id.put("booking_id", bookingId);

            // okHttpClient.networkInterceptors().add(REWRITE_CACHE_CONTROL_INTERCEPTOR);
            AndroidNetworking.initialize(getApplicationContext());
            AndroidNetworking.post("" + url)
                    .addBodyParameter(b_id)
                    .addHeaders(sessionManager.getHeader())
                    .setMaxAgeCacheControl(10, TimeUnit.SECONDS)
                    .addHeaders("locale", "" + sessionManager.getLanguage())
                    .setTag(this)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsString(new StringRequestListener() {
                        @Override
                        public void onResponse(final String jsonObject) {
                            ModelResultCheck modelResultCheck = SingletonGson.getInstance().fromJson("" + jsonObject,
                                    ModelResultCheck.class);


                            Log.d("**MODEL CHECK ::", "" + modelResultCheck.result);

                            if(modelResultCheck.getResult().equals("999"))
                                new Logout(getApplicationContext());

                            if (modelResultCheck.result.equals("0")) {
                                sendMessage("0");
//                            apifetcher.onFetchResultZero("" + modelResultCheck.message, tag);
                            } else if (modelResultCheck.result.equals("2")) {
                                Log.d("**MODEL CHECK == ::", "" + modelResultCheck.result);


                                sendMessage("0");
                                //     apifetcher_for_google.onFetchResultTwo(""+modelResultCheck.result, "SOCIAL_LOGIN");

//                            if (tag.equals("SOCIAL_LOGINGoogle")|| tag.equals("SOCIAL_LOGINfacebook")){
//
//                                apifetcher.onFetchResultZero("" + modelResultCheck.result, tag);
//
//                            }else {
//
//                                apifetcher.onFetchResultZero("" + modelResultCheck.message, tag);
//
//                            }
                            } else if (modelResultCheck.result.equals("999")) {
//                            logoutUnAuthorizedDriver(sessionManager);
                                sendMessage("0");
                            } else {
                                sendMessage(jsonObject);
//                            apifetcher.onFetchComplete(jsonObject, tag);
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
//                        apifetcher.onAPIRunningState(KEY_API_IS_STOPPED, tag);
                            Toast.makeText(getApplicationContext(), "" + anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
//                        ApporioLog.logE("errror", "" + anError.getErrorBody());
//                        ApporioLog.logE("errror", "" + anError.getErrorDetail());
//                        ApporioLog.logE("errror", "" + anError.getMessage());
//                        ApporioLog.logE("error", "" + anError.getStackTrace());
//                        ApporioLog.logE("error", "" + anError.getCause());
                        }
                    });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendMessage(String result){

        Intent intent = new Intent(RIDE_INFO_MESSAGE);
        intent.putExtra(RIDE_INFO_MESSAGE_KEY,result);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

    }

}
