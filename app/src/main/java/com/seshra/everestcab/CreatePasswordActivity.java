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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.seshra.everestcab.models.ModelLogin;
import com.seshra.everestcab.models.ModelResultCheck;
import com.seshra.everestcab.service.ForgotPasswordService;
import com.seshra.everestcab.service.RegisterUserService;
import com.seshra.everestcab.utils.IntentKeys;
import com.seshra.everestcab.utils.SingletonGson;
import com.seshra.everestcab.viewmodel.CreatePasswordActivityViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class CreatePasswordActivity extends AppCompatActivity implements View.OnClickListener{


    String phone, firstName, lastName, type;

    TextInputEditText password, cPassword;
    Button registerBtn;

    CreatePasswordActivityViewModel viewModel;

    ProgressBar progressBar;



    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            try {

                String result = intent.getStringExtra(RegisterUserService.REGISTER_KEY);
                if (!result.equals("0")) {
                    progressBar.setVisibility(View.GONE);


                    ModelLogin modelResultCheck = SingletonGson.getInstance().fromJson("" + result, ModelLogin.class);

                    if (modelResultCheck.getResult().equals("1")) {

                        startActivity(new Intent(getApplicationContext(), MainActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        );


                    } else {

                        Toast.makeText(CreatePasswordActivity.this, modelResultCheck.getMessage(), Toast.LENGTH_LONG).show();

                    }


                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(CreatePasswordActivity.this, getResources().getString(R.string.error_registration), Toast.LENGTH_LONG).show();
                }


            }catch (Exception e){
                e.printStackTrace();
            }







            try {

                String result = intent.getStringExtra(ForgotPasswordService.FORGOT_PASSWORD_MESSAGE_KEY);
                if (!result.equals("0")) {
                    progressBar.setVisibility(View.GONE);


                    ModelResultCheck modelResultCheck = SingletonGson.getInstance()
                            .fromJson("" + result, ModelResultCheck.class);

                    if (modelResultCheck.getResult().equals("1")) {


                        Intent mainIntent = new Intent(getApplicationContext(), CheckUserPhoneActivity.class);
                        Toast.makeText(CreatePasswordActivity.this, modelResultCheck.getMessage(), Toast.LENGTH_LONG).show();
                        startActivity(mainIntent);
                        finish();
                    } else {

                        Toast.makeText(CreatePasswordActivity.this, modelResultCheck.getMessage(), Toast.LENGTH_LONG).show();

                    }


                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(CreatePasswordActivity.this,getResources().getString(R.string.error_password_recover), Toast.LENGTH_LONG).show();
                }


            }catch (Exception e){
                e.printStackTrace();
            }








        }

    };





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_password);

        initViewModel();


        try {
            phone = getIntent().getStringExtra(IntentKeys.PHONE);
            firstName = getIntent().getStringExtra(IntentKeys.FIRST_NAME);
            lastName = getIntent().getStringExtra(IntentKeys.LAST_NAME);
            type = getIntent().getStringExtra(IntentKeys.TYPE);
        }catch (Exception e){
            e.printStackTrace();
        }

        password = findViewById(R.id.inputUserPassword);
        cPassword = findViewById(R.id.inputConfirmUserPassword);
        registerBtn = findViewById(R.id.registerBtn);
        progressBar = findViewById(R.id.progressBar);





        registerBtn.setOnClickListener(this);


    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(CreatePasswordActivity.this).get(CreatePasswordActivityViewModel.class);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.registerBtn:


                if(validateFields()){

                    if(type.equals("1")) {
                        progressBar.setVisibility(View.VISIBLE);
                        registerBtn.setClickable(false);
                        viewModel.registerUser(phone, firstName, lastName, password.getText().toString().trim());
                    }else if(type.equals("2")){

                        progressBar.setVisibility(View.VISIBLE);
                        registerBtn.setClickable(false);
                        viewModel.recoverPassword(phone,password.getText().toString().trim());

                    }

                }
                break;



        }

    }


    public  boolean validateFields(){

        if(password.getText().toString().trim().isEmpty() || password.getText().toString().trim().length()<6){
            password.setError(getResources().getString(R.string.at_least_eight_character));
            password.requestFocus();
            return false;
        }

        if(cPassword.getText().toString().trim().isEmpty() || cPassword.getText().toString().trim().length()<6){
            cPassword.setError(getResources().getString(R.string.at_least_eight_character));
            cPassword.requestFocus();
            return false;
        }

        if(!password.getText().toString().trim().equals(cPassword.getText().toString().trim())) {
            password.setError(getResources().getString(R.string.password_not_matching));
            password.requestFocus();
            return false;
        }

        return true;

    }



    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(broadcastReceiver,
                        new IntentFilter(RegisterUserService.REGISTER_MESSAGE));

        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(broadcastReceiver,
                        new IntentFilter(ForgotPasswordService.FORGOT_PASSWORD_MESSAGE));

    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getApplicationContext())
                .unregisterReceiver(broadcastReceiver);
    }

}
