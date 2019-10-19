package com.restrosmart.restrohotel.Captain.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.restrosmart.restrohotel.Captain.Activities.ActivityEditCartMenu;
import com.restrosmart.restrohotel.Captain.Models.FoodCartModel;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.Utils.Sessionmanager;

import java.util.ArrayList;

public class FoodCartRVAdapter extends RecyclerView.Adapter<FoodCartRVAdapter.ItemViewHolder> {

    private Context mContext;
    private ArrayList<FoodCartModel> arrayList;
    private AlertDialog alertDialog;
    private Sessionmanager sessionmanager;

    public FoodCartRVAdapter(Context context, ArrayList<FoodCartModel> cartRVModelArrayList) {
        this.mContext = context;
        this.arrayList = cartRVModelArrayList;
        sessionmanager = new Sessionmanager(mContext);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartmenu_list_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.tvCartMenuName.setText(arrayList.get(position).getMenuName());
        holder.tvFoodCartMenuPrice.setText("(" + mContext.getResources().getString(R.string.currency) + String.valueOf(arrayList.get(position).getMenuPrice()) + ")");
        holder.tvFoodMenuQty.setText(String.valueOf(arrayList.get(position).getMenuQty()) + " x");
        holder.tvQtyAmount.setText(mContext.getResources().getString(R.string.currency) + String.valueOf(arrayList.get(position).getMenuQtyPrice()));

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

        ItemViewHolder(View itemView) {
            super(itemView);

            tvCartMenuName = itemView.findViewById(R.id.tvCartMenuName);
            tvFoodCartMenuPrice = itemView.findViewById(R.id.tvFoodCartMenuPrice);
            tvFoodMenuQty = itemView.findViewById(R.id.tvFoodMenuQty);
            tvQtyAmount = itemView.findViewById(R.id.tvQtyAmount);
            rvMenuToppings = itemView.findViewById(R.id.rvMenuToppings);

            rvMenuToppings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FoodCartModel foodCartModel = arrayList.get(getAdapterPosition());

                    Intent intent = new Intent(mContext, ActivityEditCartMenu.class);

                    Bundle bundle = new Bundle();
                    bundle.putInt("OrderDetailId", foodCartModel.getOrderDetailId());
                    bundle.putString("MenuName", foodCartModel.getMenuName());
                    bundle.putString("MenuOrderMsg", foodCartModel.getMenuOrderMsg());
                    bundle.putInt("MenuQty", foodCartModel.getMenuQty());
                    bundle.putFloat("MenuPrice", foodCartModel.getMenuPrice());
                    bundle.putParcelableArrayList("ToppingsList", foodCartModel.getToppingsModelArrayList());
                    bundle.putParcelableArrayList("AllToppingsList", foodCartModel.getAllToppingsModelArrayList());

                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FoodCartModel foodCartModel = arrayList.get(getAdapterPosition());

                    Intent intent = new Intent(mContext, ActivityEditCartMenu.class);

                    Bundle bundle = new Bundle();
                    bundle.putInt("OrderDetailId", foodCartModel.getOrderDetailId());
                    bundle.putString("MenuName", foodCartModel.getMenuName());
                    bundle.putString("MenuOrderMsg", foodCartModel.getMenuOrderMsg());
                    bundle.putInt("MenuQty", foodCartModel.getMenuQty());
                    bundle.putFloat("MenuPrice", foodCartModel.getMenuPrice());
                    bundle.putParcelableArrayList("ToppingsList", foodCartModel.getToppingsModelArrayList());
                    bundle.putParcelableArrayList("AllToppingsList", foodCartModel.getAllToppingsModelArrayList());

                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });
        }
    }
        /*@Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tvFoodMinusQty:
                    if (Integer.parseInt(tvFoodMenuQty.getText().toString()) == 1) {
                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
                        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View dialogView = null;
                        if (layoutInflater != null) {
                            dialogView = layoutInflater.inflate(R.layout.cancel_menu_dialog, null);
                        }
                        dialogBuilder.setView(dialogView);

                        alertDialog = dialogBuilder.create();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();

                        Button btnRemoveYes = dialogView.findViewById(R.id.btnRemoveYes);
                        Button btnRemoveNo = dialogView.findViewById(R.id.btnRemoveNo);

                        btnRemoveYes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                sessionmanager.removeMenuFoodCart(mContext, arrayList.get(getAdapterPosition()).getMenuId());
                                qtyRefreshList();
                                mAddRemoveItemCartListener.addRemovedItem();
                                alertDialog.dismiss();
                            }
                        });

                        btnRemoveNo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog.dismiss();
                            }
                        });
                    } else {
                        FoodCartModel foodCartModel = arrayList.get(getAdapterPosition());
                        sessionmanager.updateQtyFoodCart(mContext, 0, foodCartModel.getMenuId());
                        tvFoodMenuQty.setText(String.valueOf(Integer.parseInt(tvFoodMenuQty.getText().toString()) - 1));
                        qtyRefreshList();
                        mAddRemoveItemCartListener.addRemovedItem();

                        //tvQtyAmount.setText(String.valueOf((Integer.parseInt(tvFoodMenuQty.getText().toString()) - 1) * foodCartModel.getMenuPrice()));
                    }

                    break;

                case R.id.tvFoodAddQty:
                    FoodCartModel foodCartModel = arrayList.get(getAdapterPosition());
                    sessionmanager.updateQtyFoodCart(mContext, 1, foodCartModel.getMenuId());
                    tvFoodMenuQty.setText(String.valueOf(Integer.parseInt(tvFoodMenuQty.getText().toString()) + 1));
                    qtyRefreshList();
                    mAddRemoveItemCartListener.addRemovedItem();

                    //tvQtyAmount.setText(String.valueOf((Integer.parseInt(tvFoodMenuQty.getText().toString()) + 1) * foodCartModel.getMenuPrice()));
                    break;
            }
        }*/

    /*public void qtyRefreshList() {
        this.arrayList = sessionmanager.getAddToFoodCartList(mContext);
        notifyDataSetChanged();
    }*/
}
