package com.seshra.everestcab.locations;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.seshra.everestcab.R;
import com.seshra.everestcab.models.ModelFavourite;
import com.seshra.everestcab.models.ModelGoogleApiStatus;
import com.seshra.everestcab.models.ModelGooglePlacePicker;
import com.seshra.everestcab.models.ModelPlaceIdResponse;
import com.seshra.everestcab.models.ModelPopularPlaces;
import com.seshra.everestcab.placer.PlaceHolderView;
import com.seshra.everestcab.placer.annotations.Click;
import com.seshra.everestcab.placer.annotations.Layout;
import com.seshra.everestcab.placer.annotations.Resolve;
import com.seshra.everestcab.service.FetchFavouriteLocationService;
import com.seshra.everestcab.service.FetchPopularLocations;
import com.seshra.everestcab.service.GooglePlacePickerService;
import com.seshra.everestcab.utils.API_S;
import com.seshra.everestcab.utils.BaseConfig;
import com.seshra.everestcab.utils.IntentKeys;
import com.seshra.everestcab.utils.SingletonGson;
import com.seshra.everestcab.viewmodel.SearchLocationViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.snackbar.Snackbar;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class SearchLocationActivity extends AppCompatActivity implements
        View.OnClickListener, PopularLocationAdapter.PopularClickListerner {

    private static final String TAG = "SearchLocationActivity";
    ImageView backBtn;

    EditText inputSearchAddress;
    ShimmerFrameLayout container;
    private Handler handler;

    RecyclerView recyclerView;
    PopularLocationAdapter popularLocationAdapter;


    ProgressBar progressBar;

    String CATEGORY_HOME = "1", CATEGORY_WORK = "2", CATEGORY_OTHER = "3", CATEGORY_SEARCHED = "4",
            LATITUDE = "", LONGITUDE = "";

    SearchLocationViewModel viewModel;

    LinearLayout root;

    PlaceHolderView placeHolderView;
    PlaceHolderView favouritePlaceHolder;



    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {



            String intentName = intent.getAction();
            switch (intentName) {
                case GooglePlacePickerService.SEARCH_MESSAGE:

                    try{
                        String result = intent.getStringExtra(GooglePlacePickerService.SEARCH_KEY);
                        if(result!=null) {

                            progressBar.setVisibility(View.GONE);

                            container.stopShimmer();
                            container.setVisibility(View.GONE);
                            ModelGoogleApiStatus modelGoogleApiStatus = SingletonGson.getInstance().fromJson( ""+result, ModelGoogleApiStatus.class);

//                            if(modelGoogleApiStatus.get().equals("999"))
//                                new Logout(getApplicationContext());

                            if (modelGoogleApiStatus.getStatus().equals("OK")) {
                                ModelGooglePlacePicker modelGooglePlacePicker = SingletonGson.getInstance().fromJson("" + result, ModelGooglePlacePicker.class);
                                for (int i = 0; i < modelGooglePlacePicker.getPredictions().size(); i++) {
                                    placeHolderView.addView(new HolderAddress(modelGooglePlacePicker.getPredictions().get(i).getStructured_formatting().getMain_text(),
                                            "" + modelGooglePlacePicker.getPredictions().get(i).getDescription(),
                                            "longitude",
                                            "longitude",
                                            "" + modelGooglePlacePicker.getPredictions().get(i).getPlace_id(),
                                            CATEGORY_SEARCHED));
                                }
                            }
                            if (modelGoogleApiStatus.getStatus().equals("REQUEST_DENIED")) {
                                Snackbar.make(root, getResources().getString(R.string.permission_denied), Snackbar.LENGTH_SHORT).show();
                            }


                        }else {
                            container.stopShimmer();
                            container.setVisibility(View.GONE);
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(SearchLocationActivity.this,getResources().getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show();
                        }


                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    break;


                case FetchFavouriteLocationService.FETCH_FAVOURITE_MESSAGE:

                    try{

                        String result = intent.getStringExtra(FetchFavouriteLocationService.FETCH_FAVOURITE_MESSAGE_KEY);
                        if(!result.equals("0")){

                            ModelFavourite modelFavourite = SingletonGson.getInstance().fromJson("" + result, ModelFavourite.class);
                            favouritePlaceHolder.removeAllViews();
                            favouritePlaceHolder.getViewAdapter().notifyDataSetChanged();
                            addfavouriteBlock(modelFavourite);

                        }


                    }catch (Exception e){
                        e.printStackTrace();
                    }


                    break;


                case FetchPopularLocations.FETCH_POLULAR_LOCATION_MESSAGE:
                try{

                    String result = intent.getStringExtra(FetchPopularLocations.FETCH_POLULAR_LOCATION_MESSAGE_KEY);
                    if(!result.equals("0")){

                        ModelPopularPlaces modelPopularPlaces = SingletonGson.getInstance().fromJson("" +
                                result, ModelPopularPlaces.class);

                        if(modelPopularPlaces!=null){
                            popularLocationAdapter = new PopularLocationAdapter(modelPopularPlaces.getData(),
                                    SearchLocationActivity.this,SearchLocationActivity.this::onClick);
                            recyclerView.setAdapter(popularLocationAdapter);
                        }



                    }


                }catch (Exception e){
                    e.printStackTrace();
                }


                break;


            }










        }









    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_location);
        backBtn = findViewById(R.id.backBtn);

        progressBar = findViewById(R.id.progressbar);

        inputSearchAddress = findViewById(R.id.searchLocationInput);
        container = (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
        root = findViewById(R.id.root);
        placeHolderView = findViewById(R.id.placeholder);
        favouritePlaceHolder = findViewById(R.id.favPlaceHolder);




        try {
            LATITUDE = getIntent().getStringExtra(IntentKeys.LATITUDE);
            LONGITUDE = getIntent().getStringExtra(IntentKeys.LONGITUDE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        initViewModel();

        recyclerView = findViewById(R.id.popularRecyclerView);
        initRecyclerView();



        inputSearchAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                Log.e("Lenghth",""+ s.length());
//                Log.e("AUtoCompleteChracters",""+ sessionManager.getAppConfig().getData().getGeneral_config().getAutocomplete_start());
//                if(s.length()>=sessionManager.getAppConfig().getData().getGeneral_config().getAutocomplete_start()){
                try {
                    progressBar.setVisibility(View.VISIBLE);
                    container.setVisibility(View.VISIBLE);
                    container.startShimmer();
                    placeHolderView.removeAllViews();
                } catch (Exception e) {
                }
                Runnable run = new Runnable() {
                    @Override
                    public void run() {

                        try {
                            viewModel.getGooglePlacesApi(API_S.Tags.GOOGLE_PLACE_PICKER,
                                    getPlaceAutoCompleteUrl(inputSearchAddress.getText().toString()));
                        } catch (Exception e) {
                            Log.e("" + TAG, "Exception caught while calling API " + e.getMessage());
                        }
                    }

                };
                if (handler != null) {
                    handler.removeCallbacksAndMessages(null);
                } else {
                    handler = new Handler();
                }
                handler.postDelayed(run, 800);


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        backBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.backBtn:
                onBackPressed();
                return;
        }
    }



    private void initViewModel() {
        viewModel = ViewModelProviders.of(SearchLocationActivity.this).get(SearchLocationViewModel.class);
        viewModel.fetchFavouritesLocations();
        viewModel.fetchPopularLocations(LATITUDE,LONGITUDE);
    }

    public String getPlaceAutoCompleteUrl(String input) {

        StringBuilder urlString = new StringBuilder();
        urlString.append(API_S.Endpoints.GOOGLE_PLACE_PICKER);
        urlString.append("?input=");
        try {
            urlString.append(URLEncoder.encode(input, "utf8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        urlString.append("&location=");
        urlString.append(LATITUDE + "," + LONGITUDE); // append lat long of current location to show nearby results.
        urlString.append("&radius=1000&language=en");
        urlString.append("&key=" + BaseConfig.MAP_KEY);
        urlString.append("&components=country:np");

        Log.d("FINAL URL:::   ", urlString.toString());
        return urlString.toString();
    }

    @Override
    public void onClick(String name, String lat, String lon) {
        finalizeActivity(name,lat,lon);
    }


    @Layout(R.layout.holder_address)
    class HolderAddress {

        private String addressName, addressdescription, mType,place_id, mLatitude, mLongitude;


        @com.seshra.everestcab.placer.annotations.View(R.id.address_image)
        ImageView addressImage;
        @com.seshra.everestcab.placer.annotations.View(R.id.main_address)
        TextView mainAddress;
        @com.seshra.everestcab.placer.annotations.View(R.id.address_description)
        TextView addressDescription;
        @com.seshra.everestcab.placer.annotations.View(R.id.delete_favorite_location)
        ImageView delete_favorite_location;

        HolderAddress(String address_name, String address_description, String latitude, String longitude, String place_id, String TYPE) {
            this.addressName = address_name;
            this.addressdescription = address_description;
            this.mType = TYPE;
            this.mLatitude = latitude;
            this.mLongitude = longitude;
            this.place_id = place_id;
        }


        @Resolve
        private void setdata() {
            addressDescription.setText("" + addressdescription);
            if (mType.equals("" + CATEGORY_SEARCHED)) {
                delete_favorite_location.setVisibility(View.GONE);
                mainAddress.setText("" + addressName);
                addressImage.setImageResource(R.drawable.ic_location);
            }
            if (mType.equals("" + CATEGORY_OTHER)) {
                delete_favorite_location.setVisibility(View.VISIBLE);
                mainAddress.setText("" + addressName);
                addressImage.setImageResource(R.drawable.ic_favorite);
            }
            if (mType.equals("" + CATEGORY_HOME)) {
                delete_favorite_location.setVisibility(View.VISIBLE);
                mainAddress.setText("" + SearchLocationActivity.this.getResources().getString(R.string.home));
                addressImage.setImageResource(R.drawable.ic_home);
            }
            if (mType.equals("" + CATEGORY_WORK)) {
                delete_favorite_location.setVisibility(View.VISIBLE);
                mainAddress.setText("" + SearchLocationActivity.this.getResources().getString(R.string.work));
                addressImage.setImageResource(R.drawable.ic_payment);
            }
        }

        @Click(R.id.rootitem)
        private void setOnClick() {
            if (mType.equals("" + CATEGORY_SEARCHED)) {
                String url = "" + API_S.Endpoints.GOOGLE_PLACE_ID_RESPONSE + "" + this.place_id + "&fields=geometry&key=" + BaseConfig.MAP_KEY;
                progressBar.setVisibility(View.VISIBLE);
                AndroidNetworking.get(url).build().getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressBar.setVisibility(View.GONE);
                            ModelPlaceIdResponse modelPlaceIdResponse = SingletonGson.getInstance().fromJson("" + response, ModelPlaceIdResponse.class);
                            finalizeActivity("" + HolderAddress.this.addressdescription, "" + modelPlaceIdResponse.getResult().getGeometry().getLocation().getLat(), "" + modelPlaceIdResponse.getResult().getGeometry().getLocation().getLng());

                        } catch (Exception e) {
                            Log.e("" + TAG, "Exception caught while parsing " + e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(SearchLocationActivity.this, "" + anError.getErrorBody(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                finalizeActivity("" + this.addressDescription.getText().toString(), this.mLatitude, this.mLongitude);
            }
        }


     
        



        }



    public  void finalizeActivity(String addressdescription, String latitude, String longitude) {
        Intent intent = new Intent();
        intent.putExtra("" + IntentKeys.ADDRESS_NAME, "" + addressdescription);
        intent.putExtra("" + IntentKeys.LATITUDE, latitude);
        intent.putExtra("" + IntentKeys.LONGITUDE, longitude);
        setResult(Activity.RESULT_OK, intent);
        SearchLocationActivity.this.finish();
    }


    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(broadcastReceiver,
                        new IntentFilter(GooglePlacePickerService.SEARCH_MESSAGE));

        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(broadcastReceiver,
                        new IntentFilter(FetchFavouriteLocationService.FETCH_FAVOURITE_MESSAGE));

        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(broadcastReceiver,
                        new IntentFilter(FetchPopularLocations.FETCH_POLULAR_LOCATION_MESSAGE));


    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getApplicationContext())
                .unregisterReceiver(broadcastReceiver);
    }



    private void addfavouriteBlock(ModelFavourite modelFavourite) throws Exception {
        if (modelFavourite.getData().size() > 0) {


            for (int i = 0; i < modelFavourite.getData().size(); i++) {
                favouritePlaceHolder.addView(new HolderAddress(modelFavourite.getData().get(i).getOther_name(),
                        modelFavourite.getData().get(i).getLocation(),
                        modelFavourite.getData().get(i).getLatitude(),
                        modelFavourite.getData().get(i).getLongitude(),
                        ""+modelFavourite.getData().get(i).getId(),
                        modelFavourite.getData().get(i).getCategory()));
            }


        }
    }


    private void initRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(SearchLocationActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }




}
