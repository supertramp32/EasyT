package com.seshra.everestcab.utils;


public class API_S_NEW {

    public interface Endpoints{
        String APP_CONFIGURATIONS = BaseConfig.BASE_URL+"api/user/configuration";
        String SET_STRING = BaseConfig.BASE_URL+"api/user/getString";
        String REGISTER = BaseConfig.BASE_URL+"api/user/signup";
        String SOCIAL_REGISTER = BaseConfig.BASE_URL+"api/user/socialsingup";
        String SOCIAL_LOGIN = BaseConfig.BASE_URL+"api/user/socialsignin";
        String LOGIN = BaseConfig.BASE_URL+"api/user/login";


        String CHECK_USER_PHONE = BaseConfig.BASE_URL+"api/v2/checkLoginId";

      //  String USER_DETAILS = BaseConfig.BASE_URL+"api/user/details";

        String USER_DETAILS = BaseConfig.BASE_URL+"api/user/UserDetail";

        String DEMO_LOGIN = BaseConfig.BASE_URL+"api/user/demoUser";
        String OTP = BaseConfig.BASE_URL +"api/user/otp";
        String REVERSE_GEOCODE = BaseConfig.BASE_URL+"api/user/location";
        String ADD_FAVOURITE_LOCATION = BaseConfig.BASE_URL+"api/user/favourite/location";
        String VIEW_FAVOURITE_LOCATION = BaseConfig.BASE_URL+"api/user/viewfavourite";
        String VIEW_CARS_AND_SERVICES = BaseConfig.BASE_URL+"api/user/taxi";
        String VIEW_CARS_AND_SERVICES_ESTIMATE = BaseConfig.BASE_URL+"api/user/cars";

        String EDIT_PROFILE = BaseConfig.BASE_URL+"api/user/edit-profile";


        String SEND_STRIPE_TOKEN = BaseConfig.BASE_URL+"api/user/save-card";
        String VIEW_PAYMENT_OPTIONS_API = BaseConfig.BASE_URL+"api/user/payment-option";
        String SET_PAYMENT_OPTIONS_API = BaseConfig.BASE_URL+"api/user/payment";
        String CHANGE_PASSWORD = BaseConfig.BASE_URL+"api/user/change-password";
        String FORGOT_PASSWORD = BaseConfig.BASE_URL+"api/user/forgotpassword";
//      String VIEW_CARS_AND_SERVICES = "http://atulpratise.getsandbox.com/carsmock";
        String RATE_CARD = BaseConfig.BASE_URL+"api/user/pricecard";
        String RATE_CARD_DELIVERY = BaseConfig.BASE_URL+"api/user/pricecard-delivery";
        String AREA = BaseConfig.BASE_URL+"api/user/areas";
        String GOOGLE_PLACE_PICKER = "https://maps.googleapis.com/maps/api/place/autocomplete/json";
        String GOOGLE_PLACE_MainAPICKER = "https://maps.googleapis.com/maps/api/place/autocomplete/json";
        String GOOGLE_PLACE_ID_RESPONSE = "https://maps.googleapis.com/maps/api/place/details/json?placeid=";
        String GOOGLE_STATIC_MAP_API = "https://maps.googleapis.com/maps/api/staticmap?";
        String DRIVERS = BaseConfig.BASE_URL+"api/user/driver";
        String CHECK_OUT = BaseConfig.BASE_URL+"api/user/checkout";
        String OUTSTATION_DETAILS_API = BaseConfig.BASE_URL+"api/user/outstation-details";
        String CHECK_DROP_AREA_API = BaseConfig.BASE_URL+"api/user/check-droplocation/area";
        String APPLY_COUPON = BaseConfig.BASE_URL+"api/user/checkout/apply-promo";
        String CONFIRM_BOOKING = BaseConfig.BASE_URL +"api/user/confirm";
        String ADD_PARAM_FUNC = BaseConfig.BASE_URL +"api/user/CheckSeats";
        String SELECT_PAYMENT = BaseConfig.BASE_URL+"api/user/payment";
        String MAKE_PAYMENT = BaseConfig.BASE_URL+"api/user/booking/make-payment";

        String RIDE_INFO = BaseConfig.BASE_URL+"api/user/booking/details";


        String TRACK_RIDE = BaseConfig.BASE_URL+"api/user/booking/tracking";



