package com.seshra.everestcab;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.seshra.everestcab.data.AppRepository;
import com.seshra.everestcab.onboarding.OnboardingActivity;
import com.seshra.everestcab.service.CheckActiveRideService;
import com.seshra.everestcab.utils.IntentKeys;
import com.seshra.everestcab.utils.SessionManager;

public class SplashActivity extends AppCompatActivity {



    SessionManager sessionManager;
    AppRepository appRepository;

    ImageView splashImage;

    Handler handler;



    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        sessionManager = new SessionManager(this);
        appRepository = AppRepository.getOurInstance(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit().putString(IntentKeys.PROMO_CODE,"null").apply();

//
//        try{
//
//            sessionManager.getAppConfig().getData().getLanguages().get()
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }


        appRepository.fetchRemoteConfig();
        splashImage = findViewById(R.id.splashImage);

        sessionManager.setLanguage("" + sessionManager.getLanguage());


        handler = new Handler();



        final Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
               checkFlow();
            }
        }, 1000);



//        Glide.with(this).asGif().load(R.drawable.flash_one)
//                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).listener(new RequestListener<GifDrawable>() {
//            @Override
//            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
//               checkFlow();//do your stuff
//                return false;
//            }
//
//            @Override
//            public boolean onResourceReady(final GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
//                resource.setLoopCount(1);
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            Thread.sleep(200);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        while(true) {
//                            if(!resource.isRunning()) {
//                                checkFlow();//do your stuff
//                                break;
//                            }
//                        }
//                    }
//                }).start();
//                return false;
//            }
//        }).into(splashImage);
//




    }




    public void checkFlow() {


        boolean firstTime = sharedPreferences.getBoolean(IntentKeys.FIRST_TIME, true);

        if (firstTime) {

            handler.post(new Runnable() {
                @Override
                public void run() {
                    showLanguageDialog();
                }
            });



        } else {

            if (sessionManager.isLoggedIn()) {
                startService(new Intent(SplashActivity.this, CheckActiveRideService.class));
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            } else {
                startActivity(new Intent(SplashActivity.this, CheckUserPhoneActivity.class));
                finish();
            }
        }
    }

    private void showLanguageDialog() {

        try {
            AlertDialog.Builder builderSingle = new AlertDialog.Builder(SplashActivity.this);
            builderSingle.setTitle(R.string.select_language);
            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SplashActivity.this,
                    android.R.layout.select_dialog_singlechoice);
            arrayAdapter.add("English");
            arrayAdapter.add("नेपाली");

//            builderSingle.setNegativeButton(SplashActivity.this.getResources()
//                    .getString(R.string.cancel), (DialogInterface dialogInterface, int which) -> {
//                dialogInterface.dismiss();
//            });

            builderSingle.setAdapter(arrayAdapter, (DialogInterface dialog, int which) -> {

                if(which==0)
                    sessionManager.setLanguage("en");
                else if(which==1)
                    sessionManager.setLanguage("ne");
                else
                    sessionManager.setLanguage("en");


                startActivity(new Intent(SplashActivity.this, CheckUserPhoneActivity.class));
                sharedPreferences.edit().putBoolean(IntentKeys.FIRST_TIME,false).apply();
                SplashActivity.this.finish();

            });



//            builderSingle.setPositiveButton(SplashActivity.this.getResources().getString(R.string.ok),
//                    (DialogInterface dialogInterface, int which) -> {
//                        dialogInterface.dismiss();
//                    });

            builderSingle.setCancelable(false);
            builderSingle.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
