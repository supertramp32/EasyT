package com.seshra.everestcab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.seshra.everestcab.models.ModelAutoCancel;
import com.seshra.everestcab.models.ModelCancelReasons;
import com.seshra.everestcab.models.ModelConfirm;
import com.seshra.everestcab.models.ModelResultCheck;
import com.seshra.everestcab.models.ModelTackRide;
import com.seshra.everestcab.models.TempRideInfo;
import com.seshra.everestcab.service.CallRideInfoService;
import com.seshra.everestcab.service.CancelRideService;
import com.seshra.everestcab.service.CheckStatusService;
import com.seshra.everestcab.service.FetchCancelReasons;
import com.seshra.everestcab.service.TrackRideService;
import com.seshra.everestcab.utils.BaseConfig;
import com.seshra.everestcab.utils.DirectionsJSONParser;
import com.seshra.everestcab.utils.IntentKeys;
import com.seshra.everestcab.utils.STATUS;
import com.seshra.everestcab.utils.SessionManager;
import com.seshra.everestcab.utils.SingletonGson;
import com.seshra.everestcab.viewmodel.DriverWaitingActivityViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

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


public class DriverWaitingActivity extends AppCompatActivity implements OnMapReadyCallback {

    private BottomSheetBehavior sheetBehavior;
    private ConstraintLayout bottom_sheet;
    TextView pickLocation, dropLocation, paymentType, paymentAmount;

    SupportMapFragment mapFragment;
    GoogleMap mGoogleMap;

    SessionManager sessionManager;

    ArrayList<LatLng> markerPoints = new ArrayList<LatLng>();
    Polyline polyline;


    String pickLat , pickLon, dropLat, dropLon, picLoc, dropLoc;

    Boolean infoPresent = false;


//    ModelConfirm modelConfirm;

    Marker driverMarker;


    private FusedLocationProviderClient mFusedLocationClient;


    Runnable mCheckBookingRunnable;
    Handler mCheckBookingHandler = new Handler();


    Runnable autoCancelRunnable;
    Handler autoCancelHandler = new Handler();

    Runnable trackingRunnable;
    Handler trackingHandler = new Handler();


    Marker pickMarker, dropMarker;




    //    ModelRideInfo modelRideInfo;
    TempRideInfo tempRideInfo;

    Button cancelRideBtn;


    DriverWaitingActivityViewModel viewModel;
    ShimmerFrameLayout shimmerFrameLayout;
//    ShimmerFrameLayout shareRideShimmer;

    View driverLayout;

//    TextView cancelRide;


    static String bookingId;

    ImageView  emergency;

