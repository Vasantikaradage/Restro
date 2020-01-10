package com.restrosmart.restrohotel.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.restrosmart.restrohotel.Model.OfferMenuForm;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class AdapterRVDailyOfferMenu  extends RecyclerView.Adapter<AdapterRVDailyOfferMenu.ItemViewHolder> {
    private  Context mContext;
    private  ArrayList<OfferMenuForm> offerMenuFormArrayList;

    public AdapterRVDailyOfferMenu(Context applicationContext, ArrayList<OfferMenuForm> offerMenuForms) {
        this.mContext=applicationContext;
        this.offerMenuFormArrayList=offerMenuForms;
    }

    @NonNull
    @Override
    public AdapterRVDailyOfferMenu.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rv_daily_offer_menu_item, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRVDailyOfferMenu.ItemViewHolder itemViewHolder, int i) {

       // if(offerMenuFormArrayList.get(i).getBuyGetId()==1)
       // {
            itemViewHolder.tvMenuName.setText(offerMenuFormArrayList.get(i).getMenu_Name());
            itemViewHolder.tvMenuPrice.setText("\u20B9 "+(offerMenuFormArrayList.get(i).getMenu_Ori_Price()));
       // }

    }

    @Override
    public int getItemCount() {
        return offerMenuFormArrayList.size();

    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView  tvMenuName,tvMenuPrice;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMenuName=itemView.findViewById(R.id.tv_menu_name);
            tvMenuPrice=itemView.findViewById(R.id.tv_menu_price);

        }
    }
}
