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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.restrosmart.restrohotel.Admin.ActivitySelectMenu;
import com.restrosmart.restrohotel.Interfaces.DeleteListener;
import com.restrosmart.restrohotel.Interfaces.EditListener;
import com.restrosmart.restrohotel.Interfaces.OfferListerner;

import com.restrosmart.restrohotel.Model.RushHourForm;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class AdapterRVRushHours extends RecyclerView.Adapter<AdapterRVRushHours.ItemViewHolder> {
    private Context mContext;
    private ArrayList<RushHourForm> rushHourFormArrayList;
    private EditListener editListener;
    private OfferListerner rushHoursListerner;
    private DeleteListener deleteRushHoursListener;
    private int offerTypeId;

    public AdapterRVRushHours(Context context, ArrayList<RushHourForm> rushHourPromoCode, int offerTypeId, DeleteListener deleteListener, OfferListerner positionListener, EditListener editListener) {
        this.mContext = context;
        this.rushHourFormArrayList = rushHourPromoCode;
        this.deleteRushHoursListener = deleteListener;
        this.editListener = editListener;
        this.rushHoursListerner = positionListener;
        this.offerTypeId = offerTypeId;

    }

    @NonNull
    @Override
    public AdapterRVRushHours.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView;
        itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_rush_hours, viewGroup, false);
        ItemViewHolder ItemViewHolder = new ItemViewHolder(itemView);
        return ItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterRVRushHours.ItemViewHolder itemViewHolder, final int i) {
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
        itemViewHolder.tvTime.setText(rushHourFormArrayList.get(i).getFromTime() + " - " + rushHourFormArrayList.get(i).getToTime());
        itemViewHolder.tvDate.setText(rushHourFormArrayList.get(i).getFromDate() + " - " + rushHourFormArrayList.get(i).getToDate());
        itemViewHolder.tvOfferName.setText(rushHourFormArrayList.get(i).getOfferName());
        itemViewHolder.tvOfferDesc.setText(rushHourFormArrayList.get(i).getMessage());

        if(rushHourFormArrayList.get(i).getActiveStatus()==1)
        {
            itemViewHolder.imageActive.setVisibility(View.VISIBLE);
            itemViewHolder.imageInActive.setVisibility(View.GONE);
        }
        else
        {
            itemViewHolder.imageInActive.setVisibility(View.VISIBLE);
            itemViewHolder.imageActive.setVisibility(View.GONE);
        }


        itemViewHolder.textViewOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creating a popup menu
                PopupMenu popup = new PopupMenu(mContext, v);
                //inflating menu from xml resource
                popup.inflate(R.menu.menu_offer_items);
                //adding click listener

                Menu popupMenu = popup.getMenu();
                if(rushHourFormArrayList.get(i).getActiveStatus()==1) {
                    popupMenu.findItem(R.id.offer_display).setVisible(true);
                    popupMenu.findItem(R.id.offer_edit).setVisible(false);
                    popupMenu.findItem(R.id.offer_delete).setVisible(false);
                    popupMenu.findItem(R.id.offer_apply).setVisible(false);
                }
                else {
                    popupMenu.findItem(R.id.offer_display).setVisible(true);
                    popupMenu.findItem(R.id.offer_edit).setVisible(true);
                    popupMenu.findItem(R.id.offer_delete).setVisible(true);
                    popupMenu.findItem(R.id.offer_apply).setVisible(true);
                }
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.offer_display:
                                rushHoursListerner.offerDisplay(rushHourFormArrayList.get(i).getArrayListMenu(),i);

                                //handle menu1 click
                                return true;
                            case R.id.offer_edit:
                                editListener.getEditListenerPosition(i);
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
                                                deleteRushHoursListener.getDeleteListenerPosition(i);
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
                                Intent intent = new Intent(mContext, ActivitySelectMenu.class);
                                intent.putExtra("offerTypeId", offerTypeId);
                                intent.putExtra("offerId",rushHourFormArrayList.get(i).getRushHourId());
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


            }
        });

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
        private TextView tvDate, tvTime, tvOfferName, tvOfferDesc,textViewOptions;
        private CardView imgBtn;
        private ImageView imageActive,imageInActive;
        private LinearLayout linearLayout;

        public ItemViewHolder(@NonNull final View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.tv_offer_date);
            tvTime = itemView.findViewById(R.id.tv_offer_time);
            tvOfferName = itemView.findViewById(R.id.tv_offer_name);
            tvOfferDesc = itemView.findViewById(R.id.tv_offer_desc);
            textViewOptions=itemView.findViewById(R.id.textViewOptions);
            imageActive=itemView.findViewById(R.id.img_active);
            imageInActive=itemView.findViewById(R.id.img_inactive);


           /* itemView.setOnClickListener(new View.OnClickListener() {
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
                                    rushHoursListerner.positionListern(getAdapterPosition());

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
                                    Intent intent = new Intent(mContext, ActivitySelectMenu.class);
                                    intent.putExtra("offerTypeId", offerTypeId);
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

                    //rushHoursListerner.positionListern(getAdapterPosition());
                }
            });
*/
        }
    }
}
