package com.restrosmart.restrohotel.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.restrosmart.restrohotel.Interfaces.DeleteListener;
import com.restrosmart.restrohotel.Model.FlavourUnitForm;
import com.restrosmart.restrohotel.Model.UnitForm;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class RVAdapterUnitView extends RecyclerView.Adapter<RVAdapterUnitView.MyHolder> {
  private Context mContext;
  private  ArrayList<FlavourUnitForm> arrayListUnit;
  private DeleteListener deleteListener;


    public RVAdapterUnitView(Context applicationContext, ArrayList<FlavourUnitForm> arrayListUnit, DeleteListener deleteListener) {
    this.mContext=applicationContext;
    this.arrayListUnit=arrayListUnit;
    this.deleteListener=deleteListener;

    }

    @NonNull
    @Override
    public RVAdapterUnitView.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_unit_item, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVAdapterUnitView.MyHolder myHolder, final int i) {
        myHolder.tvUnitname.setText(arrayListUnit.get(i).getUnitName());
        String price= String.valueOf(arrayListUnit.get(i).getUnitPrice());
        myHolder.tvUnitPrice.setText(price);
        myHolder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               deleteListener.getDeleteListenerPosition(i);
               notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayListUnit.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private  TextView tvUnitname,tvUnitPrice;
        private ImageButton btnCancel;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvUnitname=itemView.findViewById(R.id.tv_unit_name);
            tvUnitPrice=itemView.findViewById(R.id.tv_unit_price);
            btnCancel=itemView.findViewById(R.id.img_btn_cancel);
        }
    }
}
