package com.seshra.everestcab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.seshra.everestcab.models.ModelResultCheck;
import com.seshra.everestcab.models.ModelTermsAndCondition;
import com.seshra.everestcab.service.FetchTermsAndConditionService;
import com.seshra.everestcab.utils.SingletonGson;

public class TermsAndConditionActivity extends AppCompatActivity {

    TextView termsText;

    private Toolbar toolbar;
    ProgressBar progressBar;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            progressBar.setVisibility(View.GONE);

            try {

                String result = intent.getStringExtra(FetchTermsAndConditionService.TERMS_MESSAGE_KEY);

                if (!result.equals("0")) {

                    ModelResultCheck modelResultCheck =
                            SingletonGson.getInstance().fromJson("" + result, ModelResultCheck.class);

                    if (modelResultCheck.getResult().equals("1")) {

                        ModelTermsAndCondition modelTermsAndCondition =
                                SingletonGson.getInstance().fromJson("" + result, ModelTermsAndCondition.class);

                        termsText.setText(Html.fromHtml("" + modelTermsAndCondition.getData().getDescription()));


                    } else {
                        Toast.makeText(TermsAndConditionActivity.this, modelResultCheck.getMessage(), Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(TermsAndConditionActivity.this, getResources().getString(R.string.erro_fetching_data), Toast.LENGTH_LONG).show();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_condition);

        progressBar = findViewById(R.id.progressBar);

        toolbar=findViewById(R.id.toolbarTerms);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FetchTermsAndConditionService.startActionFetchTerms(TermsAndConditionActivity.this,"terms_and_Conditions");

        termsText = findViewById(R.id.termsAndConditionText);
        termsText.setMovementMethod(new ScrollingMovementMethod());


    }


    @Override
    public void onStart() {
        super.onStart();

        LocalBroadcastManager.getInstance(TermsAndConditionActivity.this)
                .registerReceiver(broadcastReceiver,
                        new IntentFilter(FetchTermsAndConditionService.TERMS_MESSAGE));
    }


    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(TermsAndConditionActivity.this)
                .unregisterReceiver(broadcastReceiver);
    }

}
