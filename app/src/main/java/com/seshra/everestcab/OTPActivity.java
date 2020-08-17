package com.seshra.everestcab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.seshra.everestcab.models.OTPModel;
import com.seshra.everestcab.service.GetOTPService;
import com.seshra.everestcab.utils.IntentKeys;
import com.seshra.everestcab.utils.OTPEditText;
import com.seshra.everestcab.utils.SingletonGson;

public class OTPActivity extends AppCompatActivity implements View.OnClickListener {


    OTPEditText otpEditText;
    TextView otpText, changePhone;

    ImageView back;
    Button checkOtp;
    ProgressBar progressBar;

    String phone,type;

    OTPModel otpModel;
    String otp;


    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String intentName = intent.getAction();
            switch (intentName) {

                case GetOTPService.OTP_MESSAGE:

                    String result = intent.getStringExtra(GetOTPService.OTP_KEY);
                    if (!result.equals("0")) {
                        progressBar.setVisibility(View.GONE);
                         otpModel = SingletonGson.getInstance()
                                .fromJson("" + result, OTPModel.class);

                        if (otpModel.getResult().equals("1")) {

                            otp = otpModel.getData().getOtp();
                            Toast.makeText(OTPActivity.this,otpModel.getMessage(),Toast.LENGTH_LONG).show();


                        }else {
                            Toast.makeText(OTPActivity.this,otpModel.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }

                    break;





            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        otpEditText = findViewById(R.id.et_otp);
        otpText = findViewById(R.id.enterMobileTextNext);
        changePhone = findViewById(R.id.editMobileNumber);
        back = findViewById(R.id.backBtn);
        checkOtp = findViewById(R.id.checkOtpBtn);
        progressBar = findViewById(R.id.progressBar);

        try {
            phone = getIntent().getStringExtra(IntentKeys.PHONE);
            otpText.setText(getResources().getString(R.string.otp_text)+ " +977"+phone);
            type = getIntent().getStringExtra(IntentKeys.TYPE);
        }catch (Exception e){
            e.printStackTrace();
        }


        GetOTPService.startActionGetOtp(OTPActivity.this,phone,type);



//        changePhone.setOnClickListener(this);
        back.setOnClickListener(this);
        checkOtp.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

//            case R.id.editMobileNumber:
//                startActivity(new Intent(OTPActivity.this, CheckUserPhoneActivity.class));
//                finish();
//                break;

            case R.id.backBtn:
                startActivity(new Intent(OTPActivity.this, CheckUserPhoneActivity.class));
                finish();
                break;

            case R.id.checkOtpBtn:
                progressBar.setVisibility(View.VISIBLE);
                if(type.equals("1")) {
                    if (otpEditText.getText().toString().trim().equals(otp)) {
                        progressBar.setVisibility(View.GONE);
                        Intent intent = new Intent(OTPActivity.this, PersonalInfoActivity.class);
                        intent.putExtra(IntentKeys.PHONE, phone);
                        intent.putExtra(IntentKeys.TYPE, type);
                        startActivity(intent);
                        finish();

                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(OTPActivity.this, getResources().getString(R.string.incorrect_otp), Toast.LENGTH_LONG).show();
                    }
                }else if (type.equals("2")){

                    if (otpEditText.getText().toString().trim().equals(otp)) {
                        progressBar.setVisibility(View.GONE);
                        Intent intent = new Intent(OTPActivity.this, CreatePasswordActivity.class);
                        intent.putExtra(IntentKeys.PHONE, phone);
                        intent.putExtra(IntentKeys.TYPE, type);
                        startActivity(intent);
                        finish();

                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(OTPActivity.this, getResources().getString(R.string.incorrect_otp), Toast.LENGTH_LONG).show();
                    }



                }


        }

    }



    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(broadcastReceiver,
                        new IntentFilter(GetOTPService.OTP_MESSAGE));



    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getApplicationContext())
                .unregisterReceiver(broadcastReceiver);
    }

}
