package com.restrosmart.restro.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.restrosmart.restro.Interfaces.FlavourSelectedListener;
import com.restrosmart.restro.Interfaces.FlavourSelectedListener;
import com.restrosmart.restro.R;
import com.squareup.picasso.Picasso;

public class RVLiquorFlavoursAdapter extends RecyclerView.Adapter<RVLiquorFlavoursAdapter.ItemViewHolder> {

    private int[] array;
    private Context mContext;
    private FlavourSelectedListener mFlavourSelectedListener;

    public RVLiquorFlavoursAdapter(Context mContext, int[] array, FlavourSelectedListener flavourSelectedListener) {
        this.array = array;
        this.mContext = mContext;
        this.mFlavourSelectedListener = flavourSelectedListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_liquor_flavour_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Picasso.with(mContext).load(array[position]).into(holder.ivFlavourLiqour);
    }

    @Override
    public int getItemCount() {
        return array.length;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivFlavourLiqour;

        ItemViewHolder(View itemView) {
            super(itemView);

            ivFlavourLiqour = itemView.findViewById(R.id.ivFlavourLiqour);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mFlavourSelectedListener.flavourSelected(getAdapterPosition());
                }
            });
        }
    }
}
