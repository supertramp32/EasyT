package com.seshra.everestcab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.seshra.everestcab.utils.IntentKeys;

public class PersonalInfoActivity extends AppCompatActivity implements View.OnClickListener{



    EditText inputFirstName, inputLastName;
    Button continueUserInfo;

    String phone;
    String type;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        inputFirstName = findViewById(R.id.inputFirstName);
        inputLastName = findViewById(R.id.inputLastName);
        continueUserInfo = findViewById(R.id.continuePersonalInfo);

        try{

            phone = getIntent().getStringExtra(IntentKeys.PHONE);
            type = getIntent().getStringExtra(IntentKeys.TYPE);

        }catch (Exception e){
            e.printStackTrace();
        }




        continueUserInfo.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.continuePersonalInfo:
                if(validateFields()){

                    Intent intent = new Intent(PersonalInfoActivity.this, CreatePasswordActivity.class);
                    intent.putExtra(IntentKeys.PHONE,phone);
                    intent.putExtra(IntentKeys.FIRST_NAME, inputFirstName.getText().toString().trim());
                    intent.putExtra(IntentKeys.LAST_NAME,inputLastName.getText().toString().trim());
                    intent.putExtra(IntentKeys.TYPE,type);
                    startActivity(intent);

                }
                break;



        }
    }



    public  boolean validateFields(){


        if(inputFirstName.getText().toString().trim().isEmpty() || inputFirstName.getText().toString().trim().length()<3){
            inputFirstName.setError(getResources().getString(R.string.enter_valid_name));
            inputFirstName.requestFocus();
            return false;
        }

        if(inputLastName.getText().toString().trim().isEmpty() || inputLastName.getText().toString().trim().length()<3){
            inputLastName.setError(getResources().getString(R.string.valid_last_name));
            inputLastName.requestFocus();
            return false;
        }



        return true;

    }

}
