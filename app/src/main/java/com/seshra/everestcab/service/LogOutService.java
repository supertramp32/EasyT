package com.seshra.everestcab.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.seshra.everestcab.CheckUserPhoneActivity;
import com.seshra.everestcab.models.ModelResultCheck;
import com.seshra.everestcab.utils.API_S_NEW;
import com.seshra.everestcab.utils.BaseConfig;
import com.seshra.everestcab.utils.IntentKeys;
import com.seshra.everestcab.utils.Logout;
import com.seshra.everestcab.utils.SessionManager;
import com.seshra.everestcab.utils.SingletonGson;

import org.json.JSONObject;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * helper methods.
 */
public class LogOutService extends IntentService {

    private static final String ACTION_LOGOUT = "com.seshra.user.service.action.LOG_OUT";

    String url = API_S_NEW.Endpoints.LOGOUT;

    SessionManager sessionManager;



    public LogOutService() {
        super("LogOutService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionLogOut(Context context) {
        Intent intent = new Intent(context, LogOutService.class);
        intent.setAction(ACTION_LOGOUT);
        context.startService(intent);
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_LOGOUT.equals(action)) {
                handleActionLogout();
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionLogout() {

        sessionManager = new SessionManager(getApplicationContext());

        AndroidNetworking.post("" + url)
                .addHeaders(IntentKeys.PUBLIC_KEY, "" + BaseConfig.PUBLIC_KEY)
                .addHeaders(IntentKeys.SECRET_KEY, "" + BaseConfig.SECRET_KEY)
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

                            if(modelResultCheck.getResult().equals("999"))
                                new Logout(getApplicationContext());

                            if(modelResultCheck.getResult().equals("1")) {
                                sessionManager.logoutUser();
                                sessionManager.cleartAccessToken("");
                                startActivity(new Intent(getApplicationContext(), CheckUserPhoneActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                Toast.makeText(getApplicationContext(), modelResultCheck.getMessage(), Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(getApplicationContext(), modelResultCheck.getMessage(), Toast.LENGTH_LONG).show();
                            }



                        }catch (Exception e){

                            e.printStackTrace();

                        }

                    }

                    @Override
                    public void onError(ANError anError) {

                        anError.printStackTrace();

                    }
                });

    }


}
