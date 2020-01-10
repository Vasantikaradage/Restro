package com.restrosmart.restrohotel.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.restrosmart.restrohotel.Interfaces.MenuListerner;
import com.restrosmart.restrohotel.Model.MenuDisplayForm;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class AdapterMenuCartDetails  extends RecyclerView.Adapter<AdapterMenuCartDetails.ItemViewHolder> {
    private Context mContext;
    private  ArrayList<MenuDisplayForm> menuDisplayFormArrayList;
    private  MenuListerner menuListerner;
     String price;
    private int menu [];
    private  int offerTypaId;



    public AdapterMenuCartDetails(Context mContext, int offerTypeId, ArrayList<MenuDisplayForm> menuDisplayFormArrayList, MenuListerner menuListerner) {
        this.mContext=mContext;
        this.menuDisplayFormArrayList=menuDisplayFormArrayList;
        this.menuListerner=menuListerner;
        this.offerTypaId=offerTypeId;


    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_menu_cart_list_offer, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder itemViewHolder, final int i) {


        itemViewHolder.tvMenuName.setText(menuDisplayFormArrayList.get(i).getMenu_Name());

        if(offerTypaId==2)
        {
            itemViewHolder.tvMenuPrice.setVisibility(View.GONE);
            itemViewHolder.etvMenuOfferPrice.setVisibility(View.GONE);
            itemViewHolder.etOfferQty.setVisibility(View.VISIBLE);
            itemViewHolder.tvMenuPrice.setText((String.valueOf(menuDisplayFormArrayList.get(i).getNon_Ac_Rate())));
            Log.e("VALUE ", menuDisplayFormArrayList.get(i).getOffeerPrice());

            if (!TextUtils.isEmpty(menuDisplayFormArrayList.get(i).getOffeerPrice()) && !menuDisplayFormArrayList.get(i).getOffeerPrice().equals("0")) {
                menuDisplayFormArrayList.get(i).setError(null);
                itemViewHolder.etOfferQty.setText(menuDisplayFormArrayList.get(i).getOffeerPrice());
            }

            if (!TextUtils.isEmpty(menuDisplayFormArrayList.get(i).getError())) {
                itemViewHolder.etOfferQty.setError(menuDisplayFormArrayList.get(i).getError());
            } else {

                itemViewHolder.etOfferQty.setError(null);
            }

        }else  if(offerTypaId==4){
            itemViewHolder.tvMenuPrice.setVisibility(View.VISIBLE);
            itemViewHolder.etvMenuOfferPrice.setVisibility(View.VISIBLE);
            itemViewHolder.etOfferQty.setVisibility(View.GONE);
            itemViewHolder.tvMenuPrice.setText((String.valueOf(menuDisplayFormArrayList.get(i).getNon_Ac_Rate())));
            Log.e("VALUE ", menuDisplayFormArrayList.get(i).getOffeerPrice());

            if (!TextUtils.isEmpty(menuDisplayFormArrayList.get(i).getOffeerPrice()) && !menuDisplayFormArrayList.get(i).getOffeerPrice().equals("0")) {
                menuDisplayFormArrayList.get(i).setError(null);
                itemViewHolder.etvMenuOfferPrice.setText(menuDisplayFormArrayList.get(i).getOffeerPrice());
            }
            if (!TextUtils.isEmpty(menuDisplayFormArrayList.get(i).getError())) {
                itemViewHolder.etvMenuOfferPrice.setError(menuDisplayFormArrayList.get(i).getError());
            } else{
                itemViewHolder.etvMenuOfferPrice.setError(null);
            }


        }
        else if(offerTypaId==1)
        {
            itemViewHolder.tvMenuPrice.setVisibility(View.VISIBLE);
            itemViewHolder.etvMenuOfferPrice.setVisibility(View.GONE);
            itemViewHolder.etOfferQty.setVisibility(View.GONE);
            itemViewHolder.tvMenuPrice.setText( mContext.getResources().getString(R.string.currency) +String.valueOf(menuDisplayFormArrayList.get(i).getNon_Ac_Rate()));
        }
    }

    public ArrayList<MenuDisplayForm> getArrayList() {
        return menuDisplayFormArrayList;
    }

    public  void setArrayList(ArrayList<MenuDisplayForm> pojoArrayList){
        menuDisplayFormArrayList=pojoArrayList;
        notifyDataSetChanged();

    }






    @Override
    public int getItemCount() {
        return menuDisplayFormArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMenuName,tvMenuPrice;
        private EditText  etvMenuOfferPrice;
        private  EditText etOfferQty;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMenuName=itemView.findViewById(R.id.tv_menu_name);
            tvMenuPrice=itemView.findViewById(R.id.tv_menu_price);
            etvMenuOfferPrice=itemView.findViewById(R.id.et_offer_price);
            etOfferQty=itemView.findViewById(R.id.et_offer_qty);

            etvMenuOfferPrice.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    menuDisplayFormArrayList.get(getAdapterPosition()).setOffeerPrice(charSequence.toString());
                   // menuListerner.getMenuListerner(getAdapterPosition(),offerSelectMenuFormArrayList.get(getAdapterPosition()).getOffeerPrice());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            etOfferQty.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    menuDisplayFormArrayList.get(getAdapterPosition()).setOffeerPrice(charSequence.toString());
                    // menuListerner.getMenuListerner(getAdapterPosition(),offerSelectMenuFormArrayList.get(getAdapterPosition()).getOffeerPrice());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });




        }
    }
}
