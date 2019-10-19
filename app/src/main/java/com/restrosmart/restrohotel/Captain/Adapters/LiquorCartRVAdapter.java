package com.restrosmart.restrohotel.Captain.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.restrosmart.restrohotel.Captain.Activities.ActivityEditCartMenu;
import com.restrosmart.restrohotel.Captain.Models.LiquorCartModel;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class LiquorCartRVAdapter extends RecyclerView.Adapter<LiquorCartRVAdapter.ItemViewHolder> {

    private Context mContext;
    private ArrayList<LiquorCartModel> arrayList;

    public LiquorCartRVAdapter(Context context, ArrayList<LiquorCartModel> cartRVModelArrayList) {
        this.mContext = context;
        this.arrayList = cartRVModelArrayList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartmenu_list_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.tvCartMenuName.setText(arrayList.get(position).getLiqName());
        holder.tvFoodCartMenuPrice.setText("(" + mContext.getResources().getString(R.string.currency) + String.valueOf(arrayList.get(position).getLiqPrice()) + ")");
        holder.tvLiqCartQty.setText("(" + String.valueOf(arrayList.get(position).getLiqMLQty()) + ")");
        holder.tvFoodMenuQty.setText(String.valueOf(arrayList.get(position).getLiqQty()) + " x");
        holder.tvQtyAmount.setText(mContext.getResources().getString(R.string.currency) + String.valueOf(arrayList.get(position).getLiqQtyPrice()));

        holder.tvLiqCartQty.setVisibility(View.VISIBLE);

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

        private TextView tvCartMenuName, tvLiqCartQty, tvFoodCartMenuPrice, tvFoodMenuQty, tvQtyAmount;
        private RecyclerView rvMenuToppings;

        ItemViewHolder(View itemView) {
            super(itemView);

            tvCartMenuName = itemView.findViewById(R.id.tvCartMenuName);
            tvLiqCartQty = itemView.findViewById(R.id.tvLiqCartQty);
            tvFoodCartMenuPrice = itemView.findViewById(R.id.tvFoodCartMenuPrice);
            tvFoodMenuQty = itemView.findViewById(R.id.tvFoodMenuQty);
            tvQtyAmount = itemView.findViewById(R.id.tvQtyAmount);
            rvMenuToppings = itemView.findViewById(R.id.rvMenuToppings);

            rvMenuToppings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LiquorCartModel liquorCartModel = arrayList.get(getAdapterPosition());

                    Intent intent = new Intent(mContext, ActivityEditCartMenu.class);

                    Bundle bundle = new Bundle();
                    bundle.putInt("OrderDetailId", liquorCartModel.getOrderDetailId());
                    bundle.putString("MenuName", liquorCartModel.getLiqName());
                    bundle.putString("MenuOrderMsg", liquorCartModel.getLiqOrderMsg());
                    bundle.putInt("MenuQty", liquorCartModel.getLiqQty());
                    bundle.putFloat("MenuPrice", liquorCartModel.getLiqPrice());
                    bundle.putParcelableArrayList("ToppingsList", liquorCartModel.getToppingsModelArrayList());
                    bundle.putParcelableArrayList("AllToppingsList", liquorCartModel.getAllToppingsModelArrayList());

                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    LiquorCartModel liquorCartModel = arrayList.get(getAdapterPosition());

                    Intent intent = new Intent(mContext, ActivityEditCartMenu.class);

                    Bundle bundle = new Bundle();
                    bundle.putInt("OrderDetailId", liquorCartModel.getOrderDetailId());
                    bundle.putString("MenuName", liquorCartModel.getLiqName());
                    bundle.putString("MenuOrderMsg", liquorCartModel.getLiqOrderMsg());
                    bundle.putInt("MenuQty", liquorCartModel.getLiqQty());
                    bundle.putFloat("MenuPrice", liquorCartModel.getLiqPrice());
                    bundle.putParcelableArrayList("ToppingsList", liquorCartModel.getToppingsModelArrayList());
                    bundle.putParcelableArrayList("AllToppingsList", liquorCartModel.getAllToppingsModelArrayList());

                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
