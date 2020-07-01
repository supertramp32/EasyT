package com.seshra.everestcab.locations;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.seshra.everestcab.R;
import com.seshra.everestcab.models.ModelPopularPlaces;

import java.util.List;

public class PopularLocationAdapter extends RecyclerView.Adapter<PopularLocationAdapter.FavouriteLocationViewHolder> {


    List<ModelPopularPlaces.Data> popularPlaces;
    Context context;

    PopularClickListerner popularClickListerner;




    public PopularLocationAdapter(List<ModelPopularPlaces.Data> popularPlaces, Context context, PopularClickListerner popularClickListerner) {

        this.context = context;
        this.popularPlaces = popularPlaces;
        this.popularClickListerner = popularClickListerner;

    }

    @NonNull
    @Override
    public PopularLocationAdapter.FavouriteLocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        final View v = LayoutInflater.from(context).inflate(R.layout.item_popular_locations, parent, false);
        final FavouriteLocationViewHolder viewHolder = new FavouriteLocationViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteLocationViewHolder holder, int position) {


        ModelPopularPlaces.Data popularPlace = popularPlaces.get(position);
        holder.locationName.setText(popularPlace.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popularClickListerner.onClick(popularPlace.getName(),popularPlace.getLatitude(),popularPlace.getLongitude());
            }
        });





    }

    @Override
    public int getItemCount() {
        return popularPlaces.size();
    }


    public class FavouriteLocationViewHolder extends RecyclerView.ViewHolder{

       TextView locationName;


        public FavouriteLocationViewHolder(View itemView) {
            super(itemView);

           locationName = itemView.findViewById(R.id.popularLocationName);


        }


    }


    public interface PopularClickListerner {
        void onClick(String name, String lat, String lon);
    }




}