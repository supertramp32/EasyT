package com.seshra.everestcab.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.seshra.everestcab.R;
import com.seshra.everestcab.models.GeneralNotifications;
import com.seshra.everestcab.models.ModelResultCheck;
import com.seshra.everestcab.service.FetchGeneralNotificationsService;
import com.seshra.everestcab.utils.SingletonGson;
import com.seshra.everestcab.viewmodel.GeneralNotificationsFragmentViewModel;

public class GeneralNotificationFragment extends Fragment {

    RecyclerView recyclerView;
    GeneralNotificationsFragmentViewModel viewModel;
    GeneralNotificationsAdapter generalNotificationsAdapter;
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
                    generalNotificationsAdapter = new GeneralNotificationsAdapter(generalNotifications.getData(), getContext());
                    recyclerView.setAdapter(generalNotificationsAdapter);
                }
//                else {
//                    Toast.makeText(getContext(),modelResultCheck.getMessage(),Toast.LENGTH_LONG).show();
//                }

            }else {
                Toast.makeText(getContext(),"Error Fetching Data.",Toast.LENGTH_LONG).show();

            }


        }
    };



    public GeneralNotificationFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_general_notifications, container,false);

        recyclerView = view.findViewById(R.id.generalNotificationsRecycler);
        swiperefreshLayout = view.findViewById(R.id.swiperefreshGeneralNotifications);
        placeholder = view.findViewById(R.id.placeHolder);

        initRecyclerView();
        initViewModel();

        swiperefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                recyclerView.setAdapter(null);
                viewModel.getGeneralNotifications();


            }
        });


        return view;
    }


    private void initViewModel() {
        viewModel = ViewModelProviders.of(getActivity()).get(GeneralNotificationsFragmentViewModel.class);
        viewModel.getGeneralNotifications();
        swiperefreshLayout.setRefreshing(true);
    }


    private void initRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    @Override
    public void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(broadcastReceiver,
                        new IntentFilter(FetchGeneralNotificationsService.GENERAL_NOTIFICATIONS_MESSAGE));
    }


    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity())
                .unregisterReceiver(broadcastReceiver);
    }
}
