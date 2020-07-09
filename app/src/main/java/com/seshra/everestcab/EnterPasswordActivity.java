package com.seshra.everestcab;

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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.seshra.everestcab.models.ModelLogin;
import com.seshra.everestcab.service.LoginService;
import com.seshra.everestcab.utils.IntentKeys;
import com.seshra.everestcab.utils.SingletonGson;
import com.seshra.everestcab.viewmodel.EnterPaswordActivityViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class EnterPasswordActivity extends AppCompatActivity implements View.OnClickListener{

    TextInputEditText password;
    Button loginBtn;

    String phone;

    ProgressBar progressBar;

    EnterPaswordActivityViewModel viewModel;

    ImageView backBtn;
    TextView forgotPassword;


    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String result = intent.getStringExtra(LoginService.LOGIN_KEY);
            if(!result.equals("0")) {
                progressBar.setVisibility(View.GONE);


                ModelLogin modelResultCheck = SingletonGson.getInstance().fromJson("" + result, ModelLogin.class);

                if(modelResultCheck.getResult().equals("1")) {


                    Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                    viewModel.fetchConfig();

                    startActivity(mainIntent
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    );

                    finish();
                }else {

                    Toast.makeText(EnterPasswordActivity.this,modelResultCheck.getMessage(), Toast.LENGTH_LONG).show();

                }



            }else {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(EnterPasswordActivity.this,getResources().getString(R.string.error_logging), Toast.LENGTH_LONG).show();
            }


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_password);

        password = findViewById(R.id.inputUserPassword);
        loginBtn = findViewById(R.id.loginBtn);
        progressBar = findViewById(R.id.progressBar);
        backBtn = findViewById(R.id.backBtn);

        forgotPassword = findViewById(R.id.forgotPassword);

        initViewModel();

        phone = getIntent().getStringExtra(IntentKeys.PHONE);

        loginBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
    }

    private void initViewModel() {

        viewModel = ViewModelProviders.of(EnterPasswordActivity.this).get(EnterPaswordActivityViewModel.class);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.loginBtn:
                progressBar.setVisibility(View.VISIBLE);
                viewModel.checkForLogin(phone, password.getText().toString().trim());
                break;

            case R.id.backBtn:
                onBackPressed();
                break;

            case R.id.forgotPassword:
                Intent intent = new Intent(EnterPasswordActivity.this, CheckUserPhoneActivity.class);
                intent.putExtra(IntentKeys.TYPE,"2");
                intent.putExtra(IntentKeys.PHONE, phone);
                startActivity(intent);
                break;

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(broadcastReceiver,
                        new IntentFilter(LoginService.LOGIN_MESSAGE));

    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getApplicationContext())
                .unregisterReceiver(broadcastReceiver);
    }


}
