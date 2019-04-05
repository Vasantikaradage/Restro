package com.restrosmart.restrohotel.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.restrosmart.restrohotel.Model.OfferNameForm;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

/**
 * Created by SHREE on 12/11/2018.
 */

public  class DailyOfferListAdapter extends RecyclerView.Adapter<DailyOfferListAdapter.MyHolder> {
Context context;

ArrayList<OfferNameForm> arrayOfferList;

    public DailyOfferListAdapter(Context context, ArrayList<OfferNameForm> arrayOfferList) {
        this.context=context;
        this.arrayOfferList=arrayOfferList;

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.fragment_category_list, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return arrayOfferList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public MyHolder(View itemView) {
            super(itemView);
        }
    }
}
