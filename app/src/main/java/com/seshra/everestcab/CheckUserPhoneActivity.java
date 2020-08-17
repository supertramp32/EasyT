package com.seshra.everestcab;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.seshra.everestcab.models.ModelCheckPhoneNumber;
import com.seshra.everestcab.service.CheckUserPhoneService;
import com.seshra.everestcab.utils.IntentKeys;
import com.seshra.everestcab.utils.SingletonGson;
import com.seshra.everestcab.viewmodel.CheckUserPhoneActivityViewModel;

public class CheckUserPhoneActivity extends AppCompatActivity implements View.OnClickListener{

    EditText inputPhone;
    Button checkPhone;


    TextView  infoText;

    ImageView backBtn;

    CheckUserPhoneActivityViewModel viewModel;

    ProgressBar progressBar;

    String type ="1";


    ImageView taxiRunning;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String intentName = intent.getAction();
            switch (intentName) {

                case CheckUserPhoneService.CHECK_PHONE_MESSAGE:

                String result = intent.getStringExtra(CheckUserPhoneService.CHECK_MESSAGE_KEY);
                if (!result.equals("0")) {
                    progressBar.setVisibility(View.GONE);
                    ModelCheckPhoneNumber modelResultCheck = SingletonGson.getInstance()
                            .fromJson("" + result, ModelCheckPhoneNumber.class);

                    if (modelResultCheck.getResult().equals("1")) {
                        if (type.equals("1")) {

                            Intent mainIntent = new Intent(getApplicationContext(), EnterPasswordActivity.class);
                            mainIntent.putExtra(IntentKeys.PHONE, inputPhone.getText().toString().trim());
                            mainIntent.putExtra(IntentKeys.TYPE, type);
                            startActivity(mainIntent);

                        } else if (type.equals("2")) {

                            Intent otpIntent = new Intent(getApplicationContext(), OTPActivity.class);
                            otpIntent.putExtra(IntentKeys.PHONE, inputPhone.getText().toString().trim());
                            otpIntent.putExtra(IntentKeys.TYPE, type);
                            startActivity(otpIntent);

                        }
                    } else if (modelResultCheck.getResult().equals("2")) {
                        Toast.makeText(CheckUserPhoneActivity.this, modelResultCheck.getMessage(),
                                Toast.LENGTH_LONG).show();

                    } else {
                        Intent mainIntent = new Intent(getApplicationContext(), OTPActivity.class);
                        mainIntent.putExtra(IntentKeys.PHONE, inputPhone.getText().toString().trim());
                        mainIntent.putExtra(IntentKeys.TYPE, type);
//                        viewModel.getOtp(inputPhone.getText().toString(), type);
                        startActivity(mainIntent);
                    }


                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(CheckUserPhoneActivity.this, getResources().getString(R.string.check_internet), Toast.LENGTH_LONG).show();
                }

                break;





            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_check_user_phone);

        taxiRunning = findViewById(R.id.taxiRunning);



        inputPhone = findViewById(R.id.inputUserPhone);
        checkPhone = findViewById(R.id.checkPhoneBtn);
        backBtn = findViewById(R.id.backBtn);
        progressBar = findViewById(R.id.progressBar);
        infoText = findViewById(R.id.enterMobileTextNext);

        try {
            type = getIntent().getStringExtra(IntentKeys.TYPE);
            if(type==null){
                type = "1";
            }


            if(type.equals("2")) {
                infoText.setText(getResources().getString(R.string.re_enter_phone));
                checkPhone.setText(getResources().getString(R.string.request_otp));

                inputPhone.setText(getIntent().getStringExtra(IntentKeys.PHONE));
            }



        }catch (Exception e){
            e.printStackTrace();
        }



        initViewModel();



        checkPhone.setOnClickListener(this);
        backBtn.setOnClickListener(this);

    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(CheckUserPhoneActivity.this).get(CheckUserPhoneActivityViewModel.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.backBtn:
                onBackPressed();
                break;

            case R.id.checkPhoneBtn:
                if(validateFields()) {
                    progressBar.setVisibility(View.VISIBLE);
                    viewModel.checkUserPhone(inputPhone.getText().toString().trim());
                }
                break;


        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(broadcastReceiver,
                        new IntentFilter(CheckUserPhoneService.CHECK_PHONE_MESSAGE));



    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getApplicationContext())
                .unregisterReceiver(broadcastReceiver);
    }



    public  boolean validateFields(){

        if(inputPhone.getText().toString().trim().isEmpty() || inputPhone.getText().toString().trim().length()!=10){
            inputPhone.setError(getResources().getString(R.string.enter_valid_number));
            inputPhone.requestFocus();
            return false;
        }

        return true;

    }
}
