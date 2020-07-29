package com.seshra.everestcab;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.seshra.everestcab.models.ModelResultCheck;
import com.seshra.everestcab.models.ModelSpecificTripDetails;
import com.seshra.everestcab.service.FetchSpecificTripDetailService;
import com.seshra.everestcab.utils.IntentKeys;
import com.seshra.everestcab.utils.SingletonGson;
import com.seshra.everestcab.viewmodel.ReceiptActivityViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;

public class ReceiptActivity extends AppCompatActivity {


    private static final String TAG = "Reciept Activity";
    ReceiptActivityViewModel viewModel;

    ProgressBar progressBar;

    ModelSpecificTripDetails specificTripDetails;

//    ImageView  driverImg;
//    TextView date, estFare, serviceType, paymentType;
//    TextView pickLocation, dropLocation, driverName, driverEmail, driverRating;

    TextView  estFare, serviceType ,tripType;
    TextView pickLocation, dropLocation;

    ImageView driverImage;
    TextView driverName, driverVehicleNumber, driverRating, driverVehicleType;

    Button finishRide;

    String bookingId;

    CardView recieptCard;
    View locationViews;




    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

//            progressBar.setVisibility(View.GONE);

            String result = intent.getStringExtra(FetchSpecificTripDetailService.TRIP_HISTORY_DETAIL_MESSAGE_KEY);

            if(!result.equals("0")) {

                ModelResultCheck modelResultCheck =
                        SingletonGson.getInstance().fromJson("" + result, ModelResultCheck.class);

                if(modelResultCheck.getResult().equals("1")) {

                    specificTripDetails = SingletonGson.getInstance().fromJson("" + result, ModelSpecificTripDetails.class);
                    populateViews(specificTripDetails);



                }else {
                    Toast.makeText(ReceiptActivity.this,modelResultCheck.getMessage(),Toast.LENGTH_LONG).show();
                }

            }else {
                Toast.makeText(ReceiptActivity.this,getResources().getString(R.string.erro_fetching_data),Toast.LENGTH_LONG).show();

            }


        }
    };


//    ShimmerFrameLayout shimmerFrameLayout;
//    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciept);


        initViews();
        initViewModel();

        try{


            bookingId = getIntent().getStringExtra(IntentKeys.BOOKING_ID);
//            Toast.makeText(ReceiptActivity.this,getIntent().getStringExtra(IntentKeys.BOOKING_ID),Toast.LENGTH_LONG).show();

            viewModel.getSpecificTripDetailApi(getIntent().getStringExtra(IntentKeys.BOOKING_ID));
//            viewModel.getReciept(getIntent().getStringExtra(IntentKeys.BOOKING_ID));



        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void initViewModel() {

        viewModel = ViewModelProviders.of(ReceiptActivity.this).get(ReceiptActivityViewModel.class);


    }


    private void initViews() {


        progressBar = findViewById(R.id.progressBarReciept);

        estFare = findViewById(R.id.rideestBill);
        serviceType = findViewById(R.id.ridetypeText);
        pickLocation = findViewById(R.id.pickLocationName);
        dropLocation = findViewById(R.id.dropOffLocationName);
        finishRide = findViewById(R.id.recieptFinishBtn);

        driverImage = findViewById(R.id.driverImg);
        driverName = findViewById(R.id.driverName);
        driverRating = findViewById(R.id.driverRating);
        driverVehicleNumber = findViewById(R.id.driverCarDetails);
        driverVehicleType = findViewById(R.id.driverCarModel);

        recieptCard = findViewById(R.id.recieptCard);
        locationViews = findViewById(R.id.locationLayout);




        finishRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ShowDialogForRateUser();



            }
        });

    }



    @Override
    public void onStart() {
        super.onStart();

        LocalBroadcastManager.getInstance(ReceiptActivity.this)
                .registerReceiver(broadcastReceiver,
                        new IntentFilter(FetchSpecificTripDetailService.TRIP_HISTORY_DETAIL_MESSAGE));
    }


    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(ReceiptActivity.this)
                .unregisterReceiver(broadcastReceiver);
    }



    private void populateViews(ModelSpecificTripDetails specificTripDetails) {

        progressBar.setVisibility(View.GONE);
        recieptCard.setVisibility(View.VISIBLE);
        locationViews.setVisibility(View.VISIBLE);

        Glide.with(ReceiptActivity.this).load(specificTripDetails.getData()
                .getHolder_driver().getData().getCircular_image()).into(driverImage);
        driverName.setText(specificTripDetails.getData().getHolder_driver().getData().getHighlighted_text());
        driverRating.setText(specificTripDetails.getData().getHolder_driver().getData().getRating());


        estFare.setText("NPR. "+specificTripDetails.getData().getHolder_metering().getData().getText_one());
        serviceType.setText(specificTripDetails.getData().getHolder_booking_description().getData().getSmall_left_text());
        pickLocation.setText(specificTripDetails.getData().getHolder_pickdrop_location().getData().getPick_text());
        dropLocation.setText(specificTripDetails.getData().getHolder_pickdrop_location().getData().getDrop_text());
//
//        driverEmail.setText(specificTripDetails.getData().getHolder_driver().getData().getSmall_text());
//        driverRating.setText(specificTripDetails.getData().getHolder_driver().getData().getRating());

    }



    private void ShowDialogForRateUser() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_rate_us, null);
        EditText comments = dialogView.findViewById(R.id.comments);
        RatingBar rating_bar = dialogView.findViewById(R.id.rating_bar);
        Button ll_submit_rating = dialogView.findViewById(R.id.ll_submit_rating);
        ll_submit_rating.setOnClickListener((View view) -> {
            try {


                ll_submit_rating.setClickable(false);





                viewModel.rateDriver(bookingId,rating_bar.getRating(),comments.getText().toString());



//                HashMap<String, String> data = new HashMap<>();
//                data.put("booking_id", "" + getIntent().getExtras().getString("" + IntentKeys.BOOKING_ID));
//                data.put("rating", "" + rating_bar.getRating());
//                data.put("comment", "" + comments.getText().toString());
//                apiManager._post(API_S.Tags.RATE_USER, API_S_NEW.Endpoints.RATE_DRIVER, data, sessionManager);
//                hideKeyboard();
//                alertDialog.hide();
            } catch (Exception e) {
                Toast.makeText(ReceiptActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                // Snackbar.make(fareActivityRoot, "" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                Log.e("" + TAG, "Exception caught while calling API " + e.getMessage());
            }
        });
        dialogBuilder.setView(dialogView);

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }






}
