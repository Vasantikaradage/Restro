package com.restrosmart.restrohotel.SuperAdmin.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.SuperAdmin.Models.HotelForm;

import java.util.ArrayList;

public class RVHotelDeatailAdapter  extends RecyclerView.Adapter<RVHotelDeatailAdapter.MyHolder>  {
    private  Context mContext;
    private  ArrayList<HotelForm> hotelFormArrayList;

    public RVHotelDeatailAdapter(Context context, ArrayList<HotelForm> hotelFormArrayList) {
        this.mContext=context;
        this.hotelFormArrayList=hotelFormArrayList;
    }


    @NonNull
    @Override
    public RVHotelDeatailAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_hotel_detail_list, viewGroup, false);
        MyHolder vh = new MyHolder(view); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RVHotelDeatailAdapter.MyHolder myHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return hotelFormArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public MyHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
