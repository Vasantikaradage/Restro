package com.restrosmart.restrohotel.SuperAdmin.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.SuperAdmin.Models.TagsForm;

import java.util.ArrayList;

public class RVTagsDisplay extends RecyclerView.Adapter<RVTagsDisplay.ItemViewHolder> {

    private  Context mContext;
    private  ArrayList<TagsForm> tagsFormArrayList;

    public RVTagsDisplay(Context applicationContext, ArrayList<TagsForm> tagsFormArrayList) {
        this.mContext=applicationContext;
        this.tagsFormArrayList=tagsFormArrayList;
    }

    @NonNull
    @Override
    public RVTagsDisplay.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_cuisine_tag_item, viewGroup, false);
       ItemViewHolder vh = new ItemViewHolder(view); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RVTagsDisplay.ItemViewHolder itemViewHolder, int i) {
        itemViewHolder.tvTag.setText(tagsFormArrayList.get(i).getTagName());

    }

    @Override
    public int getItemCount() {
        return tagsFormArrayList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTag;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTag=itemView.findViewById(R.id.tv_cuisine_or_tag);
        }
    }
}
