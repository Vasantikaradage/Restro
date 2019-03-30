package com.restrosmart.restro.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.restrosmart.restro.Model.FlavourForm;
import com.restrosmart.restro.Model.FlavourUnitForm;
import com.restrosmart.restro.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by SHREE on 28/12/2018.
 */

public class AdapterDisplayFlavour  extends RecyclerView.Adapter<AdapterDisplayFlavour.MyHolder> {
Context context;
ArrayList<FlavourForm>  flavourFormArrayList;
ArrayList<FlavourUnitForm> flavourUnitFormArrayList;

    private  View dialoglayout;
    private  AlertDialog dialog;

    public AdapterDisplayFlavour(Context activityFlavour, ArrayList<FlavourForm> arrayListFlavour,ArrayList<FlavourUnitForm> flavourUnitFormArrayList) {
    this.context=activityFlavour;
    this.flavourFormArrayList=arrayListFlavour;
    this.flavourUnitFormArrayList=flavourUnitFormArrayList;

    }

    @NonNull
    @Override
    public AdapterDisplayFlavour.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_flavour_list, parent, false);

        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDisplayFlavour.MyHolder holder, int position) {

        holder.flavourName.setText(flavourFormArrayList.get(position).getFlavourName());

       Picasso.with(context).load(flavourFormArrayList.get(position).getFlavourImage())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_STORE)
                .into(holder.imageFlavour);
        holder.frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater li = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                dialoglayout = li.inflate(R.layout.dialog_unit_show, null);
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(dialoglayout);
                dialog = builder.create();

                RecyclerView recyclerView=(RecyclerView)dialoglayout.findViewById(R.id.recycler_flavour_unit);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(linearLayoutManager);
                AdapterDisplayFlavourUnit adapterDisplayFlavourUnit = new AdapterDisplayFlavourUnit(context,flavourUnitFormArrayList );
                recyclerView.setAdapter(adapterDisplayFlavourUnit);

                dialog.show();



            }
        });


    }

    @Override
    public int getItemCount() {
        return flavourFormArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView flavourName;
        private ImageView imageFlavour;
        private FrameLayout frameLayout;

        public MyHolder(View itemView) {
            super(itemView);
            flavourName=(TextView)itemView.findViewById(R.id.tx_flavour_name);
            imageFlavour=(ImageView)itemView.findViewById(R.id.circle_image);
            frameLayout=(FrameLayout)itemView.findViewById(R.id.frame_layout);
        }
    }
}
