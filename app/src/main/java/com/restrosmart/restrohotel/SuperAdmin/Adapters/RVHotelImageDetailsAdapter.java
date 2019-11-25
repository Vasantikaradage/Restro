package com.restrosmart.restrohotel.SuperAdmin.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.SuperAdmin.Activities.ActivityViewHotelDetails;
import com.restrosmart.restrohotel.SuperAdmin.Interfaces.ImageSelectedListerner;
import com.restrosmart.restrohotel.SuperAdmin.Models.HotelImageForm;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RVHotelImageDetailsAdapter  extends RecyclerView.Adapter<RVHotelImageDetailsAdapter.ItemViewHolder> {

    private  Context mContext;
    private  ArrayList<HotelImageForm> hotelImageFormArrayList;
    private ImageSelectedListerner imageSelectedListerner;
    private int selected_position = 0;




    public RVHotelImageDetailsAdapter(Context mContext, ArrayList<HotelImageForm> hotelImageFormArrayList, ImageSelectedListerner imageSelectedListerner) {
        this.mContext=mContext;
        this.hotelImageFormArrayList=hotelImageFormArrayList;
        this.imageSelectedListerner=imageSelectedListerner;
    }

    @NonNull
    @Override
    public RVHotelImageDetailsAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hotel_image_list_item, viewGroup, false);
        ItemViewHolder vh = new ItemViewHolder(view); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RVHotelImageDetailsAdapter.ItemViewHolder itemViewHolder, int i) {
        Picasso.with(mContext).load(hotelImageFormArrayList.get(i).getHotelImageName()).into(itemViewHolder.ivHotelimage);

    }

    @Override
    public int getItemCount() {
        return hotelImageFormArrayList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivHotelimage;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ivHotelimage=itemView.findViewById(R.id.ivHotelImg);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getAdapterPosition() == RecyclerView.NO_POSITION) return;

                    // Updating old as well as new positions
                    notifyItemChanged(selected_position);
                    selected_position = getAdapterPosition();
                    notifyItemChanged(selected_position);

                    imageSelectedListerner.SelectedImage(getAdapterPosition());
                }
            });
        }
    }
}
