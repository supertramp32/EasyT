package com.seshra.everestcab.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.seshra.everestcab.BuildConfig;
import com.seshra.everestcab.MainActivity;
import com.seshra.everestcab.R;
import com.seshra.everestcab.models.ModelEditProfile;
import com.seshra.everestcab.service.EditProfileService;
import com.seshra.everestcab.utils.FileCompressor;
import com.seshra.everestcab.utils.SessionManager;
import com.seshra.everestcab.utils.SingletonGson;
import com.seshra.everestcab.viewmodel.ProfileActivityViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    SessionManager sessionManager;
    TextView username, email;
    ImageView userImage;
    TextView phone;

    TextView totalTrips, walletBalance;


    ProfileActivityViewModel viewModel;


    CardView editProfile;

    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sessionManager = new SessionManager(this);

        toolbar = findViewById(R.id.toolbarProfile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initViews();
        initViewModel();


        populateViews();

    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(ProfileActivity.this).get(ProfileActivityViewModel.class);

    }


    private void initViews() {


        editProfile = findViewById(R.id.cardEditProfile);

        username = findViewById(R.id.profileUserName);
        phone = findViewById(R.id.profilePhone);
        email= findViewById(R.id.profileEmail);
        userImage = findViewById(R.id.profileUserImage);

        totalTrips = findViewById(R.id.profileTotalRides);
        walletBalance = findViewById(R.id.profileTotalBalance);


        editProfile.setOnClickListener(this);

    }

    private void populateViews() {



        username.setText(sessionManager.getUserDetails().get(SessionManager.USER_FIRST_NAME)+" "
                +sessionManager.getUserDetails().get(SessionManager.USER_LAST_NAME));


        String userImg = sessionManager.getUserDetails().get(SessionManager.USER_IMAGE);
        Log.d("UserImage:",userImg);

        Glide.with(ProfileActivity.this).load(sessionManager.getUserDetails().get(SessionManager.USER_IMAGE))
                .into(userImage);

        email.setText(sessionManager.getUserDetails().get(SessionManager.USER_EMAIL));

        phone.setText(sessionManager.getUserDetails().get(SessionManager.USER_PHONE));

        totalTrips.setText(sessionManager.getUserDetails().get(SessionManager.TOTAL_TRIPS));
        walletBalance.setText(sessionManager.getUserDetails().get(SessionManager.WALLET_BALANCE));





    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.cardEditProfile:
               startActivity(new Intent(ProfileActivity.this, EditProfileActivity.class));
                break;



        }


    }









}

