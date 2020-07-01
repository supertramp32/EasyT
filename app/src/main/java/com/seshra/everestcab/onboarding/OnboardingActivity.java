package com.seshra.everestcab.onboarding;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.seshra.everestcab.CheckUserPhoneActivity;
import com.seshra.everestcab.MainActivity;
import com.seshra.everestcab.R;
import com.seshra.everestcab.service.CheckActiveRideService;
import com.seshra.everestcab.utils.IntentKeys;
import com.seshra.everestcab.utils.SessionManager;


public class OnboardingActivity extends AppCompatActivity {



    private int pagePosition = 0;
    private Button nextBtn;
    private Button finishBtn;
    private ViewPager viewPager;
    private ImageView[] indicators;
    private ImageView indicator01, indicator02, indicator03,indicator04;

    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_onboarding);



        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        nextBtn = (Button) findViewById(R.id.btn_next);
        finishBtn = (Button) findViewById(R.id.btn_finish);

        indicator01 = (ImageView) findViewById(R.id.indicator_01);
        indicator02 = (ImageView) findViewById(R.id.indicator_02);
        indicator03 = (ImageView) findViewById(R.id.indicator_03);
        indicator04 = findViewById(R.id.indicator_04);
        indicators = new ImageView[] {indicator01, indicator02, indicator03,indicator04};

        updateIndicator(pagePosition);

        final int pageColor01 = ContextCompat.getColor(this, R.color.colorWhite);
        final int pageColor02 = ContextCompat.getColor(this, R.color.colorWhite);
        final int pageColor03 = ContextCompat.getColor(this, R.color.colorWhite);
        final int pageColor04 = ContextCompat.getColor(this, R.color.colorWhite);

        final int[] pageColorList = new int[] {pageColor01, pageColor02, pageColor03,pageColor04};

        final ArgbEvaluator argbEvaluator = new ArgbEvaluator();  //used to update the page color

//        tv_brightness = (TextView) findViewById(R.id.tv_brightness);

        viewPager = (ViewPager) findViewById(R.id.onboarding_viewpager);

        // Set Adapter on ViewPager
        viewPager.setAdapter(new OnboardingAdapter(getSupportFragmentManager()));

        // Set PageTransformer on ViewPager
        viewPager.setPageTransformer(false, new OnboardingPageTransformer());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                // Update Page Background Color
                int pageColorUpdate = (Integer) argbEvaluator.evaluate(
                        positionOffset,
                        pageColorList[position],
                        pageColorList[position == 3 ? position : position + 1]  //If there's no last page, do not increment
                );
                viewPager.setBackgroundColor(pageColorUpdate);

            }

            @Override
            public void onPageSelected(int position) {
                pagePosition = position;
                updateIndicator(pagePosition);

                //set the page color when selected
                switch (position) {
                    case 0:
                        viewPager.setBackgroundColor(pageColor01);
                        break;
                    case 1:
                        viewPager.setBackgroundColor(pageColor02);
                        break;
                    case 2:
                        viewPager.setBackgroundColor(pageColor03);
                        break;

                    case 3:
                        viewPager.setBackgroundColor(pageColor04);
                        break;
                }

                nextBtn.setVisibility(position == 3 ? View.GONE : View.VISIBLE);
                finishBtn.setVisibility(position == 3 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void updateIndicator(int pagePosition) {
        for(int i = 0; i < indicators.length; i++) {
            indicators[i].setBackgroundResource(
                    i == pagePosition ? R.drawable.indicator_selected : R.drawable.indicator_unselected
            );
        }
    }

    public void onNextButton(View view) {
        pagePosition += 1;
        viewPager.setCurrentItem(pagePosition, true);
    }

    public void onFinishButton(View view) {

        sharedPreferences.edit().putBoolean(IntentKeys.FIRST_TIME,false).apply();
        checkFlow();
    }

    public void onCancelButton(View view) {
        viewPager.setCurrentItem(3, true);
        updateIndicator(3);
    }






    public void checkFlow() {

        SessionManager sessionManager = new SessionManager(this);

            if (sessionManager.isLoggedIn()) {
                startService(new Intent(OnboardingActivity.this, CheckActiveRideService.class));
                startActivity(new Intent(OnboardingActivity.this, MainActivity.class));
                finish();
            } else {
                startActivity(new Intent(OnboardingActivity.this, CheckUserPhoneActivity.class));
                finish();
            }
        }



}
