package com.restrosmart.restro.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.restrosmart.restro.Model.FlavourUnitForm;
import com.restrosmart.restro.R;

import java.util.ArrayList;

/**
 * Created by SHREE on 29/12/2018.
 */

class AdapterDisplayFlavourUnit extends RecyclerView.Adapter<AdapterDisplayFlavourUnit.MyHolder> {
    Context context;
    ArrayList<FlavourUnitForm> flavourUnitFormArrayList;

    public AdapterDisplayFlavourUnit(Context context, ArrayList<FlavourUnitForm> flavourUnitFormArrayList) {
   this.flavourUnitFormArrayList=flavourUnitFormArrayList;
   this.context=context;

    }

    @NonNull
    @Override
    public AdapterDisplayFlavourUnit.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_unit_list, parent, false);

       MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDisplayFlavourUnit.MyHolder holder, int position) {
holder.tvUnitName.setText(flavourUnitFormArrayList.get(position).getUnitName());
//holder.tvUnitPrice.setText(flavourUnitFormArrayList.get(position).getUnitPrice());
    }

    @Override
    public int getItemCount() {
        return flavourUnitFormArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView tvUnitName,tvUnitPrice;

        public MyHolder(View itemView) {
            super(itemView);
            tvUnitName=(TextView)itemView.findViewById(R.id.tv_unit_name);
            tvUnitPrice=(TextView)itemView.findViewById(R.id.tv_unit_price);
        }
    }
}
