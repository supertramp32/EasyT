package com.seshra.everestcab.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.seshra.everestcab.R;
import com.seshra.everestcab.models.ModelEditProfile;
import com.seshra.everestcab.utils.API_S_NEW;
import com.seshra.everestcab.utils.Logout;
import com.seshra.everestcab.utils.SessionManager;
import com.seshra.everestcab.utils.SingletonGson;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * helper methods.
 */
public class EditProfileService extends IntentService {

    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_EDIT_PROFILE = "com.seshra.user.service.action.EDIT_PROFILE";
    private static final String ACTION_EDIT_PROFILE_WITH_IMAGE = "com.seshra.user.service.action.EDIT_PROFILE_IMAGE";


    private static final String FULL_NAME = "com.seshra.user.service.extra.FULL_NAME";
    private static final String EMAIL = "com.seshra.user.service.extra.EMAIL";
    private static final String GENDER = "com.seshra.user.service.extra.GENDER";
    private static final String PROFILE_IMAGE = "com.seshra.user.service.extra.PROFILE_IMAGE";
    private static final String PROFILE_IMAGE_TYPE = "com.seshra.user.service.extra.PROFILE_IMAGE_TYPE";



    String url = API_S_NEW.Endpoints.EDIT_PROFILE;

    SessionManager sessionManager;



    public static final String PROFILE_EDIT_MESSAGE ="ProfileMessage" ;
    public static final String PROFILE_EDIT_MESSAGE_KEY = "pMessage" ;



    public EditProfileService() {
        super("EditProfileService");
    }

