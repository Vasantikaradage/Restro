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

public class AdapterRVScratchCardMenu extends RecyclerView.Adapter<AdapterRVScratchCardMenu.ItemViewHolder> {
    private  Context mContext;
    private  ArrayList<OfferMenuForm> offerMenuFormArrayList;


    public AdapterRVScratchCardMenu(Context applicationContext, ArrayList<OfferMenuForm> offerMenuForms) {
        this.mContext=applicationContext;
        this.offerMenuFormArrayList=offerMenuForms;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView;
        itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_rush_hours_menu, viewGroup, false);
        ItemViewHolder ItemViewHolder = new ItemViewHolder(itemView);
        return ItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        itemViewHolder.tvMenuName.setText(offerMenuFormArrayList.get(i).getMenu_Name());
        itemViewHolder.tvMenuPrice.setText(offerMenuFormArrayList.get(i).getMenu_Ori_Price());
        itemViewHolder.tvMenuOffer.setText(offerMenuFormArrayList.get(i).getMenu_Offer_Price());
    }

    @Override
    public int getItemCount() {
        return offerMenuFormArrayList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMenuName, tvMenuPrice, tvMenuOffer;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMenuName = itemView.findViewById(R.id.tv_menu_name);
            tvMenuPrice = itemView.findViewById(R.id.tv_menu_price);
            tvMenuOffer = itemView.findViewById(R.id.tv_offer_price);
        }
    }
}
