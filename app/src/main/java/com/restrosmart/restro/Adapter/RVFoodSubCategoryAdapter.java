package com.restrosmart.restro.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.restrosmart.restro.Interfaces.CategoryChangedListener;
import com.restrosmart.restro.Model.FoodSubMenuModel;
import com.restrosmart.restro.R;

import java.util.ArrayList;

public class RVFoodSubCategoryAdapter extends RecyclerView.Adapter<RVFoodSubCategoryAdapter.ItemViewHolder> {

    private Context mContext;
    private ArrayList<FoodSubMenuModel> arrayList;
    private CategoryChangedListener mCategoryChangedListener;
    private int selectedPosition = 0;

    public RVFoodSubCategoryAdapter(Context context, ArrayList<FoodSubMenuModel> arrayList, CategoryChangedListener categoryChangedListener) {
        this.mContext = context;
        this.arrayList = arrayList;
        this.mCategoryChangedListener = categoryChangedListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_sub_category_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.tvSubCategoryName.setText(arrayList.get(position).getSubmenuName());

        if (selectedPosition == position) {
            holder.llSubCategory.setBackground(mContext.getResources().getDrawable(R.drawable.tab_1));
            holder.tvSubCategoryName.setTextColor(mContext.getResources().getColor(R.color.colorDarkGray));
            holder.tvSubCategoryName.setSelected(true);
        } else {
            holder.llSubCategory.setBackground(mContext.getResources().getDrawable(R.drawable.tab_2));
            holder.tvSubCategoryName.setTextColor(mContext.getResources().getColor(R.color.white));
            holder.tvSubCategoryName.setSelected(true);
        }
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

        private TextView tvSubCategoryName;
        private LinearLayout llSubCategory;

        ItemViewHolder(View itemView) {
            super(itemView);

            llSubCategory = itemView.findViewById(R.id.llSubCategory);
            tvSubCategoryName = itemView.findViewById(R.id.tvSubCategoryName);

            llSubCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedPosition = getAdapterPosition();
                    notifyDataSetChanged();
                    mCategoryChangedListener.categoryChanged(selectedPosition);
                }
            });
        }
    }
}
