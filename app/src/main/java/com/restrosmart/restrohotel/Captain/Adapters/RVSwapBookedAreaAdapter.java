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

public class RVSwapBookedAreaAdapter extends RecyclerView.Adapter<RVSwapBookedAreaAdapter.ItemViewHolder> {

    private Context mContext;
    private ArrayList<AreaSwapModel> arrayList, mFreeAreaSwapArrayList;
    private RVSwapBookedTablesAdapter rvTableDetailAdapter;

    public RVSwapBookedAreaAdapter(Context context, ArrayList<AreaSwapModel> areaSwapModelArrayList, ArrayList<AreaSwapModel> freeAreaSwapArrayList) {
        this.mContext = context;
        this.arrayList = areaSwapModelArrayList;
        this.mFreeAreaSwapArrayList = freeAreaSwapArrayList;
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

        rvTableDetailAdapter = new RVSwapBookedTablesAdapter(mContext, arrayList.get(position).getTableSwapModelArrayList(), mFreeAreaSwapArrayList);
        holder.rvBookedTable.setHasFixedSize(true);
        holder.rvBookedTable.setNestedScrollingEnabled(false);
        holder.rvBookedTable.setLayoutManager(new GridLayoutManager(mContext, 4));
        holder.rvBookedTable.setItemAnimator(new DefaultItemAnimator());
        holder.rvBookedTable.setAdapter(rvTableDetailAdapter);
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

    public void onDestroyFragment(){
        rvTableDetailAdapter.onDestroyReceiver();
    }
}
