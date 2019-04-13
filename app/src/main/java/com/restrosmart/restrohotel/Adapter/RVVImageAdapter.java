package com.restrosmart.restrohotel.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.restrosmart.restrohotel.Interfaces.PositionListener;
import com.restrosmart.restrohotel.Model.ImageForm;
import com.restrosmart.restrohotel.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by SHREE on 21/11/2018.
 */

public class RVVImageAdapter extends RecyclerView.Adapter<RVVImageAdapter.ViewHolder>  {
    Context context;
    ArrayList<ImageForm> imageArrayList;
    PositionListener positionListener;
    String lastpos=null;

    String mImage;

    public RVVImageAdapter(Context context, ArrayList<ImageForm> arrayList_image, String mUpdatedImage, PositionListener positionListener) {
        this.context = context;
        this.imageArrayList = arrayList_image;
        this.positionListener = positionListener;
        this.mImage = mUpdatedImage;

    }


    @NonNull
    @Override
    public RVVImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_image_category_listview, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final RVVImageAdapter.ViewHolder holder, final int position) {
        if (mImage != null) {

            if (mImage.equals(imageArrayList.get(position).getImage())) {
                Picasso.with(context).load(mImage).resize(500, 500).into(holder.imageView);
                Resources resources = context.getResources();
                holder.imageView.setBackgroundColor(resources.getColor(R.color.blue_btn_bg_color));
                // positionListener.positionListern(position);
            } else {
                Picasso.with(context).load(imageArrayList.get(position).getImage()).resize(500, 500).into(holder.imageView);
            }

        } else {
            Picasso.with(context).load(imageArrayList.get(position).getImage()).resize(500, 500).into(holder.imageView);


        }

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (holder.getAdapterPosition() == position) {
                    Resources resources = context.getResources();
                    holder.imageView.setBackgroundColor(resources.getColor(R.color.blue_btn_bg_color));
                    positionListener.positionListern(position);
                    notifyItemChanged(position);
                }


            }

        });

        Resources resources = context.getResources();
        holder.imageView.setBackgroundColor(resources.getColor(R.color.colorGrey));

        lastpos= String.valueOf(position);



    }




    @Override
    public int getItemCount() {
        return imageArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;


        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.category_image);
        }


    }
}
