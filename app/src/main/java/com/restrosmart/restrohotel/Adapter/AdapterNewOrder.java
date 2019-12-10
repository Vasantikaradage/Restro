package com.restrosmart.restrohotel.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;import android.widget.ImageView;
import android.widget.TextView;

import com.restrosmart.restrohotel.Admin.ActivityMenuOrderDidplay;
import com.restrosmart.restrohotel.Model.OrderModel;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class AdapterNewOrder extends RecyclerView.Adapter<AdapterNewOrder.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 2;
    private static final int TYPE_FOOTER = 1;
    private Context context;
    private ArrayList<OrderModel> arrayList;
    private  ArrayList<String> arrayListIds;

    public AdapterNewOrder(Context context, ArrayList<OrderModel> arrayList,ArrayList<String> arrayList1) {
        this.context = context;
        this.arrayList = arrayList;
        arrayListIds=arrayList1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_new_oder_header, viewGroup, false);

        ViewHolder holder = new ViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String mob= (arrayList.get(i).getCustMob());
        String orderId= String.valueOf(arrayList.get(i).getOrder_id());
        String tableID= String.valueOf(arrayList.get(i).getTableId());
        String orderStatus=arrayList.get(i).getOrderStatusName();

       // arrayListIds.add("Order " + (i+ 1));

        if(orderStatus.equals("Ready"))
        {
            viewHolder.circle_image.setBackground(context.getResources().getDrawable(R.drawable.bg_img_green));
            viewHolder.cancelStamp.setVisibility(View.GONE);
        }
        else if(orderStatus.equals("Cancel"))
        {
            viewHolder.cancelStamp.setVisibility(View.VISIBLE);       }
        else
        {
            viewHolder.circle_image.setBackground(context.getResources().getDrawable(R.drawable.bg_yellow_circle));
            viewHolder.cancelStamp.setVisibility(View.GONE);
        }

        viewHolder.mCustNo.setText(mob);
        viewHolder.custName.setText(arrayList.get(i).getCustName());
      //  viewHolder.mOrderId.setText(orderId);
        viewHolder.mtableId.setText(tableID);
        viewHolder.mDateTime.setText(arrayList.get(i).getTime());



      /*  LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        viewHolder.rvMenu.setLayoutManager(linearLayoutManager);


        // recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);

        // if (userType.equals("Admin")) {
        AdapterMenuNewOrder adapterMenuNewOrder = new AdapterMenuNewOrder(context, arrayList.get(viewHolder.getAdapterPosition()).getArrayList());
        viewHolder.rvMenu.setAdapter(adapterMenuNewOrder);
*/



        // viewHolder.suggestion.setText(arrayList.get(i).getArrayList().get(i).getMenuDisp());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView suggestion;
        private TextView mCustNo, mDateTime, mOrderId, mTotal,mtableId,circle_image,custName;
        private RecyclerView rvMenu;
        private ImageView cancelStamp;



        // mTotal = (TextView) itemView.findViewById(R.id.total);
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCustNo = (TextView) itemView.findViewById(R.id.tv_cust_mobno);
            custName=itemView.findViewById(R.id.tv_cust_name);
            mDateTime = (TextView) itemView.findViewById(R.id.tv_date_time);
          //
            //
            //  mOrderId = (TextView) itemView.findViewById(R.id.order_id);
            suggestion = (TextView) itemView.findViewById(R.id.suggestion);
            mtableId=itemView.findViewById(R.id.circle_image);
            circle_image=itemView.findViewById(R.id.circle_image);
           // rvMenu=itemView.findViewById(R.id.recycler);
            cancelStamp=itemView.findViewById(R.id.image_cancel);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, ActivityMenuOrderDidplay.class);
                    intent.putExtra("orderArray", arrayList);
                    intent.putExtra("arrayListIds",arrayListIds);
                   context.startActivity(intent);

                }
            });

        }
    }

   /* @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       // if (viewType == TYPE_HEADER) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_new_oder_header, parent, false);

            return new HeaderViewHolder(itemView);

       *//* } else if (viewType == TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_new_order_recycler, parent, false);

            return new ItemViewHolder(itemView);
        } else if (viewType == TYPE_FOOTER) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_new_oder_footer, parent, false);

            return new FooterViewHolder(itemView);
        } else return null;*//*

        // throw new RuntimeException("no match for : " + viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {

            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            Toast.makeText(context,"HeaderViewHolder"+position,Toast.LENGTH_LONG).show();

            String mob= String.valueOf(arrayList.get(position).getCustMob());
            String orderNo= String.valueOf(arrayList.get(position).getOrder_id());
            ((headerHolder) ).mCustNo.setText(mob);
            ((headerHolder) ).mOrderId.setText(orderNo);
           // ((HeaderViewHolder) holder).mOrderId.setText(arrayList.get(position).getOrder_id());
           // ((HeaderViewHolder) holder).mDateTime.setText(arrayList.get(position).getTime());
            //((HeaderViewHolder) holder).mTotal.setText(context.getResources().getString(R.string.Rs) + " " + arrayList.get(position).getTot_bill());

        } else if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerHolder = (FooterViewHolder) holder;
            Toast.makeText(context,"FooterViewHolder"+position,Toast.LENGTH_LONG).show();

            // Toast.makeText(context,"FooterViewHolder"+position+-+2,Toast.LENGTH_LONG).show();

           // ((FooterViewHolder) holder).suggestion.setText(arrayList.get(position-2).getArrayList().get(position-2).getMenuDisp());








        } else if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            Toast.makeText(context,"ItemViewHolder"+position,Toast.LENGTH_LONG).show();

            //  Toast.makeText(context,"ItemViewHolder"+position+-+1,Toast.LENGTH_LONG).show();

            (itemViewHolder).menu_name.setText(arrayList.get(position-1).getArrayList().get(position-1).getMenuName());
            String qty= String.valueOf(arrayList.get(position-1).getArrayList().get(position-1).getMenuQty());
           String price= String.valueOf(arrayList.get(position -1).getArrayList().get(position-1).getMenuPrice());
            (itemViewHolder).menu_qty.setText(qty);
            (itemViewHolder).menu_price.setText(context.getResources().getString(R.string.Rs) + " " +price);

        } else {

        }
    }

    @Override
    public int getItemCount() {
      *//*  if (arrayList == null) {
            return 0;
        }

        if (arrayList.size() == 0) {
            //Return 1 here to show nothing
            return 1;
        }*//*

        // Add extra view to show the footer view
        return arrayList.size() + 2;
    }


    private OrderModel getItem(int position) {
        return arrayList.get(position);
    }


    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return TYPE_HEADER;
        } else if (position == arrayList.size() + 1) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
       *//* if (position == 0) {
            return TYPE_HEADER;
        } else if (position == arrayList.size()) {
            return TYPE_ITEM;
        } else if (position == arrayList.size() + 1) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;*//*

     *//*if (isPositionItem(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
        return TYPE_FOOTER;*//*

    }




    private boolean isPositionItem(int position) {
        return position == 0;
    }


    private class HeaderViewHolder extends RecyclerView.ViewHolder {

        TextView mCustNo, mDateTime, mOrderId, mTotal;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            mCustNo = (TextView) itemView.findViewById(R.id.tv_cust_mobno);
            mDateTime = (TextView) itemView.findViewById(R.id.tv_date_time);
            mOrderId = (TextView) itemView.findViewById(R.id.order_id);
           // mTotal = (TextView) itemView.findViewById(R.id.total);
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView menu_name, menu_qty, menu_price;

        public ItemViewHolder(View itemView1) {
            super(itemView1);
            menu_name = (TextView) itemView1.findViewById(R.id.tx_menu_name);
            menu_qty = (TextView) itemView1.findViewById(R.id.tx_menu_qty);
            menu_price = (TextView) itemView1.findViewById(R.id.tx_menu_price);
        }
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder {

        TextView suggestion;
       // ImageButton mPrint, mCall, mView, mCancel;
        // Button mCancel,mAcept;


        public FooterViewHolder(View itemView1) {
            super(itemView1);

            suggestion = (TextView) itemView1.findViewById(R.id.suggestion);

          //  mCall = (ImageButton) itemView1.findViewById(R.id.btn_call_cust);
           // mView = (ImageButton) itemView1.findViewById(R.id.btn_view);
           // mCancel = (ImageButton) itemView1.findViewById(R.id.btn_cancel_order);
            //  mAcept = (Button)itemView1.findViewById(R.id.btn_accept_order);
        }
    }*/
}



