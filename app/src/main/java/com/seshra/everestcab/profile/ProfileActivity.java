package com.seshra.everestcab.profile;

import androidx.appcompat.app.AppCompatActivity;
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
//    ImageView backBtn, save;

    boolean firstLoad = true;

//    Spinner genderSpinner;

//    BottomSheetDialog dialog;
//
//    static final int REQUEST_TAKE_PHOTO = 1;
//    static final int REQUEST_GALLERY_PHOTO = 2;
//
//    File mPhotoFile;
//    FileCompressor mCompressor;
//    private String mCurrentPhotoPath;
//    private Uri mImageUri;

//    RadioGroup genderGroup;
//    RadioButton gender;

    TextView totalTrips, walletBalance;

//    ProgressBar progressBar;



    ProfileActivityViewModel viewModel;

//    int type=0;

    CardView editProfile;



//    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//
//            String intentName = intent.getAction();
//            switch (intentName) {
//
//                case EditProfileService.PROFILE_EDIT_MESSAGE:
//
//                    String result = intent.getStringExtra(EditProfileService.PROFILE_EDIT_MESSAGE_KEY);
//                    if (!result.equals("0")) {
////                        progressBar.setVisibility(View.GONE);
//
//                        ModelEditProfile modelEditProfile = SingletonGson.getInstance()
//                                .fromJson("" + result, ModelEditProfile.class);
//
//
//                            if (modelEditProfile.getResult().equals("1")) {
//                                if(!firstLoad) {
//                                    startActivity(new Intent(ProfileActivity.this, MainActivity.class));
//                                    Toast.makeText(ProfileActivity.this, modelEditProfile.getMessage(), Toast.LENGTH_LONG).show();
//                                }else {
//                                    firstLoad = false;
//                                    populateViews();
//                                }
//
//                            } else {
//
////                                save.setClickable(true);
//
//                                Toast.makeText(ProfileActivity.this, modelEditProfile.getMessage(), Toast.LENGTH_LONG).show();
//
//                            }
//
//
//
//
//
//
//
//                    } else {
////                        progressBar.setVisibility(View.GONE);
////                        save.setClickable(true);
//                        Toast.makeText(ProfileActivity.this, getResources().getString(R.string.error), Toast.LENGTH_LONG).show();
//                    }
//
//                    break;
//
//
//
//
//
//            }
//        }
//    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sessionManager = new SessionManager(this);

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
//        backBtn = findViewById(R.id.profileBackBtn);
//        save = findViewById(R.id.profileSaveBtn);
//        genderGroup = findViewById(R.id.genderGroup);
        totalTrips = findViewById(R.id.profileTotalRides);
        walletBalance = findViewById(R.id.profileTotalBalance);
//        progressBar = findViewById(R.id.progressBar);
//        genderSpinner = findViewById(R.id.profileGenderSpinner);


//        //Creating the ArrayAdapter instance having the country list
//        ArrayAdapter aa = new ArrayAdapter(ProfileActivity.this,android.R.layout.simple_spinner_item, gender);
//        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        //Setting the ArrayAdapter data on the Spinner
//        genderSpinner.setAdapter(aa);

//
//        backBtn.setOnClickListener(this);
//        save.setOnClickListener(this);
//        mCompressor = new FileCompressor(this);
//        userImage.setOnClickListener(this);

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

//        int selectedId = genderGroup.getCheckedRadioButtonId();
//        gender = findViewById(selectedId);

//
//        if(firstLoad)
//            viewModel.editProfile(username.getText().toString(),email.getText().toString(),
//                "Male","",
//                0);




    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.cardEditProfile:
               startActivity(new Intent(ProfileActivity.this, EditProfileActivity.class));
                break;

//
//            case R.id.profileSaveBtn:
//
//
//                save.setClickable(false);
//
//                if(validateFields()){
//
//
//                    progressBar.setVisibility(View.VISIBLE);
//
//
////                    int selectedId = genderGroup.getCheckedRadioButtonId();
////                    gender = findViewById(selectedId);
//
//
////
//                    if(mImageUri==null)
//                    viewModel.editProfile(username.getText().toString().trim(),
//                            email.getText().toString(),
//                            "Male","",type);
//                    else
//                        viewModel.editProfile(username.getText().toString(),
//                                email.getText().toString(),
//                                "Male",mImageUri.toString(),type);
//
//                    findViewById(R.id.root).requestFocus();
//
//                    hideKeyboard(ProfileActivity.this);
//
//                }
//                break;


//            case R.id.profileUserImage:
//
//                openDialog();
//                break;


        }


    }







//    private void openDialog() {
//        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_camera, null);
//        dialog = new BottomSheetDialog(this);
//        dialog.setContentView(view);
//        TextView camera_sel = (TextView) view.findViewById(R.id.camera);
//        TextView gallery_sel = (TextView) view.findViewById(R.id.gallery);
//        camera_sel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                requestStoragePermission(true);
//                dialog.dismiss();
//            }
//        });
//        gallery_sel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                requestStoragePermission(false);
//                dialog.dismiss();
//            }
//        });
//        dialog.show();
//    }



