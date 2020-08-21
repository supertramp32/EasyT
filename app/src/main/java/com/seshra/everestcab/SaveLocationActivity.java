package com.seshra.everestcab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.seshra.everestcab.models.ModelResultCheck;
import com.seshra.everestcab.service.AddFavouriteLocation;
import com.seshra.everestcab.utils.SingletonGson;
import com.seshra.everestcab.viewmodel.SaveLocationActivityViewModel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class SaveLocationActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    ImageView closeBtn;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    private MapView mapView;
    private GoogleMap gMap;

    String locationName;
    double lat, lon;
    LatLng current;
    Button saveLocation;

    TextView locName;

    EditText saveLocationTitle;

    SaveLocationActivityViewModel viewModel;

    ProgressBar progressBar;




    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String result = intent.getStringExtra(AddFavouriteLocation.FAVOURITE_MESSAGE_KEY);
            if(!result.equals("0")) {
                progressBar.setVisibility(View.GONE);
                ModelResultCheck modelResultCheck = SingletonGson.getInstance().fromJson("" + result, ModelResultCheck.class);

                if (modelResultCheck.getResult().equals("1")){
                    Toast.makeText(SaveLocationActivity.this, getResources().getString(R.string.added_succesfully),Toast.LENGTH_LONG).show();
                    startActivity(new Intent(SaveLocationActivity.this,MainActivity.class));
                }else {
                    Toast.makeText(SaveLocationActivity.this, modelResultCheck.getMessage(),Toast.LENGTH_LONG).show();

                }



            }else {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(SaveLocationActivity.this,getResources().getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show();
            }


        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_location);

        closeBtn = findViewById(R.id.closeBtn);
        locName = findViewById(R.id.saveLocationName);
        saveLocationTitle = findViewById(R.id.saveLocationTitle);
        saveLocation = findViewById(R.id.saveLocationBtn);
        progressBar = findViewById(R.id.progressBar);


        initViewModel();

        try {
            Intent intent = getIntent();
            locationName = intent.getStringExtra("name");
            lat = intent.getDoubleExtra("lat",0);
            lon = intent.getDoubleExtra("lon",0);
            current = new LatLng(lat,lon);
            locName.setText(locationName);

        }catch (Exception e){
            e.printStackTrace();
        }

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }
        mapView = findViewById(R.id.map_view);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);



        closeBtn.setOnClickListener(this);



        saveLocation.setOnClickListener(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setMinZoomPreference(10.0f);
        gMap.setMaxZoomPreference(16.0f);
        UiSettings uiSettings = gMap.getUiSettings();
        uiSettings.setTiltGesturesEnabled(false);
        gMap.addMarker(new MarkerOptions().position(current).title(locationName));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(current, 05);
        gMap.animateCamera(cameraUpdate);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.closeBtn:
                onBackPressed();
                break;

            case R.id.saveLocationBtn:
                if(!saveLocationTitle.getText().toString().trim().isEmpty()) {
                    progressBar.setVisibility(View.VISIBLE);
                    viewModel.saveLocation(lat, lon, locationName, saveLocationTitle.getText().toString());
                }
                else{
                    saveLocationTitle.setError("");

                }
                break;


        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }
        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(broadcastReceiver,
                        new IntentFilter(AddFavouriteLocation.FAVOURITE_MESSAGE));

    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
        LocalBroadcastManager.getInstance(getApplicationContext())
                .unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    private void initViewModel() {
        viewModel = ViewModelProviders.of(SaveLocationActivity.this).get(SaveLocationActivityViewModel.class);
    }



}
