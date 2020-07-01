package com.seshra.everestcab.utils;


public class API_S {

    public interface Endpoints{



        String CHECK_USER_PHONE = BaseConfig.BASE_URL+"api/v2/agent/checkMobileNumber";
        String LOGIN = BaseConfig.BASE_URL+"api/v2/agent/service/login";
        String GOOGLE_PLACE_PICKER = "https://maps.googleapis.com/maps/api/place/autocomplete/json";
        String GOOGLE_PLACE_ID_RESPONSE = "https://maps.googleapis.com/maps/api/place/details/json?placeid=";

        String CHECK_OUT_AGENT = BaseConfig.BASE_URL + "api/v2/agent/service/checkout";

        String CONFIRM_BOOKING_AGENT = BaseConfig.BASE_URL + "api/v2/agent/service/confirm";


        String CHECK_STATUS = BaseConfig.BASE_URL+"api/checkBookingStatus";

        String RIDE_INFO = BaseConfig.BASE_URL+"api/user/booking/details";


        String AGENT_TRIP_HISTORY_ACTIVE = BaseConfig.BASE_URL+"api/v2/agent/service/booking/history/active";





////        String APP_CONFIGURATIONS = BuildConfig.BASE_URL+"api/user/configuration";
//        String APP_CONFIGURATIONS = BuildConfig.BASE_URL+"api/v2/agent/service/configuration";
//        String SET_STRING = BuildConfig.BASE_URL+"api/user/getString";
//
////        String REGISTER = BuildConfig.BASE_URL+"api/user/signup";
//
//        String REGISTER = BuildConfig.BASE_URL+"api/v2/agent/service/register";
//
//
//        String SOCIAL_REGISTER = BuildConfig.BASE_URL+"api/user/socialsingup";
//        String SOCIAL_LOGIN = BuildConfig.BASE_URL+"api/user/socialsignin";
////        String LOGIN = BuildConfig.BASE_URL+"api/user/login";
//
//
//
//
////        String CHECK_USER_PHONE = BuildConfig.BASE_URL+"api/v2/checkLoginId";



//        String GET_USER_DETAILS = BuildConfig.BASE_URL+"api/v2/agent/service/userDetails";
//
//        String CHECK_OUT_AGENT = BuildConfig.BASE_URL + "api/v2/agent/service/checkout";
//
//
//        //  String USER_DETAILS = BuildConfig.BASE_URL+"api/user/details";
//
//        String USER_DETAILS = BuildConfig.BASE_URL+"api/user/UserDetail";
//
//
//        String DEMO_LOGIN = BuildConfig.BASE_URL+"api/user/demoUser";
//        String OTP = BuildConfig.BASE_URL +"api/user/otp";
//        String REVERSE_GEOCODE = BuildConfig.BASE_URL+"api/user/location";
//        String ADD_FAVOURITE_LOCATION = BuildConfig.BASE_URL+"api/user/favourite/location";
//        String VIEW_FAVOURITE_LOCATION = BuildConfig.BASE_URL+"api/user/viewfavourite";
//        String VIEW_CARS_AND_SERVICES = BuildConfig.BASE_URL+"api/v2/agent/service/cars";
//
//
//        String VIEW_CARS_AND_SERVICES_ESTIMATE = BuildConfig.BASE_URL+"api/user/cars";
//        String EDIT_PROFILE = BuildConfig.BASE_URL+"api/user/edit-pofile";
//        String SEND_STRIPE_TOKEN = BuildConfig.BASE_URL+"api/user/save-card";
//        String VIEW_PAYMENT_OPTIONS_API = BuildConfig.BASE_URL+"api/user/payment-option";
//        String SET_PAYMENT_OPTIONS_API = BuildConfig.BASE_URL+"api/user/payment";
//        String CHANGE_PASSWORD = BuildConfig.BASE_URL+"api/user/change-password";
//        String FORGOT_PASSWORD = BuildConfig.BASE_URL+"api/user/forgotpassword";
////      String VIEW_CARS_AND_SERVICES = "http://atulpratise.getsandbox.com/carsmock";
//        String RATE_CARD = BuildConfig.BASE_URL+"api/user/pricecard";
//        String RATE_CARD_DELIVERY = BuildConfig.BASE_URL+"api/user/pricecard-delivery";
//        String AREA = BuildConfig.BASE_URL+"api/user/areas";
//        String GOOGLE_PLACE_PICKER = "https://maps.googleapis.com/maps/api/place/autocomplete/json";
//
//        String GOOGLE_PLACE_MainAPICKER = "https://maps.googleapis.com/maps/api/place/autocomplete/json";
//        String GOOGLE_PLACE_ID_RESPONSE = "https://maps.googleapis.com/maps/api/place/details/json?placeid=";
//        String GOOGLE_STATIC_MAP_API = "https://maps.googleapis.com/maps/api/staticmap?";
//        String DRIVERS = BuildConfig.BASE_URL+"api/user/driver";
//        String CHECK_OUT = BuildConfig.BASE_URL+"api/user/checkout";
//        String OUTSTATION_DETAILS_API = BuildConfig.BASE_URL+"api/user/outstation-details";
//        String CHECK_DROP_AREA_API = BuildConfig.BASE_URL+"api/user/check-droplocation/area";
//        String APPLY_COUPON = BuildConfig.BASE_URL+"api/user/checkout/apply-promo";
//        String CONFIRM_BOOKING = BuildConfig.BASE_URL +"api/user/confirm";
//
//        String CONFIRM_BOOKING_AGENT = BuildConfig.BASE_URL + "api/v2/agent/service/confirm";
//
//
//        String ADD_PARAM_FUNC = BuildConfig.BASE_URL +"api/user/CheckSeats";
//        String SELECT_PAYMENT = BuildConfig.BASE_URL+"api/user/payment";
//        String MAKE_PAYMENT = BuildConfig.BASE_URL+"api/user/booking/make-payment";
//        String RIDE_INFO = BuildConfig.BASE_URL+"api/user/booking/details";
//        String TRACK_RIDE = BuildConfig.BASE_URL+"api/user/booking/tracking";
//        String ACTIVE_RIDE = BuildConfig.BASE_URL+"api/user/booking/active";
//
//        String TRIP_HISTORY_ACTIVE = BuildConfig.BASE_URL+"api/user/booking/history/active";
//
//        String AGENT_TRIP_HISTORY_PAST = BuildConfig.BASE_URL+"api/v2/agent/service/booking/history";
//
//        String AGENT_TRIP_HISTORY_ACTIVE = BuildConfig.BASE_URL+"api/v2/agent/service/booking/history/active";
//
//
//
//        String TRIP_HISTORY_PAST = BuildConfig.BASE_URL+"api/user/booking/history";
//
//        String TRIP_SPECIFIC_DETAIL = BuildConfig.BASE_URL+"api/user/booking/history/detail";
//        String CANCEL_RIDE = BuildConfig.BASE_URL+"api/user/booking/cancel";
//        String AUTO_CANCEL = BuildConfig.BASE_URL+"api/user/booking/autocancel";
//
//        String CHECK_STATUS = BuildConfig.BASE_URL+"api/checkBookingStatus";
//
//        String VIEW_FAVOURITE_DRIVER = BuildConfig.BASE_URL+"api/user/favourite/drivers";
//        String ADD_FAVOURITE_DRIVER = BuildConfig.BASE_URL+"api/user/addfavourite/driver";
//        String DELETE_FAVOURITE_DRIVER = BuildConfig.BASE_URL+"api/user/favourite/delete";
//        String CHANGE_ADDRESS = BuildConfig.BASE_URL+"api/user/booking/change_address";
////      String RECEIPT = "http://atulpratise.getsandbox.com/user_receipt";
//        String RECEIPT = BuildConfig.BASE_URL+"api/user/receipt";
//        String SOS_REQUEST = BuildConfig.BASE_URL+"api/user/sos/request";
////      String WALLET_BALANCE = "http://atulpratise.getsandbox.com/user_wallet";
//        String WALLET_BALANCE = BuildConfig.BASE_URL+"api/user/wallet/transaction";
////      String VIEW_CARDS = "http://atulpratise.getsandbox.com/user_cards";
//        String VIEW_CARDS = BuildConfig.BASE_URL+"api/user/cards";
//
//        String CMS_PAGES = BuildConfig.BASE_URL+"api/user/cms/pages";
//
//        String ADD_MONEY_IN_WALLET = BuildConfig.BASE_URL+"api/user/wallet/addMoney";
//        String MAKE_PAYMENT_WITH_CARD = BuildConfig.BASE_URL+"api/user/card/make-payment";
//        String DELETE_CARD = BuildConfig.BASE_URL+"api/user/card/delete";
//        String RATE_DRIVER = BuildConfig.BASE_URL+"api/user/booking/rate/driver";
//
//        String PROMOTIONAL_NOTIFICATION = BuildConfig.BASE_URL+"api/user/promotion/notification";
//
//        String VIEW_CHAT = BuildConfig.BASE_URL+"api/user/chat";
//        String SEND_CHAT_MESSAGE = BuildConfig.BASE_URL+"api/user/chat/send_message";
//        String LOGOUT = BuildConfig.BASE_URL+"api/user/logout";
//        String CUSTOMER_SUPPORT = BuildConfig.BASE_URL+"api/user/customer_support";
////      String REFER_AND_EARN = "http://atulpratise.getsandbox.com/referandearn";
//        String REFER_AND_EARN = BuildConfig.BASE_URL+"api/user/refer";
//        String SEND_INVOVE = BuildConfig.BASE_URL+"api/user/booking/invoice";
//        String CANCEL_REASION=BuildConfig.BASE_URL+"api/user/cancelReasons";
//        String Paytm_checksum = "http://easytaxi.org/paytm/paytm.php";
//        String Payment_change = BuildConfig.BASE_URL+"api/user/changePaymentOption";
//        // ccAvenueApi
//        String ccvenue=BuildConfig.BASE_URL+"api/ccavenue-config";
//        String Senangpay_token = "http://127.0.0.1:8000/api/senangpay_token ";
//        String Senangpay_payment = "http://127.0.0.1:8000/api/senangpay-pay";
//        String TIP_AMOUNT = BuildConfig.BASE_URL+"api/user/AddTip";
//        String FAV_DRIVER = BuildConfig.BASE_URL+"api/user/favourite/drivers";
//        String USER_DOCUMENT = BuildConfig.BASE_URL+"api/user/userDocList";
//        String UPLOAD_DOCUMENT =BuildConfig.BASE_URL+"api/user/userDocSave";
//        String terms_and_conditions = BuildConfig.BASE_URL + "api/user/updateTerms";
//        String MERCADO_PREFRENCE_ID = BuildConfig.BASE_URL + "api/user/creatPrefId";
//        String BANCARD_URL = BuildConfig.BASE_URL +  "api/user/BancardCheckout";





//         interface DELIVERY{
//             String PACKAGE_LIST = BuildConfig.BASE_URL + "api/user/delivery/homescreen";
//             String CARRIER_LIST = BuildConfig.BASE_URL + "api/user/delivery/vehicleType";
//             String CHECK_OUT_API = BuildConfig.BASE_URL +"api/user/delivery/checkout";
//             String DELVIERY_CONFIRM = BuildConfig.BASE_URL+"api/user/confirm/delivery";
//        }
//
//        String EMERGENCY_CONTACT_LIST = BuildConfig.BASE_URL + "api/user/sos";
//        String DELETE_EMERGENCY_CONTACT = BuildConfig.BASE_URL + "api/user/sos/distory";
//        String ADD_EMERGENCY_CONTACT = BuildConfig.BASE_URL + "api/user/sos/create";
//        String ADD_CHILD = BuildConfig.BASE_URL + "api/user/AddFamilyMember";
//
//        String CHILD_DETAIL = BuildConfig.BASE_URL+"api/user/ListFamilyMember";
//        String DELETE_CHILD = BuildConfig.BASE_URL+"api/user/DeleteFamilyMember";
//        String DELETE_FAVORITE_LOCATION = BuildConfig.BASE_URL + "api/user/delete/favourite/delete";


    }

