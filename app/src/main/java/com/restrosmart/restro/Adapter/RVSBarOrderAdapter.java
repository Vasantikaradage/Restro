package com.restrosmart.restro.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.restrosmart.restro.Model.BarOrderSpecificModel;
import com.restrosmart.restro.R;

import java.util.ArrayList;

public class RVSBarOrderAdapter extends RecyclerView.Adapter<RVSBarOrderAdapter.ItemViewHolder> {

    private Context mContext;
    private ArrayList<BarOrderSpecificModel> arrayList;

    public RVSBarOrderAdapter(Context mContext, ArrayList<BarOrderSpecificModel> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_specific_bar_order_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.tvBarLiqourName.setText(arrayList.get(position).getBarOrderName());
        holder.tvBarLiqourQty.setText("x" + String.valueOf(arrayList.get(position).getBarOrderQty()));
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

        private TextView tvBarLiqourName, tvBarLiqourQty;

        ItemViewHolder(View itemView) {
            super(itemView);

            tvBarLiqourName = itemView.findViewById(R.id.tvBarLiqourName);
            tvBarLiqourQty = itemView.findViewById(R.id.tvBarLiqourQty);
        }
    }
}
