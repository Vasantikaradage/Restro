package com.restrosmart.restro.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.restrosmart.restro.Model.UserCategory;
import com.restrosmart.restro.R;
import com.restrosmart.restro.User.ActivityHotelMenu;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RVCategoryAdapter extends RecyclerView.Adapter<RVCategoryAdapter.ItemViewHolder> {

    private Context mContext;
    private ArrayList<UserCategory> arrayList;

    public RVCategoryAdapter(Context context, ArrayList<UserCategory> userCategoryArrayList) {
        this.mContext = context;
        this.arrayList = userCategoryArrayList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_user_category_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        Picasso.with(mContext)
                .load(arrayList.get(position).getCategoryImage())
                .into(holder.ivCategory);

        holder.tvCategoryName.setText(arrayList.get(position).getCategoryName());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivCategory;
        private TextView tvCategoryName;
        private View bottomView;

        ItemViewHolder(View itemView) {
            super(itemView);

            ivCategory = itemView.findViewById(R.id.ivCategory);
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            bottomView = itemView.findViewById(R.id.bottomView);

            if (arrayList.size() <= 3) {
                bottomView.setVisibility(View.GONE);
            } else {
                bottomView.setVisibility(View.VISIBLE);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intentVeg = new Intent(mContext, ActivityHotelMenu.class);
                    intentVeg.putExtra("categoryPos", getAdapterPosition());
                    intentVeg.putExtra("categoryId", arrayList.get(getAdapterPosition()).getCategoryId());
                    intentVeg.putExtra("categoryName", arrayList.get(getAdapterPosition()).getCategoryName());
                    intentVeg.putParcelableArrayListExtra("arrayList", arrayList);
                    mContext.startActivity(intentVeg);
                }
            });
        }
    }
}
