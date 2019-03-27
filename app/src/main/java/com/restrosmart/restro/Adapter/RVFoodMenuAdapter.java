package com.restrosmart.restro.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.restrosmart.restro.Model.FoodCartModel;
import com.restrosmart.restro.Model.FoodMenuModel;
import com.restrosmart.restro.R;
import com.restrosmart.restro.Utils.Sessionmanager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RVFoodMenuAdapter extends RecyclerView.Adapter<RVFoodMenuAdapter.ItemViewHolder> {

    private Context mContext;
    private ArrayList<FoodMenuModel> arrayList;
    private Animation left_to_right_anim, right_to_left_anim;
    private Sessionmanager sessionmanager;

    public RVFoodMenuAdapter(Context context, ArrayList<FoodMenuModel> arrayList) {
        this.mContext = context;
        this.arrayList = arrayList;
        sessionmanager = new Sessionmanager(mContext);

        left_to_right_anim = AnimationUtils.loadAnimation(mContext, R.anim.left_to_right_anim);
        right_to_left_anim = AnimationUtils.loadAnimation(mContext, R.anim.right_to_left_anim);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_food_menu_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, int position) {

        holder.tvVegMenuName.setText(arrayList.get(position).getMenuName());
        holder.tvMenuDesc.setText(arrayList.get(position).getMenuDesc());
        holder.tvMenuPrice.setText(mContext.getResources().getString(R.string.Rs) + " " + String.valueOf(arrayList.get(position).getMenuPrice()));

        Picasso.with(mContext)
                .load(arrayList.get(position).getMenuImage())
                .into(holder.ivMenuImg);

        holder.btnAddMenu.setVisibility(View.VISIBLE);
        holder.cardView.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tvVegMenuName, tvMenuDesc, tvMenuPrice;
        private ImageView ivMenuImg;
        private Button btnAddMenu;
        private RatingBar rbMenu;
        private TextView tvMenuQty;
        private CardView cardView;
        private TextView ivAddQty, ivMinusQty;
        //EasyGifView easyGifView;

        ItemViewHolder(View itemView) {
            super(itemView);

            tvVegMenuName = itemView.findViewById(R.id.tvVegMenuName);
            tvMenuDesc = itemView.findViewById(R.id.tvMenuDesc);
            tvMenuPrice = itemView.findViewById(R.id.tvMenuPrice);
            ivMenuImg = itemView.findViewById(R.id.ivMenuImg);
            btnAddMenu = itemView.findViewById(R.id.btnAddMenu);
            rbMenu = itemView.findViewById(R.id.rbMenu);
            tvMenuQty = itemView.findViewById(R.id.tvMenuQty);
            ivAddQty = itemView.findViewById(R.id.ivAddQty);
            ivMinusQty = itemView.findViewById(R.id.ivMinusQty);
            cardView = itemView.findViewById(R.id.cardView);

            rbMenu.setRating(3);

            btnAddMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    FoodMenuModel foodMenuModel = arrayList.get(getAdapterPosition());

                    FoodCartModel foodCartModel = new FoodCartModel();
                    foodCartModel.setMenuId(foodMenuModel.getMenuId());
                    foodCartModel.setMenuName(foodMenuModel.getMenuName());
                    foodCartModel.setMenuImage(foodMenuModel.getMenuImage());
                    foodCartModel.setMenuPrice(foodMenuModel.getMenuPrice());
                    foodCartModel.setMenuQtyPrice(foodMenuModel.getMenuPrice());
                    foodCartModel.setMenuQty(1);
                    sessionmanager.addToFoodCart(mContext, foodCartModel);

                    btnAddMenu.startAnimation(left_to_right_anim);
                    cardView.startAnimation(right_to_left_anim);
                    cardView.setVisibility(View.VISIBLE);
                    btnAddMenu.clearAnimation();
                    //cardView.clearAnimation();
                    btnAddMenu.setVisibility(View.GONE);

                    Intent intent = new Intent("com.restrosmart.restro.addmenu");
                    intent.putExtra("menuname", arrayList.get(getAdapterPosition()).getMenuName());
                    mContext.sendBroadcast(intent);
                    //stopAnimation(btnAddMenu);

                    /*ArrayList<FoodCartModel> arrayList1 = sessionmanager.getAddToFoodCartList(mContext);

                    if (arrayList1 != null) {
                        Iterator<FoodCartModel> modelIterator = arrayList1.iterator();

                        while (modelIterator.hasNext()) {
                            int id = modelIterator.next().getMenuId();
                            if (id == arrayList.get(getAdapterPosition()).getMenuId()) {
                                int menuQty = arrayList1.get(getAdapterPosition()).getMenuQty();
                                tvMenuQty.setText(String.valueOf(menuQty));
                            }
                        }
                    } else {

                    }*/

                    tvMenuQty.setText(String.valueOf(1));
                }
            });

            ivAddQty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    FoodMenuModel foodMenuModel = arrayList.get(getAdapterPosition());
                    sessionmanager.updateQtyFoodCart(mContext, 1, foodMenuModel.getMenuId());

                    tvMenuQty.setText(String.valueOf(Integer.parseInt(tvMenuQty.getText().toString()) + 1));

                    /*ArrayList<FoodCartModel> arrayList1 = sessionmanager.getAddToFoodCartList(mContext);

                    if (arrayList1 != null) {

                        Iterator<FoodCartModel> modelIterator = arrayList1.iterator();

                        while (modelIterator.hasNext()) {
                            int id = modelIterator.next().getMenuId();
                            if (id == arrayList.get(getAdapterPosition()).getMenuId()) {
                                int menuQty = arrayList1.get(getAdapterPosition()).getMenuQty();
                                tvMenuQty.setText(String.valueOf(menuQty));
                            }
                        }
                    } else {
                        tvMenuQty.setText(String.valueOf(1));
                    }*/


                   /* for (int i = 0; i < arrayList1.size(); i++) {
                        Toast.makeText(mContext, String.valueOf(arrayList1.get(i).getMenuQty()), Toast.LENGTH_SHORT).show();
                    }*/


                }
            });

            ivMinusQty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (Integer.parseInt(tvMenuQty.getText().toString()) == 1) {
                        tvMenuQty.setText(String.valueOf(1));
                        cardView.clearAnimation();
                        cardView.setVisibility(View.GONE);
                        btnAddMenu.setVisibility(View.VISIBLE);

                        FoodMenuModel foodMenuModel1 = arrayList.get(getAdapterPosition());
                        sessionmanager.removeMenuFoodCart(mContext, foodMenuModel1.getMenuId());

                        Intent intent = new Intent("com.restrosmart.restro.addmenu");
                        intent.putExtra("menuname", arrayList.get(getAdapterPosition()).getMenuName());
                        mContext.sendBroadcast(intent);
                    } else {
                        FoodMenuModel foodMenuModel = arrayList.get(getAdapterPosition());
                        sessionmanager.updateQtyFoodCart(mContext, 0, foodMenuModel.getMenuId());
                        tvMenuQty.setText(String.valueOf(Integer.parseInt(tvMenuQty.getText().toString()) - 1));
                    }

                    /*ArrayList<FoodCartModel> arrayList1 = sessionmanager.getAddToFoodCartList(mContext);

                    if (arrayList1 != null) {
                        Iterator<FoodCartModel> modelIterator = arrayList1.iterator();

                        while (modelIterator.hasNext()) {

                            if (modelIterator.next().getMenuId() == arrayList.get(getAdapterPosition()).getMenuId()) {
                                int menuQty = arrayList1.get(getAdapterPosition()).getMenuQty();

                            }
                        }
                    } else {
                        tvMenuQty.setText(String.valueOf(1));
                    }*/

                   /* for (int i = 0; i < arrayList1.size(); i++) {
                        Toast.makeText(mContext, String.valueOf(arrayList1.get(i).getMenuQty()), Toast.LENGTH_SHORT).show();
                    }*/


                }
            });
        }

        public void stopAnimation(View v) {
            v.clearAnimation();
            btnAddMenu.setVisibility(View.GONE);

        }
    }

    public void refreshList(ArrayList<FoodMenuModel> refreshList) {
        this.arrayList = refreshList;
        notifyDataSetChanged();
    }
}
