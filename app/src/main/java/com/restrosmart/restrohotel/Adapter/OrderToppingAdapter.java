package com.restrosmart.restrohotel.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.restrosmart.restrohotel.Model.ToppingsForm;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class OrderToppingAdapter extends RecyclerView.Adapter<OrderToppingAdapter.ItemViewHolder> {
    private  ArrayList<ToppingsForm> toppingsFormArrayList;
    private  Context mContext;


    public OrderToppingAdapter(Context mContext, ArrayList<ToppingsForm> arrayListToppings) {
        this.mContext=mContext;
        this.toppingsFormArrayList=arrayListToppings;
    }

    @NonNull
    @Override
    public OrderToppingAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cartmenu_top_list_item, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderToppingAdapter.ItemViewHolder itemViewHolder, int i) {
        itemViewHolder.tvTopName.setText(toppingsFormArrayList.get(i).getToppingsName());
        itemViewHolder.tvTopPrice.setText("(" + mContext.getResources().getString(R.string.currency) + String.valueOf(toppingsFormArrayList.get(i).getToppingsPrice()) + ")");

    }

    @Override
    public int getItemCount() {
        return toppingsFormArrayList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTopName,tvTopPrice;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTopName=itemView.findViewById(R.id.tvFoodTopName);
            tvTopPrice=itemView.findViewById(R.id.tvFoodTopPrice);

        }
    }
}
