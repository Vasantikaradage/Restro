package com.restrosmart.restrohotel.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.restrosmart.restrohotel.Admin.ActivitySelectMenu;
import com.restrosmart.restrohotel.Interfaces.ButtonListerner;
import com.restrosmart.restrohotel.Interfaces.DeleteListener;
import com.restrosmart.restrohotel.Interfaces.EditListener;
import com.restrosmart.restrohotel.Interfaces.PositionListener;

import com.restrosmart.restrohotel.Model.RushHourForm;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class AdapterRVRushHours extends RecyclerView.Adapter<AdapterRVRushHours.ItemViewHolder> {
    private Context mContext;
    private ArrayList<RushHourForm> rushHourFormArrayList;
    private EditListener editListener;
    private PositionListener positionListener;
    private  DeleteListener deleteRushHoursListener;

    public AdapterRVRushHours(Context context, ArrayList<RushHourForm> rushHourPromoCode, DeleteListener deleteListener, PositionListener positionListener, EditListener editListener) {
        this.mContext=context;
        this.rushHourFormArrayList=rushHourPromoCode;
        this.deleteRushHoursListener=deleteListener;
        this.editListener=editListener;
        this.positionListener=positionListener;

    }

    @NonNull
    @Override
    public AdapterRVRushHours.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView;
//        if(i == R.layout.item_rush_hours) {
//            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_rush_hours, viewGroup, false);
//        }
//        else
//        {
            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_rush_hours, viewGroup, false);

     //   }
     ItemViewHolder ItemViewHolder=new ItemViewHolder(itemView);
        return ItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRVRushHours.ItemViewHolder itemViewHolder, int i) {
       /* if(i == rushHourFormArrayList.size()) {
            itemViewHolder.imgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    buttonListerner.getEditListenerPosition(1);
                    //itemViewHolder.linearLayout.setVisibility(View.VISIBLE);
                    // Toast.makeText(mContext, "Button Clicked", Toast.LENGTH_LONG).show();
                }
            });
        }else {*/
          //  itemViewHolder.tvTime.setText(rushHourFormArrayList.get(i).getTime());
          //  itemViewHolder.tvDate.setText(rushHourFormArrayList.get(i).getDate());


       // }
    }

    @Override
    public int getItemCount() {
        return rushHourFormArrayList.size();
    }

   /* @Override
    public int getItemViewType(int position) {
        return (position == rushHourFormArrayList.size()) ? R.layout.item_display_promocode : R.layout.item_rush_hours;
    }
*/
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDate,tvTime;
        private CardView imgBtn;
        private LinearLayout linearLayout;

        public ItemViewHolder(@NonNull final View itemView) {
            super(itemView);

            tvDate=itemView.findViewById(R.id.tv_date);
            tvTime=itemView.findViewById(R.id.tv_time);
            imgBtn=itemView.findViewById(R.id.cardview_add);
            linearLayout=itemView.findViewById(R.id.linear);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //creating a popup menu
                    PopupMenu popup = new PopupMenu(mContext, itemView);
                    //inflating menu from xml resource
                    popup.inflate(R.menu.menu_offer_items);
                    //adding click listener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.offer_display:
                                    positionListener.positionListern(getAdapterPosition());

                                    //handle menu1 click
                                    return true;
                                case R.id.offer_edit:
                                    editListener.getEditListenerPosition(getAdapterPosition());
                                    //handle menu1 click
                                    return true;
                                case R.id.offer_delete:
                                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                    builder
                                            .setTitle("Delete Offer")
                                            .setMessage("Are you sure you want to delete this Offer ?")
                                            .setIcon(R.drawable.ic_action_btn_delete)
                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    deleteRushHoursListener.getDeleteListenerPosition(getAdapterPosition());
                                                }
                                            });
                                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });

                                    AlertDialog alert = builder.create();
                                    alert.show();
                                    return true;
                                case R.id.offer_apply:
                                    Intent intent=new Intent(mContext, ActivitySelectMenu.class);
                                    mContext.startActivity(intent);
                                    //handle menu3 click
                                    return true;
                                default:
                                    return false;
                            }
                        }
                    });
                    //displaying the popup
                    popup.show();

                    //positionListener.positionListern(getAdapterPosition());
                }
            });

        }
    }
}