    ModelAutoCancel checkBookingStatusModel;


    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String intentName = intent.getAction();
            switch (intentName) {

                case CheckStatusService.CHECK_BOOKING_STATUS_MESSAGE:
                try {
                    String result = intent.getStringExtra(CheckStatusService.CHECK_BOOKING_STATUS_KEY);
                    if (!result.equals("0")) {


                         checkBookingStatusModel =
                                SingletonGson.getInstance().fromJson("" + result, ModelAutoCancel.class);

                        if (Integer.parseInt(checkBookingStatusModel.getData().getBooking_status()) == STATUS.ACCEPTED) {
                            try {

                                if(!infoPresent)
                                    viewModel.callRideInfoApi("" + checkBookingStatusModel.getData().getBooking_id());

                                setStatusAccordingToRideInfo(getResources().getString(R.string.ride_accepted));

                                cancelRideBtn.setVisibility(View.VISIBLE);


                                //start tracking driver
                                autoCancelHandler.removeCallbacksAndMessages(null);

                                startTracking();

                                if(!infoPresent) {
//                                    shakeItBaby();
                                    viewModel.callRideInfoApi("" + checkBookingStatusModel.getData().getBooking_id());;
                                }

                            } catch (Exception e) {
                                e.printStackTrace();

                            }


                        }else if (Integer.parseInt(checkBookingStatusModel.getData().getBooking_status()) == STATUS.ARRIVED) {

                            if(autoCancelHandler!=null)
                                     autoCancelHandler.removeCallbacksAndMessages(null);


                            if(!infoPresent)
                                viewModel.callRideInfoApi("" + checkBookingStatusModel.getData().getBooking_id());

                            setStatusAccordingToRideInfo(getResources().getString(R.string.driver_arrived));
//                            shakeItBaby();
                            cancelRideBtn.setVisibility(View.GONE);

                            trackingHandler.removeCallbacksAndMessages(null);


                        }else if (Integer.parseInt(checkBookingStatusModel.getData().getBooking_status()) == STATUS.STARTED) {

                                viewModel.callRideInfoApi("" + checkBookingStatusModel.getData().getBooking_id());

//                            shareRideShimmer.stopShimmer();
//                            shareRideShimmer.setVisibility(View.GONE);
                            setStatusAccordingToRideInfo(getResources().getString(R.string.ride_started));
//                            shakeItBaby();
                            shareDetails.setVisibility(View.VISIBLE);
                            cancelRideBtn.setVisibility(View.GONE);


                            autoCancelHandler.removeCallbacksAndMessages(null);
                            trackingHandler.removeCallbacksAndMessages(null);

//                            autoCancelHandler.removeCallbacks(autoCancelRunnable);
//                            trackingHandler.removeCallbacks(trackingRunnable);



                        }else if (Integer.parseInt(checkBookingStatusModel.getData().getBooking_status()) == STATUS.END) {


                            cancelRideBtn.setVisibility(View.GONE);
//                            mCheckBookingHandler.removeCallbacksAndMessages(mCheckBookingRunnable);
//                            autoCancelHandler.removeCallbacksAndMessages(autoCancelRunnable);
//                            trackingHandler.removeCallbacksAndMessages(trackingRunnable);

//                            shakeItBaby();


                            removeAllCalbacks();

                            startActivity(new Intent(getApplicationContext(), ReceiptActivity.class)
                                    .putExtra(IntentKeys.BOOKING_ID,bookingId)
                            );
                            finish();



                        }else if(Integer.parseInt(checkBookingStatusModel.getData().getBooking_status())
                                == STATUS.DRIVER_CANCELLED){

                            removeAllCalbacks();
//                            shakeItBaby();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();

                            Toast.makeText(getApplicationContext(),R.string.driver_cancelled_request,Toast.LENGTH_LONG).show();


                        }else if (Integer.parseInt(checkBookingStatusModel.getData().getBooking_status())
                                == STATUS.USER_AUTO_CANCALLED) {


                            removeAllCalbacks();

                            startActivity(new Intent(DriverWaitingActivity.this, MainActivity.class));
                            finish();



                        }else if (Integer.parseInt(checkBookingStatusModel.getData().getBooking_status())
                                == STATUS.USER_CONCELLED) {


                            removeAllCalbacks();
//                            shakeItBaby();

                            startActivity(new Intent(DriverWaitingActivity.this, MainActivity.class));
                            finish();



                        }



                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
                break;



                case CallRideInfoService.RIDE_INFO_MESSAGE:

                    try {
                        String result = intent.getStringExtra(CallRideInfoService.RIDE_INFO_MESSAGE_KEY);
                        if (!result.equals("0")) {
                            tempRideInfo = SingletonGson.getInstance().fromJson("" + result, TempRideInfo.class);

                            if (tempRideInfo.getResult().equals("1")) {
//                              startTracking();

                                infoPresent = true;
                                setViewAccordingToRideInfo(tempRideInfo);
                            }


                        }
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                    break;




                case FetchCancelReasons.CANCEL_REASON_MESSAGE:

                    try {
                        String result = intent.getStringExtra(FetchCancelReasons.CANCEL_REASON_MESSAGE_KEY);
                        if (!result.equals("0")) {


                            progressBar.setVisibility(View.GONE);

                            ModelCancelReasons modelCancelReasons = SingletonGson.getInstance()
                                    .fromJson("" + result, ModelCancelReasons.class);
                            AlertDialog.Builder builderSingle = new AlertDialog.Builder(DriverWaitingActivity.this);
                            builderSingle.setTitle(getString(R.string.cancellation_fee_first)
                                    + " " + ""
                                    + modelCancelReasons.getCode() +
                                    " " + modelCancelReasons.getCancel_charges() + " "
                                    + getString(R.string.will_be_applied_second));

                            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(DriverWaitingActivity.this,
                                    android.R.layout.select_dialog_singlechoice);

                            for (int i = 0; i < modelCancelReasons.getData().size(); i++) {
                                arrayAdapter.add("" + modelCancelReasons.getData().get(i).getReason());
                            }
                            builderSingle.setNegativeButton(DriverWaitingActivity.this.getResources()
                                    .getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    cancelRideBtn.setClickable(true);
                                }
                            });

                            builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {

                                        shakeItBaby();
                                        viewModel.cancelRide(tempRideInfo.getData().getId(),
                                                modelCancelReasons.getData().get(which).getId(),
                                                modelCancelReasons.getCancel_charges());

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        // Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                                    }
                                    dialog.dismiss();
                                }
                            });


                            if (modelCancelReasons.getCancel_charges().equals("0")) {

                                builderSingle.setTitle("");

                            }

                            builderSingle.setCancelable(false);

                            builderSingle.show();


                        }else {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_cancel_ride), Toast.LENGTH_LONG).show();

                        }
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                    break;



                case TrackRideService.TRACKING_MESSAGE:
                    String result = intent.getStringExtra(TrackRideService.TRACKING_KEY);
                    if (!result.equals("0")){
                        ModelTackRide modelTackRide = SingletonGson.getInstance()
                                .fromJson("" + result, ModelTackRide.class);
                        if (modelTackRide.getData()!=null){
                            startTrackingDriver(modelTackRide);
                        }
                    }


                    break;


                case CancelRideService.CANCEL_RIDE_MESSAGE:

                    try {
                        String cancelMessage = intent.getStringExtra(CancelRideService.CANCEL_RIDE_MESSAGE_KEY);
                        if(!cancelMessage.equals("0")) {
                            ModelResultCheck modelResultCheck = SingletonGson.getInstance().fromJson("" + cancelMessage,
                                    ModelResultCheck.class);
                            if(modelResultCheck.getResult().equals("1")) {

                                mCheckBookingHandler.removeCallbacksAndMessages(null);
                                autoCancelHandler.removeCallbacksAndMessages(null);
                                trackingHandler.removeCallbacksAndMessages(null);

                                progressBar.setVisibility(View.GONE);

                                startActivity(new Intent(getApplicationContext(), MainActivity.class)
                                .putExtra(IntentKeys.PICK_LATITUDE, pickLat )
                                .putExtra(IntentKeys.PICK_LONGITUDE,pickLon)
                                .putExtra(IntentKeys.DROP_LATITUDE,dropLat)
                                        .putExtra(IntentKeys.DROP_LONGITUDE,dropLon)
                                .putExtra(IntentKeys.PICK_LOCATION,picLoc)
                                .putExtra(IntentKeys.DROP_LOCATION,dropLoc));

                                finish();

                                    Toast.makeText(getApplicationContext(), modelResultCheck.getMessage(),
                                            Toast.LENGTH_LONG).show();
                                }else {
                                cancelRideBtn.setClickable(true);
                                progressBar.setVisibility(View.GONE);

                                Toast.makeText(getApplicationContext(), modelResultCheck.getMessage(),
                                        Toast.LENGTH_LONG).show();

                                }

                        }

                    } catch(Exception e){
                        e.printStackTrace();
                        progressBar.setVisibility(View.GONE);

                    }
                    break;




            }





        }
    };




    ImageView driverImage;
    TextView driverName,driverRating,driverCarDetails,driverStatusAccepted, driverStatusStarted;

    Button contactDriver,shareDetails;
    View rideProgressView;


    CardView cardDriverDetails;
    TextView driverSearchText, driverCarModel;

    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_waiting);

        bottom_sheet = findViewById(R.id.bottom_sheet_driver);
        sheetBehavior = BottomSheetBehavior.from(bottom_sheet);
        paymentType = findViewById(R.id.paymentTypeName);
        paymentAmount = findViewById(R.id.paymentAmount);

        progressBar = findViewById(R.id.progressBar);

        driverSearchText = findViewById(R.id.driverSearchText);

        driverLayout = findViewById(R.id.driverLayout);
        driverName = findViewById(R.id.driverName);
        driverRating = findViewById(R.id.driverRating);
        driverImage = findViewById(R.id.driverImg);
        driverCarDetails = findViewById(R.id.driverCarDetails);
        driverCarModel = findViewById(R.id.driverCarModel);
        contactDriver = findViewById(R.id.driverWaitCall);
        shareDetails = findViewById(R.id.driverWaitShare);
        pickLocation = findViewById(R.id.driverSearchPickLocation);
        dropLocation = findViewById(R.id.driverSearchDropLocation);
        driverStatusAccepted = findViewById(R.id.driverStatusAccepted);
        driverStatusStarted = findViewById(R.id.driverStatusArrived);

        rideProgressView = findViewById(R.id.rideProgressView);

        cancelRideBtn = findViewById(R.id.driverWaitCancel);

