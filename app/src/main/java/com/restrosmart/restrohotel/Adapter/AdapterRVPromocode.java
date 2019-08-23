package com.restrosmart.restrohotel.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.restrosmart.restrohotel.Interfaces.ButtonListerner;
import com.restrosmart.restrohotel.Interfaces.PositionListener;
import com.restrosmart.restrohotel.Model.PromoCodeForm;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class AdapterRVPromocode extends RecyclerView.Adapter<AdapterRVPromocode.ItemViewHolder> {
    private Context mContext;
    private  ArrayList<PromoCodeForm> promoCodeFormArrayList;
    private  ButtonListerner buttonListerner;
    private  PositionListener positionListener;

    public AdapterRVPromocode(Context context, ArrayList<PromoCodeForm> arrayListPromoCode, PositionListener positionListener, ButtonListerner buttonListerner) {
   this.mContext=context;
   this.promoCodeFormArrayList=arrayListPromoCode;
   this.buttonListerner=buttonListerner;
   this.positionListener=positionListener;

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView;
        if(i == R.layout.item_promocode) {
            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_promocode, viewGroup, false);
        }
        else
        {
            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_add_promocode, viewGroup, false);

        }
       ItemViewHolder ItemViewHolder=new ItemViewHolder(itemView);
        return ItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder itemViewHolder, int i) {

        if(i == promoCodeFormArrayList.size()) {
            itemViewHolder.imgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    buttonListerner.getEditListenerPosition(1);
                    //itemViewHolder.linearLayout.setVisibility(View.VISIBLE);
                   // Toast.makeText(mContext, "Button Clicked", Toast.LENGTH_LONG).show();
                }
            });
        }else {
            itemViewHolder.tvOffer.setText(promoCodeFormArrayList.get(i).getOffer()+" "+promoCodeFormArrayList.get(i).getOfferValue());
            itemViewHolder.tvDate.setText(promoCodeFormArrayList.get(i).getDate());


        }

    }

    @Override
    public int getItemCount() {
        return promoCodeFormArrayList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == promoCodeFormArrayList.size()) ? R.layout.item_add_promocode : R.layout.item_promocode;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private  TextView tvDate,tvOffer;
        private CardView imgBtn;
        private LinearLayout linearLayout;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate=itemView.findViewById(R.id.tv_date);
            tvOffer=itemView.findViewById(R.id.tv_offer);
            imgBtn=itemView.findViewById(R.id.cardview_add);
            linearLayout=itemView.findViewById(R.id.linear);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    positionListener.positionListern(getAdapterPosition());
                }
            });


        }
    }
}
