package com.seshra.everestcab.utils;

public interface IntentKeys {


    String FIRST_NAME = "first_name";
    String LAST_NAME = "last_name";

    String TYPE = "type";

    String VERIFIER_TYPE = "VERIFIER_TYPE";
    String VERIFIER_MODE = "VERIFIER_MODE";
    String LATITUDE = "LATITUDE";
    String LONGITUDE = "LONGITUDE";
    String SCRIPT = "SCRIPT";
    String BOOKING_ID = "BOOKING_ID";
    String EMAIL="EMAIL";
    String ADDRESS_NAME= "ADDRESS_NAME";
    String ADDRESS_ArrayList= "ADDRESS_ArrayList";
    String Lat_lng_ArryList= "ADDRESS_Lat_lng";
    String SOS_ID = "SOS_ID";
    String SOS_NAMES = "SOS_NAMES";
    String SOS_NUMBERS = "SOS_NUMBERS";
    String TOP_UP_AMOUNT = "TOP_UP_AMOUNT";
    String DRIVER_IMAGE = "DRIVER_IMAGE";
    String DRIVER_NAME = "DRIVER_NAME";
    String STATUS = "STATUS";
    String SELECTED_AREA_ID="SELECTED_AREA_ID";
    String SELECTED_SERVICE_ID="SELECTED_SERVICE_ID";
    String CHECKOUT_ID = "CHECKOUT_ID";
    String PAYMENT_OPTION_ID = "PAYMENT_OPTION_ID";
    String CREDIT_CARD_ID = "CREDIT_CARD_ID";
    String COUPON_RESPONSE = "COUPON_RESPONSE";
    String SOCIAL_ID = "SOCIAL_ID";
    String SOCIAL_NAME = "SOCIAL_NAME";
    String SOCIAL_PHOTO = "SOCIAL_PHOTO";
    String SOCIAL_MAIL = "SOCIAL_MAIL";
    String PHONE = "PHONE";
    String TOTAL_DESTINATIONS = "total_destionation";

    String PICK_LATITUDE = "PICK_LATITUDE";
    String PICK_LONGITUDE = "PICK_LONGITUDE";
    String PICK_LOCATION = "PICK_LOCATION";
    String DROP_LONGITUDE = "DROP_LONGITUDE";
    String DROP_LATITUDE = "DROP_LATITUDE";
    String DROP_LOCATION = "DROP_LOCATION";

    String PUBLIC_KEY ="publicKey";
    String SECRET_KEY="secretKey";
    String DOCUMENT_ID = "DOCUMENT_ID";
    String EXPIRY_DATE = "EXPIRY_DATE";
    String Change_payment="chnage_payment";
    String TERMS_CONDITION = "termscondition";



    String PACKAGE_LIST = "PACKAGE_LIST";
    String SELECTED_PACKAGES = "SELECTED_PACKAGES";
    String CHECK_OUT_RESPONSE = "CHECK_OUT_RESPONSE";


    String SEGMENT_TYPE = "SEGMENT_TYPE";


    String OUTSTATION_BOOKING_TYPE = "OUTSTATION_BOOKING_TYPE";


    //Navigation Drawer slugs

    String TRIP_HISTORY = "trip-history";
    String ABOUT_US = "about-us";
    String CONTACT_US = "contact-us";
    String LOGOUT = "logout";
    String PROMOTION = "promotion-views";
    String REFER_EARN = "refer-and-earn";
    String TERMS_AND_CONDITION = "terms-and-condition";
    String CARD_LIST = "card-list";
    String CHILD_LIST = "child-list";
    String EMERGENCY = "emergency-contacts";
    String WALLET_ACTIVITY = "wallet-activity";
    String PRICE_CARD = "price-card";
    String FAV = "favourite-driver";
    String PRIVACY_POLICY = "privacy-policy";
    String LANGUAGE = "language";
    String  CUSTOMER_SUPPORT= "customer-support";



    // emit events for Sockets
    String USER_CONNECTION ="driverConnection";
    String SAVE_DRIVER_LAT_LONG = "saveDriverLatLong";


    // listen events sockets
    String USER_CONNECTED= "DriverConnected";
    String DRIVER_LOCATION_SAVED= "driverLatLongSaved";
    String USER_TRACKING_FETCH= "15";

    String PROMO_CODE = "promo_code";
    String FIRST_TIME = "first_time_app";
    String BOOKING_CONFIRM_MODEL = "confirm_booking_model";
}

