package com.restrosmart.restrohotel.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.restrosmart.restrohotel.Admin.ActivityDisplayDailyOffer;
import com.restrosmart.restrohotel.Admin.ActivityDisplayPromocode;
import com.restrosmart.restrohotel.Admin.ActivityDisplayRushHours;
import com.restrosmart.restrohotel.Admin.ActivityDisplayScratchCard;

import com.restrosmart.restrohotel.Model.OfferNameForm;
import com.restrosmart.restrohotel.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by SHREE on 01/10/2018.
 */

public class DailyOffersAdapter extends RecyclerView.Adapter<DailyOffersAdapter.MyHolderView> {
    private  Context context;
    private ArrayList<OfferNameForm> offerFormArrayList;

    public DailyOffersAdapter(Context context, ArrayList<OfferNameForm> arrayList) {
        this.context=context;
        this.offerFormArrayList=arrayList;

    }


    @NonNull
    @Override
    public MyHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.fragment_daily_offer_listview, parent, false);
        return new MyHolderView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolderView holder, final int position) {
        Picasso.with(context)
                .load(offerFormArrayList.get(position).getOfferImage())
                .resize(500,500)
                .into(holder.mImageView);
        holder.tvOfferTitle.setText(offerFormArrayList.get(position).getOfferName());
        holder.tvOfferDiscrip.setText(offerFormArrayList.get(position).getOfferDescription());

    }


    @Override
    public int getItemCount() {
        return offerFormArrayList.size();
    }

    public class MyHolderView extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView  tvOfferTitle,tvOfferDiscrip;


        public MyHolderView(View itemView) {
            super(itemView);
            mImageView=itemView.findViewById(R.id.iv_offer_image);
            tvOfferTitle=itemView.findViewById(R.id.tv_offer_title);
            tvOfferDiscrip=itemView.findViewById(R.id.tv_offer_description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int offerId=offerFormArrayList.get(getAdapterPosition()).getOfferId();
                    if(offerId==3)
                    {
                        Intent intent=new Intent(context, ActivityDisplayPromocode.class);
                        intent.putExtra("offerTypeId",offerId);
                        context.startActivity(intent);
                    }
                    else if(offerId==4)
                    {
                        Intent intent=new Intent(context, ActivityDisplayRushHours.class);
                        intent.putExtra("offerTypeId",offerId);
                        context.startActivity(intent);

                    }
                    else if(offerId==2)
                    {
                        Intent intent=new Intent(context, ActivityDisplayScratchCard.class);
                        intent.putExtra("offerTypeId",offerId);
                        context.startActivity(intent);
                    }
                    else  if(offerId==1)
                    {
                        Intent intent=new Intent(context, ActivityDisplayDailyOffer.class);
                        intent.putExtra("offerTypeId",offerId);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
