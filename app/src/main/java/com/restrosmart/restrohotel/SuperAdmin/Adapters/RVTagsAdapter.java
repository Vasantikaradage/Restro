package com.restrosmart.restrohotel.SuperAdmin.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.SuperAdmin.Interfaces.TagListener;
import com.restrosmart.restrohotel.SuperAdmin.Models.TagsForm;

import java.util.ArrayList;

public class RVTagsAdapter extends RecyclerView.Adapter<RVTagsAdapter.MyHolder> {
    private Context mContext;
    private ArrayList<TagsForm> tagsFormArrayList, tagsFormArrayListEdit;
    private TagListener tagListener;


    public RVTagsAdapter(Context context, ArrayList<TagsForm> tagsFormArrayList, ArrayList<TagsForm> tagsFormArrayListEdit, TagListener tagListener) {
        this.mContext = context;
        this.tagsFormArrayList = tagsFormArrayList;
        this.tagListener = tagListener;
        this.tagsFormArrayListEdit = tagsFormArrayListEdit;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cuisine_detail_list, viewGroup, false);
        MyHolder vh = new MyHolder(view); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, final int i) {
        myHolder.tvTagName.setText(tagsFormArrayList.get(i).getTagName());

        if (tagsFormArrayListEdit == null || tagsFormArrayListEdit.size() == 0) {
            myHolder.checkBox.setChecked(tagsFormArrayList.get(i).isSelected());
        } else {
            for (int position = 0; position < tagsFormArrayListEdit.size(); position++) {
                if (tagsFormArrayListEdit.get(position).getTagId() == tagsFormArrayList.get(i).getTagId()) {
                    myHolder.checkBox.setChecked(true);
                    break;

                } else {
                    //  ((ItemViewHolder) viewHolder).cbSelect.setChecked(arrayListToppings.get(i - 1).isSelected());
                    myHolder.checkBox.setChecked(tagsFormArrayList.get(i).isSelected());

                }
            }
        }


    }

    @Override
    public int getItemCount() {
        return tagsFormArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox;
        private TextView tvTagName;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox_cuisine);
            tvTagName = itemView.findViewById(R.id.tv_cuisine);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        tagsFormArrayList.get(getAdapterPosition()).setSelected(true);
                        tagListener.getTagListenerPosition(tagsFormArrayList);
                    } else {
                        tagsFormArrayList.get(getAdapterPosition()).setSelected(false);
                        tagListener.getTagListenerPosition(tagsFormArrayList);
                    }

                }
            });
        }
    }
}
