package com.restrosmart.restrohotel.Admin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.restrosmart.restrohotel.Interfaces.ButtonListerner;
import com.restrosmart.restrohotel.Interfaces.PositionListener;

import com.restrosmart.restrohotel.Model.RushHourForm;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class AdapterRVRushHours extends RecyclerView.Adapter<AdapterRVRushHours.ItemViewHolder> {
    private Context mContext;
    private ArrayList<RushHourForm> rushHourFormArrayList;
    private ButtonListerner buttonListerner;
    private PositionListener positionListener;

    public AdapterRVRushHours(Context context, ArrayList<RushHourForm> rushHourPromoCode, PositionListener positionListener, ButtonListerner select_date) {
        this.mContext=context;
        this.rushHourFormArrayList=rushHourPromoCode;
        this.buttonListerner=select_date;
        this.positionListener=positionListener;

    }

    @NonNull
    @Override
    public AdapterRVRushHours.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView;
        if(i == R.layout.item_rush_hours) {
            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_rush_hours, viewGroup, false);
        }
        else
        {
            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_add_promocode, viewGroup, false);

        }
     ItemViewHolder ItemViewHolder=new ItemViewHolder(itemView);
        return ItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRVRushHours.ItemViewHolder itemViewHolder, int i) {
        if(i == rushHourFormArrayList.size()) {
            itemViewHolder.imgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    buttonListerner.getEditListenerPosition(1);
                    //itemViewHolder.linearLayout.setVisibility(View.VISIBLE);
                    // Toast.makeText(mContext, "Button Clicked", Toast.LENGTH_LONG).show();
                }
            });
        }else {
            itemViewHolder.tvTime.setText(rushHourFormArrayList.get(i).getStartTime()+" : "+rushHourFormArrayList.get(i).getEndTime());


            itemViewHolder.tvDate.setText(rushHourFormArrayList.get(i).getDate());


        }
    }

    @Override
    public int getItemCount() {
        return rushHourFormArrayList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == rushHourFormArrayList.size()) ? R.layout.item_add_promocode : R.layout.item_rush_hours;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDate,tvTime;
        private CardView imgBtn;
        private LinearLayout linearLayout;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDate=itemView.findViewById(R.id.tv_date);
            tvTime=itemView.findViewById(R.id.tv_time);
            imgBtn=itemView.findViewById(R.id.cardview_add);
            linearLayout=itemView.findViewById(R.id.linear);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    positionListener.positionListern(getAdapterPosition());
                }
            });

        }
    }
}
