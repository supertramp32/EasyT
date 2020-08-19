package com.seshra.everestcab.rides;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.seshra.everestcab.R;
import com.seshra.everestcab.models.ModelFragmentPastRides;
import com.seshra.everestcab.utils.IntentKeys;

import java.util.List;

public class PastRideAdapter extends RecyclerView.Adapter<BaseViewHolder> {


    List<ModelFragmentPastRides.DataBean> pastRidesLists;
    Context context;


    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    private boolean isLoaderVisible = false;




    public PastRideAdapter(List<ModelFragmentPastRides.DataBean> pastRidesLists, Context context) {

        this.context = context;
        this.pastRidesLists = pastRidesLists;

    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                final View v = LayoutInflater.from(context).inflate(R.layout.item_active_rides, parent, false);
                return new ViewHolder(v);
            case VIEW_TYPE_LOADING:
                final View vP = LayoutInflater.from(context).inflate(R.layout.item_loading, parent, false);
                return new ProgressHolder(vP);
            default:
                return null;
        }

//        final View v = LayoutInflater.from(context).inflate(R.layout.item_active_rides, parent, false);
//        final PastRideViewHolder pastRideViewHolder = new PastRideViewHolder(v);
//        return pastRideViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {

        holder.onBind(position);


//        ModelFragmentPastRides.DataBean pastRidesData = pastRidesLists.get(position);
//
//
//
//        holder.rideDate.setText(pastRidesData.getHighlighted_small_text());
//        holder.ridePickLocation.setText(pastRidesData.getPick_text());
//        holder.rideDropLocation.setText(pastRidesData.getDrop_location());
//        holder.ridePaymentType.setText(pastRidesData.getPayment_method());
//        holder.ridePaymentAmount.setText(pastRidesData.getValue_text());
//        holder.rideStatus.setText(pastRidesData.getStatus_text());
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, TripDetailActivity.class);
//                intent.putExtra(IntentKeys.BOOKING_ID,""+pastRidesData.getBooking_id());
//                context.startActivity(intent);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return pastRidesLists == null ? 0 : pastRidesLists.size();
    }


        public void clear() {
            pastRidesLists.clear();
            notifyDataSetChanged();
        }




//    public class PastRideViewHolder extends RecyclerView.ViewHolder{
//
//        TextView rideDate, ridePaymentType, ridePaymentAmount,ridePickLocation, rideDropLocation;
//        TextView rideStatus;
//
//
//        public PastRideViewHolder(View itemView) {
//            super(itemView);
//
//            rideDate = itemView.findViewById(R.id.activeRideDate);
//            ridePaymentType = itemView.findViewById(R.id.activeRidePaymentType);
//            ridePaymentAmount = itemView.findViewById(R.id.activeRidePaymentAmount);
//            ridePickLocation = itemView.findViewById(R.id.activeRidePickLocation);
//            rideDropLocation = itemView.findViewById(R.id.activeRideDropLocation);
//
//            rideStatus = itemView.findViewById(R.id.activeRideStatus);
//
//        }
//
//
//    }


    public void addItems(List<ModelFragmentPastRides.DataBean> postItems) {
        pastRidesLists.addAll(postItems);
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        if (isLoaderVisible) {
            return position == pastRidesLists.size()-1  ? VIEW_TYPE_LOADING : VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_NORMAL;
        }

    }


    public void addLoading() {
        isLoaderVisible = true;
//        pastRidesLists.add(new ModelFragmentPastRides.DataBean());
//        notifyItemInserted(pastRidesLists.size() - 1);
    }


    public void removeLoading() {
        isLoaderVisible = false;
        int position = pastRidesLists.size() - 1;
//        ModelFragmentPastRides.DataBean item = getItem(position);
//        if (item != null) {
//            pastRidesLists.remove(position);
//            notifyItemRemoved(position);
//        }

    }

    ModelFragmentPastRides.DataBean getItem(int position) {
        return pastRidesLists.get(position);
    }



    public class ViewHolder extends BaseViewHolder {

        TextView rideDate, ridePaymentType, ridePaymentAmount,ridePickLocation, rideDropLocation;
        TextView rideStatus;

        ViewHolder(View itemView) {
            super(itemView);

            rideDate = itemView.findViewById(R.id.activeRideDate);
            ridePaymentType = itemView.findViewById(R.id.activeRidePaymentType);
            ridePaymentAmount = itemView.findViewById(R.id.activeRidePaymentAmount);
            ridePickLocation = itemView.findViewById(R.id.activeRidePickLocation);
            rideDropLocation = itemView.findViewById(R.id.activeRideDropLocation);

            rideStatus = itemView.findViewById(R.id.activeRideStatus);

        }

        protected void clear() {
        }


        public void onBind(int position) {
            super.onBind(position);

            ModelFragmentPastRides.DataBean pastRidesData = pastRidesLists.get(position);
            rideDate.setText(pastRidesData.getHighlighted_small_text());
            ridePickLocation.setText(pastRidesData.getPick_text());
            rideDropLocation.setText(pastRidesData.getDrop_location());
            ridePaymentType.setText(pastRidesData.getPayment_method());

            ridePaymentAmount.setText(pastRidesData.getValue_text());
            rideStatus.setText(pastRidesData.getStatus_text());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TripDetailActivity.class);
                    intent.putExtra(IntentKeys.BOOKING_ID,""+pastRidesData.getBooking_id());
                    context.startActivity(intent);
                }
            });

        }
    }




    public class ProgressHolder extends BaseViewHolder {
        ProgressHolder(View itemView) {
            super(itemView);
        }
        @Override
        protected void clear() {
        }
    }





}