//        cancelRideCard.setVisibility(View.GONE);

//        contactUs = findViewById(R.id.driverWaitingContactUs);
        emergency = findViewById(R.id.driverWaitingSos);

        shimmerFrameLayout = findViewById(R.id.shimmer_view_container);
//        shareRideShimmer = findViewById(R.id.shimmerShare);
        driverLayout = findViewById(R.id.driverLayout);

        cardDriverDetails = findViewById(R.id.cardDriverDetails);
        sessionManager = new SessionManager(this);

        initViewModel();


        bookingId = getIntent().getStringExtra(IntentKeys.BOOKING_ID);



        try{

            if(bookingId!=null) {

                sessionManager.saveTempBookingID(bookingId);
//                callRideInfoAPI(bookingId);
//                checkBookingStatus(bookingId);

            }


//            }


        }catch (Exception e){
            e.printStackTrace();
        }



        try{
            String script = getIntent().getStringExtra(IntentKeys.BOOKING_CONFIRM_MODEL);
            ModelConfirm modelConfirm = SingletonGson.getInstance()
                    .fromJson("" + script, ModelConfirm.class);
            initViews(modelConfirm);

            pickLat = modelConfirm.getData().getPickup_latitude();
            pickLon = modelConfirm.getData().getPickup_longitude();
            dropLat = modelConfirm.getData().getDrop_latitude();
            dropLon = modelConfirm.getData().getDrop_longitude();
            picLoc = modelConfirm.getData().getPickup_location();
            dropLoc = modelConfirm.getData().getDrop_location();



        }catch (Exception e){
            e.printStackTrace();
        }






        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_frag_driver);

        mapFragment.getMapAsync(this);



