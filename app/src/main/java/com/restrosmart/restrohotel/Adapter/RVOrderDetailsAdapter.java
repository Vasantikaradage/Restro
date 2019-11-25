package com.restrosmart.restrohotel.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.restrosmart.restrohotel.Model.MenuForm;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class RVOrderDetailsAdapter extends RecyclerView.Adapter<RVOrderDetailsAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<MenuForm> orderModelArrayList;

    public RVOrderDetailsAdapter(Context baseContext, ArrayList<MenuForm> arrayList) {
        this.mContext = baseContext;
        this.orderModelArrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_item_details_list, viewGroup, false);
        ViewHolder holder = new ViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        // viewHolder.tvOrdertitle.setText("Order "+i+1);
        viewHolder.tvMenuName.setText(orderModelArrayList.get(i).getMenuName());

        String price = String.valueOf(orderModelArrayList.get(i).getMenuPrice());
        String qty = String.valueOf(orderModelArrayList.get(i).getMenuQty());

        viewHolder.tvMenuPrice.setText(mContext.getResources().getString(R.string.Rs) + " " + price);
        viewHolder.tvMenuQty.setText("X " + qty);


    }

    @Override
    public int getItemCount() {
        return orderModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvOrdertitle;
        private TextView tvMenuName, tvMenuPrice, tvMenuQty;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrdertitle = itemView.findViewById(R.id.tv_order);
            tvMenuName = itemView.findViewById(R.id.tx_menu_name);
            tvMenuPrice = itemView.findViewById(R.id.tx_menu_price);
            tvMenuQty = itemView.findViewById(R.id.tx_menu_qty);
        }
    }
}
