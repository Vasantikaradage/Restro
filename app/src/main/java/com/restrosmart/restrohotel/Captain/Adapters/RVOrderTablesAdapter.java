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

import com.restrosmart.restrohotel.Captain.Models.AreaTableModel;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class RVOrderTablesAdapter extends RecyclerView.Adapter<RVOrderTablesAdapter.ItemViewHolder> {

    private Context mContext;
    private ArrayList<AreaTableModel> arrayList;

    public RVOrderTablesAdapter(Context context, ArrayList<AreaTableModel> areaTableModelArrayList) {
        this.mContext = context;
        this.arrayList = areaTableModelArrayList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_table_list_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.tvAreaName.setText(arrayList.get(position).getAreaName());

        RVTableDetailAdapter rvTableDetailAdapter = new RVTableDetailAdapter(mContext, arrayList.get(position).getScanTableModelArrayList(), arrayList.get(position).getAreaId());
        holder.rvScannedTable.setHasFixedSize(true);
        holder.rvScannedTable.setNestedScrollingEnabled(false);
        holder.rvScannedTable.setLayoutManager(new GridLayoutManager(mContext, 4));
        holder.rvScannedTable.setItemAnimator(new DefaultItemAnimator());
        holder.rvScannedTable.setAdapter(rvTableDetailAdapter);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tvAreaName;
        private RecyclerView rvScannedTable;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            tvAreaName = itemView.findViewById(R.id.tvAreaName);
            rvScannedTable = itemView.findViewById(R.id.rvScannedTable);
        }
    }
}
