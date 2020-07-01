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

import com.seshra.everestcab.R;
import com.seshra.everestcab.models.ModelFragmentPastRides;
import com.seshra.everestcab.models.ModelResultCheck;
import com.seshra.everestcab.service.FetchPastRideService;
import com.seshra.everestcab.utils.PaginationListener;
import com.seshra.everestcab.utils.SingletonGson;
import com.seshra.everestcab.viewmodel.PastRideFragmentViewModel;

import java.util.ArrayList;

import static com.seshra.everestcab.utils.PaginationListener.PAGE_START;

public class PastRideFragment extends Fragment {

    RecyclerView recyclerView;

    PastRideAdapter pastRideAdapter;

    PastRideFragmentViewModel viewModel;

    SwipeRefreshLayout swiperefreshLayout;

    ImageView placeholder;

    TextView bookRide;



    int total_pages = 0;
    int current_page = 1;
    LinearLayoutManager mLayoutManager;
    private boolean isLoading = false;
    private boolean isLastPage = false;




    ArrayList<ModelFragmentPastRides.DataBean> pastRidesList = new ArrayList<>();



    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            swiperefreshLayout.setRefreshing(false);

            String result = intent.getStringExtra(FetchPastRideService.TRIP_HISTORY_PAST_MESSAGE_KEY);
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

                    ModelFragmentPastRides pastRides = SingletonGson.getInstance().fromJson("" +
                            result, ModelFragmentPastRides.class);
                    total_pages = pastRides.getTotal_pages();



                    pastRideAdapter.addItems(pastRides.getData());

                        recyclerView.setAdapter(pastRideAdapter);


                    if (current_page != PAGE_START)
                        pastRideAdapter.removeLoading();

                    // check whether is last page or not
                    if (current_page < total_pages) {
                        pastRideAdapter.addLoading();
                    } else {
                        isLastPage = true;
                        pastRideAdapter.removeLoading();

                    }
                    isLoading = false;



//                    pastRidesList.add(pastRides.getData());








                }

            }else {
                Toast.makeText(getContext(),"Error Fetching Data.",Toast.LENGTH_LONG).show();

            }


        }
    };

    public PastRideFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_past_ride,container, false);

        recyclerView = view.findViewById(R.id.pastRideRecycler);
        swiperefreshLayout = view.findViewById(R.id.swiperefreshPastHistory);
        placeholder = view.findViewById(R.id.pastPlaceHolder);
        bookRide = view.findViewById(R.id.pastRideBook);

        initRecyclerView();



        swiperefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                current_page = 1;
                total_pages = 0;
                isLastPage = false;
                pastRideAdapter.clear();
                placeholder.setVisibility(View.GONE);
                bookRide.setVisibility(View.GONE);
                    recyclerView.setAdapter(null);
                    viewModel.getPastRides("1");
                swiperefreshLayout.setRefreshing(false);



            }
        });







        return view;

    }


    private void initRecyclerView() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        pastRideAdapter = new PastRideAdapter(pastRidesList, getContext());
        recyclerView.setAdapter(pastRideAdapter);

        initViewModel();


        recyclerView.addOnScrollListener(new PaginationListener(mLayoutManager) {
            @Override
            protected void loadMoreItems() {


                current_page++;
                isLoading = true;


                if(current_page<=total_pages) {
                    viewModel.getPastRides("" + current_page);
                } else {
                    isLastPage = true;
                    pastRideAdapter.removeLoading();

                }

            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();


        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(broadcastReceiver,
                        new IntentFilter(FetchPastRideService.TRIP_HISTORY_PAST_MESSAGE));
    }


    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity())
                .unregisterReceiver(broadcastReceiver);
    }




    private void initViewModel() {
        viewModel = ViewModelProviders.of(getActivity()).get(PastRideFragmentViewModel.class);

        swiperefreshLayout.setRefreshing(true);
        viewModel.getPastRides(""+current_page);
        isLoading = true;
    }

}
