package com.restrosmart.restrohotel.SuperAdmin.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.SuperAdmin.Interfaces.CuisineListener;
import com.restrosmart.restrohotel.SuperAdmin.Models.CuisineForm;

import java.util.ArrayList;

public class RVCuisineAdapter  extends RecyclerView.Adapter<RVCuisineAdapter.ViewHolder>{
    private  Context mContext;
    private  ArrayList<CuisineForm> cuisineFormArrayList;
  private  ArrayList<CuisineForm> cuisineCheckedFormArrayList;
    private  CuisineListener cuisineListener;


    public RVCuisineAdapter(Context context, ArrayList<CuisineForm> cuisineFormArrayList, ArrayList<CuisineForm> arrayListCheckedCuisine, ArrayList<CuisineForm> getArrayListCheckedCuisinePos, CuisineListener cuisineListener) {
        this.mContext=context;
        this.cuisineFormArrayList=cuisineFormArrayList;
        this.cuisineListener=cuisineListener;
        this.cuisineCheckedFormArrayList=getArrayListCheckedCuisinePos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cuisine_detail_list, viewGroup, false);
        ViewHolder vh = new ViewHolder(view); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.tvCuisine.setText(cuisineFormArrayList.get(i).getCuisineName());
        /*if (cuisineCheckedFormArrayList == null || cuisineCheckedFormArrayList.size() == 0) {
            viewHolder.checkBox.setChecked(cuisineFormArrayList.get(i).isSelected());
        } else {
            for (int position = 0; position < cuisineCheckedFormArrayList.size(); position++) {
                if (cuisineCheckedFormArrayList.get(i).getCuisineId() == cuisineCheckedFormArrayList.get(position).getCuisineId())
                    {
                        viewHolder.checkBox.setChecked(true);
                    break;

                } else {
                  //  ((ItemViewHolder) viewHolder).cbSelect.setChecked(arrayListToppings.get(i - 1).isSelected());
                    viewHolder.checkBox.setChecked(cuisineFormArrayList.get(i).isSelected());

                }
            }*/
    }







    @Override
    public int getItemCount() {
        return cuisineFormArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox;
        private TextView tvCuisine;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox=itemView.findViewById(R.id.checkbox_cuisine);
            tvCuisine=itemView.findViewById(R.id.tv_cuisine);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        cuisineFormArrayList.get(getAdapterPosition()).setSelected(true);
                        cuisineListener.getCuisineListenerPosition(cuisineFormArrayList);
                    } else {
                        cuisineFormArrayList.get(getAdapterPosition()).setSelected(false);
                        cuisineListener.getCuisineListenerPosition(cuisineFormArrayList);
                    }

                }
            });


        }


    }
}
