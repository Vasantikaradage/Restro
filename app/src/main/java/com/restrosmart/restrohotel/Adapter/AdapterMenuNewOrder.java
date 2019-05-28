package com.restrosmart.restrohotel.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.restrosmart.restrohotel.Model.MenuForm;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class AdapterMenuNewOrder extends RecyclerView.Adapter<AdapterMenuNewOrder.MyHolder>{
    private  Context context;
    private  ArrayList<MenuForm> arrayListMenu;


    public AdapterMenuNewOrder(Context context, ArrayList<MenuForm> arrayList) {
        this.arrayListMenu=arrayList;
        this.context=context;
    }


    @NonNull
    @Override
    public AdapterMenuNewOrder.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_new_order_recycler, viewGroup, false);

        MyHolder holder = new MyHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMenuNewOrder.MyHolder myHolder, int i) {
        myHolder.menu_name.setText(arrayListMenu.get(i).getMenuName());
        String price= String.valueOf(arrayListMenu.get(i).getMenuPrice());
        String qty= String.valueOf(arrayListMenu.get(i).getMenuQty());

        myHolder.menu_price.setText(context.getResources().getString(R.string.Rs) + " " +price);
        myHolder.menu_qty.setText("X "+qty);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        myHolder.rvOrderTopping.setLayoutManager(linearLayoutManager);


        // recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);

        // if (userType.equals("Admin")) {
        RVOrderTopping rvOrderTopping = new RVOrderTopping(context, arrayListMenu.get(myHolder.getAdapterPosition()).getArrayListToppings());
        myHolder.rvOrderTopping.setAdapter(rvOrderTopping);


    }

    @Override
    public int getItemCount() {
        return arrayListMenu.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView menu_name, menu_qty, menu_price;
        private  RecyclerView rvOrderTopping;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            menu_name = (TextView) itemView.findViewById(R.id.tx_menu_name);
            menu_qty = (TextView) itemView.findViewById(R.id.tx_menu_qty);
            menu_price = (TextView) itemView.findViewById(R.id.tx_menu_price);
            rvOrderTopping=itemView.findViewById(R.id.rvOrderTopping);
        }
    }
}
