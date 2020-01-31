package com.restrosmart.restrohotel.SuperAdmin.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.restrosmart.restrohotel.Interfaces.PositionListener;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.SuperAdmin.Activities.ActivityViewHotelDetails;
import com.restrosmart.restrohotel.SuperAdmin.Interfaces.ImageSelectedListerner;
import com.restrosmart.restrohotel.SuperAdmin.Models.HotelImageForm;
import com.restrosmart.restrohotel.Utils.FilePath;
import com.restrosmart.restrohotel.Utils.ImageFilePath;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static com.restrosmart.restrohotel.ConstantVariables.PICK_GALLERY_IMAGE;

public class RVHotelImageDetailsAdapter  extends RecyclerView.Adapter<RVHotelImageDetailsAdapter.ItemViewHolder> {

    private  Context mContext;
    private  ArrayList<HotelImageForm> hotelImageFormArrayList;
    private ImageSelectedListerner imageSelectedListerner;
    private int selected_position = 0;
    private  PositionListener positionListener;




    public RVHotelImageDetailsAdapter(Context mContext, ArrayList<HotelImageForm> hotelImageFormArrayList, PositionListener positionListener,ImageSelectedListerner imageSelectedListerner) {
        this.mContext=mContext;
        this.hotelImageFormArrayList=hotelImageFormArrayList;
        this.imageSelectedListerner=imageSelectedListerner;
        this.positionListener=positionListener;
    }

    @NonNull
    @Override
    public RVHotelImageDetailsAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        if(i==R.layout.hotel_image_list_item) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hotel_image_list_item, viewGroup, false);

        }else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hotel_image_add_item, viewGroup, false);

        }
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVHotelImageDetailsAdapter.ItemViewHolder itemViewHolder, final int i) {

        if(i == hotelImageFormArrayList.size() ) {

            if(hotelImageFormArrayList.size()>=6) {
                itemViewHolder.ivAddBtn.setVisibility(View.GONE);
            }else {
                itemViewHolder.ivAddBtn.setVisibility(View.VISIBLE);
                itemViewHolder.ivAddBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        positionListener.positionListern(i);

                    }
                });
            }

        }else {
           // itemViewHolder.ivAddBtn.setVisibility(View.GONE);
            Picasso.with(mContext).load(hotelImageFormArrayList.get(i).getHotelImageName()).into(itemViewHolder.ivHotelimage);

               }
    }

    @Override
    public int getItemCount() {
        return hotelImageFormArrayList.size()+1;
    }






    @Override
    public int getItemViewType(int position) {
        return (position == hotelImageFormArrayList.size()) ? R.layout.hotel_image_add_item : R.layout.hotel_image_list_item;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivHotelimage,ivAddBtn;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ivHotelimage=itemView.findViewById(R.id.ivHotelImg);
            ivAddBtn=itemView.findViewById(R.id.ivImageAdd);

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
