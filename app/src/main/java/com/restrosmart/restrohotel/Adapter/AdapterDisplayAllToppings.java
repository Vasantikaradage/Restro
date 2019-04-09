package com.restrosmart.restrohotel.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.restrosmart.restrohotel.Model.CategoryForm;
import com.restrosmart.restrohotel.Model.ToppingsForm;
import com.restrosmart.restrohotel.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterDisplayAllToppings extends RecyclerView.Adapter<AdapterDisplayAllToppings.MyHolder> {

    private Context mContext;
    private List<ToppingsForm> arrayList;
    public AdapterDisplayAllToppings(Context context, ArrayList<ToppingsForm> toppingsForms) {
        this.mContext = context;
        this.arrayList = toppingsForms;

    }

    @NonNull
    @Override
    public AdapterDisplayAllToppings.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_toppings_item, viewGroup, false);
        return new AdapterDisplayAllToppings.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDisplayAllToppings.MyHolder myHolder, int i) {
        myHolder.tvToppingsName.setText(arrayList.get(i).getToppingsName());
        myHolder.tvToppingsPrice.setText(arrayList.get(i).getToppingsPrice());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView tvToppingsName;
        private TextView tvToppingsPrice, tvToppingsOptionMenu;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvToppingsName = itemView.findViewById(R.id.tv_toppings_name);
            tvToppingsPrice = itemView.findViewById(R.id.tv_toppings_price);
            tvToppingsOptionMenu = itemView.findViewById(R.id.tv_toppings_option_menu);
        }
    }
}
