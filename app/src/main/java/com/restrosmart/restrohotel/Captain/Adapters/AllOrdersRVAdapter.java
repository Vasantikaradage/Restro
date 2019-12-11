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
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class AllOrdersRVAdapter extends RecyclerView.Adapter<AllOrdersRVAdapter.ItemViewHolder> {

    private Context mContext;
    private ArrayList<String> mOrderIdArrayList;
    private ArrayList<OrderStatusOrderList> mArrayList;
    private ArrayList<Integer> counter = new ArrayList<Integer>();

    public AllOrdersRVAdapter(Context context, ArrayList<String> orderIdArrayList, ArrayList<OrderStatusOrderList> orderStatusOrderListArrayList) {
        this.mContext = context;
        this.mOrderIdArrayList = orderIdArrayList;
        this.mArrayList = orderStatusOrderListArrayList;

        for (int i = 0; i < mOrderIdArrayList.size(); i++) {
            counter.add(0);
        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_order_list_item, viewGroup, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.tvOrderName.setText(mOrderIdArrayList.get(position).toString());

        OrderStatusOrderList orderStatusOrderList = mArrayList.get(position);

        if (orderStatusOrderList.getOrderStatus() != null && !orderStatusOrderList.getOrderStatus().equalsIgnoreCase("null"))
            holder.tvOrderStatus.setText("(" + orderStatusOrderList.getOrderStatus() + ")");

        OrderDetailRVAdapter orderDetailRVAdapter = new OrderDetailRVAdapter(mContext, orderStatusOrderList.getOrderStatusOrders());
        holder.rvSpecificOrder.setHasFixedSize(true);
        holder.rvSpecificOrder.setNestedScrollingEnabled(false);
        holder.rvSpecificOrder.setLayoutManager(new GridLayoutManager(mContext, 1));
        holder.rvSpecificOrder.setItemAnimator(new DefaultItemAnimator());
        holder.rvSpecificOrder.setAdapter(orderDetailRVAdapter);
    }

    @Override
    public int getItemCount() {
        return mOrderIdArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tvOrderName, tvOrderStatus;
        private ImageView ivArrow;
        private RecyclerView rvSpecificOrder;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            tvOrderName = itemView.findViewById(R.id.tvOrderName);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
            ivArrow = itemView.findViewById(R.id.ivArrow);
            rvSpecificOrder = itemView.findViewById(R.id.rvSpecificOrder);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (counter.get(getAdapterPosition()) % 2 == 0) {
                        rvSpecificOrder.setVisibility(View.VISIBLE);
                        ivArrow.setImageDrawable(mContext.getDrawable(R.drawable.ic_up_arrow_16dp));
                    } else {
                        rvSpecificOrder.setVisibility(View.GONE);
                        ivArrow.setImageDrawable(mContext.getDrawable(R.drawable.ic_down_arrow_16dp));
                    }

                    counter.set(getAdapterPosition(), counter.get(getAdapterPosition()) + 1);
                }
            });
        }
    }
}
