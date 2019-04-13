package com.restrosmart.restrohotel.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.restrosmart.restrohotel.Admin.ActivityPastViewOrderDetails;
import com.restrosmart.restrohotel.Model.Orders;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

/**
 * Created by SHREE on 05/10/2018.
 */

public class AdapterPastOrder extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER= 2;
    //ArrayList<T_Attendance_Form> formArrayList;

    Context context;

    ArrayList<Orders> arrayList;



    public AdapterPastOrder(Context context, ArrayList<Orders> arrayList) {

        this.context=context;
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_new_oder_header, parent, false);

            return new HeaderViewHolder(itemView);

        }
        else if (viewType == TYPE_ITEM) {
            View itemView1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_new_order_recycler, parent, false);

            return new ItemViewHolder(itemView1);
        }

        else if (viewType == TYPE_FOOTER) {
            View itemView2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_past_oder_footer, parent, false);

            return new FooterViewHolder(itemView2);
        }
        else return null;

        // throw new RuntimeException("no match for : " + viewType);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).mCustNo.setText(arrayList.get(position).getCust_mob_no());
            ((HeaderViewHolder) holder).mOrderId.setText(arrayList.get(position).getOrder_id());
            ((HeaderViewHolder) holder).mDateTime.setText(arrayList.get(position).getTime());
            Resources res = context.getResources();
            String format = res.getString(R.string.Rs);
            ((HeaderViewHolder) holder).mTotal.setText(format+" "+arrayList.get(position).getTot_bill());




        }else  if(holder instanceof FooterViewHolder)
        {

       //   ((FooterViewHolder) holder).suggestion.setText(arrayList.get(position-2).getMsg());
            ((FooterViewHolder) holder).mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(context, ActivityPastViewOrderDetails.class);
                    context.startActivity(i);

                }
            });

/*

            ((FooterViewHolder) holder).mPrint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
*//*


            ((FooterViewHolder) holder).mCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });


            ((FooterViewHolder) holder).mAcept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });*/



        }

        else if(holder instanceof ItemViewHolder)
        {

            Resources res = context.getResources();
            String format = res.getString(R.string.Rs);
            ((ItemViewHolder) holder).menu_name.setText(arrayList.get(position-1).getMenu_name());
            ((ItemViewHolder) holder).menu_qty.setText(arrayList.get(position-1).getMenu_qty());
            ((ItemViewHolder) holder).menu_price.setText(format+" "+arrayList.get(position-1).getMenu_price());




        }
        else {

        }




    }

    @Override
    public int getItemCount() {
        if (arrayList == null) {
            return 0;
        }

        if (arrayList.size() == 0) {
            //Return 1 here to show nothing
            return 1;
        }

        // Add extra view to show the footer view
        return arrayList.size() + 2;
    }

    private Orders getItem(int position) {
        return arrayList.get(position);
    }



    @Override
    public int getItemViewType(int position) {
       /* if (isPositionItem(position))

            return TYPE_HEADER;
        return TYPE_ITEM;
       // return TYPE_FOOTER;*/

        if (position == 0) {
            return TYPE_HEADER;
        } else if (position == arrayList.size() ) {

            return TYPE_ITEM;


        }

        else if (position == arrayList.size()+1 ) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;

    }


    private boolean isPositionItem(int position) {

        return position == 0;
    }







    private class HeaderViewHolder extends RecyclerView.ViewHolder {

        TextView mCustNo,mDateTime,mOrderId,mTotal;

        public HeaderViewHolder(View itemView) {
            super(itemView);

            mCustNo = (TextView)itemView.findViewById(R.id.tv_cust_mobno);
            mDateTime = (TextView)itemView.findViewById(R.id.tv_date_time);
            mOrderId = (TextView)itemView.findViewById(R.id.order_id);
            mTotal = (TextView)itemView.findViewById(R.id.total);
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView menu_name, menu_qty, menu_price;

        public ItemViewHolder(View itemView1) {
            super(itemView1);

            menu_name=(TextView)itemView1.findViewById(R.id.tx_menu_name);
            menu_qty=(TextView)itemView1.findViewById(R.id.tx_menu_qty);
            menu_price=(TextView)itemView1.findViewById(R.id.tx_menu_price);
        }
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder {

        TextView suggestion;
        ImageButton mView;
       /* Button mCancel,mAcept,mCall;*/


        public FooterViewHolder(View itemView1) {
            super(itemView1);

          suggestion = (TextView)itemView1.findViewById(R.id.suggestion);

            mView = (ImageButton) itemView1.findViewById(R.id.btn_pastorder_view);
        /*        // mPrint = (ImageButton)itemView1.findViewById(R.id.btn_print);
            mCancel = (Button)itemView1.findViewById(R.id.btn_cancel_order);
            mAcept = (Button)itemView1.findViewById(R.id.btn_accept_order);*/
        }
    }




}

