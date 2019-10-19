package com.restrosmart.restrohotel.Captain.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.restrosmart.restrohotel.Captain.Models.OrderModel;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class RVTableOrderAdapter extends RecyclerView.Adapter<RVTableOrderAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<OrderModel> arrayList;

    public RVTableOrderAdapter(Context context, ArrayList<OrderModel> arrayList) {
        this.mContext = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public RVTableOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.table_order_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RVTableOrderAdapter.ViewHolder viewHolder, int i) {
        viewHolder.tvCustName.setText(arrayList.get(i).getCustName());
        viewHolder.tvCustMobNo.setText(arrayList.get(i).getCustMobNo());
        viewHolder.tvTableId.setText(String.valueOf(arrayList.get(i).getTableId()));
        viewHolder.tvDateTime.setText(arrayList.get(i).getTime());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCustName, tvCustMobNo, tvDateTime, tvTableId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCustName = itemView.findViewById(R.id.tvCustName);
            tvCustMobNo = itemView.findViewById(R.id.tvCustMobNo);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            tvTableId = itemView.findViewById(R.id.tvTableId);
        }
    }
}
