package com.seshra.everestcab;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.seshra.everestcab.utils.SessionManager;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;

public class AboutUsActivity extends AppCompatActivity implements OnMapReadyCallback {


    private MapView mapView;
    private GoogleMap gMap;
    private Toolbar toolbar;

    TextView contactEmail, contactNumber, contactAddress;
    CoordinatorLayout rootView;
    TextView contactAboutUs;

    SessionManager sessionManager;



    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        toolbar=findViewById(R.id.toolbar_contact_us);


        sessionManager = new SessionManager(this);

        contactEmail = findViewById(R.id.contactEmail);
        rootView = findViewById(R.id.root);
        contactNumber = findViewById(R.id.contact_number);
        contactAddress = findViewById(R.id.address);
        contactAboutUs = findViewById(R.id.contactAboutUs);


        try {

            contactEmail.setText(sessionManager.getAppConfig().getData().getCustomer_support().getMail());
            contactNumber.setText(sessionManager.getAppConfig().getData().getCustomer_support().getPhone());
            contactAddress.setText(sessionManager.getAppConfig().getData().getCustomer_support().getAddress());
            contactAboutUs.setText(Html.fromHtml(sessionManager.getAppConfig().getData().getCustomer_support().getAbout_us()));

        }catch (Exception e){
            e.printStackTrace();
        }
//        contactAddress.setText(sessionManager.getAppConfig().getData().getCustomer_support().get);



        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }
        mapView = findViewById(R.id.map_view);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);


        contactEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto",
                            "everestCab@gmail.com" ,null ));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "" + AboutUsActivity.this.getResources().getString(R.string.report_issue));
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "");
                    startActivity(Intent.createChooser(emailIntent, "" + AboutUsActivity.this.getResources().getString(R.string.send_email)));
                    emailIntent.setType("text/plain");
                } catch (Exception e) {
                    Snackbar.make(rootView, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        contactNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dialIntent = new Intent();
                dialIntent.setAction(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:" + contactNumber.getText().toString()));
                startActivity(dialIntent);
            }
        });

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
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setMinZoomPreference(10.0f);
        gMap.setMaxZoomPreference(16.0f);
        UiSettings uiSettings = gMap.getUiSettings();
        uiSettings.setTiltGesturesEnabled(false);
        LatLng kathmandu = new LatLng(27.6831314,85.3479499);
        gMap.addMarker(new MarkerOptions().position(kathmandu).title(getString(R.string.labe_easy_taxi)));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(kathmandu, 15);
        gMap.animateCamera(cameraUpdate);
    }
}
