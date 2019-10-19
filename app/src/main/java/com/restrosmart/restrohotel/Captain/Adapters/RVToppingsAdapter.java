package com.restrosmart.restrohotel.Captain.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.restrosmart.restrohotel.Captain.Interfaces.ToppingsListener;
import com.restrosmart.restrohotel.Captain.Models.ToppingsModel;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class RVToppingsAdapter extends RecyclerView.Adapter<RVToppingsAdapter.ItemViewHolder> {

    private Context mContext;
    private ArrayList<ToppingsModel> arrayList;
    private ArrayList<ToppingsModel> toppingsList;
    private ToppingsListener mToppingsListener;

    public RVToppingsAdapter(Context mContext, ArrayList<ToppingsModel> arrayList, ToppingsListener toppingsListener) {
        this.mContext = mContext;
        this.arrayList = arrayList;
        this.mToppingsListener = toppingsListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cap_rv_toppings_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int i) {
        holder.tvToppingsName.setText(arrayList.get(i).getToppingsName());
        holder.tvToppingsPrice.setText(" ("+mContext.getResources().getString(R.string.currency) + String.valueOf(arrayList.get(i).getToppingsPrice())+")");
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

        private CheckBox cbToppings;
        private TextView tvToppingsName, tvToppingsPrice;
        private LinearLayout llToppings;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            cbToppings = itemView.findViewById(R.id.cbToppings);
            tvToppingsName = itemView.findViewById(R.id.tvToppingsName);
            tvToppingsPrice = itemView.findViewById(R.id.tvToppingsPrice);
            llToppings = itemView.findViewById(R.id.llToppings);

            toppingsList = new ArrayList<>();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!cbToppings.isChecked()) {
                        cbToppings.setChecked(true);
                    } else {
                        cbToppings.setChecked(false);
                    }
                }
            });

            cbToppings.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {
                        toppingsList.add(arrayList.get(getAdapterPosition()));
                        mToppingsListener.AddRemoveToppings(toppingsList);
                    } else {
                        ToppingsModel toppingsModel = arrayList.get(getAdapterPosition());

                        for (int i = 0; i < toppingsList.size(); i++) {
                            if (toppingsList.get(i).getToppingsId() == toppingsModel.getToppingsId()) {
                                toppingsList.remove(i);
                                mToppingsListener.AddRemoveToppings(toppingsList);
                            }
                        }
                    }
                }
            });
        }
    }
}
