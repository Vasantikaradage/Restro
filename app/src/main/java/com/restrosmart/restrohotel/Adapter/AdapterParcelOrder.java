package com.restrosmart.restrohotel.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.restrosmart.restrohotel.Admin.ActivityMenuOrderDidplay;
import com.restrosmart.restrohotel.Model.OrderModel;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class AdapterParcelOrder  extends RecyclerView.Adapter<AdapterParcelOrder.ItemViewHolder> {

    private  Context mContext;
    private  ArrayList<OrderModel> orderParcelModelArrayList;
    private  ArrayList<String> arrayListIds=new ArrayList<>();
    public AdapterParcelOrder(Context context, ArrayList<OrderModel> arraylistParcelOrder, ArrayList<String> arrayList) {
        this.mContext=context;
        this.orderParcelModelArrayList=arraylistParcelOrder;
        arrayListIds=arrayList;
    }

    @NonNull
    @Override
    public AdapterParcelOrder.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_parcel_oder_header, viewGroup, false);

        ItemViewHolder holder = new ItemViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterParcelOrder.ItemViewHolder itemViewHolder, int i) {
        String mob= (orderParcelModelArrayList.get(i).getCustMob());
        String orderId= String.valueOf(orderParcelModelArrayList.get(i).getOrder_id());
        String tableID= String.valueOf(orderParcelModelArrayList.get(i).getTableId());
        String orderStatus=orderParcelModelArrayList.get(i).getOrderStatusName();
     //   arrayListIds.add("Order " + (i + 1));

      /*  if(orderStatus.equals("Ready"))
        {
            itemViewHolder.circle_image.setBackground(mContext.getResources().getDrawable(R.drawable.bg_img_green));
            itemViewHolder.cancelStamp.setVisibility(View.GONE);
        }
        else if(orderStatus.equals("Cancel"))
        {
            itemViewHolder.cancelStamp.setVisibility(View.VISIBLE);       }
        else
        {
            itemViewHolder.circle_image.setBackground(mContext.getResources().getDrawable(R.drawable.bg_yellow_circle));
            itemViewHolder.cancelStamp.setVisibility(View.GONE);
        }*/

        itemViewHolder.mCustNo.setText(mob);
        itemViewHolder.custName.setText(orderParcelModelArrayList.get(i).getCustName());
        //  viewHolder.mOrderId.setText(orderId);
        itemViewHolder.mtableId.setText(tableID);
        itemViewHolder.mDateTime.setText(orderParcelModelArrayList.get(i).getTime());


    }

    @Override
    public int getItemCount() {
        return orderParcelModelArrayList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mCustNo, mDateTime, mOrderId, mTotal,mtableId,circle_image,custName;
        private RecyclerView rvMenu;
        private ImageView cancelStamp;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            mCustNo = (TextView) itemView.findViewById(R.id.tv_cust_mobno);
            custName=itemView.findViewById(R.id.tv_cust_name);
            mDateTime = (TextView) itemView.findViewById(R.id.tv_date_time);
            //
            //
            //  mOrderId = (TextView) itemView.findViewById(R.id.order_id);
           /// suggestion = (TextView) itemView.findViewById(R.id.suggestion);
            mtableId=itemView.findViewById(R.id.circle_image);
            circle_image=itemView.findViewById(R.id.circle_image);
           // rvMenu=itemView.findViewById(R.id.recycler);
            cancelStamp=itemView.findViewById(R.id.image_cancel);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(mContext, ActivityMenuOrderDidplay.class);

                    intent.putExtra("orderArray", orderParcelModelArrayList);
                    intent.putExtra("arrayListIds",arrayListIds);
                   // intent.putParcelableArrayListExtra("menuArray", orderParcelModelArrayList.get(getAdapterPosition()).getArrayList());
                    mContext.startActivity(intent);

                }
            });

        }
    }
}
