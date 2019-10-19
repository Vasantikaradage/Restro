package com.restrosmart.restrohotel.Captain.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.restrosmart.restrohotel.Captain.Models.ToppingsModel;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class FoodLiqCartTopAdapter extends RecyclerView.Adapter<FoodLiqCartTopAdapter.ItemViewHolder> {

    private Context mContext;
    private ArrayList<ToppingsModel> arrayList;

    public FoodLiqCartTopAdapter(Context context, ArrayList<ToppingsModel> toppingsModelArrayList) {
        this.mContext = context;
        this.arrayList = toppingsModelArrayList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartmenu_top_list_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.tvFoodTopName.setText(arrayList.get(position).getToppingsName());
        holder.tvFoodTopPrice.setText("(" + mContext.getResources().getString(R.string.currency) + String.valueOf(arrayList.get(position).getToppingsPrice()) + ")");
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

        private TextView tvFoodTopName, tvFoodTopPrice;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            tvFoodTopName = itemView.findViewById(R.id.tvFoodTopName);
            tvFoodTopPrice = itemView.findViewById(R.id.tvFoodTopPrice);
        }
    }
}
