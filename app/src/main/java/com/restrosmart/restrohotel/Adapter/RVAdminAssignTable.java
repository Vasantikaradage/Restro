package com.restrosmart.restrohotel.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.restrosmart.restrohotel.Interfaces.SelectTableListerner;
import com.restrosmart.restrohotel.Model.TableFormId;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class RVAdminAssignTable extends RecyclerView.Adapter<RVAdminAssignTable.MyHolder> {
    private ArrayList<TableFormId> tableFormIdArrayList;
    private Context mContext;
    private SelectTableListerner selectTableListerner;

    public RVAdminAssignTable(Context mContext, ArrayList<TableFormId> arrayTableFormIds, SelectTableListerner selectPhotoListerner) {
        this.mContext = mContext;
        this.tableFormIdArrayList = arrayTableFormIds;
        this.selectTableListerner =selectPhotoListerner;
    }

    @NonNull
    @Override
    public RVAdminAssignTable.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_table_item, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RVAdminAssignTable.MyHolder myHolder, final int i) {
        String id = String.valueOf(tableFormIdArrayList.get(i).getTableId());
        myHolder.tvTableNo.setText(id);
        if (tableFormIdArrayList.get(i).getTableStatus() == 1) {

            myHolder.imageView.setBackgroundResource(R.drawable.ic_table_green);
        } else {
            myHolder.imageView.setBackgroundResource(R.drawable.ic_table_gray);
        }

        myHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myHolder.imageView.setBackgroundResource(R.drawable.ic_table_blue);
                tableFormIdArrayList.get(i).setSelected(true);
                selectTableListerner.tableListerner(tableFormIdArrayList);

            }
        });
    }

    @Override
    public int getItemCount() {
        return tableFormIdArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView tvTableNo;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.table_image);
            tvTableNo = itemView.findViewById(R.id.tv_table_no);

         /*   itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                }
            });*/
        }
    }
}