//        try{
//
//            Intent intent = getIntent();
//            String script = intent.getStringExtra("extra");
//            modelConfirm = SingletonGson.getInstance().fromJson("" + script, ModelConfirm.class);
//
//            bookingId = ""+modelConfirm.getData().getId();
//
//
////            initViews();
//
//            checkBookingStatus( "" + modelConfirm.getData().getId());
//
//
//
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }




        cancelRideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shakeItBaby();
                cancelRideBtn.setClickable(false);
                try {

                    progressBar.setVisibility(View.VISIBLE);

                    if(checkBookingStatusModel!=null) {
                        if (Integer.parseInt(checkBookingStatusModel.getData().getBooking_status()) == STATUS.ACCEPTED
                                || Integer.parseInt(checkBookingStatusModel.getData().getBooking_status()) == STATUS.ARRIVED
                                || Integer.parseInt(checkBookingStatusModel.getData().getBooking_status()) == STATUS.STARTED) {


                            viewModel.fetchCancelReasons(bookingId);

                        } else {
                            viewModel.customCancelRide(bookingId);
                        }
                    }else {
                        viewModel.customCancelRide(bookingId);

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });






        // callback for do something
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
//                        btn_bottom_sheet.setText("Close Sheet");
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
//                        btn_bottom_sheet.setText("Expand Sheet");
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });


    }

    private void initViews(ModelConfirm modelConfirm) {
        pickLocation.setText(modelConfirm.getData().getPickup_location());
        dropLocation.setText(modelConfirm.getData().getDrop_location());
        paymentType.setText(modelConfirm.getData().getPayment_method().getPayment_method());
        paymentAmount.setText("Est. "+"Rs."+modelConfirm.getData().getEstimate_bill());

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMaxZoomPreference(20);



        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        if (location != null) {


                            CameraPosition cameraPosition = new CameraPosition.Builder()
                                    .target(new LatLng(location.getLatitude(), location.getLongitude()))
                                    .zoom(17)
                                    .build();


                            mGoogleMap.setMyLocationEnabled(true);

//                                setPositionOfGoogleBtn();

                            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                        }
                    }
                });


    }



    private void checkBookingStatus (String bookingId) {


        mCheckBookingRunnable = () -> {
            try {

                viewModel.checkBookingStatus(bookingId);
                mCheckBookingHandler.postDelayed(mCheckBookingRunnable, 5000);


            } catch (Exception e) {

                e.printStackTrace();

            }
        };
        mCheckBookingHandler.postDelayed(mCheckBookingRunnable, 0);




    }



    private void checkAutoCancelStatus(String bookingId){

        autoCancelRunnable = () -> {
            try {

                viewModel.checkAutoCancel(bookingId);


            } catch (Exception e) {

                e.printStackTrace();

            }
        };
        autoCancelHandler.postDelayed(autoCancelRunnable, 60000);


    }






    private void initViewModel() {
        viewModel = ViewModelProviders.of(DriverWaitingActivity.this).get(DriverWaitingActivityViewModel.class);
    }



    public void startTracking(){

        trackingRunnable = () -> {
            try {

                viewModel.startTracking(Integer.parseInt(bookingId));
                trackingHandler.postDelayed(trackingRunnable, 5000);


            } catch (Exception e) {

                e.printStackTrace();

            }
        };
        trackingHandler.postDelayed(trackingRunnable, 5000);

    }


    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(broadcastReceiver,
                        new IntentFilter(CheckStatusService.CHECK_BOOKING_STATUS_MESSAGE));


        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(broadcastReceiver,
                        new IntentFilter(CallRideInfoService.RIDE_INFO_MESSAGE));


        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(broadcastReceiver,
                        new IntentFilter(FetchCancelReasons.CANCEL_REASON_MESSAGE));

        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(broadcastReceiver,
                        new IntentFilter(TrackRideService.TRACKING_MESSAGE));

        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(broadcastReceiver,
                        new IntentFilter(CancelRideService.CANCEL_RIDE_MESSAGE));


    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getApplicationContext())
                .unregisterReceiver(broadcastReceiver);
