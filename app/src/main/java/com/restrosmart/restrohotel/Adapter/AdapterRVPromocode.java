package com.restrosmart.restrohotel.Adapter;

import android.content.Context;
import android.content.DialogInterface;
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

import com.restrosmart.restrohotel.Interfaces.ApplyPromoCode;
import com.restrosmart.restrohotel.Interfaces.DeleteListener;
import com.restrosmart.restrohotel.Interfaces.EditListener;
import com.restrosmart.restrohotel.Interfaces.PositionListener;
import com.restrosmart.restrohotel.Model.PromoCodeForm;
import com.restrosmart.restrohotel.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdapterRVPromocode extends RecyclerView.Adapter<AdapterRVPromocode.ItemViewHolder> {
    private Context mContext;
    private ArrayList<PromoCodeForm> promoCodeFormArrayList;
    private DeleteListener deletePromoCodeListener;
    private PositionListener positionListener;
    private  EditListener editListener;
    private  ApplyPromoCode applyPromoCode;

    public AdapterRVPromocode(Context context, ArrayList<PromoCodeForm> arrayListPromoCode, ApplyPromoCode applyPromoCode, EditListener editListener, DeleteListener deleteListener, PositionListener positionListener) {
        this.mContext = context;
        this.promoCodeFormArrayList = arrayListPromoCode;
        this.positionListener = positionListener;
        this.deletePromoCodeListener =deleteListener;
        this.editListener=editListener;
        this.applyPromoCode=applyPromoCode;

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
    public void onBindViewHolder(@NonNull final ItemViewHolder itemViewHolder, final int i) {

        if(promoCodeFormArrayList.get(i).getOfferStatus()==1)
        {
            itemViewHolder.imageActive.setVisibility(View.VISIBLE);
            itemViewHolder.imageInActive.setVisibility(View.GONE);
        }
        else
        {
            itemViewHolder.imageInActive.setVisibility(View.VISIBLE);
            itemViewHolder.imageActive.setVisibility(View.GONE);
        }
        String price=promoCodeFormArrayList.get(i).getOfferPrice();

        /*DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat outputFormat = new SimpleDateFormat("dd MMM");
        String mFromDate=promoCodeFormArrayList.get(i).getFromDate();
        String mToDate=promoCodeFormArrayList.get(i).getToDate();
        Date dateFrom = null;
        Date dateTo=null;
        try {
            dateFrom = inputFormat.parse(mFromDate);
            dateTo=inputFormat.parse(mToDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String strFromDate = outputFormat.format(dateFrom);
        String strToDate = outputFormat.format(dateTo);*/
        itemViewHolder.tvDate.setText(promoCodeFormArrayList.get(i).getFromDate() +" - "+promoCodeFormArrayList.get(i).getToDate());

        itemViewHolder.tvOffer.setText(promoCodeFormArrayList.get(i).getOfferValue() + "" + price);
        itemViewHolder.tvDescription.setText(promoCodeFormArrayList.get(i).getOfferDescription());
        itemViewHolder.tvTime.setText(promoCodeFormArrayList.get(i).getOfferFromTime()+" "+promoCodeFormArrayList.get(i).getOfferToTime());

        itemViewHolder.textViewOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(mContext, view);
                //inflating menu from xml resource
                popup.inflate(R.menu.menu_offer_items);


                Menu popupMenu = popup.getMenu();
                if(promoCodeFormArrayList.get(i).getOfferStatus()==1) {
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
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.offer_display:
                                positionListener.positionListern(i);

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
                                                deletePromoCodeListener.getDeleteListenerPosition(i);
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
                                applyPromoCode.applyPromoCode(i);

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
        private TextView tvDate, tvOffer,tvTime,textViewOptions,tvDescription;
        private CardView imgBtn;
        private LinearLayout linearLayout;
        private ImageView imageActive,imageInActive;

        public ItemViewHolder(@NonNull final View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tv_offer_date);
           tvOffer = itemView.findViewById(R.id.tv_offer_name);
           tvTime=itemView.findViewById(R.id.tv_offer_time);
            imageActive=itemView.findViewById(R.id.img_active);
            imageInActive=itemView.findViewById(R.id.img_inactive);
            textViewOptions=itemView.findViewById(R.id.textViewOptions);
            tvDescription=itemView.findViewById(R.id.tv_offer_desc);

          //  imgBtn = itemView.findViewById(R.id.cardview_add);
          //  linearLayout = itemView.findViewById(R.id.linear);



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




