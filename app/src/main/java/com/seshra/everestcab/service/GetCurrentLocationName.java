package com.seshra.everestcab.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.ANResponse;
import com.androidnetworking.common.Priority;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class GetCurrentLocationName extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.seshra.user.service.action.FOO";

    // TODO: Rename parameters
    private static final String LATTITUDE = "com.seshra.user.service.extra.LATTITUDE";
    private static final String LONGITUDE = "com.seshra.user.service.extra.LONGITUDE";


    public static final String PLACE_SEARCH_MESSAGE ="PlaceMessage" ;
    public static final String PLACE_SEARCH_KEY = "placeMessage" ;

    public GetCurrentLocationName() {
        super("GetCurrentLocationName");
    }


    public static void startActionCurrentPlce(Context context, Double lattitude, Double longitude) {
        Intent intent = new Intent(context, GetCurrentLocationName.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(LATTITUDE, lattitude);
        intent.putExtra(LONGITUDE, longitude);
        context.startService(intent);
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final double lattitude = intent.getDoubleExtra(LATTITUDE,0);
                final double longitude = intent.getDoubleExtra(LONGITUDE,0);
                handleActionGetPlaceName(lattitude, longitude);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionGetPlaceName(Double lattitude, Double longitude) {


//        String url = "http://206.189.128.192:7070/reverse?format=jsonv2&lat="+lattitude+"&lon="+longitude;

//        String url = "http://easytaxinepal.com/nominatim/reverse?format=jsonv2&lat="+lattitude+"&lon="+longitude;

        String url = "https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat="+lattitude+"&lon="+longitude;




        ANRequest request = AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .build();

        ANResponse<JSONObject> response = request.executeForJSONObject();
        if(response.isSuccess()){

            try {
                String placeName = response.getResult().getString("display_name");
                Log.d("PlaceName",placeName);
                sendMessage(placeName);
            } catch (JSONException e) {
                e.printStackTrace();
                sendMessage("0");
                Log.d("PlaceName","Error");

            }

        }else {
//            Toast.makeText(getApplicationContext(),"Error getting location..",Toast.LENGTH_LONG).show();
            sendMessage("0");
        }




    }


    public void sendMessage(String result){

        Intent intent = new Intent(PLACE_SEARCH_MESSAGE);
        intent.putExtra(PLACE_SEARCH_KEY,result);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

    }

}
