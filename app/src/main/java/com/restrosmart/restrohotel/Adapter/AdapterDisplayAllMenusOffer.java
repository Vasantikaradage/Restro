package com.restrosmart.restrohotel.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.restrosmart.restrohotel.Model.MenuDisplayForm;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.Utils.Sessionmanager;

import java.util.ArrayList;

public class AdapterDisplayAllMenusOffer extends RecyclerView.Adapter<AdapterDisplayAllMenusOffer.MyHolder> {
    private Context mContext;
    private ArrayList<MenuDisplayForm> menuDisplayFormArrayList;
    ArrayList<MenuDisplayForm> menuDisplayFormsarray = new ArrayList<>();
    private Sessionmanager sessionmanager;
    private int winnerQty, buyQty, offerTypeId, getQty;
    private int count = 0;
    private String flvName;


    public AdapterDisplayAllMenusOffer(Context context, ArrayList<MenuDisplayForm> arrayListMenu, int winnerQty, int buyQty, int getQty, int offerTypeId, String category_name) {
        this.mContext = context;
        this.menuDisplayFormArrayList = arrayListMenu;
        this.winnerQty = winnerQty;
        this.buyQty = buyQty;
        this.getQty = getQty;
        this.offerTypeId = offerTypeId;
        sessionmanager = new Sessionmanager(mContext);
        this.flvName = category_name;
    }

    @NonNull
    @Override
    public AdapterDisplayAllMenusOffer.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_menu_itemlist_offer, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDisplayAllMenusOffer.MyHolder myHolder, int i) {
        myHolder.tvMenu.setText(menuDisplayFormArrayList.get(i).getMenu_Name());
        String price = String.valueOf(menuDisplayFormArrayList.get(i).getNon_Ac_Rate());
        myHolder.tvPrice.setText("\u20B9 " + price);
    }

    @Override
    public int getItemCount() {
        return menuDisplayFormArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView tvMenu;
        private CheckBox checkBox;
        private TextView tvPrice;


        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvMenu = itemView.findViewById(R.id.tv_menu_name);
            checkBox = itemView.findViewById(R.id.checkbox);
            tvPrice = itemView.findViewById(R.id.tv_menu_price);


            if (offerTypeId == 2) {
                if ((count < winnerQty) || (count == winnerQty)) {
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                sessionmanager.addToMenuCart(mContext, menuDisplayFormArrayList.get(getAdapterPosition()));
                                count++;

                            } else {
                                sessionmanager.removeMenuCart(mContext, Integer.parseInt(menuDisplayFormArrayList.get(getAdapterPosition()).getMenu_Id()));
                                count--;
                            }
                        }


                    });
                } else
                    Toast.makeText(mContext, "your winner count is less that selected menu", Toast.LENGTH_SHORT).show();
            }  else if ((offerTypeId == 1)) {

                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {

                            sessionmanager.addToMenuCart(mContext, menuDisplayFormArrayList.get(getAdapterPosition()));
                            count++;
                        } else {
                            sessionmanager.removeMenuCart(mContext, Integer.parseInt(menuDisplayFormArrayList.get(getAdapterPosition()).getMenu_Id()));
                            count--;
                        }
                    }
                });
            } else if (offerTypeId == 4) {
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            sessionmanager.addToMenuCart(mContext, menuDisplayFormArrayList.get(getAdapterPosition()));
                        } else {
                            sessionmanager.removeMenuCart(mContext, Integer.parseInt(menuDisplayFormArrayList.get(getAdapterPosition()).getMenu_Id()));
                        }
                    }
                });
            }
        }
    }
}

