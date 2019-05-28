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

import com.restrosmart.restrohotel.Captain.Models.AreaSwapModel;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class RVSwapFreeAreaAdapter extends RecyclerView.Adapter<RVSwapFreeAreaAdapter.ItemViewHolder> {

    private Context mContext;
    private ArrayList<AreaSwapModel> arrayList;
    private int mOldTableId, mCustId;

    public RVSwapFreeAreaAdapter(Context context, ArrayList<AreaSwapModel> areaSwapModelArrayList, int tableId, int custId) {
        this.mContext = context;
        this.arrayList = areaSwapModelArrayList;
        this.mOldTableId = tableId;
        this.mCustId = custId;
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

        RVSwapFreeTablesAdapter rvSwapFreeTablesAdapter = new RVSwapFreeTablesAdapter(mContext, arrayList.get(position).getTableSwapModelArrayList(), mOldTableId, mCustId);
        holder.rvBookedTable.setHasFixedSize(true);
        holder.rvBookedTable.setNestedScrollingEnabled(false);
        holder.rvBookedTable.setLayoutManager(new GridLayoutManager(mContext, 4));
        holder.rvBookedTable.setItemAnimator(new DefaultItemAnimator());
        holder.rvBookedTable.setAdapter(rvSwapFreeTablesAdapter);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tvAreaName;
        private RecyclerView rvBookedTable;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            tvAreaName = itemView.findViewById(R.id.tvAreaName);
            rvBookedTable = itemView.findViewById(R.id.rvScannedTable);
        }
    }
}
