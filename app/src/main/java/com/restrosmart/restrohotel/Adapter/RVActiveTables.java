package com.restrosmart.restrohotel.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.restrosmart.restrohotel.Model.TableFormId;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class RVActiveTables extends RecyclerView.Adapter<RVActiveTables.MyHolder>{
    private Context mContex;
    private  ArrayList<TableFormId> arrayListActiveTables;


    public RVActiveTables(Context context, ArrayList<TableFormId> arrayListTable) {
        this.mContex=context;
        this.arrayListActiveTables=arrayListTable;
    }

    @NonNull
    @Override
    public RVActiveTables.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_active_tables, viewGroup, false);

        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RVActiveTables.MyHolder myHolder, int i) {

      String tableId= String.valueOf(arrayListActiveTables.get(i).getTableId());
       myHolder.tvTableNo.setText(tableId);
        myHolder.ivTable.setBackgroundResource(R.drawable.ic_table_green);
    }

    @Override
    public int getItemCount() {
        return arrayListActiveTables.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView tvTableNo;
        private ImageView ivTable;


        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvTableNo=itemView.findViewById(R.id.tv_table_no);
            ivTable=itemView.findViewById(R.id.table_image);
        }
    }
}
