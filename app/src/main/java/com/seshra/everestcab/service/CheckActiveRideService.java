package com.seshra.everestcab.service;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.seshra.everestcab.models.ModelActiveRide;
import com.seshra.everestcab.utils.API_S_NEW;
import com.seshra.everestcab.utils.Logout;
import com.seshra.everestcab.utils.STATUS;
import com.seshra.everestcab.utils.SessionManager;
import com.seshra.everestcab.utils.SingletonGson;

import java.util.concurrent.TimeUnit;


public class CheckActiveRideService extends IntentService {


    SessionManager sessionManager;
    String url = API_S_NEW.Endpoints.ACTIVE_RIDE;


    public static final String CHECK_ACTIVE_RIDE_MESSAGE ="checkActive" ;
    public static final String CHECK_ACTIVE_RIDE_MESSAGE_KEY = "checkActiveMessage" ;


    public CheckActiveRideService() {
        super("CheckActiveRideService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {



        sessionManager = new SessionManager(getApplicationContext());

        try {
            AndroidNetworking.initialize(getApplicationContext());
            AndroidNetworking.post("" + url)
                    .addHeaders(sessionManager.getHeader())
                    .setMaxAgeCacheControl(10, TimeUnit.SECONDS)
                    .addHeaders("locale", "" + sessionManager.getLanguage())
                    .setTag(this)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsString(new StringRequestListener() {
                        @Override
                        public void onResponse(final String jsonObject) {


                      ModelActiveRide modelActiveRide = SingletonGson.getInstance()
                              .fromJson("" + jsonObject, ModelActiveRide.class);

                            if(modelActiveRide.getResult().equals("999"))
                                new Logout(getApplicationContext());

                            sendMessage(jsonObject);

                      if(modelActiveRide.getData().size()!=0) {

                          if (modelActiveRide.getData().get(0).getBooking_status().equals("" + STATUS.ACCEPTED) ||
                                  modelActiveRide.getData().get(0).getBooking_status().equals("" + STATUS.ARRIVED) ||
                                  modelActiveRide.getData().get(0).getBooking_status().equals("" + STATUS.STARTED)) {


//
//                              sendMessage("1");
////
////                              showActiveRideDialog(""+ modelActiveRide.getData().get(0).getId());
////                              startActivity(new Intent(getApplicationContext(), DriverWaitingActivity.class)
////                                      .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
////                                      .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
////                                      .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//////                                      .putExtra(IntentKeys.TYPE,"1")
////                                      .putExtra(IntentKeys.BOOKING_ID,""+modelActiveRide.getData().get(0).getId())
////                              );
////
////                              Toast.makeText(getApplicationContext(),
////                                      getApplicationContext().getResources().getString(R.string.in_progress), Toast.LENGTH_LONG).show();


                          } else if (modelActiveRide.getData().get(0).getBooking_status().equals("" + STATUS.END)) {

//
//                              sendMessage("2");
////                              startActivity(new Intent(getApplicationContext(), ReceiptActivity.class)
////                                      .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
////                                      .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
////                                      .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
////                                      .putExtra("" + IntentKeys.BOOKING_ID,
////                                              "" + modelActiveRide.getData().get(0).getId()));
////
////                              Toast.makeText(getApplicationContext(),
////                                    getApplicationContext().getResources().getString(R.string.in_progress),
////                                      Toast.LENGTH_LONG).show();

                          }else {
                              sessionManager.clearTempBookingID();
                          }
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



    public void sendMessage(String result){

        Intent intent = new Intent(CHECK_ACTIVE_RIDE_MESSAGE);
        intent.putExtra(CHECK_ACTIVE_RIDE_MESSAGE_KEY,result);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

    }



}
