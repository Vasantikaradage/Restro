package com.restrosmart.restrohotel.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.restrosmart.restrohotel.Model.ScratchCardForm;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class AdapterRVScratchcard extends RecyclerView.Adapter<AdapterRVScratchcard.ItemViewHolder> {

    private  Context mContext;
    private  ArrayList<ScratchCardForm> scratchCardFormArrayList;


    public AdapterRVScratchcard(Context context, ArrayList<ScratchCardForm> scratchCardFormArrayList) {
        this.mContext=context;
        this.scratchCardFormArrayList=scratchCardFormArrayList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView;
        if(i == R.layout.item_scratch_card) {
            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_scratch_card, viewGroup, false);
        }
        else
        {
            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_add_scratch_card, viewGroup, false);

        }
       ItemViewHolder ItemViewHolder=new ItemViewHolder(itemView);
        return ItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        if(i == scratchCardFormArrayList.size()) {
            itemViewHolder.imgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //buttonListerner.getEditListenerPosition(1);
                    //itemViewHolder.linearLayout.setVisibility(View.VISIBLE);
                    // Toast.makeText(mContext, "Button Clicked", Toast.LENGTH_LONG).show();
                }
            });
        }else {
            String count= String.valueOf(scratchCardFormArrayList.get(i).getCount());
          itemViewHolder.tvCount.setText(count);
         itemViewHolder.tvDate.setText(scratchCardFormArrayList.get(i).getDate());


        }

    }

    @Override
    public int getItemCount() {
        return scratchCardFormArrayList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == scratchCardFormArrayList.size()) ? R.layout.item_add_scratch_card : R.layout.item_scratch_card;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tvDate,tvCount;
        private CardView imgBtn;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate=itemView.findViewById(R.id.tv_date);
            tvCount=itemView.findViewById(R.id.tv_count);
            imgBtn=itemView.findViewById(R.id.cardview_add);
        }
    }
}
