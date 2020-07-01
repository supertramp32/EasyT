package com.seshra.everestcab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

public class HelpForBloodActivity extends AppCompatActivity implements View.OnClickListener {

    Spinner bloodSpinner;

    String[] bloodGroup = {"A+", "A-", "B+","B-", "AB+", "AB-"};

    EditText fullName, phone;

    ImageView close;

    Button save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_for_blood);

        bloodSpinner = findViewById(R.id.userBloodGroup);
        fullName = findViewById(R.id.saveUserName);
        phone = findViewById(R.id.saveUserPhone);
        close = findViewById(R.id.closeBtn);
        save = findViewById(R.id.saveInfo);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(HelpForBloodActivity.this,android.R.layout.simple_spinner_item,bloodGroup);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        bloodSpinner.setAdapter(aa);

        close.setOnClickListener(this);


        fullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count>0){
                    save.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }else {
                    save.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.closeBtn:
                onBackPressed();
                break;

        }

    }
}
