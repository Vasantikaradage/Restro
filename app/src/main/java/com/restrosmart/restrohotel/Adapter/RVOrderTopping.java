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

public  class RVOrderTopping extends RecyclerView.Adapter<RVOrderTopping.MyHolder>  {
   private  Context mContext;
   private  ArrayList<ToppingsForm> arrayListTopping;

    public RVOrderTopping(Context context, ArrayList<ToppingsForm> arrayListToppings) {
     this.mContext=context;
     this.arrayListTopping=arrayListToppings;

    }

    @NonNull
    @Override
    public RVOrderTopping.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_order_topping, viewGroup, false);

        MyHolder holder = new MyHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RVOrderTopping.MyHolder myHolder, int i) {
myHolder.tvTopping.setText(arrayListTopping.get(i).getToppingsName());
    }

    @Override
    public int getItemCount() {
        return arrayListTopping.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView tvTopping;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvTopping=itemView.findViewById(R.id.tv_topping);
        }
    }
}