        String ACTIVE_RIDE = BaseConfig.BASE_URL+"api/user/booking/active";


        String TRIP_HISTORY_ACTIVE = BaseConfig.BASE_URL+"api/user/booking/history/active";
        String TRIP_HISTORY_PAST = BaseConfig.BASE_URL+"api/user/booking/history";
        String TRIP_SPECIFIC_DETAIL = BaseConfig.BASE_URL+"api/user/booking/history/detail";
        String CANCEL_RIDE = BaseConfig.BASE_URL+"api/user/booking/cancel";
        String AUTO_CANCEL = BaseConfig.BASE_URL+"api/user/booking/autocancel";

        String CHECK_STATUS = BaseConfig.BASE_URL+"api/checkBookingStatus";
        String POPULAR_LOCATIONS = BaseConfig.BASE_URL +"api/v2/user/popular/places";
        String CHECK_STOP_LOCATION = BaseConfig.BASE_URL+"api/user/check-droplocation/area";


        String VIEW_FAVOURITE_DRIVER = BaseConfig.BASE_URL+"api/user/favourite/drivers";
        String ADD_FAVOURITE_DRIVER = BaseConfig.BASE_URL+"api/user/addfavourite/driver";
        String DELETE_FAVOURITE_DRIVER = BaseConfig.BASE_URL+"api/user/favourite/delete";
        String CHANGE_ADDRESS = BaseConfig.BASE_URL+"api/user/booking/change_address";
//      String RECEIPT = "http://atulpratise.getsandbox.com/user_receipt";


        String RECEIPT = BaseConfig.BASE_URL+"api/user/receipt";


        String SOS_REQUEST = BaseConfig.BASE_URL+"api/user/sos/request";
//      String WALLET_BALANCE = "http://atulpratise.getsandbox.com/user_wallet";
        String WALLET_BALANCE = BaseConfig.BASE_URL+"api/user/wallet/transaction";
//      String VIEW_CARDS = "http://atulpratise.getsandbox.com/user_cards";
        String VIEW_CARDS = BaseConfig.BASE_URL+"api/user/cards";
        String CMS_PAGES = BaseConfig.BASE_URL+"api/user/cms/pages";
        String ADD_MONEY_IN_WALLET = BaseConfig.BASE_URL+"api/user/wallet/addMoney";
        String MAKE_PAYMENT_WITH_CARD = BaseConfig.BASE_URL+"api/user/card/make-payment";
        String DELETE_CARD = BaseConfig.BASE_URL+"api/user/card/delete";
        String RATE_DRIVER = BaseConfig.BASE_URL+"api/user/booking/rate/driver";

        String PROMOTIONAL_NOTIFICATION = BaseConfig.BASE_URL+"api/user/promotion/notification";
        String CAMPAIGN_NOTIFICATION = BaseConfig.BASE_URL+"api/user/campaign/notification";

        String VIEW_CHAT = BaseConfig.BASE_URL+"api/user/chat";
        String SEND_CHAT_MESSAGE = BaseConfig.BASE_URL+"api/user/chat/send_message";

        String LOGOUT = BaseConfig.BASE_URL+"api/user/logout";


        String CUSTOMER_SUPPORT = BaseConfig.BASE_URL+"api/user/customer_support";
//      String REFER_AND_EARN = "http://atulpratise.getsandbox.com/referandearn";
        String REFER_AND_EARN = BaseConfig.BASE_URL+"api/user/refer";
        String SEND_INVOVE = BaseConfig.BASE_URL+"api/user/booking/invoice";
        String CANCEL_REASION=BaseConfig.BASE_URL+"api/user/cancelReasons";
        String Paytm_checksum = "http://easytaxi.org/paytm/paytm.php";
        String Payment_change = BaseConfig.BASE_URL+"api/user/changePaymentOption";
        // ccAvenueApi
        String ccvenue=BaseConfig.BASE_URL+"api/ccavenue-config";
        String Senangpay_token = "http://127.0.0.1:8000/api/senangpay_token ";
        String Senangpay_payment = "http://127.0.0.1:8000/api/senangpay-pay";
        String TIP_AMOUNT = BaseConfig.BASE_URL+"api/user/AddTip";
        String FAV_DRIVER = BaseConfig.BASE_URL+"api/user/favourite/drivers";
        String USER_DOCUMENT = BaseConfig.BASE_URL+"api/user/userDocList";
        String UPLOAD_DOCUMENT =BaseConfig.BASE_URL+"api/user/userDocSave";
        String terms_and_conditions = BaseConfig.BASE_URL + "api/user/updateTerms";
        String MERCADO_PREFRENCE_ID = BaseConfig.BASE_URL + "api/user/creatPrefId";
        String BANCARD_URL = BaseConfig.BASE_URL +  "api/user/BancardCheckout";