//        LocalBroadcastManager.getInstance(getApplicationContext())
//                .unregisterReceiver(broadcastReceiver1);

    }


    private void callRideInfoAPI(String bookingId) {
        try {

          viewModel.callRideInfoApi(bookingId);
        } catch (Exception e) {
//            ApporioLog.logE("" + TAG, "" + e.getMessage());

            e.printStackTrace();
        }
    }




    private void setViewAccordingToRideInfo(TempRideInfo modelRideInfo) throws Exception {



        if (modelRideInfo.getData().getBooking_status() == STATUS.USER_CONCELLED ||
                modelRideInfo.getData().getBooking_status() == STATUS.DRIVER_CANCELLED ||
                modelRideInfo.getData().getBooking_status() == STATUS.ADMIN_CANCELLED) {

                removeAllCalbacks();

            showAlertDialog(getResources().getString(R.string.booking_cancel));
        }





        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
        driverLayout.setVisibility(View.VISIBLE);


        cardDriverDetails.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                cardDriverDetails.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                sheetBehavior.setPeekHeight(cardDriverDetails.getHeight());
                //height is ready
            }
        });






        if(polyline == null) {
            if (!modelRideInfo.getData().getDrop_longitude().equals("")) {
                getAllMarkers(modelRideInfo);
            }
        }







        Glide.with(this).load(modelRideInfo.getData().getDriver_details().getProfile_image()).into(driverImage);
        driverName.setText("" + modelRideInfo.getData().getDriver_details().getFullName());

        driverCarDetails.setText(getResources().getString(R.string.vehicle_number) + modelRideInfo.getData().getVehicle_details().getVehicle_number());
        driverCarModel.setText(""+modelRideInfo.getData().getVehicle_details().getVehicle_model());
        driverRating.setText("" + modelRideInfo.getData().getDriver_details().getRating());



        pickLocation.setText(""+modelRideInfo.getData().getPickup_location());
        dropLocation.setText(""+modelRideInfo.getData().getDrop_location());
        paymentType.setText(""+modelRideInfo.getData().getPayment_method().getPayment_method());
        paymentAmount.setText("Est. "+"Rs."+modelRideInfo.getData().getEstimate_bill());

        removeDriverInMap();

        showDriverInMap(modelRideInfo);

