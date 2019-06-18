package com.restrosmart.restrohotel.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.restrosmart.restrohotel.Admin.ActivityPastViewOrderDetails;
import com.restrosmart.restrohotel.Model.OrderModel;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

/**
 * Created by SHREE on 05/10/2018.
 */

public class AdapterPastOrder  extends RecyclerView.Adapter<AdapterPastOrder.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER= 2;
    //ArrayList<T_Attendance_Form> formArrayList;

    Context context;

    ArrayList<OrderModel> arrayList;



    public AdapterPastOrder(Context context, ArrayList<OrderModel> arrayList) {

        this.context=context;
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public AdapterPastOrder.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_past_cancel_order, parent, false);

        ViewHolder holder = new ViewHolder(itemView);
        return holder;
        // throw new RuntimeException("no match for : " + viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPastOrder.ViewHolder viewHolder, int i) {
        String mob= String.valueOf(arrayList.get(i).getCust_mob_no());
        String orderId= String.valueOf(arrayList.get(i).getOrder_id());
        String tableID= String.valueOf(arrayList.get(i).getTableId());
        String orderStatus=arrayList.get(i).getOrder_Status_Name();

        if(orderStatus.equals("Cancel")){
            viewHolder.image_cancel_stamp.setVisibility(View.VISIBLE);
        }
        else
        {

            viewHolder.image_cancel_stamp.setVisibility(View.GONE);
            //viewHolder.circle_image.setBackground(context.getResources().getDrawable(R.drawable.bg_yellow_circle));
        }

        viewHolder.mCustNo.setText(mob);
        viewHolder.mOrderId.setText(orderId);
        viewHolder.mtableId.setText(tableID);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        viewHolder.rvMenu.setLayoutManager(linearLayoutManager);


        // recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);

        // if (userType.equals("Admin")) {
        AdapterMenuPastOrder adapterMenuPastOrder = new AdapterMenuPastOrder(context
                , arrayList.get(viewHolder.getAdapterPosition()).getArrayList());
        viewHolder.rvMenu.setAdapter(adapterMenuPastOrder);



    }



    @Override
    public int getItemCount() {

        return arrayList.size() ;
    }

    private OrderModel getItem(int position) {
        return arrayList.get(position);
    }



    /* @Override
     public int getItemViewType(int position) {
        *//* if (isPositionItem(position))

            return TYPE_HEADER;
        return TYPE_ITEM;
       // return TYPE_FOOTER;*//*

        if (position == 0) {
            return TYPE_HEADER;
        } else if (position == arrayList.size() ) {

            return TYPE_ITEM;


        }

        else if (position == arrayList.size()+1 ) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;

    }*/
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView suggestion;
        private TextView mCustNo, mDateTime, mOrderId, mTotal,mtableId,circle_image;
        private RecyclerView rvMenu;
        private ImageView image_cancel_stamp;
        public ViewHolder(View itemView) {
            super(itemView);

            mCustNo = (TextView) itemView.findViewById(R.id.tv_cust_mobno);
            mDateTime = (TextView) itemView.findViewById(R.id.tv_date_time);
            mOrderId = (TextView) itemView.findViewById(R.id.order_id);
            suggestion = (TextView) itemView.findViewById(R.id.suggestion);
            mtableId=itemView.findViewById(R.id.circle_image);
            circle_image=itemView.findViewById(R.id.circle_image);
            rvMenu=itemView.findViewById(R.id.recycler);
            image_cancel_stamp=itemView.findViewById(R.id.image_stamp);
        }
    }
}

