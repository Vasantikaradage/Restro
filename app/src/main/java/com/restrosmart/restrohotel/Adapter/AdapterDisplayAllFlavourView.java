package com.restrosmart.restrohotel.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.restrosmart.restrohotel.Model.FlavourUnitForm;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class AdapterDisplayAllFlavourView extends RecyclerView.Adapter<AdapterDisplayAllFlavourView.MyHolder> {
    private Context mContext;
    private ArrayList<FlavourUnitForm> arrayListFlavourUnitForm;

    public AdapterDisplayAllFlavourView(Context context, ArrayList<FlavourUnitForm> arrayListUnits) {
        this.mContext = context;
        this.arrayListFlavourUnitForm = arrayListUnits;

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_flavour_all_view, viewGroup, false);

        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        myHolder.unitName.setText(arrayListFlavourUnitForm.get(i).getUnitName());

        String price = String.valueOf(arrayListFlavourUnitForm.get(i).getUnitPrice());
        myHolder.unitPrice.setText("\u20B9 " + price);

        if (arrayListFlavourUnitForm.size() == 1) {
            myHolder.viewLine.setVisibility(View.GONE);
        } else {
            myHolder.viewLine.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return arrayListFlavourUnitForm.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView unitName, unitPrice;
        private View viewLine;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            unitName = itemView.findViewById(R.id.tv_unit_name);
            unitPrice = itemView.findViewById(R.id.tv_unit_price);
            viewLine = itemView.findViewById(R.id.view_line);

        }
    }
}