//        if(carBitmap!=null)
//            showDriverInMap(modelRideInfo);
//        else {
//            new DownloadTaxiImage(DriverWaitingActivity.this).execute(modelRideInfo);
//        }


        emergency.setVisibility(View.VISIBLE);






        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                        AlertDialog.Builder builderSingle = new AlertDialog.Builder(DriverWaitingActivity.this);
                        builderSingle.setTitle(getString(R.string.emergency));

                        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(DriverWaitingActivity.this,
                                android.R.layout.select_dialog_singlechoice);

                        for (int i = 0; i < modelRideInfo.getData().getSos().size(); i++) {
                            arrayAdapter.add("" + modelRideInfo.getData().getSos().get(i).getName());
                        }
                        builderSingle.setNegativeButton(DriverWaitingActivity.this.getResources()
                                .getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {

                                    startCallIntent(modelRideInfo.getData().getSos().get(which).getNumber());


                                } catch (Exception e) {
                                    e.printStackTrace();
                                    // Snackbar.make(root, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                                }
                                dialog.dismiss();
                            }
                        });




                        builderSingle.show();

                } catch(Exception e){
                    e.printStackTrace();
                }


            }



        });



        contactDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startCallIntent(modelRideInfo.getData().getDriver_details().getPhoneNumber());
            }
        });


        shareDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT,
                            "" + modelRideInfo.getData().getShare_able_link());
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(DriverWaitingActivity.this,getString(R.string.error_sharing_details),
                            Toast.LENGTH_LONG).show();

                }
            }
        });








    }

    private void removeDriverInMap() {
        if(driverMarker!=null)
         driverMarker.remove();
    }

    private void showDriverInMap(TempRideInfo modelRideInfo) {

        createDriverMarker(modelRideInfo.getData().getMovable_marker().getDriver_marker_lat(),
                modelRideInfo.getData().getMovable_marker().getDriver_marker_long(),
                modelRideInfo.getData().getMovable_marker().getDriver_marker_bearing());
    }


    private void startTrackingDriver(ModelTackRide modelTackRide) {
        removeDriverInMap();
        createDriverMarker(modelTackRide.getData().getMovable_marker_type().getDriver_marker_lat(),
                modelTackRide.getData().getMovable_marker_type().getDriver_marker_long(),
                modelTackRide.getData().getMovable_marker_type().getDriver_marker_bearing());

    }


    private void setStatusAccordingToRideInfo(String status) {

        switch (status){
            case "Accepted":
                driverSearchText.setText(getString(R.string.driver_found_text));
                driverSearchText.setTextColor(getResources().getColor(R.color.colorDarkFont));
                driverStatusAccepted.setVisibility(View.VISIBLE);
                driverStatusStarted.setVisibility(View.GONE);
                contactDriver.setBackground(getResources().getDrawable(R.drawable.oval_background_button));
                contactDriver.setEnabled(true);
                cancelRideBtn.setEnabled(true);
                break;


            case "Arrived":
                driverSearchText.setText(getString(R.string.driver_is_arrived));
                driverSearchText.setTextColor(getResources().getColor(R.color.colorDarkFont));
                driverStatusAccepted.setVisibility(View.VISIBLE);
                driverStatusStarted.setVisibility(View.GONE);
                contactDriver.setEnabled(true);
                contactDriver.setBackground(getResources().getDrawable(R.drawable.oval_background_button));
                cancelRideBtn.setVisibility(View.GONE);
                shareDetails.setVisibility(View.VISIBLE);
                break;


            case "Started":
                driverSearchText.setText(getString(R.string.ride_is_started));
                driverSearchText.setTextColor(getResources().getColor(R.color.colorDarkFont));
                driverStatusAccepted.setVisibility(View.GONE);
                driverStatusStarted.setVisibility(View.VISIBLE);
                contactDriver.setEnabled(true);
                shareDetails.setEnabled(true);
                shareDetails.setVisibility(View.VISIBLE);
                contactDriver.setBackground(getResources().getDrawable(R.drawable.oval_background_button));
                shareDetails.setBackground(getResources().getDrawable(R.drawable.oval_background_button));
                rideProgressView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                break;

        }

//        driverStatus.setText("");
//        driverStatus.setText(status);



    }


    private void showAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(DriverWaitingActivity.this);
        builder.setCancelable(false);
        builder.setTitle(R.string.alert);
        builder.setMessage(message)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(DriverWaitingActivity.this,MainActivity.class));
                        DriverWaitingActivity.this.finish();
                    }
                });
        builder.create().show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        bookingId = sessionManager.getTempBookingID();

        try{


            if(!bookingId.equals("null")) {
//                callRideInfoAPI(bookingId);
                cancelRideBtn.setVisibility(View.VISIBLE);
                checkBookingStatus(bookingId);
                checkAutoCancelStatus(bookingId);
            }


        }catch (Exception e){
            e.printStackTrace();
        }



    }





    private void startCallIntent(String phone) {

        Intent dialIntent = new Intent();
        dialIntent.setAction(Intent.ACTION_DIAL);
        dialIntent.setData(Uri.parse("tel:" + phone));
        startActivity(dialIntent);
    }


    private void createDriverMarker(String lat, String lon, String bearing) {
        // Creating a marker


        MarkerOptions markerOptions = new MarkerOptions();

        // Setting the position for the marker
        markerOptions.position(new LatLng(Double.parseDouble(lat),
                Double.parseDouble(lon)));

        // Setting the title for the marker.
        // This will be displayed on taping the marker


        Bitmap icon = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.car);

