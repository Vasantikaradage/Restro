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
import android.widget.Toast;

import com.restrosmart.restrohotel.Captain.Activities.ActivityCaptainLogin;
import com.restrosmart.restrohotel.Captain.Models.AreaTableModel;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class ScanTableRVAdapter extends RecyclerView.Adapter<ScanTableRVAdapter.ItemViewHolder> {

    private Context mContext;
    private ArrayList<AreaTableModel> arrayList;

    public ScanTableRVAdapter(Context context, ArrayList<AreaTableModel> areaTableModelArrayList) {
        this.mContext = context;
        this.arrayList = areaTableModelArrayList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scan_table_list_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        holder.tvAreaName.setText(arrayList.get(position).getAreaName());

        ScanTableDetailAdapter scanTableDetailAdapter = new ScanTableDetailAdapter(mContext, arrayList.get(position).getScanTableModelArrayList(), arrayList.get(position).getAreaId());
        holder.rvScannedTable.setHasFixedSize(true);
        holder.rvScannedTable.setNestedScrollingEnabled(false);
        holder.rvScannedTable.setLayoutManager(new GridLayoutManager(mContext, 1));
        holder.rvScannedTable.setItemAnimator(new DefaultItemAnimator());
        holder.rvScannedTable.setAdapter(scanTableDetailAdapter);
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

        private TextView tvAreaName;
        private RecyclerView rvScannedTable;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            tvAreaName = itemView.findViewById(R.id.tvAreaName);
            rvScannedTable = itemView.findViewById(R.id.rvScannedTable);

        }
    }
}
