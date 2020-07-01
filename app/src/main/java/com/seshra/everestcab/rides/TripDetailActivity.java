package com.seshra.everestcab.rides;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.seshra.everestcab.R;
import com.seshra.everestcab.models.ModelResultCheck;
import com.seshra.everestcab.models.ModelSpecificTripDetails;
import com.seshra.everestcab.service.FetchSpecificTripDetailService;
import com.seshra.everestcab.utils.IntentKeys;
import com.seshra.everestcab.utils.SingletonGson;
import com.seshra.everestcab.viewmodel.TripDetailActivityViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;

public class TripDetailActivity extends AppCompatActivity {


    ImageView mapImg, driverImg;
    TextView date, estFare, serviceType, paymentType;
    TextView pickLocation, dropLocation, driverName, driverEmail, driverRating;

    TextView billTotal, billDistance, billDuration;

    TripDetailActivityViewModel viewModel;
    RatingBar ratingBar;


    ModelSpecificTripDetails specificTripDetails;

    ConstraintLayout root;

    ShimmerFrameLayout shimmerFrameLayout;

    ImageView backBtn;

    RecyclerView recyclerView;

    BillDetailsAdapter billDetailsAdapter;


    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            String result = intent.getStringExtra(FetchSpecificTripDetailService.TRIP_HISTORY_DETAIL_MESSAGE_KEY);

            if(!result.equals("0")) {


                ModelResultCheck modelResultCheck =
                        SingletonGson.getInstance().fromJson("" + result, ModelResultCheck.class);

                if(modelResultCheck.getResult().equals("1")) {

                    specificTripDetails = SingletonGson.getInstance().fromJson("" + result, ModelSpecificTripDetails.class);
                    populateViews(specificTripDetails);



                }else {
                    Toast.makeText(TripDetailActivity.this,modelResultCheck.getMessage(),Toast.LENGTH_LONG).show();
                }

            }else {
                Toast.makeText(TripDetailActivity.this,"Error Fetching Data.",Toast.LENGTH_LONG).show();

            }


        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);

        recyclerView = findViewById(R.id.tripDetailRecycler);

        initRecyclerView();
        initViews();
        initViewModel();

        try{

            viewModel.getSpecificTripDetailApi(getIntent().getStringExtra(IntentKeys.BOOKING_ID));

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private void initViewModel() {

        viewModel = ViewModelProviders.of(TripDetailActivity.this).get(TripDetailActivityViewModel.class);


    }

    private void initViews() {

        root = findViewById(R.id.root);

        shimmerFrameLayout = findViewById(R.id.shimmer_view_container);
        ratingBar = findViewById(R.id.rating_bar);

        mapImg = findViewById(R.id.tripDetailMapImg);
        driverImg = findViewById(R.id.tripDetailDriverImg);
        date = findViewById(R.id.tripDetailDate);
        estFare = findViewById(R.id.tripDetailPaymentAmount);
        serviceType = findViewById(R.id.tripDetailServiceType);
        paymentType = findViewById(R.id.tripDetailPaymentType);
        pickLocation = findViewById(R.id.tripDetailPickUpLocation);
        dropLocation = findViewById(R.id.tripDetailDropLocation);
        driverName = findViewById(R.id.tripDetailDriverName);
//        driverEmail = findViewById(R.id.tripDetailDriverEmail);
        driverRating = findViewById(R.id.tripDetailDriverRating);

        billTotal = findViewById(R.id.billTotalFare);
        billDistance = findViewById(R.id.billTotalDistance);
        billDuration = findViewById(R.id.billTotalDuration);

        backBtn = findViewById(R.id.backBtn);


//        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//            @Override
//            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//                viewModel.rateDriver(getIntent().getStringExtra(IntentKeys.BOOKING_ID),
//                        ratingBar.getRating(),
//                        "");
//            }
//        });



        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    private void populateViews(ModelSpecificTripDetails specificTripDetails) {


        shimmerFrameLayout.setVisibility(View.GONE);
        root.setVisibility(View.VISIBLE);

        ViewTreeObserver vto = mapImg.getViewTreeObserver();

            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        mapImg.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    } else {
                        mapImg.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                    int width = mapImg.getMeasuredWidth();
                    int height = mapImg.getMeasuredHeight();

                    setWidth(specificTripDetails.getData()
                            .getHolder_map_image().getData().getMap_image(),
                            width, height);

                }
            });



        Glide.with(TripDetailActivity.this).load(specificTripDetails.getData()
                .getHolder_driver().getData().getCircular_image()).into(driverImg);


        date.setText(specificTripDetails.getData().getHolder_booking_description().getData().getHighlighted_left_text());
        estFare.setText("NPR. "+specificTripDetails.getData().getHolder_metering().getData().getText_one());
        serviceType.setText(specificTripDetails.getData().getHolder_booking_description().getData().getSmall_left_text());
//        paymentType.setText(specificTripDetails.getData().getHolder_booking_description().getData().getHighlighted_right_text());
        pickLocation.setText(specificTripDetails.getData().getHolder_pickdrop_location().getData().getPick_text());
        dropLocation.setText(specificTripDetails.getData().getHolder_pickdrop_location().getData().getDrop_text());

        driverName.setText(specificTripDetails.getData().getHolder_driver().getData().getHighlighted_text());
//        driverEmail.setText(specificTripDetails.getData().getHolder_driver().getData().getSmall_text());
        driverRating.setText(specificTripDetails.getData().getHolder_driver().getData().getSmall_text());

        try {
            ratingBar.setRating(Float.parseFloat(specificTripDetails.getData().getHolder_driver().getData().getRating()));
        }catch (Exception e){
            e.printStackTrace();
        }

        billTotal.setText("NPR." +specificTripDetails.getData().getHolder_metering().getData().getText_one());
        billDistance.setText(specificTripDetails.getData().getHolder_metering().getData().getText_two());
        billDuration.setText(specificTripDetails.getData().getHolder_metering().getData().getText_three());
//
//        billDetailsAdapter = new BillDetailsAdapter(specificTripDetails.getData().getHolder_receipt().getData(),
//                TripDetailActivity.this);
//        recyclerView.setAdapter(billDetailsAdapter);



    }




    private void setWidth(String strMapImage, int width, int height) {

      String mapImage = strMapImage + "&size=" + width + "x" + height;

        String strNewMapImage = mapImage.replace(":", "://");


        Glide.with(this).load( strNewMapImage).into(mapImg);

    }


    @Override
    public void onStart() {
        super.onStart();

        LocalBroadcastManager.getInstance(TripDetailActivity.this)
                .registerReceiver(broadcastReceiver,
                        new IntentFilter(FetchSpecificTripDetailService.TRIP_HISTORY_DETAIL_MESSAGE));
    }


    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(TripDetailActivity.this)
                .unregisterReceiver(broadcastReceiver);
    }

    private void initRecyclerView() {
       LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


    }

    }
