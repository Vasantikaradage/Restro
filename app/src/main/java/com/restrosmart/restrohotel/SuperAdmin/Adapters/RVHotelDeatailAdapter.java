package com.restrosmart.restrohotel.SuperAdmin.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.SuperAdmin.Activities.ActivityViewHotelDetails;
import com.restrosmart.restrohotel.SuperAdmin.Models.HotelForm;
import com.restrosmart.restrohotel.SuperAdmin.Models.HotelImageForm;
import com.squareup.picasso.Picasso;

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
        myHolder.tvHotelName.setText(hotelFormArrayList.get(i).getHotelName());
        myHolder.tvHotelMob.setText(hotelFormArrayList.get(i).getHotelMob());
        myHolder.tvHotelEmail.setText(hotelFormArrayList.get(i).getHotelEmail());
        myHolder.tvHotelAddress.setText(hotelFormArrayList.get(i).getHotelAddress());

        if(hotelFormArrayList.get(i).getHotelImageFormArrayList().size()!=0) {
            Picasso.with(mContext).load(hotelFormArrayList.get(i).getHotelImageFormArrayList().get(0).getHotelImageName()).into(myHolder.image);
        }
    }

    @Override
    public int getItemCount() {
        return hotelFormArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView tvHotelName,tvHotelMob,tvHotelEmail,tvHotelAddress;
        private ImageView image;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvHotelName=itemView.findViewById(R.id.tv_hotel_name);
            tvHotelEmail=itemView.findViewById(R.id.tv_hotel_email);
            tvHotelMob=itemView.findViewById(R.id.tv_hotel_mob);
            tvHotelAddress=itemView.findViewById(R.id.tv_hotel_address);
            image=itemView.findViewById(R.id.image);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    HotelForm  hotelForm=hotelFormArrayList.get(getAdapterPosition());
                    Intent intent=new Intent(mContext, ActivityViewHotelDetails.class);
                    Bundle bundle = new Bundle();
                   // bundle.putSerializable("hotelInfo", (hotelForm));
                    bundle.putParcelableArrayList("hotelImags",hotelForm.getHotelImageFormArrayList());
                    bundle.putParcelableArrayList("CuisineList",hotelForm.getCuisineFormArrayList());
                    bundle.putParcelableArrayList("TagsList",hotelForm.getTagsFormArrayList());
                    intent.putExtras(bundle);
                    intent.putExtra("hotelInfo", (hotelForm));
                    mContext.startActivity(intent);

                }
            });
        }
    }
}
