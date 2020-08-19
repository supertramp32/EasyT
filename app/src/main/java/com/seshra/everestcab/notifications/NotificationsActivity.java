package com.seshra.everestcab.notifications;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.seshra.everestcab.R;
import com.seshra.everestcab.models.GeneralNotifications;
import com.seshra.everestcab.models.ModelResultCheck;
import com.seshra.everestcab.service.FetchGeneralNotificationsService;
import com.seshra.everestcab.utils.SingletonGson;
import com.seshra.everestcab.viewmodel.NotificationActivityViewModel;

public class NotificationsActivity extends AppCompatActivity {



    private Toolbar toolbar;
    RecyclerView recyclerView;
    NotificationActivityViewModel viewModel;
    NotificationsAdapter notificationsAdapter;
    SwipeRefreshLayout swiperefreshLayout;
    ImageView placeholder;



    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            swiperefreshLayout.setRefreshing(false);

            String result = intent.getStringExtra(FetchGeneralNotificationsService.GENERAL_NOTIFICATIONS_MESSAGE_KEY);

            if(!result.equals("0")) {

                ModelResultCheck modelResultCheck =
                        SingletonGson.getInstance().fromJson("" + result, ModelResultCheck.class);

                if(modelResultCheck.getResult().equals("1")) {

                    placeholder.setVisibility(View.GONE);
                    GeneralNotifications generalNotifications = SingletonGson.getInstance().fromJson("" +
                            result, GeneralNotifications.class);
                    notificationsAdapter = new NotificationsAdapter(generalNotifications.getData(),NotificationsActivity.this);
                    recyclerView.setAdapter(notificationsAdapter);
                }
//                else {
//                    Toast.makeText(getContext(),modelResultCheck.getMessage(),Toast.LENGTH_LONG).show();
//                }

            }else {
                Toast.makeText(NotificationsActivity.this,"Error Fetching Data.",Toast.LENGTH_LONG).show();

            }


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        toolbar = findViewById(R.id.toolbarNotifications);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = findViewById(R.id.generalNotificationsRecycler);
        swiperefreshLayout = findViewById(R.id.swiperefreshGeneralNotifications);
        placeholder = findViewById(R.id.placeHolder);

        initRecyclerView();
        initViewModel();

        swiperefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                recyclerView.setAdapter(null);
                viewModel.getGeneralNotifications();


            }
        });


    }


    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(NotificationActivityViewModel.class);
        viewModel.getGeneralNotifications();
        swiperefreshLayout.setRefreshing(true);

    }


    private void initRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    @Override
    public void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(this)
                .registerReceiver(broadcastReceiver,
                        new IntentFilter(FetchGeneralNotificationsService.GENERAL_NOTIFICATIONS_MESSAGE));
    }


    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this)
                .unregisterReceiver(broadcastReceiver);
    }


}
