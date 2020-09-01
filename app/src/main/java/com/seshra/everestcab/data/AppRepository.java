package com.seshra.everestcab.data;



import android.content.Context;


import com.seshra.everestcab.service.AddFavouriteLocation;
import com.seshra.everestcab.service.ApplyPromoCodeServcie;
import com.seshra.everestcab.service.AutoCancelService;
import com.seshra.everestcab.service.CallRideInfoService;
import com.seshra.everestcab.service.CancelRideService;
import com.seshra.everestcab.service.CheckOutService;
import com.seshra.everestcab.service.CheckServiceAvailabilityService;
import com.seshra.everestcab.service.CheckStatusService;
import com.seshra.everestcab.service.CheckStopLocationService;
import com.seshra.everestcab.service.CheckUserPhoneService;
import com.seshra.everestcab.service.ConfirmRideService;
import com.seshra.everestcab.service.EditProfileService;
import com.seshra.everestcab.service.FetchActiveRideService;
import com.seshra.everestcab.service.FetchCampaignNotification;
import com.seshra.everestcab.service.FetchCancelReasons;
import com.seshra.everestcab.service.FetchConfigurationService;
import com.seshra.everestcab.service.FetchFavouriteLocationService;
import com.seshra.everestcab.service.FetchGeneralNotificationsService;
import com.seshra.everestcab.service.FetchPastRideService;
import com.seshra.everestcab.service.FetchPopularLocations;
import com.seshra.everestcab.service.FetchRecieptService;
import com.seshra.everestcab.service.FetchSpecificTripDetailService;
import com.seshra.everestcab.service.ForgotPasswordService;
import com.seshra.everestcab.service.GetCurrentLocationName;
import com.seshra.everestcab.service.GetOTPService;
import com.seshra.everestcab.service.GooglePlacePickerService;
import com.seshra.everestcab.service.LogOutService;
import com.seshra.everestcab.service.LoginService;
import com.seshra.everestcab.service.RateDriverService;
import com.seshra.everestcab.service.RegisterUserService;
import com.seshra.everestcab.service.TrackRideService;
import com.seshra.everestcab.utils.SessionManager;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class AppRepository {

    private static  AppRepository ourInstance;

    Context context;

    SessionManager sessionManager;

    private Executor executor = Executors.newSingleThreadExecutor();

    public static AppRepository getOurInstance(Context context){

        if(ourInstance==null)
            ourInstance = new AppRepository(context);

        return ourInstance;
    }


    private AppRepository(Context context) {
        this.context = context;
        sessionManager = new SessionManager(context);

    }


    public void checkUserPhone(String phone) {

        CheckUserPhoneService.startActionCheckPhone(context, phone);

    }

    public void checkForLogin(String phone, String password) {

        LoginService.startActionLogin(context,phone,password);

    }

    public void getGooglePlaces(String url, String searchLocation) {

        GooglePlacePickerService.startActionSearch(context, url,searchLocation);
    }

    public void rideNow( String pickObj, String dropobj, String stopObj) {
        CheckOutService.startActionRideNow(context, pickObj, dropobj, stopObj);
    }

    public void getCurrentLocationName(Double lattitude, Double longitude) {
        GetCurrentLocationName.startActionCurrentPlce(context,lattitude,longitude);
    }

    public void confirmBooking( int checkOutId, String rideType) {

        ConfirmRideService.startActionConfirmRide(context, checkOutId,rideType);

    }

    public void checkBookingStatus(String b_id) {
        CheckStatusService.startActionBookingStatus(context,b_id);
    }

    public void callRideInfoApi(String bookingId) {

        CallRideInfoService.startActionCallRideInfo(context,bookingId);

    }

    public void getActiveHistoryRides() {
        FetchActiveRideService.startActionTripHistoryActive(context);
    }

    public void fetchRemoteConfig() {
        FetchConfigurationService.startActionFetchConfig(context);
    }

    public void registerUser(String phone, String firstName, String lastName, String password) {

        RegisterUserService.startActionRegister(context,firstName,lastName,phone,password);
    }

    public void getPastRides(String pageNo) {
        FetchPastRideService.startActionFetchPastRide(context,pageNo);
    }

    public void getSpecificTripDetailApi(String bookingId) {
        FetchSpecificTripDetailService.startActionSpecificTripDetails(context,bookingId);
    }

    public void recoverPassword(String phone, String password) {
        ForgotPasswordService.startActionForgotPassword(context, phone, password);
    }

    public void logOutUser() {


        LogOutService.startActionLogOut(context);
    }

    public void getGeneralNotifications() {

        FetchGeneralNotificationsService.startActionGeneralNotifications(context);

    }

    public void editProfile(String fullName, String email, String gender,String image,int type) {
        if(image.equals(""))
            EditProfileService.startActionEditProfile(context,fullName,email,gender);
        else
            EditProfileService.startActionEditProfileWithImage(context,fullName,email,gender,image,type);
    }

    public void saveLocation(double lat, double lon, String locationName, String favName) {
        AddFavouriteLocation.startActionSaveLocation(context,
                Double.toString(lat),
                Double.toString(lon),
                locationName,favName);
    }

    public void fetchFavouritesLocations() {
        FetchFavouriteLocationService.startActionFoo(context);
    }

    public void startTracking(int bookingId) {
        TrackRideService.startActionTrackRide(context,""+bookingId);
    }

    public void getCampaignNotification() {
        FetchCampaignNotification.startActionCampaignNotifications(context);
    }

    public void checkServiceAvailability(double latitude, double longitude) {
        CheckServiceAvailabilityService.startActionCheckService(context,
                Double.toString(latitude),
                Double.toString(longitude));
    }

    public void checkAutoCancel(String id) {

        AutoCancelService.startActionAutoCancel(context,id);

    }

    public void fetchCancelReasons(String bookingId) {
        FetchCancelReasons.startActionFoo(context,bookingId);
    }

    public void cancelRide(int bookingId, int cancelId, String cancel_charges) {
        CancelRideService.startCanceRideService(context,""+bookingId,cancelId,cancel_charges);
    }

    public void rateDriver(String bookingId, float rating, String comments) {

        RateDriverService.startActionRateDriver(context,bookingId,Float.toString(rating),comments);
    }

    public void applyPromoCode(String bookingId, String promoCode) {

        ApplyPromoCodeServcie.startActionApplyPromoCode(context,bookingId,promoCode);

    }

    public void getOtp(String phone, String type) {

        GetOTPService.startActionGetOtp(context,phone,type);
    }

    public void fetchPopularLocations(String LATITUDE, String LONGITUDE) {
        FetchPopularLocations.startActionFetchPopularLocations(context,LATITUDE,LONGITUDE);
    }

    public void getReciept(String bookingId) {
        FetchRecieptService.startActionFoo(context,bookingId);
    }

    public void checkStopLocation(String confirmLat, String confirmLong) {
        CheckStopLocationService.startActionCheckStopLocation(context,confirmLat,confirmLong);
    }
}

