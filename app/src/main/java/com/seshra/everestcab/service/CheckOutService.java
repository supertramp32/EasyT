package com.seshra.everestcab.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class CheckOutService extends IntentService {

    private static final String ACTION_RIDE_NOW = "com.seshra.user.service.action.ACTION_RIDE_NOW";


    private static  final String PICK_OBJECT = "com.seshra.user.service.extra.PICK_OBJECT";
    private static final String DROP_OBJECT = "com.seshra.user.service.extra.DROP_OBJECT";


    private HashMap<String, String> data = new HashMap<>();
    SessionManager sessionManager;
    String url = API_S_NEW.Endpoints.CHECK_OUT;

    JSONArray dropLocation = new JSONArray();



    public static final String RIDE_NOW_MESSAGE ="RideNowMessage" ;
    public static final String RIDE_NOW_MESSAGE_KEY = "rideMessage" ;



    public static final Interceptor REWRITE_RESPONSE_INTERCEPTOR_OFFLINE = chain -> {
        Request request = chain.request();
        if (isOnline()) {
            request = request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build();
        } else {
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
        }
        Response response = chain.proceed(request);

        System.out.println("network: " + response.networkResponse());
        System.out.println("cache: " + response.cacheResponse());

        return response;
    };





    public CheckOutService() {
        super("CheckOutService");
    }


    static  Context con;


    public static void startActionRideNow(Context context,
                                          String pickObject, String dropObject) {


        Intent intent = new Intent(context, CheckOutService.class);
        intent.setAction(ACTION_RIDE_NOW);
        intent.putExtra(PICK_OBJECT, pickObject);
        intent.putExtra(DROP_OBJECT,dropObject);
        con = context;
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_RIDE_NOW.equals(action)) {
                final String pickObjc = intent.getStringExtra(PICK_OBJECT);
                final String dropObj = intent.getStringExtra(DROP_OBJECT);
                handleActionRideNow(pickObjc, dropObj);
            }
        }
    }


    private void handleActionRideNow( String pickObj, String dropObj) {
        // okHttpClient.networkInterceptors().add(REWRITE_CACHE_CONTROL_INTERCEPTOR);

        File httpCacheDirectory = new File(con.getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 Mi


        try {

            JSONObject pick = new JSONObject(pickObj);


            data.put("area","310");
            data.put("service_type","1");
            data.put("booking_type","1");
            data.put("vehicle_type","341");
            data.put("pickup_latitude",pick.getString("latitude"));
            data.put("pickup_longitude",pick.getString("longitude"));
            data.put("pick_up_location",pick.getString("placeName"));
            data.put( "total_drop_location","0");

            JSONObject drop = new JSONObject(dropObj);
            dropLocation.put(drop);

            data.put("drop_location",dropLocation.toString());



        } catch (Throwable t) {
            t.printStackTrace();
        }







        sessionManager = new SessionManager(getApplicationContext());

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .addInterceptor(REWRITE_RESPONSE_INTERCEPTOR_OFFLINE)
                .cache(new Cache(httpCacheDirectory,cacheSize))
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                . writeTimeout(60, TimeUnit.SECONDS)
                .build();


       Log.d("RideNowS",url);

        AndroidNetworking.initialize(getApplicationContext());
        try {
            AndroidNetworking.post("" + url)
                    .addBodyParameter(data)
                    .setMaxAgeCacheControl(10, TimeUnit.SECONDS)
                    .addHeaders(sessionManager.getHeader())
                    .addHeaders("locale", "" + sessionManager.getLanguage())
                    .setTag(this)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsString(new StringRequestListener() {
                        @Override
                        public void onResponse(final String jsonObject) {
                            ModelResultCheck modelResultCheck = SingletonGson.getInstance().fromJson("" + jsonObject,
                                    ModelResultCheck.class);


                            Log.d("**MODEL CHECK ::", ""+modelResultCheck.result);

                            if (modelResultCheck!=null) {
                                if(modelResultCheck.getResult().equals("999"))
                                    new Logout(getApplicationContext());
                                sendMessage(jsonObject);
                            }else {
                                sendMessage("0");
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            sendMessage("0");
                            Toast.makeText(getApplicationContext(), ""+anError.getErrorDetail(), Toast.LENGTH_SHORT).show();

                        }
                    });
        } catch (Exception e) {

            e.printStackTrace();
            sendMessage("0");
        }
    }




    public static boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }




    public void sendMessage(String result){

        Intent intent = new Intent(RIDE_NOW_MESSAGE);
        intent.putExtra(RIDE_NOW_MESSAGE_KEY,result);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

    }





}