    public interface Tags{

        String CHECK_USER_PHONE = "CHECK_USER_PHONE";
        String GOOGLE_PLACE_PICKER = "GOOGLE_PLACE_PICKER";

        String CHECK_OUT_AGENT = "CHECK_OUT";



//        String APP_CONFIGURATIONS = "APP_CONFIGURATIONS";
//        String SET_STRING = "SET_STRING";
//        String REGISTER = "REGISTER";
//        String SOCIAL_REGISTER = "SOCIAL_REGISTER";
//        String SOCIAL_LOGIN = "SOCIAL_LOGIN";
//        String LOGIN = "LOGIN";
//        String USER_DETAILS  = "USER_DETAILS";
//        String DEMO_LOGIN  = "DEMO_LOGIN";
//        String OTP = "OTP";
//        String PAYMENT_CHANGE="payment_change";
//        String REVERSE_GEOCODE = "REVERSE_GEOCODE";
//        String ADD_FAVOURITE_LOCATION = "ADD_FAVOURITE_LOCATION";
//        String CHEC = "ADD_FAVOURITE_LOCATION";
//        String VIEW_FAVOURITE_LOCATION = "VIEW_FAVOURITE_LOCATION";
//        String VIEW_CARS_AND_SERVICES = "VIEW_CARS_AND_SERVICES";
//        String VIEW_CARS_AND_SERVICES_ESTIMATE = "VIEW_CARS_AND_SERVICES_ESTIMATE";
//        String RATE_CARD = "RATE_CARD";
//        String RATE_CARD_DELIVERY = "RATE_CARD_DELIVERY";
//        String AREA = "AREA";
//        String GOOGLE_PLACE_PICKER = "GOOGLE_PLACE_PICKER";
//        String GOOGLE_PLACE_ID_RESPONSE = "GOOGLE_PLACE_ID_RESPONSE";
//        String DRIVERS = "DRIVERS";
//        String CHECK_OUT_RIDE_NOW = "CHECK_OUT_RIDE_NOW";
//
//        String CHECK_OUT_AGENT = "CHECK_OUT";
//
//        String OUTSTAION_RIDE_DETAILS = "OUTSTAION_RIDE_DETAILS";
//        String CHECK_DROP_AREA = "CHECK_DROP_AREA";
//        String APPLY_COUPON = "APPLY_COUPON";
//        String CHECK_OUT_RIDE_LATER = "CHECK_OUT_RIDE_LATER";
//        String CONFIRM_BOOKING = "CONFIRM_BOOKING";
//
//
//        String CONFIRM_BOOKING_AGENT = "CONFIRM_BOOKING_AGENT";
//
//
//        String ADD_PARAM_FUNC ="ADD_PARAM_FUNC";
//        String SELECT_PAYMENT = "SELECT_PAYMENT";
//        String MAKE_PAYMENT = "MAKE_PAYMENT";
//        String RIDE_INFO = "RIDE_INFO";
//        String TRACK_RIDE = "TRACK_RIDE";
//        String ACTIVE_RIDE = "ACTIVE_RIDE";
//        String TRIP_HISTORY_ACTIVE = "TRIP_HISTORY_ACTIVE";
//
//        String AGENT_TRIP_HISTORY_PAST = "AGENT_TRIP_HISTORY_PAST";
//        String AGENT_TRIP_HISTORY_ACTIVE = "AGENT_TRIP_HISTORY_ACTIVE";
//
//
//        String TRIP_HISTORY_PAST = "TRIP_HISTORY_PAST";
//        String TRIP_SPECIFIC_DETAIL = "TRIP_SPECIFIC_DETAIL";
//        String CANCEL_RIDE = "CANCEL_RIDE";
//        String VIEW_FAVOURITE_DRIVER = "VIEW_FAVOURITE_DRIVER";
//        String CHECKOUT_FAV_DRIVER = "Checkout_fav_driver";
//        String ADD_FAVOURITE_DRIVER = "ADD_FAVOURITE_DRIVER";
//        String DELETE_FAVOURITE_DRIVER = "DELETE_FAVOURITE_DRIVER";
//        String RECEIPT = "RECEIPT";
//        String RATE_DRIVER = "RATE_DRIVER";
//        String PROMOTIONAL_NOTIFICATION = "PROMOTIONAL_NOTIFICATION";
//        String SOS_REQUEST = "SOS_REQUEST";
//        String CHANGE_ADDRESS = "CHANGE_ADDRESS";
//        String WALLET_BALANCE = "WALLET_BALANCE";
//        String VIEW_CARDS = "VIEW_CARDS";
//        String DELETE_CARD = "DELETE_CARD";
//        String CMS_PAGES = "CMS_PAGES";
//        String ADD_MONEY_IN_WALLET = "ADD_MONEY_IN_WALLET";
//        String MAKE_PAYMENT_WITH_CARD = "MAKE_PAYMENT_WITH_CARD";
//        String VIEW_CHAT = "VIEW_CHAT";
//        String SEND_CHAT_MESSAGE = "SEND_CHAT_MESSAGE";
//        String AUTO_CANCEL = "AUTO_CANCEL";
//        String CHECK_STATUS = "CHECK_STATUS";
//        String LOAD_MORE_TRANSACTION = "LOAD_MORE_TRANSACTION";
//        String EDIT_PROFILE = "EDIT_PROFILE";
//        String SEND_STRIPE_TOKEN = "SEND_STRIPE_TOKEN";
//        String VIEW_PAYMENT_OPTIONS = "VIEW_PAYMENT_OPTIONS";
//        String SET_PAYMENT_OPTION = "SET_PAYMENT_OPTION";
//        String LOGOUT = "LOGOUT";
//        String REFER_AND_EARN = "REFER_AND_EARN";
//        String CHANGE_PASSWORD = "CHANGE_PASSWORD";
//        String FORGOT_PASSWORD = "FORGOT_PASSWORD";
//        String SEND_INVOVE = "SEND_INVOVE";
//        String CUSTOMER_SUPPORT = "CUSTOMER_SUPPORT";
//        String CANCEL_REASION="CANCEL_REASION";
//        String CCVENUE="CCVENUE";
//        String PAYTM_CHECKSUM = "Paytm";
//        String SENANGPAY_TOKEN = "SENANGPAY_TOKEN";
//        String SENANGPAY_PAYMENT = "SENANGPAY_PAYMENT";
//        String TIP_AMOUNT = "tip_amount";
//        String DOCUMENT = "DOCUMENT";
//        String UPLOAD_DOCUMENT = "UPLOAD_DOCUMENT";
//        String FACEBOOK = "facebook";
//        String GOOGLE = "Google";
//        String TERMS_AND_CONDITIONS = "terms_and_conditions";
//
//        String EMERGENCY_CONTACT_LIST = "EMERGENCY_CONTACT_LIST";
//        String DELETE_EMERGENCY_CONTACT = "DELETE_EMERGENCY_CONTACT";
//        String ADD_EMERGENCY_CONTACT = "ADD_EMERGENCY_CONTACT";
//        String ADD_CHILD = "ADD_CHILD";
//
//        String CHILD_DETAIL = "CHILD_DETAILS";
//        String DELETE_CHILD = "DELETE CHILD";
//        String MERCADO_PREFRENCE_ID = "MERCADO_PREFRENCE_ID";
//        String BANCARD_URL = "BANCARD_URL";
//
//
//            //Delivery_Api_Tags
//        String PACKAGE_LIST_API = "PACKAGE_LIST_API";
//        String CARIER_LIST_API = "CARIER_LIST_API";
//        String DELIVERY_CHECK_OUT_API ="DELIVERY_CHECK_OUT_API" ;
//        String DELETE_FAVORITE_LOCATION = "DELETE_FAVORITE_LOCATION";
//
//
//        String GET_USER_DETAILS = "GET_USER_DETAILS";



    }
}
