package com.restrosmart.restrohotel.Captain.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.restrosmart.restrohotel.Captain.Models.OrderStatusOrderList;
import com.restrosmart.restrohotel.Captain.Models.OrderStatusOrders;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class OrderDetailRVAdapter extends RecyclerView.Adapter<OrderDetailRVAdapter.ItemViewHolder> {

    private Context mContext;
    private ArrayList<OrderStatusOrders> arrayList;

    public OrderDetailRVAdapter(Context context, ArrayList<OrderStatusOrders> ordersArrayList) {
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
        holder.tvCartMenuName.setText(arrayList.get(position).getOrderMenuName());
        holder.tvFoodCartMenuPrice.setText("(" + mContext.getResources().getString(R.string.currency) + String.valueOf(arrayList.get(position).getOrderMenuPrice()) + ")");
        holder.tvFoodMenuQty.setText(String.valueOf(arrayList.get(position).getOrderMenuQty()) + " x");
        holder.tvQtyAmount.setText(mContext.getResources().getString(R.string.currency) + String.valueOf(arrayList.get(position).getTotalMenuPrice()));

        if (arrayList.get(position).getToppingsModelArrayList() != null && arrayList.get(position).getToppingsModelArrayList().size() > 0) {
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
