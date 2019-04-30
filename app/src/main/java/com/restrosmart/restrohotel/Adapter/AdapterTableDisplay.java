package com.restrosmart.restrohotel.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.restrosmart.restrohotel.Admin.ActivityTableInformation;
import com.restrosmart.restrohotel.Model.TableFormId;
import com.restrosmart.restrohotel.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterTableDisplay extends RecyclerView.Adapter<AdapterTableDisplay.MyHolder>{
   private  Context mContext;
   private  ArrayList<? extends TableFormId> arrayListTableId;

    public AdapterTableDisplay(Context context, ArrayList<? extends TableFormId> arrayListTableIds) {
   this.mContext=context;
   this.arrayListTableId=arrayListTableIds;

    }

    @NonNull
    @Override
    public AdapterTableDisplay.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_table_item, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTableDisplay.MyHolder myHolder, int i) {

      /*  Picasso.with(mContext).
                load(R.drawable.ic_table_green)
                .resize(500, 500).
                       into(myHolder.imageView);*/
        String id = String.valueOf(arrayListTableId.get(i).getTableId());
      myHolder.textView.setText(id);
      if(arrayListTableId.get(i).getTableStatus()==1) {

          myHolder.imageView.setBackgroundResource(R.drawable.ic_table_green);
      }
      else
      {
          myHolder.imageView.setBackgroundResource(R.drawable.ic_table_gray);
      }


    }

    @Override
    public int getItemCount() {
        return arrayListTableId.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;



        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.table_image);
            textView=itemView.findViewById(R.id.tv_table_no);
        }
    }
}
