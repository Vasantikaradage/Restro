package com.restrosmart.restro.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.restrosmart.restro.Interfaces.FlavourSelectedListener;
import com.restrosmart.restro.R;
import com.squareup.picasso.Picasso;

public class RVLiquorsAdapter extends RecyclerView.Adapter<RVLiquorsAdapter.ItemViewHolder> {

    private int[] intArray;
    private Context mContext;
    private Animation left_to_right_anim, right_to_left_anim;

    public RVLiquorsAdapter(Context mContext, int[] intArray) {
        this.intArray = intArray;
        this.mContext = mContext;

        left_to_right_anim = AnimationUtils.loadAnimation(mContext, R.anim.left_to_right_anim);
        right_to_left_anim = AnimationUtils.loadAnimation(mContext, R.anim.right_to_left_anim);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_liquor_item, parent, false);
        return new RVLiquorsAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        //Picasso.with(mContext).load(intArray[position]).into(holder.ivImage);

        holder.ivImage.setImageResource(intArray[position]);
    }

    @Override
    public int getItemCount() {
        return intArray.length;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivImage;
        private AlertDialog flavourDialog;

        public ItemViewHolder(View itemView) {
            super(itemView);

            ivImage = itemView.findViewById(R.id.ivImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    flavourDialog();
                }
            });
        }

        private void flavourDialog() {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View dialogView = null;
            if (layoutInflater != null) {
                dialogView = layoutInflater.inflate(R.layout.specific_liquor_dialog, null);
            }
            dialogBuilder.setView(dialogView);

            RecyclerView rvLiquorFlavours = dialogView.findViewById(R.id.rvLiquorFlavours);
            final ImageView ivFullFlavour = dialogView.findViewById(R.id.ivFullFlavour);
            final Button btnAddLiqour = dialogView.findViewById(R.id.btnAddLiqour);
            final CardView cvFlavour = dialogView.findViewById(R.id.cvFlavour);
            final TextView tvMinusQty = dialogView.findViewById(R.id.tvMinusQty);
            final TextView tvLiqourQty = dialogView.findViewById(R.id.tvLiqourQty);
            final TextView tvAddQty = dialogView.findViewById(R.id.tvAddQty);

            rvLiquorFlavours.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            //rvLiquors.setLayoutManager(new GridLayoutManager(getContext(), 1, LinearLayoutManager.HORIZONTAL, false));
            RVLiquorFlavoursAdapter rvLiquorFlavoursAdapter = new RVLiquorFlavoursAdapter(mContext, intArray, new FlavourSelectedListener() {
                @Override
                public void flavourSelected(int adapterPosition) {
                    Picasso.with(mContext).load(intArray[adapterPosition]).into(ivFullFlavour);
                }
            });
            rvLiquorFlavours.setAdapter(rvLiquorFlavoursAdapter);


            flavourDialog = dialogBuilder.create();
            //flavourDialog.setCanceledOnTouchOutside(false);
            //flavourDialog.setCancelable(false);
            flavourDialog.show();


            btnAddLiqour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btnAddLiqour.startAnimation(left_to_right_anim);
                    cvFlavour.startAnimation(right_to_left_anim);
                    cvFlavour.setVisibility(View.VISIBLE);
                    btnAddLiqour.clearAnimation();
                    btnAddLiqour.setVisibility(View.GONE);
                }
            });

            tvAddQty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tvLiqourQty.setText(String.valueOf(Integer.parseInt(tvLiqourQty.getText().toString()) + 1));
                }
            });

            tvMinusQty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Integer.parseInt(tvLiqourQty.getText().toString()) == 1) {
                        tvLiqourQty.setText(String.valueOf(1));
                        cvFlavour.clearAnimation();
                        cvFlavour.setVisibility(View.GONE);
                        btnAddLiqour.setVisibility(View.VISIBLE);
                    } else {
                        tvLiqourQty.setText(String.valueOf(Integer.parseInt(tvLiqourQty.getText().toString()) - 1));
                    }
                }
            });

        }
    }
}
