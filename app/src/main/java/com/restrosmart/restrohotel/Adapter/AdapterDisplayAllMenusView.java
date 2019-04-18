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

public class AdapterDisplayAllMenusView extends RecyclerView.Adapter<AdapterDisplayAllMenusView.MyHolder> {
   private  Context mContext;
   private  ArrayList<ToppingsForm> arrayListToppings;

    public AdapterDisplayAllMenusView(Context context, ArrayList<ToppingsForm> arrayListToppings) {
    this.mContext=context;
    this.arrayListToppings=arrayListToppings;

    }

    @NonNull
    @Override
    public AdapterDisplayAllMenusView.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_menu_all_view, viewGroup, false);

     MyHolder holder = new MyHolder(view);
     return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDisplayAllMenusView.MyHolder myHolder, int i) {
      myHolder.tvToppingName.setText(arrayListToppings.get(i).getToppingsName());

      String price= String.valueOf(arrayListToppings.get(i).getToppingsPrice());
      myHolder.tvToppingPrice.setText("\u20B9 "+price);

      if(arrayListToppings.size()==1)
      {
          myHolder.viewLine.setVisibility(View.GONE);
      }
      else
      {
          myHolder.viewLine.setVisibility(View.VISIBLE);
      }
    }

    @Override
    public int getItemCount() {
        return arrayListToppings.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView tvToppingName, tvToppingPrice;
        private  View viewLine;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvToppingName=itemView.findViewById(R.id.tvToppingsName);
            tvToppingPrice=itemView.findViewById(R.id.tvToppingsPrice);
            viewLine=itemView.findViewById(R.id.view_line);
        }
    }
}
