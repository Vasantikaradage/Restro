package com.restrosmart.restrohotel.Adapter;

import android.content.Context;

import android.support.annotation.NonNull;

import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.restrosmart.restrohotel.Interfaces.DeleteListener;
import com.restrosmart.restrohotel.Interfaces.EditListener;
import com.restrosmart.restrohotel.Interfaces.PositionListener;
import com.restrosmart.restrohotel.Model.ScratchCardForm;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class AdapterRVScratchcard extends RecyclerView.Adapter<AdapterRVScratchcard.ItemViewHolder> {

    private  Context mContext;
    private  ArrayList<ScratchCardForm> scratchCardFormArrayList;
    private  PositionListener positionListener;
    private  EditListener editListener;
    private  DeleteListener deleteListener;
    private  View view;


    public AdapterRVScratchcard(Context context, ArrayList<ScratchCardForm> scratchCardFormArrayList, PositionListener positionListener, EditListener editListener, DeleteListener deleteListener) {
        this.mContext=context;
        this.scratchCardFormArrayList=scratchCardFormArrayList;
        this.positionListener=positionListener;
        this.editListener=editListener;
        this.deleteListener=deleteListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView;
//        if(i == R.layout.item_rush_hours) {
//            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_rush_hours, viewGroup, false);
//        }
//        else
//        {
        itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_scratch_card, viewGroup, false);

        //   }
        ItemViewHolder ItemViewHolder=new ItemViewHolder(itemView);
        return ItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder itemViewHolder, int i) {
        /*if(i == scratchCardFormArrayList.size()) {
            itemViewHolder.imgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //buttonListerner.getEditListenerPosition(1);
                    //itemViewHolder.linearLayout.setVisibility(View.VISIBLE);
                    // Toast.makeText(mContext, "Button Clicked", Toast.LENGTH_LONG).show();
                }
            });
        }else {*/
            String count= String.valueOf(scratchCardFormArrayList.get(i).getCount());
          itemViewHolder.tvCount.setText(count);
         itemViewHolder.tvDate.setText(scratchCardFormArrayList.get(i).getDate());


         itemViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 //creating a popup menu
                 PopupMenu popup = new PopupMenu(mContext, v);
                 //inflating menu from xml resource
                 MenuInflater inflater = popup.getMenuInflater();
                 inflater.inflate(R.menu.menu_offer_items, popup.getMenu());
                 //adding click listener

                 //displaying the popup
                 popup.show();

             }
         });


      //  }

    }

    @Override
    public int getItemCount() {
        return scratchCardFormArrayList.size();
    }

    /*@Override
    public int getItemViewType(int position) {
        return (position == scratchCardFormArrayList.size()) ? R.layout.item_add_scratch_card : R.layout.item_scratch_card;
    }*/

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tvDate,tvCount;
        private CardView imgBtn;
        private LinearLayout linearLayout;

        public ItemViewHolder(@NonNull final View itemView) {
            super(itemView);
            tvDate=itemView.findViewById(R.id.tv_date);
            tvCount=itemView.findViewById(R.id.tv_count);
            linearLayout=itemView.findViewById(R.id.linear_layout);
           // imgBtn=itemView.findViewById(R.id.cardview_add);
           // view=itemView;
/*

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    //positionListener.positionListern(getAdapterPosition());
                }
            });
*/

        }
    }
}
