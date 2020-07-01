package com.seshra.everestcab.rides;



import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
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

import com.seshra.everestcab.MainActivity;
import com.seshra.everestcab.R;
import com.seshra.everestcab.models.ModelFragmentActiveRideds;
import com.seshra.everestcab.models.ModelResultCheck;
import com.seshra.everestcab.service.FetchActiveRideService;
import com.seshra.everestcab.utils.SingletonGson;
import com.seshra.everestcab.viewmodel.ActiveRideFragmentViewModel;


public class ActiveRideFragment extends Fragment {



    RecyclerView recyclerView;

    ActiveRideFragmentViewModel viewModel;

    ActiveRideAdapter activeRideAdapter;

    SwipeRefreshLayout swiperefreshLayout;

    ImageView placeholder;

    TextView bookRide;




    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            swiperefreshLayout.setRefreshing(false);

            String result = intent.getStringExtra(FetchActiveRideService.TRIP_HISTORY_ACTIVE_MESSAGE_KEY);

            if(!result.equals("0")) {

                ModelResultCheck modelResultCheck =
                        SingletonGson.getInstance().fromJson("" + result, ModelResultCheck.class);

                if(modelResultCheck.getResult().equals("0")){
                       placeholder.setVisibility(View.VISIBLE);
                        bookRide.setVisibility(View.VISIBLE);

                }

                if(modelResultCheck.getResult().equals("1")) {
                    placeholder.setVisibility(View.GONE);
                    bookRide.setVisibility(View.GONE);
                    ModelFragmentActiveRideds activeRideds = SingletonGson.getInstance().fromJson("" +
                            result, ModelFragmentActiveRideds.class);
                    activeRideAdapter = new ActiveRideAdapter(activeRideds.getData(), getContext());
                    recyclerView.setAdapter(activeRideAdapter);
                }

//                }else {
//                    Toast.makeText(getContext(),modelResultCheck.getMessage(),Toast.LENGTH_LONG).show();
//                }

            }else {
                Toast.makeText(getContext(),"Error Fetching Data.",Toast.LENGTH_LONG).show();

            }


        }
    };


    public ActiveRideFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_active_ride, container, false);

        recyclerView = view.findViewById(R.id.activeRideRecycler);
        swiperefreshLayout = view.findViewById(R.id.swiperefreshActiveHistory);
        placeholder = view.findViewById(R.id.placeHolder);
        bookRide = view.findViewById(R.id.bookRideText);

        initRecyclerView();
        initViewModel();


        swiperefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                placeholder.setVisibility(View.GONE);
                bookRide.setVisibility(View.GONE);
                recyclerView.setAdapter(null);
                viewModel.getActiveHistoryRides();

                swiperefreshLayout.setRefreshing(false);



            }
        });


        bookRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });



        return view;
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(getActivity()).get(ActiveRideFragmentViewModel.class);
        viewModel.getActiveHistoryRides();
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
                        new IntentFilter(FetchActiveRideService.TRIP_HISTORY_ACTIVE_MESSAGE));
    }


    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity())
                .unregisterReceiver(broadcastReceiver);
    }



}
