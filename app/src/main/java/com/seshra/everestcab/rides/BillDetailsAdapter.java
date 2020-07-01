package com.seshra.everestcab.rides;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.seshra.everestcab.R;
import com.seshra.everestcab.models.ModelSpecificTripDetails;

import java.util.List;

public class BillDetailsAdapter extends RecyclerView.Adapter<BillDetailsAdapter.BillDetailsViewHolder> {


    List<ModelSpecificTripDetails.DataBeanXXXXXX.HolderReceiptBean.DataBeanXXXXX> billDetails;
    Context context;




    public BillDetailsAdapter(List<ModelSpecificTripDetails.DataBeanXXXXXX.HolderReceiptBean.DataBeanXXXXX> billDetails, Context context) {

        this.context = context;
        this.billDetails = billDetails;

    }

    @NonNull
    @Override
    public BillDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        final View v = LayoutInflater.from(context).inflate(R.layout.item_bill_details, parent, false);
        final BillDetailsViewHolder billDetailsViewHolder = new BillDetailsViewHolder(v);
        return billDetailsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BillDetailsViewHolder holder, int position) {

        ModelSpecificTripDetails.DataBeanXXXXXX.HolderReceiptBean.DataBeanXXXXX billdetails = billDetails.get(position);

        if(position==0){
            holder.billTitle.setText(billdetails.getHighlighted_text());
            holder.billTitle.setTypeface(null, Typeface.BOLD);
            holder.billFare.setText("");
            holder.billTitle.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }else {
            holder.billTitle.setText(billdetails.getHighlighted_text());
            holder.billFare.setText(billdetails.getValue_text());
        }






    }

    @Override
    public int getItemCount() {
        return billDetails.size();
    }


    public class BillDetailsViewHolder extends RecyclerView.ViewHolder{


        TextView billTitle, billFare;

        public BillDetailsViewHolder(View itemView) {
            super(itemView);

            billTitle = itemView.findViewById(R.id.billDetailsTitle);
            billFare = itemView.findViewById(R.id.billDetailsFare);


        }


    }





}