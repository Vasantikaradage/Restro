package com.restrosmart.restrohotel.SuperAdmin.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.SuperAdmin.Models.CuisineForm;

import java.util.ArrayList;

public class RVCuisineDisplay  extends RecyclerView.Adapter<RVCuisineDisplay.ItemViewHolder> {
    private Context mContext;
    private  ArrayList<CuisineForm> cuisineFormArrayList;


    public RVCuisineDisplay(Context mContext, ArrayList<CuisineForm> cuisineFormArrayList) {
        this.mContext=mContext;
        this.cuisineFormArrayList=cuisineFormArrayList;
    }

    @NonNull
    @Override
    public RVCuisineDisplay.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_cuisine_tag_item, viewGroup, false);
        ItemViewHolder vh = new ItemViewHolder(view); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RVCuisineDisplay.ItemViewHolder itemViewHolder, int i) {
        itemViewHolder.tvCuisine.setText(cuisineFormArrayList.get(i).getCuisineName());

    }

    @Override
    public int getItemCount() {
        return cuisineFormArrayList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCuisine;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCuisine=itemView.findViewById(R.id.tv_cuisine_or_tag);
        }
    }
}
