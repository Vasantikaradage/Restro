package com.restrosmart.restrohotel.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.restrosmart.restrohotel.Model.MenuDisplayForm;
import com.restrosmart.restrohotel.Model.ToppingsForm;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class AdapterDisplayAllMenusOffer  extends RecyclerView.Adapter<AdapterDisplayAllMenusOffer.MyHolder> {
    private  Context mContext;
    private  ArrayList<MenuDisplayForm> menuDisplayFormArrayList;


    public AdapterDisplayAllMenusOffer(Context context, ArrayList<MenuDisplayForm> arrayListMenu) {
        this.mContext=context;
        this.menuDisplayFormArrayList=arrayListMenu;
    }

    @NonNull
    @Override
    public AdapterDisplayAllMenusOffer.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_menu_itemlist_offer, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDisplayAllMenusOffer.MyHolder myHolder, int i) {
        myHolder.tvMenu.setText(menuDisplayFormArrayList.get(i).getMenu_Name());
        String price= String.valueOf(menuDisplayFormArrayList.get(i).getNon_Ac_Rate());
        myHolder.tvPrice.setText("\u20B9 " + price);

       /* ArrayList<ToppingsForm> arrayListtoppings=menuDisplayFormArrayList.get(i).getArrayListtoppings();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        myHolder.rvTopping.setHasFixedSize(true);
        myHolder.rvTopping.setLayoutManager(linearLayoutManager);

        AdapterDisplayAllToppingOffer adapterDisplayAllToppingOffer = new AdapterDisplayAllToppingOffer(mContext, arrayListtoppings);
        myHolder. rvTopping.setAdapter(adapterDisplayAllToppingOffer);*/

    }

    @Override
    public int getItemCount() {
        return menuDisplayFormArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView tvMenu;
        private CheckBox checkBox;
        private  TextView tvPrice;
        private EditText tvOfferPrice;
       // private  RecyclerView rvTopping;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvMenu=itemView.findViewById(R.id.tv_menu_name);
            checkBox=itemView.findViewById(R.id.checkbox);
            tvPrice=itemView.findViewById(R.id.tv_menu_price);
           // rvTopping=itemView.findViewById(R.id.rv_topping);
            tvOfferPrice=itemView.findViewById(R.id.et_offer_price);

        }
    }
}
