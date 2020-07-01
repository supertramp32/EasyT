package com.seshra.everestcab.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


import com.seshra.everestcab.models.ModelConfiguration;

import java.util.HashMap;
import java.util.Locale;

public class SessionManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    SharedPreferences pref1;
    SharedPreferences.Editor editor1;

    Context _context;
    int PRIVATE_MODE = 0;

    private HashMap<String, String> headers = new HashMap<>();

    private String TAG = "SessionManager";
    private static final String PREF_NAME = "LoginPrefrences";
    private static final String PREF_NAME_1 = "LanguagePrefrences";
    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String USER_ID = "user_id";

    public static final String USER_FULL_NAME = "user_full_name";
    public static final String USER_BUSINESS_NAME = "user_business_name";



    public static final String USER_FIRST_NAME = "user_first_name";
    public static final String USER_LAST_NAME = "user_last_name";
    public static final String USER_GENDER = "user_gender";
    public static final String USER_SMOKER_TYPE = "user_smoker_type";
    public static final String USER_ALLOW_SMOKER = "user_allow_smoker";
    public static final String USER_PHONE = "user_phone_number";
    public static final String USER_PHONE_CODE = "user_phone_code";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_PASSWORD = "user_password";
    public static final String USER_IMAGE = "user_image";
    public static final String FACEBOOK_MAIL = "facebook_mail";
    public static final String GOOGLE_MAIL = "googlr_mail";
    public static final String LOGIN_TYPE = "logintype";
    public static final String LOGIN_NORAL = "normal";
    public static final String LOGIN_GOOGLE = "google";
    public static final String LOGIN_FACEBOOK = "facebook";
    public static final String LANGUAGE = "Languagae";
    public static final String KEY_ACCESS_TOKEN = "access_token";
    public static final String KEY_APP_CONFIG = "KEY_APP_CONFIG";
    public static final String DEFAULT_LANGUAGE = "DEFAULT_LANGUAGE";
    public static final String SHOW_LANGUAGE_LIST = "SHOW_LANGUAGE_LIST";
    public static final String COUNTRYID = "COUNTRY_ID";
    public static final String Carrier_selected = "Carrier_selected";
    public static final String TOTAL_TRIPS = "TOTAL_TRIPS";
    public static final String WALLET_BALANCE = "WALLET_BALANCE";



    public static final String KEY_CAR_TYPE_POSITION = "KEY_CAR_TYPE_POSITION";
    public static final String KEY_SERVICE_TYPE_ID = "KEY_SERVICE_TYPE_ID";
    public static final String KEY_TAB_POSITION = "KEY_TAB_POSITION";
    public static final String KEY_STRING_VERSION = "KEY_STRING_VERSION";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        pref1 = _context.getSharedPreferences(PREF_NAME_1, PRIVATE_MODE);
        editor1 = pref1.edit();
    }

    public String getLanguage() {
        return pref1.getString(LANGUAGE, "");
    }


    public void setLanguage(String locale) {
        editor1.putString(LANGUAGE, "" + locale);
        editor1.commit();

        Locale myLocale = new Locale("" + locale);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        _context.getResources().updateConfiguration(config, _context.getResources().getDisplayMetrics());
    }

    @SuppressLint("LongLogTag")
    public void createLoginSession(String user_id, String fist_name,String last_name, String user_email, String user_password, String user_phone,String user_phone_code, String userimage, String user_gender,String smoker_type,String allow_smoker, String login_type) {
        editor.putString(USER_ID, user_id);
        editor.putString(USER_FIRST_NAME, fist_name);
        editor.putString(USER_LAST_NAME, last_name);
        editor.putString(USER_GENDER, user_gender);
        editor.putString(USER_SMOKER_TYPE, smoker_type);
        editor.putString(USER_ALLOW_SMOKER, allow_smoker);
        editor.putString(USER_EMAIL, user_email);
        editor.putString(USER_PASSWORD, user_password);
        editor.putString(USER_PHONE, user_phone);
        editor.putString(USER_PHONE_CODE,user_phone_code);
        editor.putString(USER_IMAGE, userimage);
        editor.putString(LOGIN_TYPE, login_type);
        editor.commit();
    }




