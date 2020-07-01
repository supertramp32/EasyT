package com.seshra.everestcab.rides;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.seshra.everestcab.DriverWaitingActivity;
import com.seshra.everestcab.R;
import com.seshra.everestcab.models.ModelFragmentActiveRideds;
import com.seshra.everestcab.utils.IntentKeys;

import java.util.List;

public class ActiveRideAdapter extends RecyclerView.Adapter<ActiveRideAdapter.ActiveRideViewHolder> {


    List<ModelFragmentActiveRideds.DataBean> activeRideLists;
    Context context;




    public ActiveRideAdapter(List<ModelFragmentActiveRideds.DataBean> activeRideLists, Context context) {

        this.context = context;
        this.activeRideLists = activeRideLists;

    }

    @NonNull
    @Override
    public ActiveRideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        final View v = LayoutInflater.from(context).inflate(R.layout.item_active_rides, parent, false);
        final ActiveRideViewHolder activeRideViewHolder = new ActiveRideViewHolder(v);
        return activeRideViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveRideViewHolder holder, int position) {


        ModelFragmentActiveRideds.DataBean activeRideData = activeRideLists.get(position);

        holder.rideDate.setText(activeRideData.getSmall_text());
        holder.ridePickLocation.setText(activeRideData.getPick_text());
        holder.rideDropLocation.setText(activeRideData.getDrop_location());
        holder.ridePaymentType.setText(activeRideData.getValue_text());
        holder.ridePaymentAmount.setText(activeRideData.getEstimate_bill());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DriverWaitingActivity.class);
                intent.putExtra(IntentKeys.BOOKING_ID,""+activeRideData.getBooking_id());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return activeRideLists.size();
    }


    public class ActiveRideViewHolder extends RecyclerView.ViewHolder{

        TextView rideDate, ridePaymentType, ridePaymentAmount,ridePickLocation, rideDropLocation;


        public ActiveRideViewHolder(View itemView) {
            super(itemView);

            rideDate = itemView.findViewById(R.id.activeRideDate);
            ridePaymentType = itemView.findViewById(R.id.activeRidePaymentType);
            ridePaymentAmount = itemView.findViewById(R.id.activeRidePaymentAmount);
            ridePickLocation = itemView.findViewById(R.id.activeRidePickLocation);
            rideDropLocation = itemView.findViewById(R.id.activeRideDropLocation);


        }


    }





}