package com.restrosmart.restrohotel.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.restrosmart.restrohotel.Model.MenuDisplayForm;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class RVDailyOfferBuyGetList  extends RecyclerView.Adapter<RVDailyOfferBuyGetList.ItemViewHolder> {

    private  Context mContext;
    private  ArrayList<MenuDisplayForm> menuDisplayFormArrayList;


    public RVDailyOfferBuyGetList(Context baseContext, ArrayList<MenuDisplayForm> arrayListBuyMenu) {
        this.mContext=baseContext;
        this.menuDisplayFormArrayList=arrayListBuyMenu;
    }

    @NonNull
    @Override
    public RVDailyOfferBuyGetList.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_daily_offer_buy_get, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVDailyOfferBuyGetList.ItemViewHolder itemViewHolder, int i) {
        itemViewHolder.tvMenuName.setText(menuDisplayFormArrayList.get(i).getMenu_Name());
        itemViewHolder.tvMenuPrice.setText( mContext.getResources().getString(R.string.currency)+" "+menuDisplayFormArrayList.get(i).getNon_Ac_Rate());

    }

    @Override
    public int getItemCount() {
        return menuDisplayFormArrayList.size();
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
