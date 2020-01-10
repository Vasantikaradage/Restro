package com.restrosmart.restrohotel.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.restrosmart.restrohotel.Model.OfferMenuForm;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class AdapterRVDailyOfferMenuGet  extends RecyclerView.Adapter<AdapterRVDailyOfferMenuGet.ItemViewHolder>{
    private Context mContext;
    private ArrayList<OfferMenuForm> offerMenuFormArrayList;

    public AdapterRVDailyOfferMenuGet(Context applicationContext, ArrayList<OfferMenuForm> offerMenuForms) {
        this.mContext=applicationContext;
        this.offerMenuFormArrayList=offerMenuForms;
    }

    @NonNull
    @Override
    public AdapterRVDailyOfferMenuGet.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rv_daily_offer_menu_item, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRVDailyOfferMenuGet.ItemViewHolder itemViewHolder, int i) {
        if(offerMenuFormArrayList.get(i).getBuyGetId()==2)
        {
            itemViewHolder.tvMenuName.setText(offerMenuFormArrayList.get(i).getMenu_Name());
            Toast.makeText(mContext, offerMenuFormArrayList.get(i).getMenu_Name(), Toast.LENGTH_SHORT).show();
            itemViewHolder.tvMenuPrice.setText("\u20B9 "+(offerMenuFormArrayList.get(i).getMenu_Ori_Price()));
        }
    }

    @Override
    public int getItemCount() {
        return offerMenuFormArrayList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMenuName,tvMenuPrice;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMenuName=itemView.findViewById(R.id.tv_menu_name);
            tvMenuPrice=itemView.findViewById(R.id.tv_menu_price);
        }
    }
}
