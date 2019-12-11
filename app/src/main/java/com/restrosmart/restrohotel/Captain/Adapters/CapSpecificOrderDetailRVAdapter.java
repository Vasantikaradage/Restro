package com.restrosmart.restrohotel.Captain.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.restrosmart.restrohotel.Captain.Models.CapMenuModel;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class CapSpecificOrderDetailRVAdapter extends RecyclerView.Adapter<CapSpecificOrderDetailRVAdapter.ItemViewHolder> {

    private Context mContext;
    private ArrayList<CapMenuModel> arrayList;

    CapSpecificOrderDetailRVAdapter(Context context, ArrayList<CapMenuModel> ordersArrayList) {
        this.mContext = context;
        this.arrayList = ordersArrayList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.order_status_list_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        CapMenuModel capMenuModel = arrayList.get(position);

        holder.tvCartMenuName.setText(capMenuModel.getMenuName());
        //holder.tvFoodCartMenuPrice.setText("(" + mContext.getResources().getString(R.string.currency) + String.valueOf(capMenuModel.getMenuPrice()) + ")");
        holder.tvFoodMenuQty.setText("x " + String.valueOf(capMenuModel.getMenuQty()));
        holder.tvQtyAmount.setText(mContext.getResources().getString(R.string.currency) + String.valueOf(capMenuModel.getMenuPrice()));

        if (capMenuModel.getToppingsModelArrayList() != null && capMenuModel.getToppingsModelArrayList().size() > 0) {
            FoodLiqCartTopAdapter foodLiqCartTopAdapter = new FoodLiqCartTopAdapter(mContext, arrayList.get(position).getToppingsModelArrayList());
            holder.rvMenuToppings.setHasFixedSize(true);
            holder.rvMenuToppings.setNestedScrollingEnabled(false);
            holder.rvMenuToppings.setLayoutManager(new GridLayoutManager(mContext, 1));
            holder.rvMenuToppings.setItemAnimator(new DefaultItemAnimator());
            holder.rvMenuToppings.setAdapter(foodLiqCartTopAdapter);
        }
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

        private TextView tvCartMenuName, tvFoodCartMenuPrice, tvFoodMenuQty, tvQtyAmount;
        private RecyclerView rvMenuToppings;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCartMenuName = itemView.findViewById(R.id.tvCartMenuName);
            tvFoodCartMenuPrice = itemView.findViewById(R.id.tvFoodCartMenuPrice);
            tvFoodMenuQty = itemView.findViewById(R.id.tvFoodMenuQty);
            tvQtyAmount = itemView.findViewById(R.id.tvQtyAmount);
            rvMenuToppings = itemView.findViewById(R.id.rvMenuToppings);
        }
    }
}
