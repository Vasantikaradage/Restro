package com.restrosmart.restrohotel.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.restrosmart.restrohotel.Model.AdminOrderModel;
import com.restrosmart.restrohotel.Model.MenuForm;
import com.restrosmart.restrohotel.Model.OrderModel;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class RVOrderDetailsAdapter extends RecyclerView.Adapter<RVOrderDetailsAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<String> arrayListIds;
    private ArrayList<AdminOrderModel> omenuFormArrayList;
    private ArrayList<Integer> counter = new ArrayList<Integer>();


    public RVOrderDetailsAdapter(Context baseContext, ArrayList<String> arrayList, ArrayList<AdminOrderModel> menuFormArrayList) {
        this.mContext = baseContext;
        this.arrayListIds = arrayList;
        this.omenuFormArrayList=menuFormArrayList;
        for (int i = 0; i < arrayListIds.size(); i++) {
            counter.add(0);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_item_details_list, viewGroup, false);
        ViewHolder holder = new ViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

       viewHolder.tvOrdertitle.setText(arrayListIds.get(i).toString());
       viewHolder.tvOrderStatus.setText(" ("+omenuFormArrayList.get(i).getOrderStatus() +")");
       viewHolder.tvSubTotal.setText(mContext.getResources().getString(R.string.currency) + String.valueOf(omenuFormArrayList.get(i).getSubTotal()));



       ArrayList<MenuForm> menuFormArrayList=omenuFormArrayList.get(i).getAdminMenuModelArrayList();

       if(menuFormArrayList != null && menuFormArrayList.size() > 0) {

           LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
           viewHolder.rvMenuDetails.setHasFixedSize(true);
           viewHolder.rvMenuDetails.setLayoutManager(linearLayoutManager);
           RVOrderMenuDetailsAdapter rvOrderMenuDetailsAdapter = new RVOrderMenuDetailsAdapter(mContext, menuFormArrayList);
           viewHolder.rvMenuDetails.setAdapter(rvOrderMenuDetailsAdapter);
       }



    }

    @Override
    public int getItemCount() {
        return arrayListIds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvOrdertitle,tvOrderStatus,tvSubTotal;
        private TextView tvMenuName, tvMenuPrice, tvMenuQty;
        private ImageView ivArrow;
        private  RecyclerView rvMenuDetails;
        private LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           tvOrdertitle = itemView.findViewById(R.id.tvOrderName);
            tvOrderStatus=itemView.findViewById(R.id.tvOrderStatus);
            ivArrow=itemView.findViewById(R.id.ivArrow);
            rvMenuDetails=itemView.findViewById(R.id.rvMenuOrder);
            linearLayout=itemView.findViewById(R.id.llView);
            tvSubTotal=itemView.findViewById(R.id.tvSubTotal);
           // tvMenuName = itemView.findViewById(R.id.tx_menu_name);
           // tvMenuPrice = itemView.findViewById(R.id.tx_menu_price);
          //  tvMenuQty = itemView.findViewById(R.id.tx_menu_qty);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (counter.get(getAdapterPosition()) % 2 == 0) {
                        linearLayout.setVisibility(View.VISIBLE);
                        rvMenuDetails.setVisibility(View.VISIBLE);
                        ivArrow.setImageDrawable(mContext.getDrawable(R.drawable.ic_up_arrow_16dp));
                    } else {
                        rvMenuDetails.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.GONE);
                        ivArrow.setImageDrawable(mContext.getDrawable(R.drawable.ic_down_arrow_16dp));
                    }

                    counter.set(getAdapterPosition(), counter.get(getAdapterPosition()) + 1);
                }
            });

        }
    }
}
