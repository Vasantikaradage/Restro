package com.restrosmart.restrohotel.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.restrosmart.restrohotel.Model.TableFormId;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class RVAssignCaptainTableDetails extends RecyclerView.Adapter<RVAssignCaptainTableDetails.MyHolder> {
    private  Context mContext;
    private  ArrayList<TableFormId> tableFormIdArrayList;

    public RVAssignCaptainTableDetails(Context mContext, ArrayList<TableFormId> arrayTableFormIds) {
        this.mContext=mContext;
        this.tableFormIdArrayList=arrayTableFormIds;
    }

    @NonNull
    @Override
    public RVAssignCaptainTableDetails.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_table_item, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVAssignCaptainTableDetails.MyHolder myHolder, int i) {
        String id = String.valueOf(tableFormIdArrayList.get(i).getTableNo());
       myHolder.textView.setText(id);

       myHolder.imageView.setBackgroundResource(R.drawable.ic_table_green);

       /*else
        {
            myHolder.imageView.setBackgroundResource(R.drawable.ic_table_gray);
        }*/

    }

    @Override
    public int getItemCount() {
        return tableFormIdArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        private FrameLayout frameLayout;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.table_image);
            textView=itemView.findViewById(R.id.tv_table_no);
            frameLayout =itemView.findViewById(R.id.frameLayout);

        }
    }
}
