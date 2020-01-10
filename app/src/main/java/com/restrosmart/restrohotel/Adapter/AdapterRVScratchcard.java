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
import android.view.MenuInflater;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.restrosmart.restrohotel.Admin.ActivitySelectMenu;
import com.restrosmart.restrohotel.Interfaces.DeleteListener;
import com.restrosmart.restrohotel.Interfaces.EditListener;
import com.restrosmart.restrohotel.Interfaces.OfferListerner;
import com.restrosmart.restrohotel.Interfaces.PositionListener;
import com.restrosmart.restrohotel.Model.ScratchCardForm;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class AdapterRVScratchcard extends RecyclerView.Adapter<AdapterRVScratchcard.ItemViewHolder> {

    private Context mContext;
    private ArrayList<ScratchCardForm> scratchCardFormArrayList;
    private PositionListener positionListener;
    private EditListener editListener;
    private DeleteListener deleteListener;
    private View view;
    private  int pos,offerTypeId;
    private OfferListerner offerListerner;

    public AdapterRVScratchcard(Context context, ArrayList<ScratchCardForm> scratchCardFormArrayList, int offerTypeId, EditListener editListener, OfferListerner offerListerner, DeleteListener deleteListener) {
        this.mContext = context;
        this.scratchCardFormArrayList = scratchCardFormArrayList;
        this.positionListener = positionListener;
        this.editListener = editListener;
        this.deleteListener = deleteListener;
        this.offerTypeId=offerTypeId;
        this.offerListerner=offerListerner;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_scratch_card, viewGroup, false);
        ItemViewHolder ItemViewHolder = new ItemViewHolder(itemView);
        return ItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull  ItemViewHolder itemViewHolder, final int i) {
        itemViewHolder.tvTime.setText(scratchCardFormArrayList.get(i).getFromTime() + " - " + scratchCardFormArrayList.get(i).getToTime());
        itemViewHolder.tvDate.setText(scratchCardFormArrayList.get(i).getFromDate() + " - " + scratchCardFormArrayList.get(i).getToDate());
        itemViewHolder.tvOfferName.setText(scratchCardFormArrayList.get(i).getOfferName());
        itemViewHolder.tvOfferDesc.setText(scratchCardFormArrayList.get(i).getDiscription());
       // itemViewHolder.tvPplWinnerCnt.setText(scratchCardFormArrayList.get(i).getTotalPplCnt());

       // pos=i;

        if(scratchCardFormArrayList.get(i).getStatus()==1)
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

                Menu popupMenu = popup.getMenu();
                if(scratchCardFormArrayList.get(i).getStatus()==1) {
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
                                offerListerner.offerDisplay(scratchCardFormArrayList.get(i).getOfferMenuFormArrayList(),i);

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
                                               deleteListener.getDeleteListenerPosition(i);
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
                                intent.putExtra("offerId",scratchCardFormArrayList.get(i).getOfferId());
                                intent.putExtra("winnerQty",scratchCardFormArrayList.get(i).getWinnerPplCnt());
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


    }

    @Override
    public int getItemCount() {
        return scratchCardFormArrayList.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDate, tvTime, tvOfferName, tvOfferDesc,textViewOptions,tvPplWinnerCnt;
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
          //  tvPplWinnerCnt=itemView.findViewById(R.id.tv_ppl_winner_cnt);
        }
    }
}
