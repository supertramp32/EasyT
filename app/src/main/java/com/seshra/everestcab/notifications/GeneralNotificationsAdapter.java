package com.seshra.everestcab.notifications;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.seshra.everestcab.MainActivity;
import com.seshra.everestcab.R;
import com.seshra.everestcab.models.GeneralNotifications;
import com.seshra.everestcab.utils.IntentKeys;


import java.util.List;

public class GeneralNotificationsAdapter extends RecyclerView.Adapter<GeneralNotificationsAdapter.GeneralNotificationsViewHolder> {


    List<GeneralNotifications.Data> notificationsData;
    Context context;

    SharedPreferences sharedPreferences;



    public GeneralNotificationsAdapter(List<GeneralNotifications.Data> notificationsData, Context context) {

        this.context = context;
        this.notificationsData = notificationsData;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

    }

    @NonNull
    @Override
    public GeneralNotificationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        final View v = LayoutInflater.from(context).inflate(R.layout.item_notification_general, parent, false);
        final GeneralNotificationsViewHolder viewHolder = new GeneralNotificationsViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GeneralNotificationsAdapter.GeneralNotificationsViewHolder holder, int position) {


        GeneralNotifications.Data generalData = notificationsData.get(position);
        holder.notDate.setText(generalData.getCreated_at());
        holder.notTitle.setText(generalData.getTitle());
        holder.notDesc.setText(generalData.getMessage());
        Glide.with(context).load(generalData.getImage()).into(holder.notImage);

        try {


            if(generalData.isHas_code()){

                holder.applyCode.setVisibility(View.VISIBLE);
                sharedPreferences.edit().putString(IntentKeys.PROMO_CODE,generalData.getCode()).apply();

                holder.applyCode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, MainActivity.class));
                    }
                });

            }else {
                holder.applyCode.setVisibility(View.GONE);

            }

        }catch (Exception e){
            e.printStackTrace();
        }







    }

    @Override
    public int getItemCount() {
        return notificationsData.size();
    }


    public class GeneralNotificationsViewHolder extends RecyclerView.ViewHolder{

        TextView notDate, notTitle, notDesc;
        ImageView notImage;
        TextView applyCode;


        public GeneralNotificationsViewHolder(View itemView) {
            super(itemView);

            notDate = itemView.findViewById(R.id.generalDate);
            notTitle = itemView.findViewById(R.id.generalTitle);
            notDesc = itemView.findViewById(R.id.generalDescription);
            notImage = itemView.findViewById(R.id.generalImage);
            applyCode = itemView.findViewById(R.id.generalApplyCode);


        }


    }





}