//    @SuppressLint("LongLogTag")
//    public void createLoginSession(String agent_id, String full_name,String business_name, String mobile_number, String avatar) {
//        editor.putString(USER_ID, agent_id);
//        editor.putString(USER_FULL_NAME, full_name);
//        editor.putString(USER_BUSINESS_NAME, business_name);
//        editor.putString(USER_PHONE, mobile_number);
//        editor.putString(USER_IMAGE, avatar);
//        editor.commit();
//    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<>();
        user.put(USER_ID, pref.getString(USER_ID, ""));
        user.put(USER_FIRST_NAME, pref.getString(USER_FIRST_NAME, ""));
        user.put(USER_LAST_NAME, pref.getString(USER_LAST_NAME, ""));
        user.put(USER_GENDER, pref.getString(USER_GENDER, ""));
        user.put(USER_SMOKER_TYPE, pref.getString(USER_SMOKER_TYPE, ""));
        user.put(USER_ALLOW_SMOKER, pref.getString(USER_ALLOW_SMOKER, ""));
        user.put(USER_EMAIL, pref.getString(USER_EMAIL, "default@gmail.com"));
        user.put(USER_PASSWORD, pref.getString(USER_PASSWORD, ""));
        user.put(USER_PHONE, pref.getString(USER_PHONE, ""));
        user.put(USER_PHONE_CODE, pref.getString(USER_PHONE_CODE, ""));
        user.put(USER_IMAGE, pref.getString(USER_IMAGE, ""));
        user.put(LOGIN_TYPE, pref.getString(LOGIN_TYPE, ""));
        user.put(USER_BUSINESS_NAME, pref.getString(USER_BUSINESS_NAME,""));
        user.put(USER_FULL_NAME,pref.getString(USER_FULL_NAME,""));
        user.put(TOTAL_TRIPS,pref.getString(TOTAL_TRIPS,"0"));
        user.put(WALLET_BALANCE,pref.getString(WALLET_BALANCE,"0"));
        return user;
    }

    public void makUserLoggedIn(){
        editor.putBoolean(IS_LOGIN, true);
        editor.commit();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void logoutUser() {
        editor.clear();
        editor.commit();
    }


    public String getEmail() {
        String email = "";
        if (pref.getString(LOGIN_TYPE, "").equals("" + LOGIN_NORAL)) {
            email = pref.getString(USER_EMAIL, "");
        }
        if (pref.getString(LOGIN_TYPE, "").equals("" + LOGIN_FACEBOOK)) {
            email = pref.getString(FACEBOOK_MAIL, "");
        }
        if (pref.getString(LOGIN_TYPE, "").equals("" + LOGIN_GOOGLE)) {
            email = pref.getString(GOOGLE_MAIL, "");
        }
        return email;
    }

    @SuppressLint("LongLogTag")
    public HashMap<String, String> getHeader() throws Exception {
        headers.clear();
        if (!getAccessToken().equals("")) {
            headers.put("Authorization", "" + getAccessToken());
            Log.d("authorization key:", getAccessToken());
            return headers;
        } else {
            headers.put(IntentKeys.PUBLIC_KEY, "" + BaseConfig.PUBLIC_KEY);
            headers.put(IntentKeys.SECRET_KEY, "" + BaseConfig.SECRET_KEY);
            return headers;
        }
    }



    @SuppressLint("LongLogTag")
    public void setAccessToken(String accessToken) {
        editor.putString(KEY_ACCESS_TOKEN, "Bearer " + accessToken);
        editor.commit();
    }

    public String getAccessToken() {
        return "" + pref.getString(KEY_ACCESS_TOKEN, "");
    }

    @SuppressLint("LongLogTag")
    public void setCarType(Integer position) {
        editor.putInt(KEY_CAR_TYPE_POSITION, position);
        editor.putInt(KEY_CAR_TYPE_POSITION, position);
        editor.commit();
    }

    public Integer getCartYpe() {
        return Integer.valueOf("" + pref.getInt(KEY_CAR_TYPE_POSITION, 0));
    }

    @SuppressLint("LongLogTag")
    public void setServiceTypeId(Integer service_type_id) {
        editor.putInt(KEY_SERVICE_TYPE_ID, service_type_id);
        editor.commit();
    }

    public Integer getServiceTypeId() {
        return Integer.valueOf("" + pref.getInt(KEY_SERVICE_TYPE_ID, 0));
    }

    @SuppressLint("LongLogTag")
    public void setTabSelected(Integer tab_state_position) {
        editor.putInt(KEY_TAB_POSITION, tab_state_position);
        editor.commit();
    }

    @SuppressLint("LongLogTag")
    public void setUpdatedStringVersion(String version) {

        editor.putString(KEY_STRING_VERSION, version);
        editor.commit();
    }

    @SuppressLint("LongLogTag")
    public String getUpdateStringVersion() {
        return "" + pref.getString(KEY_STRING_VERSION, "0.0");
    }

    public Integer gettabId() {
        return Integer.valueOf("" + pref.getInt(KEY_TAB_POSITION, 0));
    }



    public String getDefaultLanguage() {
        return pref.getString(DEFAULT_LANGUAGE, "en");
    }

    public void setDefaultLanguage(String defaultLanguage) {
        editor.putString(DEFAULT_LANGUAGE, defaultLanguage);
        editor.commit();
    }

    public boolean isShowLanguageList() {
        return pref.getBoolean(SHOW_LANGUAGE_LIST, true);
    }

    public void setShowLanguageList(boolean defaultLanguage) {
        editor.putBoolean(SHOW_LANGUAGE_LIST, defaultLanguage);
        editor.commit();
    }

    @SuppressLint("LongLogTag")
    public void cleartAccessToken(String accessToken) {

        editor.putString(KEY_ACCESS_TOKEN, "" + accessToken);
        editor.commit();
    }

    public void setcountryid(int id)
    {
        editor.putInt(COUNTRYID, id);
        editor.commit();

    }

    public int getcountryid()
    {
        return pref.getInt(COUNTRYID, 1);

    }

    public void setCarriers(int position) {
        editor.putString(Carrier_selected,""+position);
        editor.commit();
    }

    public String getCarriers() {
        return "" + pref.getString(Carrier_selected, "");
    }


     @SuppressLint("LongLogTag")
     public void createLoginSession( String first_name, String last_name, String email, String mobile_number, String profile_image,
                                     String totalTrips, String walletBalance) {

        editor.putString(USER_FIRST_NAME, first_name);
        editor.putString(USER_LAST_NAME, last_name);
        editor.putString(USER_EMAIL, email);
        editor.putString(USER_PHONE, mobile_number);
        editor.putString(USER_IMAGE, profile_image);
        editor.putString(TOTAL_TRIPS,totalTrips);
        editor.putString(WALLET_BALANCE,walletBalance);
        editor.commit();

    }


    public void setAppConfig(String appConfig) {

        editor.putString(KEY_APP_CONFIG, "" + appConfig);
        editor.commit();

    }


    public ModelConfiguration getAppConfig() {
        ModelConfiguration data = SingletonGson.getInstance()
                .fromJson("" + pref.getString(KEY_APP_CONFIG, ""), ModelConfiguration.class);
        return data;
    }

    public void saveTempBookingID(String bookingId) {
        editor.putString(IntentKeys.BOOKING_ID,bookingId).apply();
    }



    public String getTempBookingID() {
        return  pref.getString(IntentKeys.BOOKING_ID,"null");
    }


    public void clearTempBookingID(){
        editor.putString(IntentKeys.BOOKING_ID,"null").apply();

    }
}
