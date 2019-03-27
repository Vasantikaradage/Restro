package com.restrosmart.restro.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.restrosmart.restro.Model.KitchenOrderModel;
import com.restrosmart.restro.R;

import java.util.ArrayList;

public class RVKitchenOrderAdapter extends RecyclerView.Adapter<RVKitchenOrderAdapter.ItemViewHolder> {

    private Context mContext;
    private ArrayList<KitchenOrderModel> arrayList;
    private RVskOrderAdapter rvSKOrderAdapter;

    public RVKitchenOrderAdapter(Context context, ArrayList<KitchenOrderModel> kitchenOrderModelArrayList) {
        this.mContext = context;
        this.arrayList = kitchenOrderModelArrayList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_kitchen_order_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        holder.tvOrderId.setText(String.valueOf(arrayList.get(position).getmOrderNo()));
        holder.tvTableNoOrder.setText(String.valueOf(arrayList.get(position).getmTableNo()));

        holder.rvSpecificKitchenOrder.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

        if (position % 2 == 0) {
            rvSKOrderAdapter = new RVskOrderAdapter(mContext, arrayList.get(position).getArrayList());
            holder.rlOrderHeader.setBackgroundColor(mContext.getResources().getColor(R.color.mediumGreen));
            holder.ivAcceptOrder.setVisibility(View.GONE);
            holder.ivCancelOrder.setVisibility(View.GONE);
            holder.ivReadyOrder.setVisibility(View.VISIBLE);
        } else {
            rvSKOrderAdapter = new RVskOrderAdapter(mContext, arrayList.get(position).getArrayList());
            //holder.rlOrderHeader.setBackgroundColor(mContext.getResources().getColor(R.color.lightGreen));
            holder.ivAcceptOrder.setVisibility(View.VISIBLE);
            holder.ivCancelOrder.setVisibility(View.VISIBLE);
        }

        holder.rvSpecificKitchenOrder.setAdapter(rvSKOrderAdapter);
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

        private TextView tvTableNoOrder, tvLabelOrderId, tvOrderId, tvDateTimeOrder;
        private RelativeLayout rlOrderHeader;
        private RecyclerView rvSpecificKitchenOrder;
        private ImageView ivReadyOrder, ivAcceptOrder, ivCancelOrder;

        ItemViewHolder(View itemView) {
            super(itemView);

            tvTableNoOrder = itemView.findViewById(R.id.tvTableNoOrder);
            tvLabelOrderId = itemView.findViewById(R.id.tvLabelOrderId);
            tvOrderId = itemView.findViewById(R.id.tvOrderId);
            tvDateTimeOrder = itemView.findViewById(R.id.tvDateTimeOrder);
            rlOrderHeader = itemView.findViewById(R.id.rlOrderHeader);
            rvSpecificKitchenOrder = itemView.findViewById(R.id.rvSpecificKitchenOrder);
            ivReadyOrder = itemView.findViewById(R.id.ivReadyOrder);
            ivAcceptOrder = itemView.findViewById(R.id.ivAcceptOrder);
            ivCancelOrder = itemView.findViewById(R.id.ivCancelOrder);

            ivAcceptOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("Want to accept the order?");
                    builder.setTitle(R.string.app_name);
                    builder.setIcon(R.drawable.ic_accept);
                    builder.setCancelable(false);

                    builder.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    builder.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder.create();
                    alert11.show();
                }
            });

            ivCancelOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("Want to cancel the order?");
                    builder.setTitle(R.string.app_name);
                    builder.setIcon(R.drawable.ic_cancel);
                    builder.setCancelable(false);

                    builder.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    builder.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder.create();
                    alert11.show();
                }
            });

            ivReadyOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("Your order is ready...");
                    builder.setTitle(R.string.app_name);
                    builder.setIcon(R.drawable.ic_kitchen_order_ready);
                    builder.setCancelable(false);

                    builder.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    builder.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder.create();
                    alert11.show();
                }
            });

        }
    }
}