//        getResizedBitmap(pickMarkerIcon,32,60);
//
//        profileImage.setImageBitmap(Bitmap.createScaledBitmap(b, 120, 120, false));




        markerOptions.icon(BitmapDescriptorFactory.fromBitmap
                (Bitmap.createScaledBitmap(icon, 80,80, false)));

        driverMarker = mGoogleMap.addMarker(markerOptions);
        driverMarker.setRotation(Float.parseFloat(bearing));



    }






    private void createPickUpMarker(String lat, String lon,String title) {
        // Creating a marker
        MarkerOptions markerOptions = new MarkerOptions();

        // Setting the position for the marker
        markerOptions.position(new LatLng(Double.parseDouble(lat), Double.parseDouble(lon)));

        // Setting the title for the marker.
        // This will be displayed on taping the marker
        markerOptions.title(title);

        Bitmap pickMarkerIcon = BitmapFactory.decodeResource(getResources(),
                R.drawable.pick_marker_down);

        Bitmap resized = Bitmap.createScaledBitmap(pickMarkerIcon, 64, 124, true);


        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(resized));


        pickMarker = mGoogleMap.addMarker(markerOptions);


    }


    private void createDropMarker(String lat, String lon,String title) {

        // Creating a marker
        MarkerOptions markerOptions = new MarkerOptions();

        // Setting the position for the marker
        markerOptions.position(new LatLng(Double.parseDouble(lat), Double.parseDouble(lon)));

        // Setting the title for the marker.
        // This will be displayed on taping the marker
        markerOptions.title(title);

        Bitmap dropMarkerIcon = BitmapFactory.decodeResource(getResources(),
                R.drawable.drop_marker_down);

        Bitmap resized = Bitmap.createScaledBitmap(dropMarkerIcon, 64, 124, true);


        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(resized));

