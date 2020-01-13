package com.restrosmart.restrohotel.Captain.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.restrosmart.restrohotel.Captain.Models.ScanTableModel;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class ScanTableDetailAdapter extends RecyclerView.Adapter<ScanTableDetailAdapter.ItemViewHolder> {

    private Context mContext;
    private ArrayList<ScanTableModel> arrayList;
    private int mAreaId;

    ScanTableDetailAdapter(Context context, ArrayList<ScanTableModel> scanTableModelArrayList, int areaId) {
        this.mContext = context;
        this.arrayList = scanTableModelArrayList;
        this.mAreaId = areaId;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scan_table_detail_list_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        ScanTableModel scanTableModel = arrayList.get(position);

        holder.tvTableNo.setText("Table No: " + scanTableModel.getTableNo());
        holder.tvCustName.setText("Customer Name: " + scanTableModel.getCustName());
        holder.tvCustMb.setText("Customer Mob No: " + scanTableModel.getCustMob());
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

        private TextView tvTableNo, tvCustName, tvCustMb, tvAcceptTable, tvDeclineTable;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTableNo = itemView.findViewById(R.id.tvTableNo);
            tvCustName = itemView.findViewById(R.id.tvCustName);
            tvCustMb = itemView.findViewById(R.id.tvCustMb);
            tvAcceptTable = itemView.findViewById(R.id.tvAcceptTable);
            tvDeclineTable = itemView.findViewById(R.id.tvDeclineTable);

            tvAcceptTable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showConfirmDialog("Are you sure you want to confirm the table?", "Confirming the table. Please wait...", 2);
                }
            });

            tvDeclineTable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showConfirmDialog("Are you sure you want to decline the table?", "Declining the table. Please wait...", 0);
                }
            });
        }

        private void showConfirmDialog(String message, final String progressMsg, final int tableConfStatus) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
            builder1.setTitle(mContext.getResources().getString(R.string.app_name));
            builder1.setMessage(message);
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    android.R.string.yes,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ScanTableModel scanTableModel = arrayList.get(getAdapterPosition());

                            Intent intent = new Intent("com.restrohotel.captain.accept.decline.table");
                            intent.putExtra("tableId", scanTableModel.getTableId());
                            intent.putExtra("tableNo", scanTableModel.getTableNo());
                            intent.putExtra("areaId", mAreaId);
                            intent.putExtra("progressMsg", progressMsg);
                            intent.putExtra("tableConfStatus", tableConfStatus);
                            mContext.sendBroadcast(intent);
                            dialog.dismiss();
                        }
                    });

            builder1.setNegativeButton(
                    android.R.string.no,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
    }
}
