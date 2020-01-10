package com.restrosmart.restrohotel.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.restrosmart.restrohotel.Model.MenuForm;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class RVOrderMenuDetailsAdapter  extends RecyclerView.Adapter<RVOrderMenuDetailsAdapter.ItemViewHolder> {
    private  Context mContext;
    private ArrayList<MenuForm> menuFormArrayList;

    public RVOrderMenuDetailsAdapter(Context mContext, ArrayList<MenuForm> arrayList) {
        this.mContext=mContext;
        this.menuFormArrayList=arrayList;

    }

    @NonNull
    @Override
    public RVOrderMenuDetailsAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_order_menu_item_details, viewGroup, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RVOrderMenuDetailsAdapter.ItemViewHolder itemViewHolder, int i) {
        itemViewHolder.tvMenuName.setText(menuFormArrayList.get(i).getMenuName());

        itemViewHolder.tvMenuPrice.setText("(" + mContext.getResources().getString(R.string.currency) + String.valueOf(menuFormArrayList.get(i).getMenuPrice()) + ")");
        itemViewHolder.tvMenuQty.setText("x " + String.valueOf(menuFormArrayList.get(i).getMenuQty()));



        itemViewHolder.tvQtyTotalPrice.setText(mContext.getResources().getString(R.string.currency) + String.valueOf(menuFormArrayList.get(i).getMenuPrice()));

        if (menuFormArrayList.get(i).getArrayListToppings() != null && menuFormArrayList.get(i).getArrayListToppings().size() > 0) {
           OrderToppingAdapter oredrToppingAdapter = new OrderToppingAdapter(mContext, menuFormArrayList.get(i).getArrayListToppings());
            itemViewHolder.rvToppings.setHasFixedSize(true);
            itemViewHolder.rvToppings.setNestedScrollingEnabled(false);
            itemViewHolder.rvToppings.setLayoutManager(new GridLayoutManager(mContext, 1));
            itemViewHolder.rvToppings.setItemAnimator(new DefaultItemAnimator());
            itemViewHolder.rvToppings.setAdapter(oredrToppingAdapter);
        }


    }

    @Override
    public int getItemCount() {
        return menuFormArrayList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMenuName,tvMenuQty,tvMenuPrice,tvQtyTotalPrice;
        private  RecyclerView rvToppings;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMenuName=itemView.findViewById(R.id.tvOrderMenuName);
            tvMenuPrice=itemView.findViewById(R.id.tvOrderMenuPrice);
            tvMenuQty=itemView.findViewById(R.id.tvOrderMenuQty);
            rvToppings=itemView.findViewById(R.id.rvMenuToppings);
            tvQtyTotalPrice=itemView.findViewById(R.id.tvQtyAmount);
        }
    }
}