//    /**
//     * Requesting multiple permissions (storage and camera) at once
//     * This uses multiple permission model from dexter
//     * On permanent denial opens settings dialog
//     */
//    private void
//    requestStoragePermission(boolean isCamera) {
//        Dexter.withActivity(this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
//                .withListener(new MultiplePermissionsListener() {
//                    @Override
//                    public void onPermissionsChecked(MultiplePermissionsReport report) {
//                        // check if all permissions are granted
//                        if (report.areAllPermissionsGranted()) {
//                            if (isCamera) {
//                                dispatchTakePictureIntent();
//                            } else {
//                                dispatchGalleryIntent();
//                            }
//                        }
//                        // check for permanent denial of any permission
//                        if (report.isAnyPermissionPermanentlyDenied()) {
//                            // show alert dialog navigating to Settings
//                            showSettingsDialog();
//                        }
//                    }
//
//                    @Override
//                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
//                        token.continuePermissionRequest();
//                    }
//                }).withErrorListener(error -> Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_occured), Toast.LENGTH_SHORT).show())
//                .onSameThread()
//                .check();
//    }
//
//
//    /**
//     * Showing Alert Dialog with Settings option
//     * Navigates user to app settings
//     * NOTE: Keep proper title and message depending on your app
//     */
//    private void showSettingsDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle(getResources().getString(R.string.permission_needed));
//        builder.setMessage(getResources().getString(R.string.permission_reason));
//        builder.setPositiveButton(getResources().getString(R.string.goto_settings), (dialog, which) -> {
//            dialog.cancel();
//            openSettings();
//        });
//        builder.setNegativeButton(getResources().getString(R.string.cancel), (dialog, which) -> dialog.cancel());
//        builder.show();
//
//    }
//
//
//    // navigating user to app settings
//    private void openSettings() {
//        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//        Uri uri = Uri.fromParts("package", getPackageName(), null);
//        intent.setData(uri);
//        startActivityForResult(intent, 101);
//    }
//
//
//    private void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // Ensure that there's a camera activity to handle the intent
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            // Create the File where the photo should go
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            } catch (IOException ex) {
//                // Error occurred while creating the File
//            }
//            // Continue only if the File was successfully created
//            if (photoFile != null) {
//
//                Uri photoURI;
//
////                // N is for Nougat Api 24 Android 7
////                if (Build.VERSION_CODES.N <= android.os.Build.VERSION.SDK_INT) {
//                // FileProvider required for Android 7.  Sending a file URI throws exception.
//                photoURI = FileProvider.getUriForFile(this,
//                        BuildConfig.APPLICATION_ID + ".provider",
//                        photoFile);
//                mPhotoFile = photoFile;
//
////                } else {
////                    // For older devices:
////                    // Samsung Galaxy Tab 7" 2 (Samsung GT-P3113 Android 4.2.2, API 17)
////                    // Samsung S3
////                    photoURI = Uri.fromFile(photoFile);
////                    mPhotoFile = photoFile;
////                }
//
//
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
//            }
//        }
//    }
//
//
//
//    /**
//     * Select image fro gallery
//     */
//    private void dispatchGalleryIntent() {
//        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        pickPhoto.addFlags(FLAG_GRANT_READ_URI_PERMISSION);
//        startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO);
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == RESULT_OK) {
//            if (requestCode == REQUEST_TAKE_PHOTO) {
//
//                try {
//                    mPhotoFile = mCompressor.compressToFile(mPhotoFile);
//                    mImageUri = Uri.parse(mPhotoFile.getAbsolutePath());
//                    type = 1;
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                populateImageFromCamera(mPhotoFile);
//
//
//
//
//
//            } else if (requestCode == REQUEST_GALLERY_PHOTO) {
//                mImageUri = data.getData();
//                type = 2;
//                populateImageFromGallery(mImageUri);
//
//
//            }
//        }
//    }
//
//
//    private File createImageFile() throws IOException {
//        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String mFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File mFile = File.createTempFile(mFileName, ".jpg", storageDir);
////        File image = File.createTempFile(
////                imageFileName,  /* prefix */
////                ".jpg",         /* suffix */
////                storageDir      /* directory */
////        );
//
//        // Save a file: path for use with ACTION_VIEW intents
//        mCurrentPhotoPath = mFile.getAbsolutePath();
//
//        return mFile;
//    }
//
//
//    private void populateImageFromCamera(File mPhotoFile) {
//
//            Glide.with(ProfileActivity.this).load(mPhotoFile.getAbsolutePath())
//                    .apply(new RequestOptions().placeholder(R.drawable.user))
//                    .into(userImage);
//
//
//
//
//    }
//
//    private void populateImageFromGallery(Uri mImageUri) {
//
//
//            Glide.with(ProfileActivity.this).load(mImageUri)
//                    .apply
//                            (new RequestOptions().placeholder(R.drawable.user))
//                    .into(userImage);
//
//    }
//
//


//    @Override
//    protected void onStart() {
//        super.onStart();
//        LocalBroadcastManager.getInstance(getApplicationContext())
//                .registerReceiver(broadcastReceiver,
//                        new IntentFilter(EditProfileService.PROFILE_EDIT_MESSAGE));
//
//
//
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        LocalBroadcastManager.getInstance(getApplicationContext())
//                .unregisterReceiver(broadcastReceiver);
//    }





}