         interface DELIVERY{
             String PACKAGE_LIST = BaseConfig.BASE_URL + "api/user/delivery/homescreen";
             String CARRIER_LIST = BaseConfig.BASE_URL + "api/user/delivery/vehicleType";
             String CHECK_OUT_API = BaseConfig.BASE_URL +"api/user/delivery/checkout";
             String DELVIERY_CONFIRM = BaseConfig.BASE_URL+"api/user/confirm/delivery";
        }

        String EMERGENCY_CONTACT_LIST = BaseConfig.BASE_URL + "api/user/sos";
        String DELETE_EMERGENCY_CONTACT = BaseConfig.BASE_URL + "api/user/sos/distory";
        String ADD_EMERGENCY_CONTACT = BaseConfig.BASE_URL + "api/user/sos/create";
        String ADD_CHILD = BaseConfig.BASE_URL + "api/user/AddFamilyMember";

        String CHILD_DETAIL = BaseConfig.BASE_URL+"api/user/ListFamilyMember";
        String DELETE_CHILD = BaseConfig.BASE_URL+"api/user/DeleteFamilyMember";
        String DELETE_FAVORITE_LOCATION = BaseConfig.BASE_URL + "api/user/delete/favourite/delete";


    }

    public interface Tags{
        String APP_CONFIGURATIONS = "APP_CONFIGURATIONS";
        String SET_STRING = "SET_STRING";
        String REGISTER = "REGISTER";
        String SOCIAL_REGISTER = "SOCIAL_REGISTER";
        String SOCIAL_LOGIN = "SOCIAL_LOGIN";
        String LOGIN = "LOGIN";
        String USER_DETAILS  = "USER_DETAILS";
        String DEMO_LOGIN  = "DEMO_LOGIN";
        String OTP = "OTP";
        String PAYMENT_CHANGE="payment_change";
        String REVERSE_GEOCODE = "REVERSE_GEOCODE";
        String ADD_FAVOURITE_LOCATION = "ADD_FAVOURITE_LOCATION";
        String CHEC = "ADD_FAVOURITE_LOCATION";
        String VIEW_FAVOURITE_LOCATION = "VIEW_FAVOURITE_LOCATION";
        String VIEW_CARS_AND_SERVICES = "VIEW_CARS_AND_SERVICES";
        String VIEW_CARS_AND_SERVICES_ESTIMATE = "VIEW_CARS_AND_SERVICES_ESTIMATE";
        String RATE_CARD = "RATE_CARD";
        String RATE_CARD_DELIVERY = "RATE_CARD_DELIVERY";
        String AREA = "AREA";
        String GOOGLE_PLACE_PICKER = "GOOGLE_PLACE_PICKER";
        String GOOGLE_PLACE_ID_RESPONSE = "GOOGLE_PLACE_ID_RESPONSE";
        String DRIVERS = "DRIVERS";
        String CHECK_OUT_RIDE_NOW = "CHECK_OUT_RIDE_NOW";
        String OUTSTAION_RIDE_DETAILS = "OUTSTAION_RIDE_DETAILS";
        String CHECK_DROP_AREA = "CHECK_DROP_AREA";
        String APPLY_COUPON = "APPLY_COUPON";
        String CHECK_OUT_RIDE_LATER = "CHECK_OUT_RIDE_LATER";
        String CONFIRM_BOOKING = "CONFIRM_BOOKING";
        String ADD_PARAM_FUNC ="ADD_PARAM_FUNC";
        String SELECT_PAYMENT = "SELECT_PAYMENT";
        String MAKE_PAYMENT = "MAKE_PAYMENT";
        String RIDE_INFO = "RIDE_INFO";
        String TRACK_RIDE = "TRACK_RIDE";
        String ACTIVE_RIDE = "ACTIVE_RIDE";
        String TRIP_HISTORY_ACTIVE = "TRIP_HISTORY_ACTIVE";
        String TRIP_HISTORY_PAST = "TRIP_HISTORY_PAST";
        String TRIP_SPECIFIC_DETAIL = "TRIP_SPECIFIC_DETAIL";
        String CANCEL_RIDE = "CANCEL_RIDE";
        String VIEW_FAVOURITE_DRIVER = "VIEW_FAVOURITE_DRIVER";
        String CHECKOUT_FAV_DRIVER = "Checkout_fav_driver";
        String ADD_FAVOURITE_DRIVER = "ADD_FAVOURITE_DRIVER";
        String DELETE_FAVOURITE_DRIVER = "DELETE_FAVOURITE_DRIVER";
        String RECEIPT = "RECEIPT";
        String RATE_DRIVER = "RATE_DRIVER";
        String PROMOTIONAL_NOTIFICATION = "PROMOTIONAL_NOTIFICATION";
        String SOS_REQUEST = "SOS_REQUEST";
        String CHANGE_ADDRESS = "CHANGE_ADDRESS";
        String WALLET_BALANCE = "WALLET_BALANCE";
        String VIEW_CARDS = "VIEW_CARDS";
        String DELETE_CARD = "DELETE_CARD";
        String CMS_PAGES = "CMS_PAGES";
        String ADD_MONEY_IN_WALLET = "ADD_MONEY_IN_WALLET";
        String MAKE_PAYMENT_WITH_CARD = "MAKE_PAYMENT_WITH_CARD";
        String VIEW_CHAT = "VIEW_CHAT";
        String SEND_CHAT_MESSAGE = "SEND_CHAT_MESSAGE";
        String AUTO_CANCEL = "AUTO_CANCEL";
        String CHECK_STATUS = "CHECK_STATUS";
        String LOAD_MORE_TRANSACTION = "LOAD_MORE_TRANSACTION";
        String EDIT_PROFILE = "EDIT_PROFILE";
        String SEND_STRIPE_TOKEN = "SEND_STRIPE_TOKEN";
        String VIEW_PAYMENT_OPTIONS = "VIEW_PAYMENT_OPTIONS";
        String SET_PAYMENT_OPTION = "SET_PAYMENT_OPTION";
        String LOGOUT = "LOGOUT";
        String REFER_AND_EARN = "REFER_AND_EARN";
        String CHANGE_PASSWORD = "CHANGE_PASSWORD";
        String FORGOT_PASSWORD = "FORGOT_PASSWORD";
        String SEND_INVOVE = "SEND_INVOVE";
        String CUSTOMER_SUPPORT = "CUSTOMER_SUPPORT";
        String CANCEL_REASION="CANCEL_REASION";
        String CCVENUE="CCVENUE";
        String PAYTM_CHECKSUM = "Paytm";
        String SENANGPAY_TOKEN = "SENANGPAY_TOKEN";
        String SENANGPAY_PAYMENT = "SENANGPAY_PAYMENT";
        String TIP_AMOUNT = "tip_amount";
        String DOCUMENT = "DOCUMENT";
        String UPLOAD_DOCUMENT = "UPLOAD_DOCUMENT";
        String FACEBOOK = "facebook";
        String GOOGLE = "Google";
        String TERMS_AND_CONDITIONS = "terms_and_conditions";

        String EMERGENCY_CONTACT_LIST = "EMERGENCY_CONTACT_LIST";
        String DELETE_EMERGENCY_CONTACT = "DELETE_EMERGENCY_CONTACT";
        String ADD_EMERGENCY_CONTACT = "ADD_EMERGENCY_CONTACT";
        String ADD_CHILD = "ADD_CHILD";

        String CHILD_DETAIL = "CHILD_DETAILS";
        String DELETE_CHILD = "DELETE CHILD";
        String MERCADO_PREFRENCE_ID = "MERCADO_PREFRENCE_ID";
        String BANCARD_URL = "BANCARD_URL";

    
            //Delivery_Api_Tags
        String PACKAGE_LIST_API = "PACKAGE_LIST_API";
        String CARIER_LIST_API = "CARIER_LIST_API";
        String DELIVERY_CHECK_OUT_API ="DELIVERY_CHECK_OUT_API" ;
        String DELETE_FAVORITE_LOCATION = "DELETE_FAVORITE_LOCATION";


        String CHECK_USER_PHONE = "CHECK_USER_PHONE";

    
    }
}
