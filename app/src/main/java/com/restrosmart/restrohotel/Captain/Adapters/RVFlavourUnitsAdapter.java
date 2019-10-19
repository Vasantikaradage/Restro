package com.restrosmart.restrohotel.Captain.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.restrosmart.restrohotel.Captain.Interfaces.FlavourUnitSelectedListener;
import com.restrosmart.restrohotel.Captain.Models.FlavourUnitModel;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class RVFlavourUnitsAdapter extends RecyclerView.Adapter<RVFlavourUnitsAdapter.ItemViewHolder> {

    private Context mContext;
    private ArrayList<FlavourUnitModel> arrayList;
    private FlavourUnitSelectedListener mFlavourUnitSelectedListener;
    private int selected_position = 0;

    public RVFlavourUnitsAdapter(Context context, ArrayList<FlavourUnitModel> flavourUnitModelArrayList, FlavourUnitSelectedListener flavourUnitSelectedListener) {
        this.mContext = context;
        this.arrayList = flavourUnitModelArrayList;
        this.mFlavourUnitSelectedListener = flavourUnitSelectedListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cap_rv_unit_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int position) {
        itemViewHolder.tvFlavourUnit.setText(arrayList.get(position).getUnitName());

        itemViewHolder.tvFlavourUnit.setBackground(selected_position == position ? mContext.getDrawable(R.drawable.item_selected) : mContext.getDrawable(R.drawable.item_unselected));
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

        private TextView tvFlavourUnit;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            tvFlavourUnit = itemView.findViewById(R.id.tvFlavourUnit);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getAdapterPosition() == RecyclerView.NO_POSITION) return;

                    // Updating old as well as new positions
                    notifyItemChanged(selected_position);
                    selected_position = getAdapterPosition();
                    notifyItemChanged(selected_position);

                    mFlavourUnitSelectedListener.unitSelected(getAdapterPosition(), arrayList);
                }
            });
        }
    }

    public void refreshList(ArrayList<FlavourUnitModel> refreshList) {
        selected_position = 0;
        this.arrayList = refreshList;
        notifyDataSetChanged();
    }
}
