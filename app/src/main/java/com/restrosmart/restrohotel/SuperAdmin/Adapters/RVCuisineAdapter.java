package com.restrosmart.restrohotel.SuperAdmin.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.SuperAdmin.Models.CuisineForm;

import java.util.ArrayList;

public class RVCuisineAdapter  extends RecyclerView.Adapter<RVCuisineAdapter.ViewHolder>{
    private  Context mContext;
    private  ArrayList<CuisineForm> cuisineFormArrayList;


    public RVCuisineAdapter(Context context, ArrayList<CuisineForm> cuisineFormArrayList) {
        this.mContext=context;
        this.cuisineFormArrayList=cuisineFormArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cuisine_detail_list, viewGroup, false);
        ViewHolder vh = new ViewHolder(view); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.checkBox.setText(cuisineFormArrayList.get(i).getCuisineName());

    }

    @Override
    public int getItemCount() {
        return cuisineFormArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox=itemView.findViewById(R.id.checkbox_cuisine);
        }
    }
}
