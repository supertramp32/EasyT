package com.seshra.everestcab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.seshra.everestcab.service.CheckStopLocationService;
import com.seshra.everestcab.service.GetCurrentLocationName;
import com.seshra.everestcab.utils.IntentKeys;
import com.seshra.everestcab.utils.LocationUtils;
import com.seshra.everestcab.viewmodel.AddStopActivityViewModel;

import java.util.List;
import java.util.Locale;

public class AddStopActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "AddStopActivity:";
    SupportMapFragment mapFragment;
    GoogleMap mGoogleMap;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 11;

    private FusedLocationProviderClient mFusedLocationClient;


    private SettingsClient mSettingsClient;
    private static final int REQUEST_CHECK_SETTINGS = 0x1;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationRequest mLocationRequest;

    AddStopActivityViewModel viewModel;

    ImageView pickUpPin;

    Location targetLocation;

    TextView stopLocationName;
    Button confirmStopPoint;

    String confirmLat, confirmLong, confirmAddress;

    ProgressBar progressBar;


    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            String intentName = intent.getAction();
            switch (intentName) {

                case GetCurrentLocationName.PLACE_SEARCH_MESSAGE:

                    try {
                        String placeResult = intent.getStringExtra(GetCurrentLocationName.PLACE_SEARCH_KEY);
                        if (!placeResult.equals("0")) {


                                confirmAddress = placeResult;
                                stopLocationName.setText(placeResult);


                        } else {

                            try {
                                new GetAddressTask(getApplicationContext()).execute(targetLocation);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;


                case CheckStopLocationService.CHECK_STOP_LOCATION_MESSAGE:

                    progressBar.setVisibility(View.GONE);

                    try {
                        String result = intent.getStringExtra(CheckStopLocationService.CHECK_STOP_LOCATION_MESSAGE_KEY);
                        if (!result.equals("0")) {

                            finalizeActivity(confirmAddress,confirmLat,confirmLong);

                        } else {

                            confirmStopPoint.setClickable(true);
                            Toast.makeText(AddStopActivity.this,getResources().getString(R.string.error_occured),
                                    Toast.LENGTH_LONG).show();

                        }
                    } catch (Exception e) {
                        confirmStopPoint.setClickable(true);
                        Toast.makeText(AddStopActivity.this,getResources().getString(R.string.error_occured),
                                Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                    break;



            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stop);


        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_frag_add_stop);

        mapFragment.getMapAsync(this);

        pickUpPin = findViewById(R.id.stopPointPin);
        stopLocationName = findViewById(R.id.stopPointName);
        confirmStopPoint = findViewById(R.id.confirmStopPoint);
        progressBar = findViewById(R.id.progressBarStopPoint);

        viewModel = ViewModelProviders.of(this).get(AddStopActivityViewModel.class);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mSettingsClient = LocationServices.getSettingsClient(this);
        createLocationRequest();
        buildLocationSettingsRequest();


        confirmStopPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewModel.checkStopLocation(confirmLat,confirmLong);
                confirmStopPoint.setClickable(false);
                progressBar.setVisibility(View.VISIBLE);
            }
        });


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMaxZoomPreference(20);

        //Check For Location Permission
        if (hasRuntimePermissions()) {


            checkSettings();


        } else {
            requestRuntimePermissions();
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
                                            AddStopActivity.this,
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
                    AddStopActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }


    public void checkSettings() {
        try {
            mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
                    .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                        @Override
                        public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                            //toast("All location settings are satisfied.");
                            showCurrentLocation();


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
                                        rae.startResolutionForResult(AddStopActivity.this,
                                                REQUEST_CHECK_SETTINGS);
                                    } catch (IntentSender.SendIntentException sie) {
                                        Log.i(TAG, "PendingIntent unable to execute request.");
                                    }
                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                    String errorMessage = getResources().getString(R.string.not_all_requirements_given);
                                    Log.e(TAG, errorMessage);
                                    Toast.makeText(AddStopActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                                case LocationSettingsStatusCodes.DEVELOPER_ERROR:
                                    Log.e(TAG, "DEVELOPER_ERROR");
                            }
                        }
                    });

        } catch (SecurityException e) {
            e.printStackTrace();
        }
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


    public void showCurrentLocation() {


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        if (location != null) {


                            confirmLat = ""+location.getLatitude();
                            confirmLong = "" + location.getLongitude();

                            viewModel.getCurrentLocationName(location.getLatitude(), location.getLongitude());

//                            new GetAddressTask(getApplicationContext()).execute(location);

                            CameraPosition cameraPosition = new CameraPosition.Builder()
                                    .target(new LatLng(location.getLatitude(), location.getLongitude()))
                                    .zoom(17)
                                    .build();


                            mGoogleMap.setMyLocationEnabled(true);

//                                setPositionOfGoogleBtn();

                            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                        } else {

                            CameraPosition cameraPosition = new CameraPosition.Builder()
                                    .target(new LatLng(27.6850439, 85.345278))
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


            }
        });


        mGoogleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {


                pickUpPin.setBackground(getResources().getDrawable(R.drawable.pick_marker_down));




                targetLocation = new Location("");//provider name is unnecessary
                targetLocation.setLatitude(mGoogleMap.getCameraPosition().target.latitude);//your coords of course
                targetLocation.setLongitude(mGoogleMap.getCameraPosition().target.longitude);


                confirmLat = ""+targetLocation.getLatitude();
                confirmLong = "" + targetLocation.getLongitude();


                viewModel.getCurrentLocationName(targetLocation.getLatitude(),
                            targetLocation.getLongitude());







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

                    stopLocationName.setText(address);

                confirmAddress = address;
                confirmLat = Double.toString(loc.getLatitude());
                confirmLong = Double.toString(loc.getLongitude());



            } else {

            }
        }

    }


    @Override
    protected void onStart() {
        super.onStart();


        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(broadcastReceiver,
                        new IntentFilter(GetCurrentLocationName.PLACE_SEARCH_MESSAGE));

        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(broadcastReceiver,
                        new IntentFilter(CheckStopLocationService.CHECK_STOP_LOCATION_MESSAGE));



    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getApplicationContext())
                .unregisterReceiver(broadcastReceiver);
    }



    private void finalizeActivity(String addressdescription, String latitude, String longitude) {
        Intent intent = new Intent();
        intent.putExtra("" + IntentKeys.ADDRESS_NAME, "" + addressdescription);
        intent.putExtra("" + IntentKeys.LATITUDE, latitude);
        intent.putExtra("" + IntentKeys.LONGITUDE, longitude);
        setResult(Activity.RESULT_OK, intent);
        AddStopActivity.this.finish();
    }



}