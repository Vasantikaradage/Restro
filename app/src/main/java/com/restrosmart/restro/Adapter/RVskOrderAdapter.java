package com.restrosmart.restro.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.restrosmart.restro.Model.KitchenOrderMenuModel;
import com.restrosmart.restro.R;

import java.util.ArrayList;

public class RVskOrderAdapter extends RecyclerView.Adapter<RVskOrderAdapter.ItemViewHolder> {

    private Context mContext;
    private ArrayList<KitchenOrderMenuModel> arrayList;

    public RVskOrderAdapter(Context mContext, ArrayList<KitchenOrderMenuModel> kitchenOrderMenuModelArrayList) {
        this.mContext = mContext;
        this.arrayList = kitchenOrderMenuModelArrayList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_specific_kitchen_order_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.tvMenuName.setText(arrayList.get(position).getOrderMenuName());
        holder.tvMenuQty.setText("x" + String.valueOf(arrayList.get(position).getOrderMenuQty()));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tvMenuName, tvMenuQty;

        ItemViewHolder(View itemView) {
            super(itemView);

            tvMenuName = itemView.findViewById(R.id.tvMenuName);
            tvMenuQty = itemView.findViewById(R.id.tvMenuQty);
        }
    }
}
