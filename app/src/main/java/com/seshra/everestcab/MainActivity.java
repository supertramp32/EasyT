package com.seshra.everestcab;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.seshra.everestcab.locations.SearchLocationActivity;
import com.seshra.everestcab.models.ModelActiveRide;
import com.seshra.everestcab.models.ModelApplyPromoCode;
import com.seshra.everestcab.models.ModelCheckOut;
import com.seshra.everestcab.models.ModelConfirm;
import com.seshra.everestcab.models.ResultCheckHomeServices;
import com.seshra.everestcab.notifications.NotificationsActivity;
import com.seshra.everestcab.profile.ProfileActivity;
import com.seshra.everestcab.rides.YourRideActivity;
import com.seshra.everestcab.service.ApplyPromoCodeServcie;
import com.seshra.everestcab.service.CheckActiveRideService;
import com.seshra.everestcab.service.CheckOutService;
import com.seshra.everestcab.service.CheckServiceAvailabilityService;
import com.seshra.everestcab.service.ConfirmRideService;
import com.seshra.everestcab.service.GetCurrentLocationName;
import com.seshra.everestcab.utils.BaseConfig;
import com.seshra.everestcab.utils.DirectionsJSONParser;
import com.seshra.everestcab.utils.IntentKeys;
import com.seshra.everestcab.utils.LocationUtils;
import com.seshra.everestcab.utils.STATUS;
import com.seshra.everestcab.utils.SessionManager;
import com.seshra.everestcab.utils.SingletonGson;
import com.seshra.everestcab.viewmodel.MainActivityViewModel;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
View.OnClickListener,
        OnMapReadyCallback {

    private static final String TAG = "MainActivity.java";


    private SettingsClient mSettingsClient;
    private static final int REQUEST_CHECK_SETTINGS = 0x1;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationRequest mLocationRequest;

    private final int ADD_STOP_ACTIVITY = 777;


    boolean confirmClicked = false;

    ArrayList<LatLng> points = null;
    PolylineOptions lineOptions = null;
    ParserTask parserTask = null;


    String pickLat, pickLon, dropLat, dropLon, picLoc , dropLoc;


//    TextInputEditText inputPromoCode;
//    TextInputLayout promoCodeLayout;

//    TextView estimatedTime;

    TextView stopLocationName;

    TextView pickLocationName;


    Animation slide_up, slide_down;


    String stopLocation = "";


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView navIcon, favIcon;
    View mapView;
    private GoogleMap mGoogleMap;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 11;
    TextView internetStatus;
    private FusedLocationProviderClient mFusedLocationClient;
    TextView pickAddressText;
    SupportMapFragment mapFragment;

    TextView yahoo;

    MainActivityViewModel viewModel;

    TextView userName, userPhone;
    ImageView userImage;

    Location PICKUP_LOCATION;

    Location targetLocation;

    ImageView pickUpPin, dropOffPin;
//    ImageView rewardIcon;

    ArrayList<LatLng> markerPoints = new ArrayList<LatLng>();
    Marker stopMarker;

    Marker pickMarker, dropMarker;

    Polyline polyline;

    LatLng centerLatLng;

    String type = "";


    Button addStop;


    List<Marker> driverMarker = new ArrayList<Marker>();




    Boolean plottingPoints = false;

    Button confirmBtn, confirmDropBtn;

    TextView dPickLocationName, dDropLocationName;

    ArrayList<String> pickDropLocationNames = new ArrayList<>();

//    ImageView scheduler;

    View confirmDropLayout, confirmPickLayout, pickUpLocationLayout, dropOffLocationLayout;
    View finalConfirmLayout, finalRequirementsLayout;
//    View selectServiceLayout;





    BottomSheetDialog dialog;

    TextView detailsText;

    SharedPreferences sharedPreferences;

    SessionManager sessionManager;

    ProgressBar progressBar;

    JSONObject pickObject, dropObject,stopObject;

    private ModelCheckOut modelCheckOut;

    TextView serviceAvailability;



    TextView estBillFixed, estBillMetered;

    private LatLng PICK_LOCATION, DROP_LOCATION, CURRENT_LOCATION;
    private LatLng STOP_LOCATION;

    private final int DROP_PLACE_PICKER_ACTIVITY = 111,  PICK_PLACE_PICKER_ACTIVITY = 333;



    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            String intentName = intent.getAction();
            switch (intentName) {



                case CheckActiveRideService.CHECK_ACTIVE_RIDE_MESSAGE:

                    try {
                        String placeResult = intent.getStringExtra(CheckActiveRideService.CHECK_ACTIVE_RIDE_MESSAGE_KEY);
                        if (!placeResult.equals("0")) {

                            ModelActiveRide modelActiveRide = SingletonGson.getInstance()
                                    .fromJson("" + placeResult, ModelActiveRide.class);
                            if(modelActiveRide.getData().size()!=0) {

                                if (modelActiveRide.getData().get(0).getBooking_status().equals("" + STATUS.ACCEPTED) ||
                                        modelActiveRide.getData().get(0).getBooking_status().equals("" + STATUS.ARRIVED) ||
                                        modelActiveRide.getData().get(0).getBooking_status().equals("" + STATUS.STARTED)) {


                                    showActiveRideDialog("1", modelActiveRide.getData().get(0).getId());


                                } else if (modelActiveRide.getData().get(0).getBooking_status().equals("" + STATUS.END)) {


                                    showActiveRideDialog("2", modelActiveRide.getData().get(0).getId());
//

                                }
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;


                case GetCurrentLocationName.PLACE_SEARCH_MESSAGE:

                    try {
                        String placeResult = intent.getStringExtra(GetCurrentLocationName.PLACE_SEARCH_KEY);
                        if (!placeResult.equals("0")) {


                            if (pickDropLocationNames.size() == 0) {
                                pickAddressText.setText(placeResult);
                            } else if (pickDropLocationNames.size() == 1) {

                                dDropLocationName.setText(placeResult);
                            }


                        } else {

                            try {
                                new GetAddressTask(getApplicationContext()).execute(targetLocation);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

//                    Toast.makeText(MainActivity.this, "Couldn't get location name.", Toast.LENGTH_LONG).show();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;


                case CheckOutService.RIDE_NOW_MESSAGE:


                    try {
                        String result = intent.getStringExtra(CheckOutService.RIDE_NOW_MESSAGE_KEY);
                        if (!result.equals("0")) {

//                    mapFragment.getView().setVisibility(View.INVISIBLE);

                            type ="";

                            modelCheckOut = SingletonGson.getInstance().fromJson("" + result, ModelCheckOut.class);

                            if(pickDropLocationNames.size()>2) {
                                if (modelCheckOut.getResult().equals("1")) {
                                    setViews(modelCheckOut);

//
                                    if (!modelCheckOut.getData().getDrop_longitude().equals("")) {
                                        getAllMarkers();
                                        yahoo.setClickable(true);
//                                        progressBar.setVisibility(View.GONE);


                                    }

                                    dropOffLocationLayout.setVisibility(View.GONE);
                                    confirmDropLayout.setVisibility(View.GONE);
                                    finalRequirementsLayout.setVisibility(View.VISIBLE);
                                    finalConfirmLayout.setVisibility(View.VISIBLE);


                                } else {

                                    progressBar.setVisibility(View.GONE);
                                    pickDropLocationNames.remove(2);
                                }

                            }
//                                progressBar.setVisibility(View.GONE);
//                                Toast.makeText(MainActivity.this, modelCheckOut.getMessage(), Toast.LENGTH_LONG).show();



                        } else {

                            confirmDropBtn.setClickable(true);

                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(MainActivity.this, getResources().getString(R.string.error), Toast.LENGTH_LONG).show();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;


                case ConfirmRideService.CONFIRM_RIDE_MESSAGE:


                    try {

                        String script = intent.getStringExtra(ConfirmRideService.CONFIRM_RIDE_MESSAGE_KEY);
                        if (!script.equals("0")) {


                            progressBar.setVisibility(View.GONE);
                            ModelConfirm modelConfirm = SingletonGson.getInstance()
                                    .fromJson("" + script, ModelConfirm.class);



                            if (modelConfirm.getResult().equals("1")) {
                                Intent intent1 = new Intent(MainActivity.this, DriverWaitingActivity.class);
                                intent1.putExtra(IntentKeys.BOOKING_ID, ""+modelConfirm.getData().getId());
                                intent1.putExtra(IntentKeys.BOOKING_CONFIRM_MODEL,script);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent1);
                                finish();

                            } else {


                                showErrorConfirmRideDialog(modelConfirm.getMessage());



//                                Toast.makeText(MainActivity.this, modelConfirm.getMessage(), Toast.LENGTH_LONG).show();
                            }




                        } else {


                            Toast.makeText(MainActivity.this, getResources().getString(R.string.error_confirm), Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;



                case CheckServiceAvailabilityService.CHECK_SERVICE_AVAILABILTY_MESSAGE:


                    try {
                        String script = intent.getStringExtra(CheckServiceAvailabilityService.CHECK_SERVICE_AVAILABILITY_KEY);
                        if (!script.equals("0")) {



                            progressBar.setVisibility(View.GONE);
                            ResultCheckHomeServices modelConfirm = SingletonGson.getInstance()
                                    .fromJson("" + script, ResultCheckHomeServices.class);


                            if(modelConfirm.getResult().equals("1")){

                                serviceAvailability.setVisibility(View.GONE);
                                if(modelConfirm.getData()!=null){
//                                    estimatedTime.setText(modelConfirm.getData().getEta());

//                                    if(driverImage==null) {
//                                        new DownloadTaxiImage(MainActivity.this).execute(modelConfirm.getData());
//                                    }else {
                                        if(modelConfirm.getData().getDrivers().size()!=0){
                                            showDriversInMap(modelConfirm.getData().getDrivers());
                                        }
//                                    }



                                }

                            }else {
                                serviceAvailability.setVisibility(View.VISIBLE);

                            }


                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;


                case ApplyPromoCodeServcie.PROMO_MESSAGE:
                    try {
                        String script = intent.getStringExtra(ApplyPromoCodeServcie.PROMO_MESSAGE_KEY);
                        if (!script.equals("0")) {

                            ModelApplyPromoCode modelResultCheck = SingletonGson.getInstance()
                                    .fromJson("" + script, ModelApplyPromoCode.class);

                            progressBar.setVisibility(View.GONE);


                            if(modelResultCheck.getResult().equals("1")){


//                                promoCodeLayout.setHint(getResources().getString(R.string.promo_applied));
//                                inputPromoCode.clearFocus();

//                                afterCouponRate.setVisibility(View.VISIBLE);
                                estBillFixed.setText(modelResultCheck.getData().getEstimate_bill());
                                estBillFixed.setTextColor(getResources().getColor(R.color.colorRed));
                                estBillFixed.setBackground(getResources().getDrawable(R.drawable.text_strike_through));
//                                afterCouponRate.setText(modelResultCheck.getData().getDiscounted_amout());

                            }else {

//                                promoCodeLayout.setHint(getResources().getString(R.string.promo));
//                                inputPromoCode.clearFocus();
//                                inputPromoCode.setText("");

//                                afterCouponRate.setVisibility(View.GONE);

//                                estBill.setText(modelResultCheck.getData().getEstimate_bill());
//                                estBill.setTextColor(getResources().getColor(R.color.colorRed));
//                                estBill.setBackground(getResources().getDrawable(R.drawable.text_strike_through));
//                                afterCouponRate.setText(modelResultCheck.getData().getDiscounted_amout());
                                Toast.makeText(MainActivity.this,modelResultCheck.getMessage(),Toast.LENGTH_LONG).show();
//                                serviceAvailability.setVisibility(View.VISIBLE);

                            }


                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;




            }
        }
        };


    private void removeExistingDrivers() {

        if(driverMarker!=null){
            for (Marker marker: driverMarker) {
                marker.remove();
            }
            driverMarker.clear();
        }

    }

    private void showDriversInMap(ArrayList<ResultCheckHomeServices.Data.Drivers> drivers) {


        for(int i=0; i<drivers.size();i++){

            createDriverMarker(drivers.get(i).getCurrent_latitude(),
                    drivers.get(i).getCurrent_longitude(),
                    drivers.get(i).getBearing());

        }

    }

    private void setViews(ModelCheckOut modelCheckOut) {

//            imageLayout.setVisibility(View.GONE);


            pickAddressText.setText(modelCheckOut.getData().getPickup_location());
            dDropLocationName.setText(modelCheckOut.getData().getDrop_location());

            estBillFixed.setText("" + modelCheckOut.getData().getEstimate_bill());
//            estBillMetered.setText("" + modelCheckOut.getData().getEstimate_bill());



    }


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.navigation_drawer);


            pickLocationName = findViewById(R.id.pickLocationName);

            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

            mSettingsClient = LocationServices.getSettingsClient(this);
            createLocationRequest();
            buildLocationSettingsRequest();



            //Load animation
             slide_down = AnimationUtils.loadAnimation(this, R.anim.slide_down);

             slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.slide_up);


            pickDropLocationNames.clear();

            sessionManager = new SessionManager(this);
            dropOffPin = findViewById(R.id.dropOffPin);

//            afterCouponRate = findViewById(R.id.serviceEstimatedBillAfterCoupon);
            drawerLayout = findViewById(R.id.drawer_layout);
            navigationView = findViewById(R.id.navigation_view);
            navIcon = findViewById(R.id.nav_hamburg_icon);
            internetStatus = findViewById(R.id.internetStatus);
            favIcon = findViewById(R.id.favouriteBtn);
            pickUpPin = findViewById(R.id.pickUpPin);
            confirmDropBtn = findViewById(R.id.confirmDropOffBtn);
//            scheduler = findViewById(R.id.scheduler);
            detailsText = findViewById(R.id.detailsText);
//            inputPromoCode = findViewById(R.id.inputPromoCode);
//            promoCodeLayout = findViewById(R.id.promoCodeLayout);
//            rewardIcon = findViewById(R.id.reward_icon);


            estBillFixed = findViewById(R.id.serviceEstimatedBill);
            estBillMetered = findViewById(R.id.estimatedPriceMetered);

//            imageLayout = findViewById(R.id.image_layout);

            dPickLocationName = findViewById(R.id.pickLocationName);
            dDropLocationName = findViewById(R.id.dropOffLocationName);

            confirmPickLayout = findViewById(R.id.confirmPickupLayout);
            pickUpLocationLayout = findViewById(R.id.pickUpLocationLayout);
            confirmDropLayout = findViewById(R.id.confirmDropLayout);
            dropOffLocationLayout = findViewById(R.id.dropUpLocationLayout);
            stopLocationName = findViewById(R.id.stopPointLocationName);
//            selectServiceLayout = findViewById(R.id.selectServiceLayout);

            yahoo = findViewById(R.id.finalConfirmBtn);
            progressBar = findViewById(R.id.progressbar);

            finalConfirmLayout = findViewById(R.id.finalConfirmLayout);
            finalRequirementsLayout = findViewById(R.id.finalRequirementLayout);

            serviceAvailability = findViewById(R.id.serviceAvailability);
            addStop = findViewById(R.id.addStopPoint);

//            estimatedTime = findViewById(R.id.serviceTypeEst);

            confirmBtn = findViewById(R.id.confirmPickupBtn);
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);


            initViewModel();







            //Inflate navigation view Header
            View header = navigationView.getHeaderView(0);
            userName = header.findViewById(R.id.nav_head_user_name);
            userImage = header.findViewById(R.id.nav_head_user_img);
            userPhone = header.findViewById(R.id.nav_head_phone);

            header.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                }
            });


            updateNavHeader();


            mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map_frag_main);

            pickAddressText = findViewById(R.id.pickUpLocationName);

            dialog = new BottomSheetDialog(this);


            navigationView.setNavigationItemSelectedListener(this);
            navIcon.setOnClickListener(this);

            mapView = mapFragment.getView();

            mapFragment.getMapAsync(this);

            pickAddressText.setOnClickListener(this);
            favIcon.setOnClickListener(this);
            confirmBtn.setOnClickListener(this);
            confirmDropBtn.setOnClickListener(this);
//            scheduler.setOnClickListener(this);
            detailsText.setOnClickListener(this);
//            inputPromoCode.setOnClickListener(this);
            dDropLocationName.setOnClickListener(this);
            yahoo.setOnClickListener(this);
//            rewardIcon.setOnClickListener(this);
            pickLocationName.setOnClickListener(this);
            addStop.setOnClickListener(this);






        }

    private void showForceUpdateDialog(String dialog_message) {

      AlertDialog.Builder alertDialog =   new AlertDialog.Builder(MainActivity.this)
                .setTitle("Update Required!")
                .setMessage(dialog_message)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        }

                    }

                })

                .setIcon(android.R.drawable.ic_dialog_alert);

       boolean mandatory = sessionManager.getAppConfig().getData().getApp_version().isMadatory();
       if(mandatory){
           alertDialog.setCancelable(false);
           // A null listener allows the button to dismiss the dialog and take no further action.
                alertDialog.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                   finish();
               }
           });
       }else {

           alertDialog.setNegativeButton(getResources().getString(R.string.update_later), new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {

                   dialog.dismiss();

               }
           });
       }

       alertDialog.show();

    }


    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(LocationUtils.UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(LocationUtils.FASTEST_UPDATE_INTERVAL);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        //mLocationRequest.setSmallestDisplacement(Utils.SMALLEST_DISPLACEMENT);
        //mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
        mLocationRequest.setMaxWaitTime(LocationUtils.MAX_WAIT_TIME);
    }



    private void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }


        private void updateNavHeader() {
            userName.setText(sessionManager.getUserDetails().get(SessionManager.USER_FIRST_NAME) +" "
                            +sessionManager.getUserDetails().get(SessionManager.USER_LAST_NAME));

            Glide.with(MainActivity.this).load(sessionManager.getUserDetails().get(SessionManager.USER_IMAGE))
                    .into(userImage);

            userPhone.setText(sessionManager.getUserDetails().get(SessionManager.USER_PHONE));


        }

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


            int id = menuItem.getItemId();
            switch (id) {

                case R.id.nav_about_us:
                    drawerLayout.closeDrawers();
                    startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                    break;

                case R.id.nav_your_trips:
                    drawerLayout.closeDrawers();
                    startActivity(new Intent(MainActivity.this, YourRideActivity.class));
                    break;

//                case R.id.nav_contact_us:
//                    drawerLayout.closeDrawers();
//                    try {
//                        String[] addresses = {"easytaxinepal@gmail.com"};
////                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "" + sessionmanager.getAppConfig().getData().getCustomer_support().getMail(), null));
//                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
//                        emailIntent.setData(Uri.parse("mailto:")); // only email apps should handle this
//                        emailIntent.putExtra(Intent.EXTRA_EMAIL, addresses);
////                        emailIntent.setType("message/rfc822");
//                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "" + MainActivity.this.getResources().getString(R.string.report_issue));
//                        emailIntent.putExtra(Intent.EXTRA_TEXT, "User Phone:"+sessionManager.getUserDetails().get(SessionManager.USER_PHONE)
//                        +("\n User Device:"+ Build.MODEL));
//                        startActivity(Intent.createChooser(emailIntent, "" + MainActivity.this.getResources().getString(R.string.send_email)));
////                     emailIntent.setType("text/plain");
//
//                    } catch (Exception e) {
//                        Snackbar.make(drawerLayout, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
//                    }
//                    break;

                case R.id.nav_notification:
                    drawerLayout.closeDrawers();
                    startActivity(new Intent(MainActivity.this, NotificationsActivity.class));
                    break;

//                case R.id.nav_help_for_blood:
//                    drawerLayout.closeDrawers();
//                    startActivity(new Intent(MainActivity.this, HelpForBloodActivity.class));
//                    break;


                case R.id.nav_privacy_policy:
                    drawerLayout.closeDrawers();
                    startActivity(new Intent(MainActivity.this, PrivacyPolicyActivity.class));
                    break;


                case R.id.nav_terms:
                    drawerLayout.closeDrawers();
                    startActivity(new Intent(MainActivity.this, TermsAndConditionActivity.class));
                    break;

                case R.id.nav_language:

                    drawerLayout.closeDrawers();
                    try {
                        AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
                        builderSingle.setTitle(R.string.select_language);
                        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this,
                                android.R.layout.select_dialog_singlechoice);
                        arrayAdapter.add("English");
                        arrayAdapter.add("नेपाली");

//                        builderSingle.setNegativeButton(MainActivity.this.getResources()
//                                .getString(R.string.cancel), (DialogInterface dialogInterface, int which) -> {
//                            dialogInterface.dismiss();
//                        });

                        builderSingle.setAdapter(arrayAdapter, (DialogInterface dialog, int which) -> {

                            sessionManager.setLanguage("" + sessionManager.getAppConfig().getData(

                            ).getLanguages().get(which).getLocale());

                            finish();
                            MainActivity.this.finish();
                            startActivity(new Intent(MainActivity.this, SplashActivity.class));
                            dialog.dismiss();

                        });



//                        builderSingle.setPositiveButton(MainActivity.this.getResources().getString(R.string.ok),
//                                (DialogInterface dialogInterface, int which) -> {
//                            dialogInterface.dismiss();
//                        });

                        builderSingle.show();

                    } catch (Exception e) {
                        Snackbar.make(drawerLayout, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                    break;


                case R.id.nav_log_out:
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setCancelable(false);
                    builder.setTitle(R.string.logout_small);
                    builder.setTitle(R.string.logout_small);
                    builder.setMessage(R.string.are_you_sure_to_log_out)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {


                                    viewModel.logOutUser();



                                }
                            })
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User cancelled the dialog
                                }
                            });
                    drawerLayout.closeDrawers();
                    builder.create().show();
                    break;

            }


            return super.onOptionsItemSelected(menuItem);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.nav_hamburg_icon:
                    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        drawerLayout.closeDrawer(GravityCompat.START);
                    } else {
                        drawerLayout.openDrawer(GravityCompat.START);
                    }
                    break;


                case R.id.pickUpLocationName:
                    if(pickDropLocationNames.size()<1) {
                        startPlacePickerActivity(PICK_PLACE_PICKER_ACTIVITY);
                    }else {
                        moveMapto(pickMarker.getPosition());
                    }
                    return;


                case R.id.pickLocationName:

                    if(pickDropLocationNames.size()<1) {
                        startPlacePickerActivity(PICK_PLACE_PICKER_ACTIVITY);
                    }else {
                        moveMapto(pickMarker.getPosition());
                    }

                    break;

                case R.id.favouriteBtn:
                    if (PICKUP_LOCATION != null) {
                        Intent intent = new Intent(MainActivity.this, SaveLocationActivity.class);
                        intent.putExtra("lat", PICKUP_LOCATION.getLatitude());
                        intent.putExtra("lon", PICKUP_LOCATION.getLongitude());
                        intent.putExtra("name", pickAddressText.getText().toString());
                        startActivity(intent);
                    }
                    return;


                case R.id.confirmPickupBtn:

                    if(serviceAvailability.getVisibility()!= View.VISIBLE) {
                        if (CURRENT_LOCATION != null && !pickAddressText.getText().toString().isEmpty()) {

                            shakeItBaby();
                            PICK_LOCATION = CURRENT_LOCATION;

                            pickDropLocationNames.add(pickAddressText.getText().toString());
                            createPickUpMarker();

                            dropOffPin.setVisibility(View.VISIBLE);

                            confirmBtn.setClickable(false);

                            pickUpLocationLayout.setVisibility(View.GONE);
                            confirmPickLayout.setVisibility(View.GONE);
//                            selectServiceLayout.setVisibility(View.GONE);
//
                            confirmDropLayout.setVisibility(View.VISIBLE);
                            dropOffLocationLayout.setVisibility(View.VISIBLE);
                            dPickLocationName.setText(pickDropLocationNames.get(0));
                        } else {
                            Toast.makeText(MainActivity.this,
                                    getResources().getString(R.string.move__marker), Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(MainActivity.this,
                                getResources().getString(R.string.service_unavailable), Toast.LENGTH_LONG).show();
                    }

                    break;


                case R.id.confirmDropOffBtn:

                    if(serviceAvailability.getVisibility()!=View.VISIBLE) {
                        if (CURRENT_LOCATION != null && !dDropLocationName.getText().toString().isEmpty()) {

                            shakeItBaby();
                            DROP_LOCATION = CURRENT_LOCATION;
                            confirmDropBtn.setClickable(false);

                            plottingPoints = true;

                            progressBar.setVisibility(View.VISIBLE);
                            pickDropLocationNames.add(dDropLocationName.getText().toString());
                            createDropMarker();
//                        confirmDropBtn.setVisibility(View.GONE);

                            sharedPreferences.edit().putString(IntentKeys.PROMO_CODE,"null").apply();


                            createDropLocationObject();
                            createPickLocationObject();

                            if(STOP_LOCATION!=null)
                                createStopLocationObject();

                            callRideNowApi();

                            yahoo.setClickable(false);


                        } else {
                            Toast.makeText(MainActivity.this,
                                    getResources().getString(R.string.move__marker), Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(MainActivity.this,
                                getResources().getString(R.string.service_unavailable), Toast.LENGTH_LONG).show();
                    }

                    break;


                case R.id.addStopPoint:

                    Intent intent = new Intent(MainActivity.this, AddStopActivity.class);
                    startActivityForResult(intent, ADD_STOP_ACTIVITY);

                    break;

                case R.id.detailsText:
                    openDialog(1);
                    break;

//                case R.id.inputPromoCode:
//
//                    createPromoDialog();
////                    openDialog(2);
//                    break;

                case R.id.dropOffLocationName:
                    if(pickDropLocationNames.size()<3) {
                        startPlacePickerActivity(DROP_PLACE_PICKER_ACTIVITY);
                    }else {
                        moveMapto(dropMarker.getPosition());
                    }
                    break;


                case R.id.finalConfirmBtn:
                    progressBar.setVisibility(View.VISIBLE);
                    shakeItBaby();
                    confirmClicked = true;
                    confirmRideApi();

                    yahoo.setClickable(false);
                    break;

//                case R.id.reward_icon:
//                    startActivity(new Intent(MainActivity.this, NotificationsActivity.class));
//                    break;


            }

        }

    private void createDropMarker() {

        // Creating a marker
        MarkerOptions markerOptions = new MarkerOptions();

        LatLng location;

        Point mappoint;
        if(!type.equals("cancel")) {
            markerOptions.title(pickDropLocationNames.get(1));
            mappoint = mGoogleMap.getProjection().toScreenLocation(new LatLng(targetLocation.getLatitude(),
                    targetLocation.getLongitude()));

            location = new LatLng(targetLocation.getLatitude(),
                    targetLocation.getLongitude());

            mappoint.set(mappoint.x, mappoint.y + 64);
            markerOptions.position(mGoogleMap.getProjection().fromScreenLocation(mappoint));
        }else {

            markerOptions.title(dropLoc);

            location = new LatLng(Double.parseDouble(dropLat),
                    Double.parseDouble(dropLon));


            mappoint = mGoogleMap.getProjection().toScreenLocation(new LatLng(Double.parseDouble(dropLat),
                    Double.parseDouble(dropLon)));
            markerOptions.position(location);
//                    markerOptions.position(new LatLng(targetLocation.getLatitude(), targetLocation.getLongitude()));


//            mappoint.set(mappoint.x, mappoint.y + 64);
        }



        Bitmap dropMarkerIcon = BitmapFactory.decodeResource(getResources(),
                R.drawable.drop_marker_down);
//            getResizedBitmap(pickMarkerIcon,32,60);

        Bitmap resized = Bitmap.createScaledBitmap(dropMarkerIcon, 64, 124,true);


        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(resized));

//        // Placing a marker on the touched position
//        mGoogleMap.addMarker(markerOptions);
            dropOffPin.setVisibility(View.GONE);


         dropMarker = mGoogleMap.addMarker(markerOptions);



    }

    private void createPickUpMarker() {
            // Creating a marker
            MarkerOptions markerOptions = new MarkerOptions();


            // Setting the title for the marker.
            // This will be displayed on taping the marker

            Bitmap pickMarkerIcon = BitmapFactory.decodeResource(getResources(),
                R.drawable.pick_marker_down);
//            getResizedBitmap(pickMarkerIcon,32,60);

        Bitmap resized = Bitmap.createScaledBitmap(pickMarkerIcon, 64, 124, true);


        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(resized));



        pickUpPin.setVisibility(View.GONE);
        Point mappoint;
        LatLng location;

        if(!type.equals("cancel")) {
            markerOptions.title(pickDropLocationNames.get(0));
            mappoint = mGoogleMap.getProjection().toScreenLocation(new LatLng(targetLocation.getLatitude(),
                    targetLocation.getLongitude()));
            location = new LatLng(targetLocation.getLatitude(),
                    targetLocation.getLongitude());

            mappoint.set(mappoint.x, mappoint.y + 64);
            markerOptions.position(mGoogleMap.getProjection().fromScreenLocation(mappoint));


            // Setting the position for the marker
//        markerOptions.position(mappoint);

            pickMarker = mGoogleMap.addMarker(markerOptions);
        }else {

            markerOptions.title(picLoc);

//            mappoint = new Point()

            location = new LatLng(Double.parseDouble(pickLat),
                    Double.parseDouble(pickLon));

            mappoint = mGoogleMap.getProjection().toScreenLocation(location);

            mappoint.set(mappoint.x, mappoint.y + 64);

            markerOptions.position(location);


            // Setting the position for the marker
//        markerOptions.position(mappoint);

            pickMarker = mGoogleMap.addMarker(markerOptions);

        }





    }





        @Override
        public void onMapReady(GoogleMap googleMap) {

            mGoogleMap = googleMap;
            mGoogleMap.setMaxZoomPreference(20);

            try{

                Intent intent = getIntent();
                pickLat = intent.getStringExtra(IntentKeys.PICK_LATITUDE);
                pickLon = intent.getStringExtra(IntentKeys.PICK_LONGITUDE);
                dropLat= intent.getStringExtra(IntentKeys.DROP_LATITUDE);
                dropLon = intent.getStringExtra(IntentKeys.DROP_LONGITUDE);
                picLoc = intent.getStringExtra(IntentKeys.PICK_LOCATION);
                dropLoc = intent.getStringExtra(IntentKeys.DROP_LOCATION);

                if((pickLat!=null && pickLon!=null && dropLat!=null && dropLon!=null && picLoc!=null&& dropLoc!=null)
                ){

                    type = "cancel";
                    pickDropLocationNames.add(picLoc);
                    pickDropLocationNames.add(dropLoc);

                    dropOffLocationLayout.setVisibility(View.VISIBLE);
                    finalConfirmLayout.setVisibility(View.VISIBLE);
                    finalRequirementsLayout.setVisibility(View.VISIBLE);
                    pickLocationName.setText(pickDropLocationNames.get(0));
                    dDropLocationName.setText(pickDropLocationNames.get(1));

                    createPickUpMarker();
                    createDropMarker();
                    createPickLocationObject();
                    createDropLocationObject();
                    callRideNowApi();

                }else {



                    dropOffLocationLayout.setVisibility(View.GONE);
                    finalConfirmLayout.setVisibility(View.GONE);
                    finalRequirementsLayout.setVisibility(View.GONE);
                    pickUpLocationLayout.setVisibility(View.VISIBLE);
                }




            }catch (Exception e){
                e.printStackTrace();
            }




            setMyLocationButtonPosition();


            //Check For Location Permission
            if (hasRuntimePermissions()) {


                checkSettings();


            } else {
                requestRuntimePermissions();
            }


        }


        @Override
        public void onRequestPermissionsResult(
                int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            Log.i(TAG, "onRequestPermissionResult");
            if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
                if (grantResults.length <= 0) {
                    // If user interaction was interrupted, the permission request is cancelled and you
                    // receive empty arrays.
                    Log.i(TAG, "User interaction was cancelled.");
                } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission was granted.

                    checkSettings();
//                    isNetworkAvailable();
//                    showCurrentLocation();

                }


            } else {
                // Permission denied.

                // In this Activity we've chosen to notify the user that they
                // have rejected a core permission for the app since it makes the Activity useless.
                // We're communicating this message in a Snackbar since this is a sample app, but
                // core permissions would typically be best requested during a welcome-screen flow.

                // Additionally, it is important to remember that a permission might have been
                // rejected without asking the user for permission (device policy or "Never ask
                // again" prompts). Therefore, a user interface affordance is typically implemented
                // when permissions are denied. Otherwise, your app could appear unresponsive to
                // touches or interactions which have required permissions.
                Snackbar.make(
                        findViewById(R.id.mainLayout),
                        R.string.permission_denied_explanation,
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction(
                                R.string.settings,
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        // Build intent that displays the App settings screen.
                                        Intent intent = new Intent();
                                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null);
                                        intent.setData(uri);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                })
                        .show();
            }
        }


        @Override
        protected void onResume() {
            super.onResume();

            if(mFusedLocationClient==null)
                mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


            try {

                boolean update = sessionManager.getAppConfig().getData().getApp_version().isShow_dialog();


                if (update) {
                    showForceUpdateDialog(sessionManager.getAppConfig().getData().getApp_version().getDialog_message());
                }
            }catch (Exception e){
                e.printStackTrace();
            }


        }

        /**
         * Returns the current state of the permissions needed.
         */
        private boolean hasRuntimePermissions() {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                //Ask for the permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSIONS_REQUEST_CODE);
                Toast.makeText(this, getResources().getString(R.string.location_permission_needed), Toast.LENGTH_SHORT).show();
                return false;
            }

            return true;
        }


        private void requestRuntimePermissions() {
            boolean shouldProvideRationale =
                    ActivityCompat.shouldShowRequestPermissionRationale(
                            this, Manifest.permission.ACCESS_FINE_LOCATION);

            // Provide an additional rationale to the user. This would happen if the user denied the
            // request previously, but didn't check the "Don't ask again" checkbox.
            if (shouldProvideRationale) {
                Log.i(TAG, "Displaying permission rationale to provide additional context.");
                Snackbar.make(
                        findViewById(R.id.mainLayout),
                        R.string.permission_rationale,
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction(
                                R.string.ok,
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        // Request permission
                                        ActivityCompat.requestPermissions(
                                                MainActivity.this,
                                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                                REQUEST_PERMISSIONS_REQUEST_CODE);
                                    }
                                })
                        .show();
            } else {
                Log.i(TAG, "Requesting permission");
                // Request permission. It's possible this can be auto answered if device policy
                // sets the permission in a given state or the user denied the permission
                // previously and checked "Never ask again".
                ActivityCompat.requestPermissions(
                        MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_PERMISSIONS_REQUEST_CODE);
            }
        }


        public void isNetworkAvailable() {

            ConnectivityManager connectivityManager
                    = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {

                internetStatus.setVisibility(View.GONE);

            } else {
                internetStatus.setVisibility(View.VISIBLE);
            }

        }


        public void showCurrentLocation() {



            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {

                            if (location != null) {


                                PICKUP_LOCATION = location;

                                Log.d(TAG, ""+location.getLatitude()+location.getLongitude());


//                            PICK_LOCATION = new LatLng(loca
//                            tion.getLatitude(), location.getLongitude());



                                viewModel.checkServiceAvailability(location.getLatitude(),location.getLongitude());

                                viewModel.getCurrentLocationName(location.getLatitude(), location.getLongitude());

//                            new GetAddressTask(getApplicationContext()).execute(location);

                                CameraPosition cameraPosition = new CameraPosition.Builder()
                                        .target(new LatLng(location.getLatitude(), location.getLongitude()))
                                        .zoom(17)
                                        .build();


                                mGoogleMap.setMyLocationEnabled(true);

//                                setPositionOfGoogleBtn();

                                mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                            }else {

                                CameraPosition cameraPosition = new CameraPosition.Builder()
                                        .target(new LatLng(27.6850439,85.345278))
                                        .zoom(17)
                                        .build();


                                mGoogleMap.setMyLocationEnabled(true);

//                                setPositionOfGoogleBtn();

                                mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                            }



                        }
                    });


            mGoogleMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {

                /* User starts moving map - change your pin's image here
                 */
                @Override
                public void onCameraMoveStarted(int i) {


                        pickUpPin.setBackground(getResources().getDrawable(R.drawable.pick_marker_moving));
                        dropOffPin.setBackground(getResources().getDrawable(R.drawable.drop_marker_up));


                }
            });


            mGoogleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                @Override
                public void onCameraIdle() {


                    pickUpPin.setBackground(getResources().getDrawable(R.drawable.pick_marker_down));
                    dropOffPin.setBackground(getResources().getDrawable(R.drawable.drop_marker_down));




                    removeExistingDrivers();


                    VisibleRegion visibleRegion = mGoogleMap.getProjection()
                            .getVisibleRegion();

                    Point x = mGoogleMap.getProjection().toScreenLocation(
                            visibleRegion.farRight);

                    Point y = mGoogleMap.getProjection().toScreenLocation(
                            visibleRegion.nearLeft);

                    Point centerPoint = new Point(x.x / 2, y.y / 2);

                     centerLatLng = mGoogleMap.getProjection().fromScreenLocation(
                            centerPoint);


//                    centerLatLng = mGoogleMap.getProjection().getVisibleRegion().latLngBounds.getCenter();



                    targetLocation = new Location("");//provider name is unnecessary
                    targetLocation.setLatitude(mGoogleMap.getCameraPosition().target.latitude);//your coords of course
                    targetLocation.setLongitude(mGoogleMap.getCameraPosition().target.longitude);


                    CURRENT_LOCATION = new LatLng(targetLocation.getLatitude(), targetLocation.getLongitude());

                    if(pickDropLocationNames.size()<3) {


                            viewModel.getCurrentLocationName(targetLocation.getLatitude(),
                                    targetLocation.getLongitude());
                            viewModel.checkServiceAvailability(targetLocation.getLatitude(), targetLocation.getLongitude());



                    }



                }
            });


        }




        private class GetAddressTask extends AsyncTask<Location, Void, String> {
            Context mContext;
            Location loc;

            public GetAddressTask(Context context) {
                super();
                mContext = context;
            }

            @Override
            protected String doInBackground(Location... params) {
                Geocoder geocoder =
                        new Geocoder(mContext, Locale.getDefault());
                // Get the current location from the input parameter list
                loc = params[0];
                // Create a list to contain the result address
                List<Address> addresses = null;

                try {
                    addresses = geocoder.getFromLocation(loc.getLatitude(),
                            loc.getLongitude(), 10);
                    // If the reverse geocode returned an address
                    if (addresses != null && addresses.size() > 0) {
                        // Get the first address
                        Address address = addresses.get(0);
                        /*
                         * Format the first line of address (if available),
                         * city, and country name.
                         */
                        Log.i("***Address", "" + address);

                        String addressText = null;

                        addressText = String.format(
                                "%s",
                                // If there's a street address, add it
                                address.getMaxAddressLineIndex() > 0 ?
                                        "" : address.getAddressLine(0));


                        // Return the text
                        return addressText;
                    } else {

                        return "";
                    }
                } catch (Exception e1) {
                    Log.e("LocationSampleActivity",
                            "IO Exception in getFromLocation()");
                    e1.printStackTrace();
                    return "";
                }


            }

            @Override
            protected void onPostExecute(String address) {


                if (!address.equals("")) {
                    if (pickUpLocationLayout.getVisibility() == View.VISIBLE) {
//                        PICK_LOCATION = new LatLng(loc.getLatitude(), loc.getLongitude());
                        pickAddressText.setText(address);
                    } else {
//                        DROP_LOCATION = new LatLng(loc.getLatitude(), loc.getLongitude());
                        dDropLocationName.setText(address);
                    }


                } else {

                }
            }

        }




        @Override
        public void onBackPressed() {


            if(!confirmClicked && !plottingPoints) {
                switch (pickDropLocationNames.size()) {


                    default:
                    case 0:

                        showExitAppDialog();

                        break;


                    case 1:
                        pickUpLocationLayout.setVisibility(View.VISIBLE);
                        confirmBtn.setClickable(true);
                        confirmPickLayout.setVisibility(View.VISIBLE);
                        finalRequirementsLayout.setVisibility(View.GONE);
                        pickAddressText.setText(pickDropLocationNames.get(0));
                        pickDropLocationNames.remove(0);
                        dropOffPin.setVisibility(View.GONE);
                        moveMapto(pickMarker.getPosition());
                        removePickMarker();
                        pickUpPin.setVisibility(View.VISIBLE);
                        sharedPreferences.edit().putString("details", "null").apply();

                        stopLocationName.setVisibility(View.GONE);
                        confirmDropLayout.setVisibility(View.GONE);
                        dropOffLocationLayout.setVisibility(View.GONE);


                        if (polyline != null) {
                            polyline.remove();
                            polyline=null;
                            points = null;
                            lineOptions = null;
                            markerPoints.clear();
                        }

                        try {
                            parserTask.cancel(true);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        break;

                    case 2:

                        confirmDropLayout.setVisibility(View.VISIBLE);
                        finalConfirmLayout.setVisibility(View.GONE);
                        finalRequirementsLayout.setVisibility(View.GONE);
                        finalConfirmLayout.setVisibility(View.GONE);
                        dDropLocationName.setText(pickDropLocationNames.get(1));
                        pickDropLocationNames.remove(1);
                        sharedPreferences.edit().putString("promoCode", "null").apply();
                        pickUpPin.setVisibility(View.VISIBLE);
                        dropOffPin.setVisibility(View.GONE);
                        mapFragment.getView().setVisibility(View.VISIBLE);

                        removeDropMarker();
                        removeStopMarker();


                        if (polyline != null) {
                            polyline.remove();
                            polyline=null;
                            points = null;
                            lineOptions = null;
                            markerPoints.clear();
                        }

                        try {
                            parserTask.cancel(true);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        break;


                    case 3:

                        pickDropLocationNames.remove(2);
                        pickDropLocationNames.remove(1);
                        progressBar.setVisibility(View.GONE);
                        dropOffPin.setVisibility(View.VISIBLE);
                        confirmDropBtn.setClickable(true);
                        moveMapto(dropMarker.getPosition());
                        removeDropMarker();
                        removeStopMarker();

                        dropOffLocationLayout.setVisibility(View.VISIBLE);
                        confirmDropLayout.setVisibility(View.VISIBLE);
                        finalRequirementsLayout.setVisibility(View.GONE);
                        finalConfirmLayout.setVisibility(View.GONE);


//                        promoCodeLayout.setHint(getResources().getString(R.string.promo));
//                        inputPromoCode.clearFocus();
//                        inputPromoCode.setText("");


//                        estBill.setTextColor(getResources().getColor(R.color.colorDarkGray));
//                        estBill.setBackground(null);

//                        afterCouponRate.setVisibility(View.GONE);


                        if (polyline != null) {
                            polyline.remove();
                            polyline=null;
                            points = null;
                            lineOptions = null;
                            markerPoints.clear();
                        }


                        try {
                            parserTask.cancel(true);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        break;


                }
            }

        }



    private void showExitAppDialog() {


        try {
            AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
            builderSingle.setTitle(R.string.exit_app);

            builderSingle.setNegativeButton(MainActivity.this.getResources()
                    .getString(R.string.cancel), (DialogInterface dialogInterface, int which) -> {
                dialogInterface.dismiss();
            });





            builderSingle.setPositiveButton(MainActivity.this.getResources().getString(R.string.ok),
                    (DialogInterface dialogInterface, int which) -> {
                        super.onBackPressed();
                    });

            builderSingle.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void removePickMarker() {

           pickMarker.remove();

        }


    private void removeDropMarker() {

        dropMarker.remove();

    }

    private void removeStopMarker() {

            if(stopMarker!=null){
                stopMarker.remove();
                STOP_LOCATION = null;
                stopObject = null;
            }


    }





        private void openDialog(final int option) {
            View view = getLayoutInflater().inflate(R.layout.item_bottom_sheet, null);
            dialog = new BottomSheetDialog(this);
            dialog.setContentView(view);
            final TextView details = (TextView) view.findViewById(R.id.inputDetails);

            if (option == 1) {
                details.setHint(getResources().getString(R.string.add_pickup_details));
                String detailsText = sharedPreferences.getString("details", "null");

                if (!detailsText.equals("null")) {
                    details.setText(detailsText);
                }

            } else if (option == 2) {
                details.setHint(getResources().getString(R.string.input_promo));
                String promoCode = sharedPreferences.getString("promoCode", "null");

                if (!promoCode.equals("null")) {
                    details.setText(promoCode);
                }
            }


            Button okay = view.findViewById(R.id.okay);
            okay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (option == 1) {
                        if (!details.getText().toString().trim().isEmpty())
                            sharedPreferences.edit().putString("details", details.getText().toString()).apply();
                    } else if (option == 2) {


                    }
                    dialog.dismiss();


                }
            });


            dialog.show();
        }


        private void startPlacePickerActivity(int requestCode) {

            Intent intent = new Intent(MainActivity.this, SearchLocationActivity.class);
            intent.putExtra(IntentKeys.LATITUDE, Double.toString(CURRENT_LOCATION.latitude));
            intent.putExtra(IntentKeys.LONGITUDE,Double.toString(CURRENT_LOCATION.longitude));
            startActivityForResult(intent, requestCode);
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            try {
                switch (requestCode) {


                    case PICK_PLACE_PICKER_ACTIVITY:
                        if (data != null) {
                            pickAddressText.setText(data.getExtras().getString(IntentKeys.ADDRESS_NAME));
                            double lattitude, longitude;
                            lattitude = Double.parseDouble(data.getExtras().getString(IntentKeys.LATITUDE));
                            longitude = Double.parseDouble(data.getExtras().getString(IntentKeys.LONGITUDE));

                            PICK_LOCATION = new LatLng(lattitude, longitude);
                            moveMapto(PICK_LOCATION);

                        }
                        break;


                    case DROP_PLACE_PICKER_ACTIVITY:
                        if (data != null) {
                            dDropLocationName.setText(data.getExtras().getString(IntentKeys.ADDRESS_NAME));
                            double lattitude, longitude;
                            lattitude = Double.parseDouble(data.getExtras().getString(IntentKeys.LATITUDE));
                            longitude = Double.parseDouble(data.getExtras().getString(IntentKeys.LONGITUDE));

                            DROP_LOCATION = new LatLng(lattitude, longitude);
                            moveMapto(DROP_LOCATION);

                        }
                        break;



                    case REQUEST_CHECK_SETTINGS:
                        if(resultCode == RESULT_OK) {
                            showCurrentLocation();
                            isNetworkAvailable();

                        }else {
                            Toast.makeText(MainActivity.this, getResources().getString(R.string.gps_required),Toast.LENGTH_LONG).show();
                            checkSettings();
                        }
                          break;


                    case ADD_STOP_ACTIVITY:
                        if (data != null) {
                            stopLocationName.setVisibility(View.VISIBLE);
                            stopLocation = data.getExtras().getString(IntentKeys.ADDRESS_NAME);
                            stopLocationName.setText(data.getExtras().getString(IntentKeys.ADDRESS_NAME));
                            double lattitude, longitude;
                            lattitude = Double.parseDouble(data.getExtras().getString(IntentKeys.LATITUDE));
                            longitude = Double.parseDouble(data.getExtras().getString(IntentKeys.LONGITUDE));

                            STOP_LOCATION = new LatLng(lattitude, longitude);

                        }
                        break;



                }
            } catch (Exception e) {
                //   Snackbar.make(drawerLayout, "" + e.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        }


        public void moveMapto(LatLng location) {

            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(location,17);
//                    .target(new LatLng(location.latitude, location.longitude))
//                    .zoom(17)
//                    .build();

            mGoogleMap.animateCamera(cameraUpdate);

//            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }


        private void createDropLocationObject() {

            if(!type.equals("cancel")) {

                try {
                    dropObject = new JSONObject();
                    dropObject.put("stop",0);
                    dropObject.put("drop_latitude", "" + dropMarker.getPosition().latitude);
                    dropObject.put("drop_longitude", "" + dropMarker.getPosition().longitude);
                    dropObject.put("drop_location", "" + dDropLocationName.getText().toString());
                    dropObject.put("status","1");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }else {

                try {
                    dropObject = new JSONObject();
                    dropObject.put("stop",0);
                    dropObject.put("drop_latitude", "" + dropLat);
                    dropObject.put("drop_longitude", "" + dropLon);
                    dropObject.put("drop_location", "" + dropLoc);
                    dropObject.put("status","1");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }



    private void createStopLocationObject() {

            try {
                stopObject = new JSONObject();
                stopObject.put("stop",1);
                stopObject.put("drop_latitude", "" + STOP_LOCATION.latitude);
                stopObject.put("drop_longitude", "" + STOP_LOCATION.longitude);
                stopObject.put("drop_location", "" + stopLocation);
                stopObject.put("status","1");
            } catch (JSONException e) {
                e.printStackTrace();
            }


    }




        private void createPickLocationObject() {
            if(!type.equals("cancel")) {
                try {
                    pickObject = new JSONObject();
                    pickObject.put("latitude", "" + pickMarker.getPosition().latitude);
                    pickObject.put("longitude", "" + pickMarker.getPosition().longitude);
                    pickObject.put("placeName", "" + pickAddressText.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                try {
                    pickObject = new JSONObject();
                    pickObject.put("latitude", "" +pickLat);
                    pickObject.put("longitude", "" + pickLon);
                    pickObject.put("placeName", "" + picLoc);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }


        private void callRideNowApi() {


            try {
                if(stopObject== null)
                   viewModel.rideNow(pickObject.toString(), dropObject.toString(),"");
                else
                    viewModel.rideNow(pickObject.toString(), dropObject.toString(),stopObject.toString());

                pickDropLocationNames.add("Checkout");

            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        private void initViewModel() {
            viewModel = ViewModelProviders.of(MainActivity.this).get(MainActivityViewModel.class);
        }


        @Override
        protected void onStart() {
            super.onStart();
            LocalBroadcastManager.getInstance(getApplicationContext())
                    .registerReceiver(broadcastReceiver,
                            new IntentFilter(CheckOutService.RIDE_NOW_MESSAGE));

            LocalBroadcastManager.getInstance(getApplicationContext())
                    .registerReceiver(broadcastReceiver,
                            new IntentFilter(GetCurrentLocationName.PLACE_SEARCH_MESSAGE));

            LocalBroadcastManager.getInstance(getApplicationContext())
                    .registerReceiver(broadcastReceiver,
                            new IntentFilter(ConfirmRideService.CONFIRM_RIDE_MESSAGE));

            LocalBroadcastManager.getInstance(getApplicationContext())
                    .registerReceiver(broadcastReceiver,
                            new IntentFilter(CheckServiceAvailabilityService.CHECK_SERVICE_AVAILABILTY_MESSAGE));

            LocalBroadcastManager.getInstance(getApplicationContext())
                    .registerReceiver(broadcastReceiver,
                            new IntentFilter(ApplyPromoCodeServcie.PROMO_MESSAGE));

            LocalBroadcastManager.getInstance(getApplicationContext())
                    .registerReceiver(broadcastReceiver,
                            new IntentFilter(CheckActiveRideService.CHECK_ACTIVE_RIDE_MESSAGE));


        }

        @Override
        protected void onStop() {
            super.onStop();
            LocalBroadcastManager.getInstance(getApplicationContext())
                    .unregisterReceiver(broadcastReceiver);
        }




        private void confirmRideApi() {

            try {
                viewModel.confirmBooking(modelCheckOut.getData().getId());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    private void getAllMarkers() {

        if (markerPoints.size() >= 10) {
            return;
        }
        LatLng point;
        point = new LatLng(Double.parseDouble(modelCheckOut.getData().getPickup_latitude()),
                Double.parseDouble(modelCheckOut.getData().getPickup_longitude()));
        markerPoints.add(point);


//


        point = new LatLng(Double.parseDouble(modelCheckOut.getData().getDrop_latitude()),
                Double.parseDouble(modelCheckOut.getData().getDrop_longitude()));
        markerPoints.add(point);


        try {
            for (int i = 0; i < modelCheckOut.getData().getWaypoints().size(); i++) {

                createMarker(Double.parseDouble(modelCheckOut.getData().getWaypoints().get(i).getDrop_latitude()),
                        Double.parseDouble(modelCheckOut.getData().getWaypoints().get(i).getDrop_longitude()),
                        modelCheckOut.getData().getWaypoints().get(i).getDrop_location());

                point = new LatLng(Double.parseDouble(modelCheckOut.getData().getWaypoints().get(i).getDrop_latitude()),
                        Double.parseDouble(modelCheckOut.getData().getWaypoints().get(i).getDrop_longitude()));

                markerPoints.add(point);
            }

            Log.d("**** MarksSize==>", "" + markerPoints.size());
        }catch (Exception e){

        }

        drawpathMethod();


    }
//
    private void drawpathMethod() {

        if (markerPoints.size() >= 2) {
            LatLng origin = markerPoints.get(0);
            LatLng dest = markerPoints.get(1);

            // Getting URL to the Google Directions API
            String url = getDirectionsUrl(origin, dest);


            Log.e("******Url===>", "" + url);
            DownloadTask downloadTask = new DownloadTask();

            // Start downloading json data from Google Directions API
            downloadTask.execute(url);
        }

    }


    private String getDirectionsUrl(LatLng origin, LatLng dest) {


        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Waypoints
        String waypoints = "";
        for (int i = 2; i < markerPoints.size(); i++) {
            LatLng point = (LatLng) markerPoints.get(i);
            if (i == 2)
                waypoints = "waypoints=";
            waypoints += point.latitude + "," + point.longitude + "|";
        }

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + waypoints;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"
                + output + "?" + parameters + "&key="
                + BaseConfig.MAP_KEY;

        return url;
    }

//
    protected Marker createMarker(double latitude, double longitude, String address) {
        View marker = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.main_marker_layout, null);

        // Toast.makeText(mainActivity, "Marker", Toast.LENGTH_SHORT).show();
        stopMarker = mGoogleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .title(address)
//                .anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(this, marker))));
        return stopMarker;
    }
//
//
    public static Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }
//
//
    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service

            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {


            super.onPostExecute(result);


             parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }


    @SuppressLint("LongLogTag")
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception while downloading url", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }


    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {


            try {
                 points = null;
                  lineOptions = null;

                // Traversing through all the routes
                for (int i = 0; i < result.size(); i++) {
                    points = new ArrayList<LatLng>();
                    lineOptions = new PolylineOptions();

                    // Fetching i-th route
                    List<HashMap<String, String>> path = result.get(i);

                    // Fetching all the points in i-th route
                    for (int j = 0; j < path.size(); j++) {
                        HashMap<String, String> point = path.get(j);

                        double lat = Double.parseDouble(point.get("lat"));
                        double lng = Double.parseDouble(point.get("lng"));
                        LatLng position = new LatLng(lat, lng);

                        points.add(position);
                    }

                    // Adding all the points in the route to LineOptions
                    lineOptions.addAll(points);
                    lineOptions.width(8);
                    lineOptions.color(Color.BLACK);
                }

                // Drawing polyline in the Google Map for the i-th route


//               polyline = mGoogleMap.addPolyline(lineOptions);

                if (points.size() != 0)
                    polyline =   mGoogleMap.addPolyline(lineOptions);

                try {
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    for (int i = 0; i < points.size(); i++) {
                        builder.include(points.get(i));
                    }

                    LatLngBounds bounds = builder.build();
                    Point displaySize = new Point();
                    getWindowManager().getDefaultDisplay().getSize(displaySize);
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, displaySize.x, 670, 100));
                    progressBar.setVisibility(View.GONE);

                } catch (Exception e) {
//                    Toast.makeText(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);


                }
                plottingPoints = false;
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                plottingPoints = false;


            }


        }


    }



    public void checkSettings(){
        try {
            mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
                    .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                        @Override
                        public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                            //toast("All location settings are satisfied.");
                            showCurrentLocation();
                            isNetworkAvailable();


                        }
                    })
                    .addOnFailureListener(this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            int statusCode = ((ApiException) e).getStatusCode();
                            switch (statusCode) {
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                    Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                            "location settings ");
                                    try {
                                        ResolvableApiException rae = (ResolvableApiException) e;
                                        rae.startResolutionForResult(MainActivity.this,
                                                REQUEST_CHECK_SETTINGS);
                                    } catch (IntentSender.SendIntentException sie) {
                                        Log.i(TAG, "PendingIntent unable to execute request.");
                                    }
                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                    String errorMessage = getResources().getString(R.string.not_all_requirements_given);
                                    Log.e(TAG, errorMessage);
                                    Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                                case LocationSettingsStatusCodes.DEVELOPER_ERROR:
                                    Log.e(TAG, "DEVELOPER_ERROR");
                            }
                        }
                    });

        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }






    private void setMyLocationButtonPosition() {
        View locationButton = ((View) mapView.findViewById(Integer.parseInt("1"))
                .getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        // position on right bottom
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.CENTER_VERTICAL);
//        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        rlp.setMargins(0, 0, 36, 0);
    }



    private void createDriverMarker(String lat, String lon, String bearing) {
        // Creating a marker

        Marker tempMarker;

        MarkerOptions markerOptions = new MarkerOptions();

        // Setting the position for the marker
        markerOptions.position(new LatLng(Double.parseDouble(lat),
                Double.parseDouble(lon)));

        // Setting the title for the marker.
        // This will be displayed on taping the marker


        Bitmap icon = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.car);

        markerOptions.icon(BitmapDescriptorFactory.fromBitmap
                (Bitmap.createScaledBitmap(icon, 60,60, false)));


        tempMarker = mGoogleMap.addMarker(markerOptions);
        tempMarker.setRotation(Float.parseFloat(bearing));

        driverMarker.add(tempMarker);


    }









    // Vibrate for 150 milliseconds
    private void shakeItBaby() {
        if (Build.VERSION.SDK_INT >= 26) {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE))
                    .vibrate(VibrationEffect.createOneShot(250, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(250);
        }
    }






    public void showErrorConfirmRideDialog(String message){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_error_booking, null);

        TextView dialogMessage = dialogView.findViewById(R.id.dialogMessage);
        Button dialogPositiveBtn = dialogView.findViewById(R.id.dialogPositiveBtn);

        dialogMessage.setText(message);

        dialogPositiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        );
            }
        });


        dialogBuilder.setView(dialogView);


        AlertDialog alertDialog = dialogBuilder.create();
        ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
        InsetDrawable inset = new InsetDrawable(back, 20);
        alertDialog.getWindow().setBackgroundDrawable(inset);
        alertDialog.setCancelable(false);
        alertDialog.show();



    }



    private void showActiveRideDialog(String type, int id) {

        new AlertDialog.Builder(MainActivity.this)
                .setTitle(getResources().getString(R.string.ride_active))
                .setMessage(getResources().getString(R.string.go_to_ride))

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        if(type.equals("1")) {
                            startActivity(new Intent(getApplicationContext(), DriverWaitingActivity.class)
                                      .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                      .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                      .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                                      .putExtra(IntentKeys.TYPE,"1")
                                      .putExtra(IntentKeys.BOOKING_ID,""+id)
                              );
                        }else if(type.equals("2")){
                            startActivity(new Intent(getApplicationContext(), ReceiptActivity.class)
                                      .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                      .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                      .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                      .putExtra("" + IntentKeys.BOOKING_ID,
                                              "" + id));
                        }
                    }

                })

                .setIcon(android.R.drawable.ic_dialog_alert)
                .setCancelable(false)
                .show();


    }




}
