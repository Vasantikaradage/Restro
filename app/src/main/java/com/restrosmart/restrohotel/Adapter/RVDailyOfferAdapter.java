package com.restrosmart.restrohotel.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.restrosmart.restrohotel.Interfaces.DailyOfferDisplayListerner;
import com.restrosmart.restrohotel.Interfaces.DeleteListener;
import com.restrosmart.restrohotel.Interfaces.EditListener;
import com.restrosmart.restrohotel.Interfaces.PositionListener;
import com.restrosmart.restrohotel.Model.DailyOfferForm;
import com.restrosmart.restrohotel.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RVDailyOfferAdapter  extends RecyclerView.Adapter<RVDailyOfferAdapter.ItemViewHolder> {
    private  Context mContext;
    private  ArrayList<DailyOfferForm> dailyOfferFormArrayList;
    private DeleteListener deleteDailyOfferListerner;
    private DailyOfferDisplayListerner offerDisplayListerner;
    private  PositionListener positionListener;
    private EditListener dailyeOfferEditListener;

    public RVDailyOfferAdapter(Context context, EditListener editListener, ArrayList<DailyOfferForm> dailyOfferFormArrayList, PositionListener positionListener, DeleteListener deleteListener, DailyOfferDisplayListerner offerListerner) {
        this.mContext=context;
        this.dailyOfferFormArrayList=dailyOfferFormArrayList;
        this.deleteDailyOfferListerner=deleteListener;
        this.offerDisplayListerner=offerListerner;
        this.positionListener=positionListener;
        this.dailyeOfferEditListener=editListener;
    }

    @NonNull
    @Override
    public RVDailyOfferAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_daily_offer_adapter, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVDailyOfferAdapter.ItemViewHolder itemViewHolder, int i) {
        Picasso.with(mContext).load(dailyOfferFormArrayList.get(i).getOfferImg()).resize(500, 500).into(itemViewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return dailyOfferFormArrayList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public ItemViewHolder(@NonNull final View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
                                    offerDisplayListerner.dailyOfferDisplay(dailyOfferFormArrayList.get(getAdapterPosition()).getOfferMenuFormArrayList(),dailyOfferFormArrayList.get(getAdapterPosition()).getOfferSubMenuArrayList(),getAdapterPosition()) ;

                                    //handle menu1 click
                                    return true;
                                case R.id.offer_edit:
                                    dailyeOfferEditListener.getEditListenerPosition(getAdapterPosition());
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
                                                    deleteDailyOfferListerner.getDeleteListenerPosition(getAdapterPosition());
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
                                    positionListener.positionListern(getAdapterPosition());
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
    }
}
