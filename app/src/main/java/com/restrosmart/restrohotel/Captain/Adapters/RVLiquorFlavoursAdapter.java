package com.restrosmart.restrohotel.Captain.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.restrosmart.restrohotel.Captain.Interfaces.FlavourSelectedListener;
import com.restrosmart.restrohotel.Captain.Models.FlavoursModel;
import com.restrosmart.restrohotel.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RVLiquorFlavoursAdapter extends RecyclerView.Adapter<RVLiquorFlavoursAdapter.ItemViewHolder> {

    private ArrayList<FlavoursModel> arrayList;
    private Context mContext;
    private FlavourSelectedListener mFlavourSelectedListener;
    private int selected_position = 0;

    public RVLiquorFlavoursAdapter(Context mContext, ArrayList<FlavoursModel> array, FlavourSelectedListener flavourSelectedListener) {
        this.arrayList = array;
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
        Picasso.with(mContext)
                .load(arrayList.get(position).getFlavourImg())
                .into(holder.ivFlavourLiqour);

        holder.ivFlavourLiqour.setBackground(selected_position == position ? mContext.getDrawable(R.drawable.item_selected) : mContext.getDrawable(R.drawable.item_unselected));
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

        private ImageView ivFlavourLiqour;

        ItemViewHolder(View itemView) {
            super(itemView);

            ivFlavourLiqour = itemView.findViewById(R.id.ivFlavourLiqour);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getAdapterPosition() == RecyclerView.NO_POSITION) return;

                    // Updating old as well as new positions
                    notifyItemChanged(selected_position);
                    selected_position = getAdapterPosition();
                    notifyItemChanged(selected_position);
                    
                    mFlavourSelectedListener.flavourSelected(getAdapterPosition());
                }
            });
        }
    }
}
