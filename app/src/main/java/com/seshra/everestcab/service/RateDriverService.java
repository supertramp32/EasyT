package com.seshra.everestcab.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.seshra.everestcab.MainActivity;
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
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class RateDriverService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_RATE_DRIVER = "com.seshra.user.service.action.RATE_DRIVER";

    private static final String BOOKING_ID = "com.seshra.user.service.extra.BOOKING_ID";
    private static final String DRIVER_RATING = "com.seshra.user.service.extra.DRIVER_RATING";
    private static final String COMMENTS = "com.seshra.user.service.extra.COMMENTS";


    public RateDriverService() {
        super("RateDriverService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionRateDriver(Context context, String bookingId, String driverRate, String comments) {
        Intent intent = new Intent(context, RateDriverService.class);
        intent.setAction(ACTION_RATE_DRIVER);
        intent.putExtra(BOOKING_ID, bookingId);
        intent.putExtra(DRIVER_RATING, driverRate);
        intent.putExtra(COMMENTS,comments);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_RATE_DRIVER.equals(action)) {
                final String bookingId = intent.getStringExtra(BOOKING_ID);
                final String driverRate = intent.getStringExtra(DRIVER_RATING);
                final String comments = intent.getStringExtra(COMMENTS);
                handleActionRateDriverService(bookingId, driverRate,comments);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionRateDriverService(String bookingId, String driverRate, String comments) {


        String url = API_S_NEW.Endpoints.RATE_DRIVER;
        HashMap<String, String> data = new HashMap<>();
        data.put("booking_id", "" + bookingId);
        data.put("rating", "" + driverRate);
        data.put("comment", "" + comments);
        SessionManager sessionManager = new SessionManager(getApplicationContext());

        try {
            // okHttpClient.networkInterceptors().add(REWRITE_CACHE_CONTROL_INTERCEPTOR);
            AndroidNetworking.initialize(getApplicationContext());
            AndroidNetworking.post("" + url)
                    .addBodyParameter(data)
                    .addHeaders(sessionManager.getHeader())
                    .setMaxAgeCacheControl(10, TimeUnit.SECONDS)
                    .addHeaders("locale", "" + sessionManager.getLanguage())
                    .setTag(this)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsString(new StringRequestListener() {
                        @Override
                        public void onResponse(final String jsonObject) {


                            ModelResultCheck modelResultCheck = SingletonGson.getInstance().fromJson("" +
                                    jsonObject, ModelResultCheck.class);


                          if(modelResultCheck!=null){

                              if(modelResultCheck.getResult().equals("999"))
                                  new Logout(getApplicationContext());

                              if(modelResultCheck.getResult().equals("1")){
                                  Toast.makeText(getApplicationContext(),modelResultCheck.getMessage(),Toast.LENGTH_LONG).show();


                              }else
                                  Toast.makeText(getApplicationContext(),modelResultCheck.getMessage(),Toast.LENGTH_LONG).show();



                                  startActivity(new Intent(getApplicationContext(), MainActivity.class)
                                          .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                          .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                          .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                  );


                          }









                        }

                        @Override
                        public void onError(ANError anError) {
                            Toast.makeText(getApplicationContext(), "" + anError.getErrorDetail(), Toast.LENGTH_SHORT).show();

                        }
                    });
        }catch (Exception e){
            e.printStackTrace();

        }


    }


}
