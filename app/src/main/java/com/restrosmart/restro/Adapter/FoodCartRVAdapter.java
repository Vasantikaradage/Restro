package com.restrosmart.restro.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.restrosmart.restro.Interfaces.AddRemoveItemCartListener;
import com.restrosmart.restro.Model.FoodCartModel;
import com.restrosmart.restro.R;
import com.restrosmart.restro.Utils.Sessionmanager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FoodCartRVAdapter extends RecyclerView.Adapter<FoodCartRVAdapter.ItemViewHolder> {

    private Context mContext;
    private ArrayList<FoodCartModel> arrayList;
    private AlertDialog alertDialog;
    private Sessionmanager sessionmanager;
    private AddRemoveItemCartListener mAddRemoveItemCartListener;

    public FoodCartRVAdapter(Context context, ArrayList<FoodCartModel> cartRVModelArrayList, AddRemoveItemCartListener addRemoveItemCartListener) {
        this.mContext = context;
        this.arrayList = cartRVModelArrayList;
        this.mAddRemoveItemCartListener = addRemoveItemCartListener;
        sessionmanager = new Sessionmanager(mContext);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartmenu_list_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.tvCartMenuName.setText(arrayList.get(position).getMenuName());
        holder.tvFoodCartMenuPrice.setText(mContext.getResources().getString(R.string.Rs) + " " + String.valueOf(arrayList.get(position).getMenuPrice()));
        holder.tvFoodMenuQty.setText(String.valueOf(arrayList.get(position).getMenuQty()));
        holder.tvQtyAmount.setText(mContext.getResources().getString(R.string.Rs) + " " + String.valueOf(arrayList.get(position).getMenuQtyPrice()));

        if (!arrayList.get(position).getMenuImage().isEmpty()) {
            Picasso.with(mContext)
                    .load(arrayList.get(position).getMenuImage())
                    .into(holder.ivMenuImg);

            /*.memoryPolicy(MemoryPolicy.NO_CACHE)
             .networkPolicy(NetworkPolicy.NO_CACHE)*/
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvCartMenuName, tvFoodCartMenuPrice, tvFoodMinusQty, tvFoodMenuQty, tvFoodAddQty, tvQtyAmount;
        private ImageView ivMenuImg;

        ItemViewHolder(View itemView) {
            super(itemView);

            //ivCancel = itemView.findViewById(R.id.ivCancel);
            tvCartMenuName = itemView.findViewById(R.id.tvCartMenuName);
            tvFoodCartMenuPrice = itemView.findViewById(R.id.tvFoodCartMenuPrice);
            tvFoodMinusQty = itemView.findViewById(R.id.tvFoodMinusQty);
            tvFoodMenuQty = itemView.findViewById(R.id.tvFoodMenuQty);
            tvFoodAddQty = itemView.findViewById(R.id.tvFoodAddQty);
            tvQtyAmount = itemView.findViewById(R.id.tvQtyAmount);
            ivMenuImg = itemView.findViewById(R.id.ivMenuImg);

            /*ivCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });*/

            tvFoodMinusQty.setOnClickListener(this);
            tvFoodAddQty.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tvFoodMinusQty:
                    if (Integer.parseInt(tvFoodMenuQty.getText().toString()) == 1) {
                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
                        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View dialogView = null;
                        if (layoutInflater != null) {
                            dialogView = layoutInflater.inflate(R.layout.cancel_menu_dialog, null);
                        }
                        dialogBuilder.setView(dialogView);

                        alertDialog = dialogBuilder.create();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();

                        Button btnRemoveYes = dialogView.findViewById(R.id.btnRemoveYes);
                        Button btnRemoveNo = dialogView.findViewById(R.id.btnRemoveNo);

                        btnRemoveYes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                sessionmanager.removeMenuFoodCart(mContext, arrayList.get(getAdapterPosition()).getMenuId());
                                qtyRefreshList();
                                mAddRemoveItemCartListener.addRemovedItem();
                                alertDialog.dismiss();
                            }
                        });

                        btnRemoveNo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog.dismiss();
                            }
                        });
                    } else {
                        FoodCartModel foodCartModel = arrayList.get(getAdapterPosition());
                        sessionmanager.updateQtyFoodCart(mContext, 0, foodCartModel.getMenuId());
                        tvFoodMenuQty.setText(String.valueOf(Integer.parseInt(tvFoodMenuQty.getText().toString()) - 1));
                        qtyRefreshList();
                        mAddRemoveItemCartListener.addRemovedItem();

                        //tvQtyAmount.setText(String.valueOf((Integer.parseInt(tvFoodMenuQty.getText().toString()) - 1) * foodCartModel.getMenuPrice()));
                    }

                    break;

                case R.id.tvFoodAddQty:
                    FoodCartModel foodCartModel = arrayList.get(getAdapterPosition());
                    sessionmanager.updateQtyFoodCart(mContext, 1, foodCartModel.getMenuId());
                    tvFoodMenuQty.setText(String.valueOf(Integer.parseInt(tvFoodMenuQty.getText().toString()) + 1));
                    qtyRefreshList();
                    mAddRemoveItemCartListener.addRemovedItem();

                    //tvQtyAmount.setText(String.valueOf((Integer.parseInt(tvFoodMenuQty.getText().toString()) + 1) * foodCartModel.getMenuPrice()));
                    break;
            }
        }
    }

    public void qtyRefreshList() {
        this.arrayList = sessionmanager.getAddToFoodCartList(mContext);
        notifyDataSetChanged();
    }
}