//        // Placing a marker on the touched position
        dropMarker = mGoogleMap.addMarker(markerOptions);



    }



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
                + output + "?" + parameters + "&key=" + BaseConfig.MAP_KEY;

        return url;
    }


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


           ParserTask parserTask = new ParserTask();

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
                ArrayList<LatLng> points = null;
                PolylineOptions lineOptions = null;

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
                    lineOptions.width(6);
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

                } catch (Exception e) {
                    Toast.makeText(DriverWaitingActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            } catch (Exception e) {
                Toast.makeText(DriverWaitingActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }


    }


    private void getAllMarkers(TempRideInfo modelRideInfo) {

        if (markerPoints.size() >= 10) {
            return;
        }
        LatLng point;
        point = new LatLng(Double.parseDouble(modelRideInfo.getData().getPickup_latitude()),
                Double.parseDouble(modelRideInfo.getData().getPickup_longitude()));
        markerPoints.add(point);

        createPickUpMarker(modelRideInfo.getData().getPickup_latitude(),modelRideInfo.getData().getPickup_longitude(),
                modelRideInfo.getData().getPickup_location());




        createDropMarker(modelRideInfo.getData().getDrop_latitude(),modelRideInfo.getData().getDrop_longitude(),
                modelRideInfo.getData().getDrop_location());




        point = new LatLng(Double.parseDouble(modelRideInfo.getData().getDrop_latitude()),
                Double.parseDouble(modelRideInfo.getData().getDrop_longitude()));
        markerPoints.add(point);



        drawpathMethod();


    }


    @Override
    protected void onPause() {
        super.onPause();


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        sessionManager.clearTempBookingID();
    }



    public void removeAllCalbacks(){


        mCheckBookingHandler.removeCallbacksAndMessages(null);
        trackingHandler.removeCallbacksAndMessages(null);
        autoCancelHandler.removeCallbacksAndMessages(null);


    }


    @Override
    public void onBackPressed() {

        showExitAppDialog();
    }


    private void showExitAppDialog() {


        try {
            AlertDialog.Builder builderSingle = new AlertDialog.Builder(DriverWaitingActivity.this);
            builderSingle.setTitle(R.string.exit_app);

            builderSingle.setNegativeButton(DriverWaitingActivity.this.getResources()
                    .getString(R.string.cancel), (DialogInterface dialogInterface, int which) -> {
                dialogInterface.dismiss();
            });





            builderSingle.setPositiveButton(DriverWaitingActivity.this.getResources().getString(R.string.ok),
                    (DialogInterface dialogInterface, int which) -> {
                        super.onBackPressed();
                    });

            builderSingle.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

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
}
