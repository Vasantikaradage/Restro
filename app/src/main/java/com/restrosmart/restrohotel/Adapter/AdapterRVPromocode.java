package com.restrosmart.restrohotel.Adapter;

import android.content.Context;
import android.content.DialogInterface;
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

import com.restrosmart.restrohotel.Interfaces.DeleteListener;
import com.restrosmart.restrohotel.Interfaces.EditListener;
import com.restrosmart.restrohotel.Interfaces.PositionListener;
import com.restrosmart.restrohotel.Model.PromoCodeForm;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class AdapterRVPromocode extends RecyclerView.Adapter<AdapterRVPromocode.ItemViewHolder> {
    private Context mContext;
    private ArrayList<PromoCodeForm> promoCodeFormArrayList;
    private DeleteListener deletePromoCodeListener;
    private PositionListener positionListener;
    private  EditListener editListener;

    public AdapterRVPromocode(Context context, ArrayList<PromoCodeForm> arrayListPromoCode, EditListener editListener, DeleteListener deleteListener, PositionListener positionListener) {
        this.mContext = context;
        this.promoCodeFormArrayList = arrayListPromoCode;
        this.positionListener = positionListener;
        this.deletePromoCodeListener =deleteListener;
        this.editListener=editListener;

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView;
        //   if(i == R.layout.item_promocode) {
        itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_promocode, viewGroup, false);
       /* }
        else
        {
            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_add_promocode, viewGroup, false);

        }*/
        ItemViewHolder ItemViewHolder = new ItemViewHolder(itemView);
        return ItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder itemViewHolder, int i) {

        /*if(i == promoCodeFormArrayList.size()) {
            itemViewHolder.imgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    buttonListerner.getEditListenerPosition(1);
                    //itemViewHolder.linearLayout.setVisibility(View.VISIBLE);
                   // Toast.makeText(mContext, "Button Clicked", Toast.LENGTH_LONG).show();
                }
            });
        }else {*/
       // itemViewHolder.tvOffer.setText(promoCodeFormArrayList.get(i).getOffer() + " " + promoCodeFormArrayList.get(i).getOfferValue());
       // itemViewHolder.tvDate.setText(promoCodeFormArrayList.get(i).getDate());


        //   }

    }

    @Override
    public int getItemCount() {
        return promoCodeFormArrayList.size();
    }

    /*@Override
    public int getItemViewType(int position) {
        return (position == promoCodeFormArrayList.size()) ? R.layout.item_add_promocode : R.layout.item_promocode;
    }*/


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDate, tvOffer;
        private CardView imgBtn;
        private LinearLayout linearLayout;

        public ItemViewHolder(@NonNull final View itemView) {
            super(itemView);
          //  tvDate = itemView.findViewById(R.id.tv_date);
           // tvOffer = itemView.findViewById(R.id.tv_offer);
            imgBtn = itemView.findViewById(R.id.cardview_add);
            linearLayout = itemView.findViewById(R.id.linear);

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
                                                    deletePromoCodeListener.getDeleteListenerPosition(getAdapterPosition());
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

           /* itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    //creating a popup menu
                    PopupMenu popup = new PopupMenu(mContext, itemView);
                    //inflating menu from xml resource
                    popup.inflate(R.menu.menu_offer_items);
                    //adding click listener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.offer_edit:
                                    //handle menu1 click
                                    return true;
                                case R.id.offer_delete:
                                    //handle menu2 click
                                    return true;
                                case R.id.offer_apply:
                                    //handle menu3 click
                                    return true;
                                default:
                                    return false;
                            }
                        }
                    });
                    //displaying the popup
                    popup.show();
                    return false;


                }
            });*/
        }
    }
}




