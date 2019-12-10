package com.restrosmart.restrohotel.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.restrosmart.restrohotel.Model.OrderModel;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

/**
 * Created by SHREE on 10/10/2018.
 */

public class Kitchen_Ongoing_Order extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
private static final int TYPE_HEADER = 0;
private static final int TYPE_ITEM = 1;
private static final int TYPE_FOOTER= 2;

        Context context;

        ArrayList<OrderModel> arrayList;

public Kitchen_Ongoing_Order(Context context, ArrayList<OrderModel> arrayList) {

        this.context=context;
        this.arrayList=arrayList;
        }


@NonNull
@Override
public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.kitchen_new_oder_header, parent, false);

        return new HeaderViewHolder(itemView);

        }
        else if (viewType == TYPE_ITEM) {
        View itemView1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_new_order_recycler, parent, false);

        return new ItemViewHolder(itemView1);
        }

        else if (viewType == TYPE_FOOTER) {
        View itemView2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.kitchen_ongoing_footer, parent, false);

        return new FooterViewHolder(itemView2);
        }
        else return null;
        }

@Override
public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof HeaderViewHolder) {
        //((HeaderViewHolder) holder).mCustNo.setText(arrayList.get(position).getCustMob());
        ((HeaderViewHolder) holder).mOrderId.setText(arrayList.get(position).getOrder_id());
        // ((HeaderViewHolder) holder).mKitchenDateTime.setText(arrayList.get(position).getTime());
        //  Resources res = context.getResources();
        //  String format = res.getString(R.string.Rs);
        //((HeaderViewHolder) holder).mTotal.setText(format+" "+arrayList.get(position).getTot_bill());




        }else  if(holder instanceof FooterViewHolder)
        {

        //((FooterViewHolder) holder).suggestion.setText(arrayList.get(position-2).getMsg());
            /*((FooterViewHolder) holder).mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });*/



           /* ((FooterViewHolder) holder).mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i=new Intent(context,ActivityNewOrderViewDetails.class);
                    context.startActivity(i);

                }
            });
*/

           /* ((FooterViewHolder) holder).mCancel.setOnClickListener(new View.OnClickListener() {
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
       /* ((ItemViewHolder) holder).menu_name.setText(arrayList.get(position-1).getMenu_name());
        ((ItemViewHolder) holder).menu_qty.setText(arrayList.get(position-1).getMenu_qty());
        ((ItemViewHolder) holder).menu_price.setText(format+" "+arrayList.get(position-1).getMenu_price());
*/



        }
        else {

        }







        }

private class HeaderViewHolder extends RecyclerView.ViewHolder {

    TextView mCustNo,mDateTime,mOrderId,mTotal,mKitchenDateTime,tx_mtotal;

    public HeaderViewHolder(View itemView) {
        super(itemView);

        //  mCustNo = (TextView)itemView.findViewById(R.id.tv_cust_mobno);
        mDateTime = (TextView)itemView.findViewById(R.id.tv_date_time);
        mOrderId = (TextView)itemView.findViewById(R.id.order_id);


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
    Button mAccept;
    public FooterViewHolder(View itemView2) {
        super(itemView2);

        //    suggestion = (TextView)itemView2.findViewById(R.id.suggestion);
        //   mAccept = (Button) itemView2.findViewById(R.id.btn_accept);


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

    private OrderModel getItem(int position) {
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




}