    /**
     * Starts this service to perform action Edit Profile with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionEditProfile(Context context, String fullName, String email, String gender) {
        Intent intent = new Intent(context, EditProfileService.class);
        intent.setAction(ACTION_EDIT_PROFILE);
        intent.putExtra(FULL_NAME, fullName);
        intent.putExtra(EMAIL, email);
        intent.putExtra(GENDER,gender);
        context.startService(intent);
    }


    public static void startActionEditProfileWithImage(Context context, String fullName, String email, String gender,String image,int type) {
        Intent intent = new Intent(context, EditProfileService.class);
        intent.setAction(ACTION_EDIT_PROFILE_WITH_IMAGE);
        intent.putExtra(FULL_NAME, fullName);
        intent.putExtra(EMAIL, email);
        intent.putExtra(GENDER,gender);
        intent.putExtra(PROFILE_IMAGE,image);
        intent.putExtra(PROFILE_IMAGE_TYPE,type);
        context.startService(intent);
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_EDIT_PROFILE.equals(action)) {
                final String fullName = intent.getStringExtra(FULL_NAME);
                final String email = intent.getStringExtra(EMAIL);
                final  String gender = intent.getStringExtra(GENDER);
                handleActionEditProfile(fullName, email,gender);
            }else if(ACTION_EDIT_PROFILE_WITH_IMAGE.equals(action)){
                final String fullName = intent.getStringExtra(FULL_NAME);
                final String email = intent.getStringExtra(EMAIL);
                final  String gender = intent.getStringExtra(GENDER);
                final String image = intent.getStringExtra(PROFILE_IMAGE);
                final int type = intent.getIntExtra(PROFILE_IMAGE_TYPE,0);

                handleActionEditProfilewithImage(fullName, email,gender,image,type);

            }
        }
    }

    private void handleActionEditProfilewithImage(String fullName, String email, String gender, String image,int type) {

        sessionManager = new SessionManager(this);


        String[] splitStr = fullName.trim().split("\\s+");
        HashMap<String, String> data = new HashMap<>();
        data.put("first_name",  splitStr[0]);
        data.put("last_name",splitStr[1]);
        if(gender.equals("Male"))
            data.put("user_gender","1");
        else
            data.put("user_gender","2");

        if(!email.isEmpty())
            data.put("email",email);
        HashMap<String, File> file_data = new HashMap<>();

        try {
            if(type==2)
                file_data.put("profile_image", new File(getRealPathFromURI(Uri.parse(image))));
            else
                file_data.put("profile_image", new File(image));

        }catch (Exception e){
            e.printStackTrace();

        }

        try {


            AndroidNetworking.upload("" + url)
                    .addMultipartFile(file_data)
                    .addQueryParameter(data)
                    .addHeaders(sessionManager.getHeader())
                    .addHeaders("locale", "" + sessionManager.getLanguage())
                    .setTag(this)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(final JSONObject jsonObject) {
                            try{
                                ModelEditProfile modelResultCheck = SingletonGson.getInstance()
                                        .fromJson("" + jsonObject, ModelEditProfile.class);
                                if (modelResultCheck.getData()!=null) {

                                    if(modelResultCheck.getResult().equals("999"))
                                        new Logout(getApplicationContext());

                                    saveUserDetails(jsonObject);
                                }else {

                                    Toast.makeText(getApplicationContext(), modelResultCheck.getMessage(),Toast.LENGTH_LONG).show();


                                }
                            }catch (Exception e){

                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error),Toast.LENGTH_LONG).show();

                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                           anError.printStackTrace();
                        }
                    });
        } catch (Exception e) {

            sendMessage("0");
            e.printStackTrace();
        }

    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionEditProfile(String fullName, String email , String gender) {

        String[] splitStr = fullName.trim().split("\\s+");




         sessionManager = new SessionManager(this);

        HashMap<String, String> data = new HashMap<>();
        data.put("first_name",  splitStr[0]);
        data.put("last_name",splitStr[1]);
        if(gender.equals("Male"))
            data.put("user_gender","1");
        else
            data.put("user_gender","2");
        if(!email.isEmpty())
            data.put("email",email);


        HashMap<String, String> headers = new HashMap<>();
        try {
            headers = sessionManager.getHeader();
            headers.put("content-type","application/json");
            headers.put("accept","application/json");
        } catch (Exception e) {
            e.printStackTrace();
        }



        AndroidNetworking.post("" + url)
                .addBodyParameter(data)
                .addHeaders(headers)
                .setTag(this)

                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(final JSONObject jsonObject) {
                        try{
                            ModelEditProfile modelResultCheck = SingletonGson.getInstance().fromJson(""
                                    + jsonObject, ModelEditProfile.class);

                            if (modelResultCheck.getData()!=null) {

                                if(modelResultCheck.getResult().equals("1"))
                                    saveUserDetails(jsonObject);
                                else
                                    sendMessage(jsonObject.toString());

                            }else {


                                sendMessage("0");
//                                Toast.makeText(getApplicationContext(), modelResultCheck.getMessage(),Toast.LENGTH_LONG).show();


                            }
                        }catch (Exception e){

                            sendMessage("0");
//                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.error),Toast.LENGTH_LONG).show();

                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        sendMessage("0");

                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_internet_connection),Toast.LENGTH_LONG).show();

                    }
                });


    }


    private void saveUserDetails(JSONObject jsonObject) {


        ModelEditProfile modelEditProfile = SingletonGson.getInstance().fromJson("" + jsonObject, ModelEditProfile.class);

        sessionManager = new SessionManager(this);




        sessionManager.createLoginSession(modelEditProfile.getData().getFirst_name(),
                modelEditProfile.getData().getLast_name(),
                modelEditProfile.getData().getEmail(),
                modelEditProfile.getData().getMobile_number(),
                modelEditProfile.getData().getProfile_image(),
                modelEditProfile.getData().getTotal_trips(),
                modelEditProfile.getData().getWallet_balance()
        );

        sendMessage(jsonObject.toString());

//        Toast.makeText(getApplicationContext(), modelEditProfile.getMessage(),Toast.LENGTH_LONG).show();

    }



    public void sendMessage(String result){

        Intent intent = new Intent(PROFILE_EDIT_MESSAGE);
        intent.putExtra(PROFILE_EDIT_MESSAGE_KEY,result);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

    }


      public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

}
