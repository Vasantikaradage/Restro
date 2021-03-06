package com.restrosmart.restrohotel.Captain.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.restrosmart.restrohotel.Captain.Models.LiquorBrandsModel;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class RVLiquorBrandsAdapter extends RecyclerView.Adapter<RVLiquorBrandsAdapter.ItemViewHolder> {

    private Context mContext;
    private int mCategoryId;
    private ArrayList<LiquorBrandsModel> arrayList;

    public RVLiquorBrandsAdapter(Context context, int categoryId, ArrayList<LiquorBrandsModel> arrayList) {
        this.mContext = context;
        this.mCategoryId = categoryId;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_liquor_brand_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        LiquorBrandsModel liquorBrandsModel = arrayList.get(position);

        holder.tvBrandName.setText(liquorBrandsModel.getBrandName());

        holder.rvLiquors.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        //rvLiquors.setLayoutManager(new GridLayoutManager(getContext(), 1, LinearLayoutManager.HORIZONTAL, false));
        RVLiquorsAdapter rvLiquorsAdapter = new RVLiquorsAdapter(mContext, mCategoryId, liquorBrandsModel.getArrayList());
        holder.rvLiquors.setAdapter(rvLiquorsAdapter);
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

        private RecyclerView rvLiquors;
        private TextView tvBrandName;

        ItemViewHolder(View itemView) {
            super(itemView);

            tvBrandName = itemView.findViewById(R.id.tvBrandName);
            rvLiquors = itemView.findViewById(R.id.rvLiquors);
        }
    }
}
